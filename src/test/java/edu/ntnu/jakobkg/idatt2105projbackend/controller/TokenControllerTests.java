package edu.ntnu.jakobkg.idatt2105projbackend.controller;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import edu.ntnu.jakobkg.idatt2105projbackend.model.LoginRequest;
import edu.ntnu.jakobkg.idatt2105projbackend.model.User;
import edu.ntnu.jakobkg.idatt2105projbackend.repo.UserRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
public class TokenControllerTests {

    @Mock
    UserRepository userRepo;

    @InjectMocks
    private TokenController tokenController;

    User testUser = new User(
            "Test",
            "Test",
            "test@test.no",
            "test",
            "Testveien 1",
            1111,
            "Testeby") {
        {
            setId(1);
        }
    };

    @Test
    public void testCreateTokenPositive() {
        when(userRepo.findByEmail("test"))
            .thenReturn(Optional.of(testUser));
        
        assertEquals(
            tokenController.getToken(
                new LoginRequest("test", "test")),
            TokenController.generateToken(testUser));
    }

    @Test(expected = ResponseStatusException.class)
    public void testCreateTokenWrongPassword() {
        when(userRepo.findByEmail("test"))
            .thenReturn(Optional.of(testUser));

        tokenController.getToken(
            new LoginRequest("test", "not the right password"));
    }

    @Test
    public void testCreateTokenNonexistingUser() {
        when(userRepo.findByEmail("test"))
            .thenReturn(Optional.empty());

        assertThrows(
            ResponseStatusException.class, 
            () -> {
                tokenController.getToken(
                    new LoginRequest("test", "irrelevant to this test")
                );
            }
        );
    }

    @Test
    public void testTokenContainsEmailTypeAndID() {
        when(userRepo.findByEmail("test"))
            .thenReturn(Optional.of(testUser));
        
        final String token = tokenController.getToken(
            new LoginRequest("test", "test")
        );

        DecodedJWT decoded = JWT.decode(token);

        assertEquals(testUser.getEmail(), decoded.getSubject());
        assertEquals(testUser.getType().toString(), decoded.getClaim("type").asString());
        assertEquals(decoded.getClaim("id").asString(), testUser.getId().toString());
    }

}
