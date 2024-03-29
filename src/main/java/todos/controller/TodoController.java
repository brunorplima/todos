package todos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import todos.dto.TodoDTO;
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
    public ResponseEntity<List<TodoDTO>> getAllTodos(@RequestParam(required = false) String title) {
        return new ResponseEntity<>(todoService.getAllTodos(title), HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoDTO> getTodo(@PathVariable String id) {
        TodoDTO todo = todoService.getTodo(id);
        if (todo != null) return new ResponseEntity<>(todo, HttpStatus.FOUND);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<TodoDTO> createTodo(@RequestBody Todo newTodo) {
        TodoDTO todo = todoService.createTodo(newTodo);
        return new ResponseEntity<>(todo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoDTO> updateTodo(@PathVariable String id, @RequestBody Todo newTodo) {
        TodoDTO todo = todoService.updateTodo(id, newTodo);
        if (todo != null) return new ResponseEntity<>(todo, HttpStatus.ACCEPTED);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}/check")
    public ResponseEntity<TodoDTO> checkCompleted(@PathVariable String id) {
        TodoDTO todo = todoService.toggleCompleted(id, true);
        return new ResponseEntity<>(todo, HttpStatus.OK);
    }

    @PutMapping("/{id}/uncheck")
    public ResponseEntity<TodoDTO> uncheckCompleted(@PathVariable String id) {
        TodoDTO todo = todoService.toggleCompleted(id, false);
        return new ResponseEntity<>(todo, HttpStatus.OK);
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
