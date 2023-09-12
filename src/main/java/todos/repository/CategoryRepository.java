package todos.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import todos.model.Category;

import java.util.List;

public interface CategoryRepository extends MongoRepository<Category, String> {
    List<Category> findAllByNameContainingIgnoreCase(String name);
}
