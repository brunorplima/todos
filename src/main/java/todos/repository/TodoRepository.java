package todos.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import todos.model.Todo;

import java.util.List;

public interface TodoRepository extends MongoRepository<Todo, String> {
    List<Todo> findByTitleContainingIgnoreCaseOrderByCreatedDesc(String title);
}
