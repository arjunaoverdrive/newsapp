package org.arjunaoverdrive.newsapp.web.aop;

import lombok.RequiredArgsConstructor;
import org.arjunaoverdrive.newsapp.exception.UpdateStateException;
import org.arjunaoverdrive.newsapp.model.Authorable;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;

@Aspect
@Component
@RequiredArgsConstructor
public class StillCanEditAspect {

    @Before(value = "@annotation(StillCanEdit)")
    public void ifStillCanEdit(JoinPoint joinPoint){
        Authorable authorable = (Authorable) joinPoint.getArgs()[0];
        Duration duration = Duration.between(authorable.getCreatedAt(), Instant.now());
        if(duration.toDays() > 1){
            throw new UpdateStateException(
                    MessageFormat.format("Cannot update {0}: too much time passed since creation.", authorable.getClassName()));
        }
    }
}
