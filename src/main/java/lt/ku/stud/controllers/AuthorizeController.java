package lt.ku.stud.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthorizeController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
