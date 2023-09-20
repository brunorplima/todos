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

    private final String READ_TITLE = "Read", READ_DESCRIPTION = "Read a book";
    private final String STUDY_TITLE = "Study", STUDY_DESCRIPTION = "Study for the exam";

    @BeforeEach
    void beforeEach() {
        Todo readTodo = new Todo(READ_TITLE, READ_DESCRIPTION);
        readTodo.setCompleted(true);
        Todo studyTodo = new Todo(STUDY_TITLE, STUDY_DESCRIPTION);
        repository.save(readTodo);
        repository.save(studyTodo);
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
        List<Todo> onlyReadTodo = repository.findByTitleContainingIgnoreCaseOrderByCreatedDesc("rE");
        List<Todo> onlyStudyTodo = repository.findByTitleContainingIgnoreCaseOrderByCreatedDesc("sTU");
        List<Todo> both = repository.findByTitleContainingIgnoreCaseOrderByCreatedDesc("D");

        assertEquals(1, onlyReadTodo.size());
        assertEquals(1, onlyStudyTodo.size());
        assertEquals(2, both.size());
    }


    @DisplayName("given saved todos "
            + "when retrieved by title "
            + "then it returns the expected data")
    @Test
    void itShouldFindExpectedTodoData() {
        List<Todo> onlyReadTodo = repository.findByTitleContainingIgnoreCaseOrderByCreatedDesc("READ");
        List<Todo> onlyStudyTodo = repository.findByTitleContainingIgnoreCaseOrderByCreatedDesc("STUDY");

        assertEquals(1, onlyReadTodo.size());
        assertEquals(1, onlyStudyTodo.size());

        Todo readTodo = onlyReadTodo.get(0);
        Todo studyTodo = onlyStudyTodo.get(0);

        assertEquals(READ_TITLE, readTodo.getTitle());
        assertEquals(STUDY_TITLE, studyTodo.getTitle());

        assertEquals(READ_DESCRIPTION, readTodo.getDescription());
        assertEquals(STUDY_DESCRIPTION, studyTodo.getDescription());

        assertTrue(readTodo.isCompleted());
        assertFalse(studyTodo.isCompleted());
    }
}