package com.example.springjwt.ws;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {
    @GetMapping("/connect")
    public String get() {
        return "connect";
    }

}
