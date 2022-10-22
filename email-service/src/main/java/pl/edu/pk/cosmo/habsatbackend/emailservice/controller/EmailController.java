package pl.edu.pk.cosmo.habsatbackend.emailservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pk.cosmo.habsatbackend.emailservice.entity.Email;

@RestController
@RequestMapping("/email-service")
public class EmailController {

    @PostMapping("/send")
    public ResponseEntity<String> sendMail(@RequestBody Email email) {
        return ResponseEntity.ok(new String("Email has been sent"));
    }

    @GetMapping()
    public String hello() {
        return "hello";
    }

}
