package LDDT.projects.reddit_clone.services;

import LDDT.projects.reddit_clone.dtos.AuthenticationResponse;
import LDDT.projects.reddit_clone.dtos.LoginRequest;
import LDDT.projects.reddit_clone.dtos.RegisterRequestDto;
import LDDT.projects.reddit_clone.exceptions.SpringRedditException;
import LDDT.projects.reddit_clone.model.NotificationEmail;
import LDDT.projects.reddit_clone.model.User;
import LDDT.projects.reddit_clone.model.VerificationToken;
import LDDT.projects.reddit_clone.repositories.UserRepository;
import LDDT.projects.reddit_clone.repositories.VerificationTokenRepository;
import LDDT.projects.reddit_clone.security.JwtProvider;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;


@AllArgsConstructor
@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    @Transactional
    public void signUp(RegisterRequestDto registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);
        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail("Please Activate your Account",
                user.getEmail(), "Thank you for signing up to Spring Reddit, " +
                "please click on the below url to activate your account : " +
                "http://localhost:8080/api/auth/accountVerification/" + token));
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new SpringRedditException("Invalid Token"));
        fetchUserAndEnable(verificationToken.get());
    }

    @Transactional
    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        Optional<User> user = userRepository.findByUsername(username);
        user.orElseThrow(() -> new SpringRedditException("User not found with name" + username));
        user.get().setEnabled(true);
        userRepository.save(user.get());
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            String token = jwtProvider.generateToken(authenticate);
            return new AuthenticationResponse(token, loginRequest.getUsername());
        } catch (AuthenticationException e) {
            System.out.println("Can not authenticate user: " + e.getMessage());
            return new AuthenticationResponse("Failed to get token", loginRequest.getUsername());
        }
    }
}