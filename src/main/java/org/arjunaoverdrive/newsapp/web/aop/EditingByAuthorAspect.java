package org.arjunaoverdrive.newsapp.web.aop;

import lombok.RequiredArgsConstructor;
import org.arjunaoverdrive.newsapp.exception.CannotEditEntityException;
import org.arjunaoverdrive.newsapp.model.AppUser;
import org.arjunaoverdrive.newsapp.security.AppUserDetails;
import org.arjunaoverdrive.newsapp.service.AuthorableService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;

@Aspect
@Component
@RequiredArgsConstructor
public class EditingByAuthorAspect {

    private final List<AuthorableService> services;


    @Before("@annotation(EditableByAuthor)")
    public void canEditEntityAdvice(JoinPoint joinPoint) {
        Class<?> aClass = joinPoint.getTarget().getClass();
        AuthorableService service =
                services.stream().filter(s -> s.getType().equals(aClass)).findFirst().orElseThrow();

        Long id = (Long) joinPoint.getArgs()[0];
        AppUser author = service.getAuthor(id);

        AppUserDetails currentUser = getCurrentUser();

        if (!author.getId().equals(currentUser.getId())) {
            throw new CannotEditEntityException(
                    MessageFormat.format("Cannot edit {0} object created by another user!",
                            service.getType())
            );
        }
    }

    private AppUserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return (AppUserDetails) authentication.getPrincipal();
    }

}
