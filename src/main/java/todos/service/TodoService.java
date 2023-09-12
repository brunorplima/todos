package todos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import todos.model.Todo;
import todos.repository.TodoRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    @Autowired
    public TodoService(TodoRepository repository) {
        this.todoRepository = repository;
    }

    public List<Todo> getAllTodos(String title) {
        List<Todo> todos = new ArrayList<>();
        if (title != null) todos.addAll(todoRepository.findByTitleContainingIgnoreCase(title));
        else todos.addAll(todoRepository.findAll());
        return todos;
    }

    public Todo getTodo(String id) {
        return todoRepository.findById(id).orElse(null);
    }

    public Todo createTodo(Todo newTodo) {
        Todo todo = new Todo(newTodo.getTitle(), newTodo.getDescription());
        return todoRepository.insert(todo);
    }

    public Todo updateTodo(String id, Todo newTodo) {
        Todo todo = todoRepository.findById(id).orElse(null);
        if (todo == null) return null;
        todo.setTitle(newTodo.getTitle());
        todo.setDescription(newTodo.getDescription());
        todo.setCompleted(newTodo.isCompleted());
        return todoRepository.save(todo);
    }

    public void deleteAllTodos() {
        todoRepository.deleteAll();
    }

    public boolean deleteTodo(String id) {
        Todo todo = todoRepository.findById(id).orElse(null);
        if (todo == null) return false;
        todoRepository.delete(todo);
        return true;
    }
}
