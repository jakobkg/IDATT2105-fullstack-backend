package edu.ntnu.jakobkg.idatt2105projbackend.controller;

import edu.ntnu.jakobkg.idatt2105projbackend.model.User;
import edu.ntnu.jakobkg.idatt2105projbackend.repo.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.Assert.*;
import org.junit.Test;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        User user = new User("Testbruker", "Testmann", "test@test.no", "test", "test", 1234, "Testeby");
    }


    @Test
    public void saveUserWorks() {
        userRepository.save(new User("Testbruker", "Testmann", "test@test.no", "test", "test", 1234, "Testeby"));
        Mockito.verify(userRepository).save(new User("Testbruker", "Testmann", "test@test.no", "test", "test", 1234, "Testeby"));
        assertEquals(0, userRepository.count());

        Mockito.when(userRepository.save(new User("Testbruker", "Testmann", "test@test.no", "test", "test", 1234, "Testeby"))).thenReturn(new User());
        assertEquals(new User(), userRepository.save(new User("Testbruker", "Testmann", "test@test.no", "test", "test", 1234, "Testeby")));
    }


}
