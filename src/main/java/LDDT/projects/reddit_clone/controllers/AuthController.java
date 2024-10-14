package LDDT.projects.reddit_clone.controllers;

import LDDT.projects.reddit_clone.dtos.AuthenticationResponse;
import LDDT.projects.reddit_clone.dtos.LoginRequest;
import LDDT.projects.reddit_clone.dtos.RegisterRequestDto;
import LDDT.projects.reddit_clone.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    @PostMapping("signup")
    public ResponseEntity<String> signUp(@RequestBody RegisterRequestDto registerRequest) {
        authService.signUp(registerRequest);
        return new ResponseEntity<>("User Registration Successful", HttpStatus.OK);
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}
