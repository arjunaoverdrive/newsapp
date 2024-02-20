package org.arjunaoverdrive.newsapp.web.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String email;
    @NotNull
    @Size(min = 8, max = 15, message = "Password length must be between 1 and 15")
    private String password;
}
