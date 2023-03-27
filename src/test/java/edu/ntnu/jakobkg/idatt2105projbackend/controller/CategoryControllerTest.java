package edu.ntnu.jakobkg.idatt2105projbackend.controller;
import edu.ntnu.jakobkg.idatt2105projbackend.model.Category;
import edu.ntnu.jakobkg.idatt2105projbackend.model.User;
import edu.ntnu.jakobkg.idatt2105projbackend.repo.CategoryRepository;
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
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryController categoryController;

    Category testCategory = new Category("Testkategori");

    @Test
    public void getCategoryPositiveWhenCategoryExists() {
        when(categoryRepository.findById(1)).thenReturn(Optional.of(testCategory));
        assertEquals(testCategory, categoryController.getCategory(1));
    }

    @Test
    public void getCategoryNegativeWhenCategoryDoesNotExists() {
        assertThrows(ResponseStatusException.class, () -> categoryController.getCategory(99));
    }

    @Test
    public void getCategoryDoesNotThrowExeption() {
        when(categoryRepository.findById(1)).thenReturn(Optional.of(testCategory));
        assertDoesNotThrow(() -> categoryController.getCategory(1));
    }

    @Test
    public void getCategoryNameReturnsCorrectName() {
        when(categoryRepository.findById(1)).thenReturn(Optional.of(testCategory));
        assertEquals(categoryController.getCategory(1).getCategoryName(),"Testkategori");
    }

    @Test
    public void updateCategoryReturnsNewName() {
        when(categoryRepository.findById(1)).thenReturn(Optional.of(testCategory));
        when(categoryRepository.findById(2)).thenReturn(Optional.of(new Category("NyttNavn")));

        categoryController.updateCategoryName(categoryController.getCategory(2),1);
        assertEquals(categoryController.getCategory(1).getCategoryName(),"NyttNavn");
    }

}
