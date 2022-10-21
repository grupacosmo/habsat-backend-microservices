package pl.edu.pk.cosmo.habsatbackend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pk.cosmo.habsatbackend.entity.tmp.User;

// TODO DELETE
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @PostMapping("/login")
    public ResponseEntity<?> getUser(@RequestBody User user) {

        log.info(user.toString());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
