package todos.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import todos.dto.TodoDTO;
import todos.model.Todo;
import todos.repository.TodoRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoService {
    private final TodoRepository todoRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TodoService(TodoRepository repository, ModelMapper modelMapper) {
        this.todoRepository = repository;
        this.modelMapper = modelMapper;
    }

    public List<TodoDTO> getAllTodos(String title) {
        List<Todo> todos = new ArrayList<>();
        if (title != null) todos.addAll(todoRepository.findByTitleContainingIgnoreCase(title));
        else todos.addAll(todoRepository.findAll());
        return todos.stream().map(this::convertToDTO).toList();
    }

    public TodoDTO getTodo(String id) {
        Todo todo = todoRepository.findById(id).orElse(null);
        if (todo != null) return convertToDTO(todo);
        return null;
    }

    public TodoDTO createTodo(Todo newTodo) {
        Todo todo = new Todo(newTodo.getTitle(), newTodo.getDescription());
        return convertToDTO(todoRepository.insert(todo));
    }

    public TodoDTO updateTodo(String id, Todo newTodo) {
        Todo todo = todoRepository.findById(id).orElse(null);
        if (todo == null) return null;
        todo.setTitle(newTodo.getTitle());
        todo.setDescription(newTodo.getDescription());
        todo.setCompleted(newTodo.isCompleted());
        return convertToDTO(todoRepository.save(todo));
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

    private TodoDTO convertToDTO(Todo todo) {
        return modelMapper.map(todo, TodoDTO.class);
    }
}
