package todos.repository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import todos.model.Todo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TodoRepositoryTest {

    @Autowired
    private TodoRepository repository;

    private final String TITLE1 = "Interesting Title", TITLE2 = "Weird Title";
    private final String DESCRIPTION1 = "Interesting description", DESCRIPTION2 = "Weird description";

    @BeforeEach
    void beforeEach() {
        Todo todo1 = new Todo(TITLE1, DESCRIPTION1);
        todo1.setCompleted(true);
        Todo todo2 = new Todo(TITLE2, DESCRIPTION2);
        repository.save(todo1);
        repository.save(todo2);
    }

    @AfterEach
    void afterEach() {
        repository.deleteAll();
    }

    @DisplayName("given saved todos "
            + "when retrieved by title containing "
            + "then it returns expected amount of todos")
    @Test
    void itShouldFindExpectedTodoAmount() {
        List<Todo> onlyTodo1 = repository.findByTitleContainingIgnoreCase("INTERESTING");
        List<Todo> onlyTodo2 = repository.findByTitleContainingIgnoreCase("WEIRD");
        List<Todo> both = repository.findByTitleContainingIgnoreCase("TITLE");

        assertEquals(1, onlyTodo1.size());
        assertEquals(1, onlyTodo2.size());
        assertEquals(2, both.size());
    }


    @DisplayName("given saved todos "
            + "when retrieved by title "
            + "then it returns the expected data")
    @Test
    void itShouldFindExpectedTodoData() {
        List<Todo> onlyTodo1 = repository.findByTitleContainingIgnoreCase("INTERESTING");
        List<Todo> onlyTodo2 = repository.findByTitleContainingIgnoreCase("WEIRD");

        assertEquals(1, onlyTodo1.size());
        assertEquals(1, onlyTodo2.size());

        Todo todo1 = onlyTodo1.get(0);
        Todo todo2 = onlyTodo2.get(0);

        assertEquals(TITLE1, todo1.getTitle());
        assertEquals(TITLE2, todo2.getTitle());

        assertEquals(DESCRIPTION1, todo1.getDescription());
        assertEquals(DESCRIPTION2, todo2.getDescription());

        assertTrue(todo1.isCompleted());
        assertFalse(todo2.isCompleted());
    }
}