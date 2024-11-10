package LDDT.projects.reddit_clone.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {
    @NotBlank(message = "email must not be empty")
    private String email;
    @NotBlank(message = "email must not be empty")
    private String username;
    @NotBlank(message = "email must not be empty")
    private String password;
}
