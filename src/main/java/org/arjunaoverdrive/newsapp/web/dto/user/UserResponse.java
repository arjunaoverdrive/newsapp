package org.arjunaoverdrive.newsapp.web.dto.user;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
