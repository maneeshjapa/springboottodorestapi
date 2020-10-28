package com.sampleAPI.springbootrestapitests.todoitems;

import com.sampleAPI.springbootrestapitests.users.Users;
import com.sampleAPI.springbootrestapitests.users.UsersRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users/{userid}")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;
    private UsersRepository usersRepository;

    public TodoController(UsersRepository usersRepository) {
        this.todoRepository = todoRepository;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/todoitems")
    public List<TodoItems> getAllTodoItems(@PathVariable(value = "userid") int userid) throws NotFoundException {
        usersRepository.findById(userid).orElseThrow(() -> new NotFoundException("User not found on :: "+userid));

        return todoRepository.findAll();
    }

    @GetMapping("/todoitems/{todoid}")
    public ResponseEntity<TodoItems> getTodoItemById(@PathVariable(value = "userid") int userid, @PathVariable(value = "todoid") int todoid) throws NotFoundException{
        usersRepository.findById(userid).orElseThrow(() -> new NotFoundException("User not found on :: "+userid));
        Optional<TodoItems> todoItem = Optional.ofNullable(todoRepository.findById(todoid).orElseThrow(() -> new NotFoundException("Todo note not found for todoId :: " + todoid)));
        return ResponseEntity.ok().body(todoItem.get());
    }

    @PostMapping("/todoitems")
    public TodoItems createTodoItem(@PathVariable(value = "userid") int userid, @RequestBody TodoItems todoitem) throws NotFoundException{
       Optional<Users> optionaluser = Optional.ofNullable(usersRepository.findById(userid).orElseThrow(() -> new NotFoundException("User not found on :: " + userid)));
       todoitem.setUser(optionaluser.get());
       return todoRepository.save(todoitem);
    }

    @PutMapping("/todoitems/{todoid}")
    public ResponseEntity<TodoItems> updateTodoItem(@PathVariable(value = "userid") int userid, @PathVariable(value = "todoid") int todoid, @RequestBody TodoItems todoitemdetails) throws NotFoundException{
        Optional<Users> optionaluser = Optional.ofNullable(usersRepository.findById(userid).orElseThrow(() -> new NotFoundException("User not found on :: " + userid)));
        TodoItems optionalitem = todoRepository.findById(todoid).orElseThrow(() -> new NotFoundException("Todo Item not found on :: " + todoid));
        optionalitem.setUser(optionaluser.get());
        optionalitem.setNotes(todoitemdetails.getNotes());
        optionalitem.setUpdatedDate();

        final TodoItems updatedTodoItem = todoRepository.save(optionalitem);
        return ResponseEntity.ok().body(updatedTodoItem);
    }

    @DeleteMapping("/todoitems/{todoid}")
    public Map<String, Boolean> deleteTodoItem(@PathVariable(value = "userid") int userid, @PathVariable(value = "todoid") int todoid) throws NotFoundException{
        usersRepository.findById(userid).orElseThrow(() -> new NotFoundException("User not found on :: "+userid));
        TodoItems todoitem = todoRepository.findById(todoid).orElseThrow(() -> new NotFoundException("Todo Item not found on :: "+todoid));
        todoRepository.delete(todoitem);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Todo Item deleted successfully", Boolean.TRUE);
        return response;
    }
}
