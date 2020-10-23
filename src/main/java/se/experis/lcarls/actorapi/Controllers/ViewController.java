package se.experis.lcarls.actorapi.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String getHomepage() {

        return "home";
    }
}
