package LDDT.projects.reddit_clone.test_Controllers;

import LDDT.projects.reddit_clone.configuration.Configuration;
import LDDT.projects.reddit_clone.controllers.AuthController;
import LDDT.projects.reddit_clone.dtos.RegisterRequestDto;
import LDDT.projects.reddit_clone.services.AuthService;
import LDDT.projects.reddit_clone.services.RefreshTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@ContextConfiguration(classes = Configuration.class)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private RefreshTokenService refreshTokenService;

//    @MockBean
//    private PasswordEncoder passwordEncoder;
//
//    private User createUser(RegisterRequestDto registerRequestDto) {
//        return User.builder().userId(1L)
//                .username(registerRequestDto.getUsername())
//                .password(passwordEncoder.encode(registerRequestDto.getPassword()))
//                .email(registerRequestDto.getEmail())
//                .enabled(false)
//                .build();
//    }

    @Test
    public void test_WhenEmailEmpty_ThrowException() throws Exception{
        RegisterRequestDto registerRequest = new RegisterRequestDto("", "username", "P55w0rd");
        mockMvc.perform(post("/api/auth/signup")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(registerRequest))
                )
                .andExpect(status().is4xxClientError())
                .andDo(print());
        // Do we need to create a mapper for mapping RegisterRequestDto -> User

    }

    @Test
    public void test_WhenRegisterRequestValid_UserRegistered() throws Exception{
        // we need to set up behaviour for the mock AuthService
        // expectation:
        // - status == 200
        // User.username, .password, email is
        RegisterRequestDto registerRequest = new RegisterRequestDto("user@example.com", "username", "P55w0rd");
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("User Registration Successful")))
                .andDo(print());
    }
}