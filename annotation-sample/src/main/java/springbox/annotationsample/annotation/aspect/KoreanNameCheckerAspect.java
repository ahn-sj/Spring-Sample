package springbox.annotationsample.annotation.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import springbox.annotationsample.dto.MemberUpdateRequest;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

@Aspect
@Component
@Slf4j
public class KoreanNameCheckerAspect {

    private final String pattern = "^[가-힣]*$";

    @Before("@annotation(springbox.annotationsample.annotation.KoreanNameChecker)")
    public void hasOnlyEnglishName(JoinPoint joinPoint) {
        Object[] parameterValues = joinPoint.getArgs();

        for (Object obj : parameterValues) {
            if(obj instanceof MemberUpdateRequest) {
                MemberUpdateRequest memberRequest = (MemberUpdateRequest) obj;
                String updateName = memberRequest.getName();
                log.info("parameterValue [name] is {}", updateName);

                if(!Pattern.matches(pattern, updateName)) {
                    throw new IllegalArgumentException("한국 이름만 가능합니다.");
                }
            }
        }

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        for (int i = 0; i < method.getParameters().length; i++) {
            String parameterName = method.getParameters()[i].getName();
            if (parameterName.equals("id")) {
                log.info("parameterValue [id] is {}", parameterValues[i]);
            }
        }
    }

}
