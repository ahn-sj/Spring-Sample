package hello.jdbc.connection;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

@Slf4j
class DBConnectionUtilTest {

    @Test
    public void connection() throws Exception {
        Connection con = DBConnectionUtil.getConnection();
        Assertions.assertThat(con).isNotNull();
    }
}