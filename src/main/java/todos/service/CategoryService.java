package todos.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import todos.dto.CategoryDTO;
import todos.model.Category;
import todos.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryService(CategoryRepository repository, ModelMapper modelMapper) {
        this.categoryRepository = repository;
        this.modelMapper = modelMapper;
    }

    public List<CategoryDTO> getAllCategories(String name) {
        List<Category> categories = new ArrayList<>();
        if (name != null) categories.addAll(categoryRepository.findAllByNameContainingIgnoreCase(name));
        else categories.addAll(categoryRepository.findAll());
        return categories.stream().map(this::convertToDTO).toList();
    }

    public CategoryDTO getCategory(String id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category != null) return convertToDTO(category);
        return null;
    }

    public CategoryDTO createCategory(Category newCategory) {
        Category category = new Category(newCategory.getName());
        return convertToDTO(categoryRepository.insert(category));
    }

    public CategoryDTO updateCategory(String id, Category updatedCategory) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) return null;
        category.setName(updatedCategory.getName());
        return convertToDTO(categoryRepository.save(category));
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

    private CategoryDTO convertToDTO(Category category) {
        return modelMapper.map(category, CategoryDTO.class);
    }
}
