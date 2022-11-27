package pl.edu.pk.cosmo.habsatbackend.gateway.service;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class cont {

    @GetMapping("/test")
    public String test(Principal principal) {
        // Why does Principal not work, but 16 line works?
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
