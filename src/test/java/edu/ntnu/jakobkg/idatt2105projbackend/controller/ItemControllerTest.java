package edu.ntnu.jakobkg.idatt2105projbackend.controller;

import edu.ntnu.jakobkg.idatt2105projbackend.model.AddItemRequest;
import edu.ntnu.jakobkg.idatt2105projbackend.model.LoginRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public void login() {
        LoginRequest loginRequest = new LoginRequest("admin@admin.no", "admin");

    }

    @Test
    public void addItem() throws Exception {
        AddItemRequest request = new AddItemRequest();
        this.mockMvc.perform(post("/item").content(request).header()).()
    }
}
