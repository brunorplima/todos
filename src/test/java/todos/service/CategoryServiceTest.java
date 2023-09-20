package todos.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import todos.dto.CategoryDTO;
import todos.model.Category;
import todos.repository.CategoryRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryServiceTest {
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final String GIVEN = "given saved categories ";
    private final String NAME1 = "Working", NAME2 = "Gardening";
    private String id1, id2;

    @Autowired
    public CategoryServiceTest(CategoryService categoryService, CategoryRepository categoryRepository) {
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
    }

    @BeforeEach
    void beforeEach() {
        id1 = categoryRepository.save(new Category(NAME1)).getId();
        id2 = categoryRepository.save(new Category(NAME2)).getId();
    }

    @AfterEach
    void afterEach() {
        categoryRepository.deleteAll();
    }

    @DisplayName(GIVEN
            + "when retrieving all without name "
            + "then all categories should return")
    @Test
    void getAllCategoriesTest() {
        List<CategoryDTO> categories = categoryService.getAllCategories(null);
        CategoryDTO workCategory = categories.get(0);
        CategoryDTO gardenCategory = categories.get(1);

        assertEquals(2, categories.size());
        assertEquals(NAME1, workCategory.getName());
        assertEquals(NAME2, gardenCategory.getName());
    }

    @DisplayName(GIVEN
            + "when retrieving all with name "
            + "then all matches should return")
    @Test
    void getAllCategoriesWithNameTest() {
        CategoryDTO workCategory, gardenCategory;
        List<CategoryDTO> categories = categoryService.getAllCategories("no match");
        assertEquals(0, categories.size());

        categories = categoryService.getAllCategories(NAME1);
        workCategory = categories.get(0);
        assertEquals(1, categories.size());
        assertEquals(NAME1, workCategory.getName());

        categories = categoryService.getAllCategories("ing");
        workCategory = categories.get(0);
        gardenCategory = categories.get(1);
        assertEquals(2, categories.size());
        assertEquals(NAME1, workCategory.getName());
        assertEquals(NAME2, gardenCategory.getName());
    }

    @DisplayName(GIVEN
            + "when retrieving a single category "
            + "then its data should return or null if not existent")
    @Test
    void getCategoryTest() {
        CategoryDTO workCategory = categoryService.getCategory(id1);
        CategoryDTO gardenCategory = categoryService.getCategory(id2);
        CategoryDTO categoryNull = categoryService.getCategory("not an id");

        assertEquals(id1, workCategory.getId());
        assertEquals(NAME1, workCategory.getName());

        assertEquals(id2, gardenCategory.getId());
        assertEquals(NAME2, gardenCategory.getName());

        assertNull(categoryNull);
    }

    @DisplayName(GIVEN
            + "when creating a category "
            + "then it should be correctly stored in DB")
    @Test
    void createCategory() {
        String name = "Home";
        long count = categoryRepository.count();
        assertEquals(2, count);

        CategoryDTO homeCategory = categoryService.createCategory(new Category(name));
        count = categoryRepository.count();

        assertEquals(3, count);
        assertEquals(name, homeCategory.getName());
        assertNotNull(homeCategory.getId());
    }

    @DisplayName(GIVEN
            + "when updating a category "
            + "then new data should be stored in DB")
    @Test
    void updateCategory() {
        String name = "Groceries";
        Category body = new Category(name);
        CategoryDTO category = categoryService.updateCategory("not an id", body);

        assertNull(category);

        category = categoryService.updateCategory(id2, body);

        assertEquals(name, category.getName());
        assertNotNull(category.getId());
    }

    @DisplayName(GIVEN
            + "when deleting all categories "
            + "then collection should be emptied")
    @Test
    void deleteAllCategories() {
        long count = categoryRepository.count();
        assertEquals(2, count);

        categoryService.deleteAllCategories();
        count = categoryRepository.count();
        assertEquals(0, count);
    }

    @DisplayName(GIVEN
            + "when deleting a single category "
            + "then it should be removed from the DB")
    @Test
    void deleteCategory() {
        long count = categoryRepository.count();
        assertEquals(2, count);

        boolean deleted = categoryService.deleteCategory("not an id");
        count = categoryRepository.count();
        assertFalse(deleted);
        assertEquals(2, count);

        deleted = categoryService.deleteCategory(id1);
        count = categoryRepository.count();
        assertTrue(deleted);
        assertEquals(1, count);

        CategoryDTO deletedTodo = categoryService.getCategory(id1);
        assertNull(deletedTodo);
    }
}