package hexlet.code.app.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Getter
@Setter
public class UserUpdateDTO {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 3)
    private String password;
}
