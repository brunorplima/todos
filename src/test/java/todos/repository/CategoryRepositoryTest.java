package todos.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import todos.model.Category;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository repository;

    private final String NAME1 = "Home", NAME2 = "Gardening", NAME3 = "Training";

    @BeforeEach
    void beforeEach() {
        Category category1 = new Category(NAME1);
        Category category2 = new Category(NAME2);
        Category category3 = new Category(NAME3);
        repository.save(category1);
        repository.save(category2);
        repository.save(category3);
    }

    @AfterEach
    void afterEach() {
        repository.deleteAll();
    }

    @DisplayName("given saved categories "
            + "when retrieving all by name "
            + "then it should return all matched categories")
    @Test
    void itShouldReturnMatchedCategories() {
        List<Category> onlyCategory1 = repository.findAllByNameContainingIgnoreCase("ME");
        List<Category> categories1And2 = repository.findAllByNameContainingIgnoreCase("E");
        List<Category> categories2And3 = repository.findAllByNameContainingIgnoreCase("ING");

        Category category1, category2, category3;

        assertEquals(1, onlyCategory1.size());
        category1 = onlyCategory1.get(0);
        assertEquals(NAME1, category1.getName());

        assertEquals(2, categories1And2.size());
        category1 = categories1And2.get(0);
        category2 = categories1And2.get(1);
        assertEquals(NAME1, category1.getName());
        assertEquals(NAME2, category2.getName());

        assertEquals(2, categories2And3.size());
        category2 = categories2And3.get(0);
        category3 = categories2And3.get(1);
        assertEquals(NAME2, category2.getName());
        assertEquals(NAME3, category3.getName());
    }
}