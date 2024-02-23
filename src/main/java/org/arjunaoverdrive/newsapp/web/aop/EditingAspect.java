package org.arjunaoverdrive.newsapp.web.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arjunaoverdrive.newsapp.exception.CannotEditEntityException;
import org.arjunaoverdrive.newsapp.model.AppUser;
import org.arjunaoverdrive.newsapp.model.Authorable;
import org.arjunaoverdrive.newsapp.service.user.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.text.MessageFormat;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class EditingAspect {

    private final UserService userService;

    @Before("@annotation(EditableByAuthor)")
    public void canEditEntity(JoinPoint joinPoint) {
        Authorable authorable = (Authorable) joinPoint.getArgs()[0];
        AppUser author = authorable.getAuthor();
        AppUser currentUser = getCurrentUser();

        if (!author.equals(currentUser)) {
            throw new CannotEditEntityException(
                    MessageFormat.format("Cannot edit {0} object created by another user!", authorable.getClassName())
            );
        }
    }

    private AppUser getCurrentUser() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        Long currentUserId = Long.valueOf(request.getParameter("user"));
        AppUser currentUser = userService.findUserById(currentUserId);
        return currentUser;
    }

}
