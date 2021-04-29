package ru.otus.controller;

import org.springframework.web.bind.annotation.*;
import ru.otus.domain.User;
import ru.otus.service.UserService;

@RestController
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/user/{id}")
    public User getUserById(@PathVariable(name = "id") long id) {
        return userService.findById(id).orElse(null);
    }

    @GetMapping("/api/user")
    public User getUserByName(@RequestParam(name = "login") String login) {
        return userService.findByLogin(login).orElse(null);
    }

    @PostMapping("/api/user")
    public Long saveUser(@RequestBody User user) {
        return userService.save(user);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/user/random")
    public User findRandomUser() {
        return userService.findRandom();
    }
}
