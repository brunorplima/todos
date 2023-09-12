package todos.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import todos.model.Todo;
import todos.repository.TodoRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TodoServiceTest {

    @Autowired
    private TodoService todoService;
    @Autowired
    private TodoRepository todoRepository;

    private final String GIVEN = "given saved todos ";
    private final String TITLE1 = "First todo", DESCRIPTION1 = "task for Monday";
    private final String TITLE2 = "Second task", DESCRIPTION2 = "task for Tuesday";
    private final String TITLE3 = "Third activity", DESCRIPTION3 = "task for Wednesday";

    private String todoId1, todoId2, todoId3;

    @BeforeEach
    public void beforeEach() {
        Todo firstTodo = new Todo(TITLE1, DESCRIPTION1);
        todoId1 = todoRepository.save(firstTodo).getId();
        Todo secondTodo = new Todo(TITLE2, DESCRIPTION2);
        secondTodo.setCompleted(true);
        todoId2 = todoRepository.save(secondTodo).getId();
        Todo thirdTodo = new Todo(TITLE3, DESCRIPTION3);
        todoId3 = todoRepository.save(thirdTodo).getId();
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
        List<Todo> todos = todoService.getAllTodos(null);
        Todo firstTodo = todos.get(0);
        Todo secondTodo = todos.get(1);
        Todo thirdTodo = todos.get(2);

        assertEquals(3, todos.size());

        assertEquals(TITLE1, firstTodo.getTitle());
        assertEquals(DESCRIPTION1, firstTodo.getDescription());
        assertFalse(firstTodo.isCompleted());

        assertEquals(TITLE2, secondTodo.getTitle());
        assertEquals(DESCRIPTION2, secondTodo.getDescription());
        assertTrue(secondTodo.isCompleted());

        assertEquals(TITLE3, thirdTodo.getTitle());
        assertEquals(DESCRIPTION3, thirdTodo.getDescription());
        assertFalse(thirdTodo.isCompleted());
    }

    @DisplayName(GIVEN
            + "when retrieving all with title "
            + "then all matches should return")
    @Test
    void getAllTodosWithTitleTest() {
        List<Todo> todos = todoService.getAllTodos("ToDo");
        Todo firstTodo = todos.get(0);

        assertEquals(1, todos.size());
        assertEquals(TITLE1, firstTodo.getTitle());
        assertEquals(DESCRIPTION1, firstTodo.getDescription());

        todos = todoService.getAllTodos("A");
        Todo secondTodo = todos.get(0);
        Todo thirdTodo = todos.get(1);

        assertEquals(2, todos.size());
        assertEquals(TITLE2, secondTodo.getTitle());
        assertEquals(DESCRIPTION2, secondTodo.getDescription());
        assertEquals(TITLE3, thirdTodo.getTitle());
        assertEquals(DESCRIPTION3, thirdTodo.getDescription());

        todos = todoService.getAllTodos("No matches");

        assertEquals(0, todos.size());
    }

    @DisplayName(GIVEN
        + "when retrieving a single todo "
        + "then its data should return or null if not existent")
    @Test
    void getTodoTest() {
        Todo firstTodo = todoService.getTodo(todoId1);
        Todo secondTodo = todoService.getTodo(todoId2);
        Todo thirdTodo = todoService.getTodo(todoId3);
        Todo todoNull = todoService.getTodo("not an id");

        assertEquals(todoId1, firstTodo.getId());
        assertEquals(TITLE1, firstTodo.getTitle());

        assertEquals(todoId2, secondTodo.getId());
        assertEquals(TITLE2, secondTodo.getTitle());

        assertEquals(todoId3, thirdTodo.getId());
        assertEquals(TITLE3, thirdTodo.getTitle());

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

        Todo todo = todoService.createTodo(new Todo(title, description));
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
        Todo todo = todoService.updateTodo("Not an id", body);

        assertNull(todo);

        todo = todoService.updateTodo(todoId1, body);

        assertEquals(title, todo.getTitle());
        assertEquals(description, todo.getDescription());
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

        deleted = todoService.deleteTodo(todoId1);
        count = todoRepository.count();
        assertTrue(deleted);
        assertEquals(2, count);

        Todo deletedTodo = todoService.getTodo(todoId1);
        assertNull(deletedTodo);
    }
}