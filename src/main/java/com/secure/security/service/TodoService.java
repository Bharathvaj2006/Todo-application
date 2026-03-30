package com.secure.security.service;

import com.secure.security.models.Todo;
import com.secure.security.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepocitory;

    public Todo creatTodo(Todo todo){
        return todoRepocitory.save(todo);
    }

    public Todo getTodo(Long id){
        return todoRepocitory.findById(id).orElseThrow(()-> new RuntimeException("Not Fount"));
    }

    public void deleteTodo(Long id){
        todoRepocitory.deleteById(id);
    }

    public Todo updade(Long id){
        Todo temp=todoRepocitory.findById(id).orElseThrow(()-> new RuntimeException("Not Fount"));
        temp.setCompleted(!temp.isCompleted());
        return todoRepocitory.save(temp);
    }

    public List<Todo> getAll(){
        return todoRepocitory.findAll();
    }

    public void deleteAll(){
        todoRepocitory.deleteAll();
    }
}

