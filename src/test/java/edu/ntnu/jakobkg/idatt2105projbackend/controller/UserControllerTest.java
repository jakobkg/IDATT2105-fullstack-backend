package edu.ntnu.jakobkg.idatt2105projbackend.controller;

import edu.ntnu.jakobkg.idatt2105projbackend.model.User;
import edu.ntnu.jakobkg.idatt2105projbackend.repo.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    User testUser = new User("Testbruker", "Testmann", "test@test.no", "test", "test", 1234, "Testeby");


    @Test
    public void saveUserWorks() {
        when(userRepository.save(testUser)).thenReturn(new User());
        assertEquals(new User().getUsername(), userRepository.save(testUser).getUsername());
    }

    @Test
    public void getUserPositive() {
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        assertEquals(testUser, userController.getUser(1));
    }

    @Test(expected = ResponseStatusException.class)
    public void getUserNegative() {
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        assertEquals(testUser, userController.getUser(2));
    }
}
