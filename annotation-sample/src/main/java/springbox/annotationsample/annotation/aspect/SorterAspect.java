package springbox.annotationsample.annotation.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import springbox.annotationsample.annotation.Sorter;

import java.util.Collections;
import java.util.List;

@Aspect
@Component
@Slf4j
public class SorterAspect {

    @Around("@annotation(springbox.annotationsample.annotation.Sorter) && @annotation(annotation)")
    public Object parameterAppender(ProceedingJoinPoint joinPoint, Sorter annotation) throws Throwable {
        Sorter sorter = ((MethodSignature) joinPoint.getSignature())
                .getMethod()
                .getAnnotation(Sorter.class);
        log.info("sorter.order = {}", sorter.order());

        Object proceed = null;
        try {
            proceed = joinPoint.proceed();
        } catch (Throwable e) {
            log.info(e.getMessage());
        }

        if(proceed instanceof List) {
            if (sorter.order().equals("ASC")) {
                Collections.sort((List) proceed);
            } else if (sorter.order().equals("DESC")) {
                Collections.sort((List) proceed, Collections.reverseOrder());
            }
        }
        return proceed;
    }
}
