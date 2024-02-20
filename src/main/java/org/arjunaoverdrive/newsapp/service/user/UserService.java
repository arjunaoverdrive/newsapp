package org.arjunaoverdrive.newsapp.service.user;

import org.arjunaoverdrive.newsapp.model.AppUser;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface UserService {

    List<AppUser> findAll(Pageable pageable);

    AppUser create(AppUser appUser);

    AppUser findUserById(Long id);

    AppUser update(AppUser appUser);

    void deleteUserById(Long id);
}
