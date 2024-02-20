package org.arjunaoverdrive.newsapp.service.user.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arjunaoverdrive.newsapp.dao.UserRepository;
import org.arjunaoverdrive.newsapp.exception.CannotSaveEntityException;
import org.arjunaoverdrive.newsapp.exception.UserNotFoundException;
import org.arjunaoverdrive.newsapp.model.AppUser;
import org.arjunaoverdrive.newsapp.service.user.UserService;
import org.arjunaoverdrive.newsapp.utils.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<AppUser> findAll(Pageable pageable) {
        log.debug("Getting all users.");
        return userRepository.findAll(pageable).getContent();
    }

    @Override
    public AppUser create(AppUser appUser) {
        log.debug("Saving user {}", appUser);
        AppUser user;
        try {
            user = userRepository.save(appUser);
        }catch (Exception e){
            throw new CannotSaveEntityException(e.getMessage());
        }
        return user;
    }

    @Override
    public AppUser findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(
                        MessageFormat.format("User with id {0} not found!", id))
        );
    }

    @Override
    public AppUser update(AppUser appUser) {
        log.debug("Updating user {}", appUser);
        AppUser fromDb = findUserById(appUser.getId());
        BeanUtils.copyNonNullProperties(appUser, fromDb);
        log.debug("Updated user {}", appUser);
        try{
            appUser = userRepository.save(appUser);
        }catch (Exception e){
            throw new CannotSaveEntityException(e.getMessage());
        }
        return appUser;
    }

    @Override
    public void deleteUserById(Long id) {
        AppUser toDelete = findUserById(id);
        log.debug("Deleting user {}", toDelete);
        userRepository.delete(toDelete);
    }
}
