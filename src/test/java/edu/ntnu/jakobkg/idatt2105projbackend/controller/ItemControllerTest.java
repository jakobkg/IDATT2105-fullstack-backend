package edu.ntnu.jakobkg.idatt2105projbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for ItemController
 */

@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ItemControllerTest {
    @Autowired
    private MockMvc mockMvc;
    public String token;

    /**
     * Test/run login before testing endpoints that require to be logged in
     */
    @BeforeAll
    public void login() {
        User testUser = new User("Admin", "Adminsen", "admin@admin.no", "admin", "Admingata 1", 1101, "Adminby");
        testUser.setId(1);
        this.token = TokenController.generateToken(testUser);
    }

    /**
     * AddItem - POST request to /item
     * @throws Exception
     */
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

    /**
     * Update an item - PUT request to /item/id
     * Expect: status code 200 and "1" on return which is item id for the updated item
     * @throws Exception
     */
    @Test
    public void updateItem() throws Exception {
        AddItemRequest request = new AddItemRequest(
                "Plante", "Veldig fin plante",
                "63.404734992005245", "10.371198931159565", "0100 Sted",
                "5000", 6,
                "https://media.houseandgarden.co.uk/photos/618944690a583de660124d52/master/w_1600%2Cc_limit/1-house-29mar17-Nick-Pope_b.jpg"
        );

        //Check if returned status code is 200 and "1" is returned (item id)
        this.mockMvc.perform(put("/item/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request))
                        .header("Authorization", "Bearer "+this.token))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("1")));
    }


    /**
     * Delete an item - DELETE request to /item/id
     * Expect: status code 200, nothing on return
     * @throws Exception
     */
    @Test
    public void deleteItem() throws Exception {
        //Check if returned status code is 200
        this.mockMvc.perform(delete("/item/1")
                        .header("Authorization", "Bearer "+this.token))
                .andExpect(status().isOk());
    }

    /**
     * getItem - GET request to /item/id (no authorization required)
     * Expect: status code 200 and item in return which contains "Plante" - this comes from startup/CreateItems
     * @throws Exception
     */
    @Test
    public void getItem() throws Exception {
        this.mockMvc.perform(get("/item/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Plante")));
    }

    /**
     * getItems - GET request to /item (no authorization required)
     * Expect: status code 200 and
     * @throws Exception
     */
    @Test
    public void getItems() throws Exception {
        this.mockMvc.perform(get("/item"))
                .andExpect(status().isOk());
                //.andExpect(jsonPath("$.*", hasSize(11))); -> we dont know the actual size of items
    }


}
