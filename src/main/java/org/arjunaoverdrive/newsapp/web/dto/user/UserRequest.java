package org.arjunaoverdrive.newsapp.web.dto.user;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.arjunaoverdrive.newsapp.model.RoleType;
import org.arjunaoverdrive.newsapp.validation.annotations.EmailAlreadyExists;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NotNull
    @NotBlank
    @Size(min = 3, max = 15, message = "First name must be between 3 and 15")
    private String firstName;
    @NotBlank
    @NotNull
    @Size(min = 1, max = 15, message = "Last name length must be between 1 and 15")
    private String lastName;
    @Email
    @EmailAlreadyExists
    private String email;
    @NotNull
    @Size(min = 8, max = 15, message = "Password length must be between 1 and 15")
    private String password;
    @NotEmpty
    private Set<RoleType> roles;
}
