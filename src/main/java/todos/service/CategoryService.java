package todos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import todos.model.Category;
import todos.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository repository) {
        this.categoryRepository = repository;
    }

    public List<Category> getAllCategories(String name) {
        List<Category> categories = new ArrayList<>();
        if (name != null) categories.addAll(categoryRepository.findAllByNameContainingIgnoreCase(name));
        else categories.addAll(categoryRepository.findAll());
        return categories;
    }

    public Category getCategory(String id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public Category createCategory(Category newCategory) {
        Category category = new Category(newCategory.getName());
        return categoryRepository.insert(category);
    }

    public Category updateCategory(String id, Category updatedCategory) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) return null;
        category.setName(updatedCategory.getName());
        return categoryRepository.save(category);
    }

    public void deleteAllCategories() {
        categoryRepository.deleteAll();
    }

    public boolean deleteCategory(String id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) return false;
        categoryRepository.delete(category);
        return true;
    }
}
