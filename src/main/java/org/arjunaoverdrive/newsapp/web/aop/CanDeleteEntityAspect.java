package org.arjunaoverdrive.newsapp.web.aop;

import lombok.RequiredArgsConstructor;
import org.arjunaoverdrive.newsapp.model.AppUser;
import org.arjunaoverdrive.newsapp.model.RoleType;
import org.arjunaoverdrive.newsapp.security.AppUserDetails;
import org.arjunaoverdrive.newsapp.service.AuthorableService;
import org.arjunaoverdrive.newsapp.service.user.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
@RequiredArgsConstructor
public class CanDeleteEntityAspect {

    private final UserService userService;
    private final List<? extends AuthorableService> services;

    @Pointcut(value = "@annotation(CanDeleteEntity)")
    public void canDeleteEntityMethods() {
    }

    @Before("canDeleteEntityMethods()")
    public void canDeleteEntityAdvice(JoinPoint joinPoint) throws IllegalAccessException {
        Class<?> aClass = joinPoint.getTarget().getClass();
        AuthorableService service =
                services.stream()
                        .filter(s -> s.getType().equals(aClass))
                        .findFirst().orElseThrow();

        Long id = (Long) joinPoint.getArgs()[0];
        AppUser author = service.getAuthor(id);

        AppUserDetails principal = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser currentUser = userService.findUserById(principal.getId());

        if (!author.equals(currentUser) && !hasAtLeastModeratorRole(currentUser)) {
            throw new IllegalAccessException("Cannot delete entity created by another user!");
        }
    }

    private boolean hasAtLeastModeratorRole(AppUser currentUser) {
        return currentUser.getRoles().contains(RoleType.ROLE_ADMIN)
                ||
                currentUser.getRoles().contains(RoleType.ROLE_MODERATOR);
    }

}
