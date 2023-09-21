package todos.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import todos.dto.TodoDTO;
import todos.model.Todo;
import todos.repository.TodoRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TodoServiceTest {
    private final TodoService todoService;
    private final TodoRepository todoRepository;
    private final String GIVEN = "given saved todos ";
    private final String READ_TITLE = "Read", READ_DESCRIPTION = "Read a book";
    private final String GROCERIES_TITLE = "Groceries", GROCERIES_DESCRIPTION = "Buy groceries";
    private final String STUDY_TITLE = "Study";
    private String readTodoId, groceriesTodoId, studyTodoId;

    @Autowired
    public TodoServiceTest(TodoService todoService, TodoRepository todoRepository) {
        this.todoService = todoService;
        this.todoRepository = todoRepository;
    }

    @BeforeEach
    public void beforeEach() {
        Todo readTodo = new Todo(READ_TITLE, READ_DESCRIPTION);
        readTodoId = todoRepository.save(readTodo).getId();
        Todo groceriesTodo = new Todo(GROCERIES_TITLE, GROCERIES_DESCRIPTION);
        groceriesTodo.setCompleted(true);
        groceriesTodoId = todoRepository.save(groceriesTodo).getId();
        Todo studyTodo = new Todo(STUDY_TITLE, null);
        studyTodoId = todoRepository.save(studyTodo).getId();
    }

    @AfterEach
    public void afterEach() {
        todoRepository.deleteAll();
    }

    @DisplayName(GIVEN
        + "when retrieving all without title "
        + "then all todos should return")
    @Test
    void getAllTodosTest() {
        List<TodoDTO> todos = todoService.getAllTodos(null);
        TodoDTO studyTodo = todos.get(0);
        TodoDTO groceriesTodo = todos.get(1);
        TodoDTO readTodo = todos.get(2);

        assertEquals(3, todos.size());

        assertEquals(STUDY_TITLE, studyTodo.getTitle());
        assertNull(studyTodo.getDescription());
        assertFalse(studyTodo.isCompleted());

        assertEquals(GROCERIES_TITLE, groceriesTodo.getTitle());
        assertEquals(GROCERIES_DESCRIPTION, groceriesTodo.getDescription());
        assertTrue(groceriesTodo.isCompleted());

        assertEquals(READ_TITLE, readTodo.getTitle());
        assertEquals(READ_DESCRIPTION, readTodo.getDescription());
        assertFalse(readTodo.isCompleted());
    }

    @DisplayName(GIVEN
            + "when retrieving all with title "
            + "then all matches should return")
    @Test
    void getAllTodosWithTitleTest() {
        List<TodoDTO> todos = todoService.getAllTodos("ReAd");
        TodoDTO readTodo = todos.get(0);

        assertEquals(1, todos.size());
        assertEquals(READ_TITLE, readTodo.getTitle());
        assertEquals(READ_DESCRIPTION, readTodo.getDescription());

        todos = todoService.getAllTodos("s");
        TodoDTO studyTodo = todos.get(0);
        TodoDTO groceriesTodo = todos.get(1);

        assertEquals(2, todos.size());
        assertEquals(STUDY_TITLE, studyTodo.getTitle());
        assertNull(studyTodo.getDescription());
        assertEquals(GROCERIES_TITLE, groceriesTodo.getTitle());
        assertEquals(GROCERIES_DESCRIPTION, groceriesTodo.getDescription());

        todos = todoService.getAllTodos("No matches");

        assertEquals(0, todos.size());
    }

    @DisplayName(GIVEN
        + "when retrieving a single todo "
        + "then its data should return or null if not existent")
    @Test
    void getTodoTest() {
        TodoDTO readTodo = todoService.getTodo(readTodoId);
        TodoDTO groceriesTodo = todoService.getTodo(groceriesTodoId);
        TodoDTO studyTodo = todoService.getTodo(studyTodoId);
        TodoDTO todoNull = todoService.getTodo("not an id");

        assertEquals(readTodoId, readTodo.getId());
        assertEquals(READ_TITLE, readTodo.getTitle());

        assertEquals(groceriesTodoId, groceriesTodo.getId());
        assertEquals(GROCERIES_TITLE, groceriesTodo.getTitle());

        assertEquals(studyTodoId, studyTodo.getId());
        assertEquals(STUDY_TITLE, studyTodo.getTitle());

        assertNull(todoNull);
    }

    @DisplayName(GIVEN
        + "when creating a todo "
        + "then it should be correctly stored in DB")
    @Test
    void createTodoTest() {
        String title = "Fourth action", description = "Explanation of 4th action";
        long count = todoRepository.count();
        assertEquals(3, count);

        TodoDTO todo = todoService.createTodo(new Todo(title, description));
        count = todoRepository.count();

        assertEquals(4, count);
        assertEquals(title, todo.getTitle());
        assertEquals(description, todo.getDescription());
        assertNotNull(todo.getId());
    }

    @DisplayName(GIVEN
        + "when updating a todo "
        + "then new data should be stored in DB")
    @Test
    void updateTodoTest() {
        String title = "Changed first title", description = "Changed first description";
        Todo body = new Todo(title, description);
        TodoDTO todo = todoService.updateTodo("Not an id", body);

        assertNull(todo);

        todo = todoService.updateTodo(readTodoId, body);

        assertEquals(title, todo.getTitle());
        assertEquals(description, todo.getDescription());
    }

    @DisplayName(GIVEN
        + "when toggling a todo completed "
        + "then the todo should be saved and returned")
    @Test
    void toggleCompletedTest() {
        TodoDTO readTodo = todoService.getTodo(readTodoId);
        TodoDTO groceriesTodo = todoService.getTodo(groceriesTodoId);
        TodoDTO studyTodo = todoService.getTodo(studyTodoId);

        assertFalse(readTodo.isCompleted());
        assertTrue(groceriesTodo.isCompleted());
        assertFalse(studyTodo.isCompleted());

        readTodo = todoService.toggleCompleted(readTodo.getId(), true);
        groceriesTodo = todoService.toggleCompleted(groceriesTodo.getId(), false);
        studyTodo = todoService.toggleCompleted(studyTodo.getId(), true);

        assertTrue(readTodo.isCompleted());
        assertFalse(groceriesTodo.isCompleted());
        assertTrue(studyTodo.isCompleted());
    }

    @DisplayName(GIVEN
        + "when deleting all todos "
        + "then collection should be emptied")
    @Test
    void deleteAllTodosTest() {
        long count = todoRepository.count();
        assertEquals(3, count);

        todoService.deleteAllTodos();
        count = todoRepository.count();
        assertEquals(0, count);
    }

    @DisplayName(GIVEN
        + "when deleting a single todo "
        + "then it should be removed from the DB")
    @Test
    void deleteTodoTest() {
        long count = todoRepository.count();
        assertEquals(3, count);

        boolean deleted = todoService.deleteTodo("not an id");
        count = todoRepository.count();
        assertFalse(deleted);
        assertEquals(3, count);

        deleted = todoService.deleteTodo(readTodoId);
        count = todoRepository.count();
        assertTrue(deleted);
        assertEquals(2, count);

        TodoDTO deletedTodo = todoService.getTodo(readTodoId);
        assertNull(deletedTodo);
    }
}