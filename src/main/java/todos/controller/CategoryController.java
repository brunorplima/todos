package todos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import todos.dto.CategoryDTO;
import todos.model.Category;
import todos.service.CategoryService;
import todos.util.Constants;

import java.util.List;

@CrossOrigin(origins = Constants.DEFAULT_ORIGIN)
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories(@RequestParam(required = false) String name) {
        List<CategoryDTO> categories = categoryService.getAllCategories(name);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable String id) {
        CategoryDTO category = categoryService.getCategory(id);
        if (category == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(category, HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody Category newCategory) {
        return new ResponseEntity<>(categoryService.createCategory(newCategory), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable String id, @RequestBody Category _category) {
        CategoryDTO category = categoryService.updateCategory(id, _category);
        if (category == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllCategories() {
        categoryService.deleteAllCategories();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> updateCategory(@PathVariable String id) {
        if (categoryService.deleteCategory(id)) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
