package com.sampleAPI.springbootrestapitests.users;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class UsersController {

    @Autowired
    public UsersRepository usersRepository;

    @GetMapping("/users")
    public List<Users> getAllUsers(){
        return usersRepository.findAll();
    }

    @GetMapping("/users/{userid}")
    public ResponseEntity<Users> getUsersById(@PathVariable(value = "userid") int userId) throws NotFoundException{
        Users user = usersRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found on :: " + userId));
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/users")
    public Users createUser(@RequestBody Users user){
        return usersRepository.save(user);
    }

    @PutMapping("/users/{userid}")
    public ResponseEntity<Users> updateUser(@PathVariable(value = "userid") int userId, @RequestBody Users userDetails) throws NotFoundException{
        Users user = usersRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found on :: "+userId));

        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());

        final Users updatedUser = usersRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users/{userid}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "userid") int userId) throws NotFoundException{
        Users user = usersRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found on :: "+userId));
        usersRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted successfully", Boolean.TRUE);
        return response;

    }
}
