package com.secure.security.controller;

import com.secure.security.models.Todo;
import com.secure.security.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping("/create")
    public ResponseEntity<Todo> createTodo(@RequestBody @Valid Todo todo){
        return new ResponseEntity<>(todoService.creatTodo(todo), HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<Todo> getTodo(@RequestParam Long id){
        try {
            return new ResponseEntity<>(todoService.getTodo(id), HttpStatus.FOUND);
        }
        catch (RuntimeException exception){
            return new ResponseEntity<>((HttpHeaders) null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getall")
    public List<Todo> getAllTodos(){
        return todoService.getAll();
    }

    @PutMapping("/update")
    public ResponseEntity<Todo> updateTodo(@RequestBody Todo todo){
        try {
            return new ResponseEntity<>(todoService.updade(todo.getId()), HttpStatus.OK);
        }
        catch (RuntimeException exception){
            return new ResponseEntity<>((HttpHeaders) null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable("id") Long id){
        todoService.deleteTodo(id);
    }

    @DeleteMapping("/deleteall")
    public void deleteAllTodos(){
        todoService.deleteAll();
    }
}

