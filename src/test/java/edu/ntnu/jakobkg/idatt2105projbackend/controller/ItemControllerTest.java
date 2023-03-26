package edu.ntnu.jakobkg.idatt2105projbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.ntnu.jakobkg.idatt2105projbackend.model.AddItemRequest;
import edu.ntnu.jakobkg.idatt2105projbackend.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.containsString;

import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ItemControllerTest {
    @Autowired
    private MockMvc mockMvc;
    public String token;
    @BeforeAll
    public void login() {
        User testUser = new User("Admin", "Adminsen", "admin@admin.no", "admin", "Admingata 1", 1101, "Adminby");
        testUser.setId(1);
        this.token = TokenController.generateToken(testUser);
    }

    @Test
    public void addItem() throws Exception {
        AddItemRequest request = new AddItemRequest(
                "Hodetelefoner",
                "Veldig fint headset",
                "60.4",
                "10.4",
                "7031 Trondheim",
                "2000",
                2,
                ""
        );

        this.mockMvc.perform(post("/item")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request))
                .header("Authorization", "Bearer "+this.token))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("Hodetelefoner")));
    }
}
