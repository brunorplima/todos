package todos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import todos.model.Todo;
import todos.service.TodoService;
import todos.util.Constants;

import java.util.List;

@CrossOrigin(origins = Constants.DEFAULT_ORIGIN)
@RestController
@RequestMapping("/api/todos")
public class TodoController {
    @Autowired
    private TodoService todoService;

    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos(@RequestParam(required = false) String title) {
        return new ResponseEntity<>(todoService.getAllTodos(title), HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodo(@PathVariable String id) {
        Todo todo = todoService.getTodo(id);
        if (todo != null) return new ResponseEntity<>(todo, HttpStatus.FOUND);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody Todo newTodo) {
        Todo todo = todoService.createTodo(newTodo);
        return new ResponseEntity<>(todo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable String id, @RequestBody Todo newTodo) {
        Todo todo = todoService.updateTodo(id, newTodo);
        if (todo != null) return new ResponseEntity<>(todo, HttpStatus.ACCEPTED);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllTodos() {
        todoService.deleteAllTodos();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTodo(@PathVariable String id) {
        if (todoService.deleteTodo(id)) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
