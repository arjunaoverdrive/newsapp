package org.arjunaoverdrive.newsapp.service;

import org.arjunaoverdrive.newsapp.model.AppUser;

import java.time.Instant;

public interface AuthorableService {

    Instant getCreatedAtById(Long id);

    default Class getType(){
        return  this.getClass();
    }

    AppUser getAuthor(Long entityId);

}
