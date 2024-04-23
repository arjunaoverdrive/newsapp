package org.arjunaoverdrive.newsapp.web.aop;

import lombok.RequiredArgsConstructor;
import org.arjunaoverdrive.newsapp.exception.UpdateStateException;
import org.arjunaoverdrive.newsapp.service.AuthorableService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Aspect
@Component
@RequiredArgsConstructor
public class StillCanEditAspect {

    private final List<AuthorableService> services;

    @Pointcut(value = "@annotation(StillCanEdit)")
    public void canStillEditMarkedMethods() {
    }

    @Before(value = "canStillEditMarkedMethods()")
    public void canStillEditAdvice(JoinPoint joinPoint) {
        Class<?> aClass = joinPoint.getTarget().getClass();

        AuthorableService service =
                services.stream()
                        .filter(s -> s.getType().equals(aClass))
                        .findFirst().orElseThrow();

        Long id = (Long) joinPoint.getArgs()[0];
        Instant createdAt = service.getCreatedAtById(id);

        Duration duration = Duration.between(createdAt, Instant.now());
        if (duration.toDays() > 1) {
            throw new UpdateStateException(
                    "Cannot update entity: too much time passed since creation."
            );

        }
    }
}
