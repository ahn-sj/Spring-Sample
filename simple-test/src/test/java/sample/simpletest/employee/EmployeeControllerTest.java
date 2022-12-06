package sample.simpletest.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    EmployeeService employeeService;

    @Test
    @DisplayName("home")
    public void home() throws Exception {
        // given
        mockMvc.perform(get("/api/home"))
                .andExpect(content().string("Home"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("findOne")
    public void findOne() throws Exception {
        given(employeeService.findOne(123L)).willReturn(createEmployee("손오공"));

        // given
        mockMvc.perform(get("/api/" + 123))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId").exists())
                .andExpect(jsonPath("$.nickname").exists())
                .andExpect(jsonPath("$.isEnter").exists());

        verify(employeeService).findOne(123L);
    }

    @Test
    @DisplayName("EmployeeController.join()")
    public void join() throws Exception {
//        String requestJson = "{\"nickname\":\"심슨\"}";
        String requestString = "심슨";

        given(employeeService.enterCompany(requestString)).willReturn(createEmployee(requestString));

//        String content = objectMapper.writeValueAsString(new Employee(111L, "QWE123", "심슨", true));

        mockMvc.perform(post("/api/join")
                        .content(requestString)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.employeeId").exists())
                .andExpect(jsonPath("$.nickname").exists())
                .andExpect(jsonPath("$.isEnter").exists())
                .andDo(print());

        verify(employeeService).enterCompany(requestString);
    }

    @Test
    @DisplayName("checkOut")
    public void checkOut() throws Exception {
        // given
        String requestString = "심슨";
        given(employeeService.findOne(123L)).willReturn(createEmployee(requestString)); // true

        // given
        mockMvc.perform(get("/api/check/" + 0)) // false
                .andExpect(status().isOk())
                .andExpect(content().string("false"))
                .andDo(print());
    }

    @Test
    @DisplayName("checkIn")
    public void checkIn() throws Exception {
        // given
        String requestString = "심슨";
        given(employeeService.findOne(123L)).willReturn(createEmployee(requestString)); // true
        given(employeeService.workOnOff(123L)).willReturn(true); // true

        // given
        mockMvc.perform(get("/api/check/" + 123))
                .andExpect(status().isOk())
                .andExpect(content().string("true"))
                .andDo(print());
    }

    private Employee createEmployee(String nickname) {
        return new Employee(111L, "QQQ111", nickname, true);
    }

}