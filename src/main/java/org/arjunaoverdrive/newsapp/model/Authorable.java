package org.arjunaoverdrive.newsapp.model;

import java.time.Instant;

public interface Authorable {
    AppUser getAuthor();

    String getClassName();

    Instant getCreatedAt();
}
