package org.arjunaoverdrive.newsapp.web.aop;

import lombok.RequiredArgsConstructor;
import org.arjunaoverdrive.newsapp.model.AppUser;
import org.arjunaoverdrive.newsapp.model.RoleType;
import org.arjunaoverdrive.newsapp.security.AppUserDetails;
import org.arjunaoverdrive.newsapp.service.user.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class CanAccessOtherUsersAspect {

    private final UserService userService;

    @Before(value = "@annotation(CanAccessOtherUsers)")
    public void canAccessOtherUsers(JoinPoint joinPoint) throws IllegalAccessException {

        Long targetUserId = (Long) joinPoint.getArgs()[0];
        AppUser user = userService.findUserById(targetUserId);

        AppUserDetails appUserDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser currentUser = userService.findUserById(appUserDetails.getId());

        if (!user.equals(currentUser) && !hasAtLeastModeratorRole(currentUser)) {
            throw new IllegalAccessException("Cannot access another user account: not enough permissions!");
        }
    }

    private boolean hasAtLeastModeratorRole(AppUser currentUser) {
        return currentUser.getRoles().contains(RoleType.ROLE_ADMIN)
                ||
                currentUser.getRoles().contains(RoleType.ROLE_MODERATOR);
    }
}
