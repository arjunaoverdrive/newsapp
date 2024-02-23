package org.arjunaoverdrive.newsapp.service.user;

import org.arjunaoverdrive.newsapp.model.AppUser;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    List<AppUser> findAllUsers(Pageable pageable);

    AppUser createUser(AppUser appUser);

    AppUser findUserById(Long id);

    AppUser updateUser(AppUser appUser);

    void deleteUserById(Long id);
}
