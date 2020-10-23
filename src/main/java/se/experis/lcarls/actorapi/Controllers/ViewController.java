package se.experis.lcarls.actorapi.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import se.experis.lcarls.actorapi.Repositories.ActorRepository;
import se.experis.lcarls.actorapi.Repositories.MovieRepository;

import java.util.Optional;


@Controller
public class ViewController {

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/")
    public String getHomepage(Model model) {

        model.addAttribute("randMovie", movieRepository.findById(1));
        model.addAttribute("    ", movieRepository.findAll());

        return "home";
    }

    @GetMapping("/actor/{id}")
    public String getSpecificActor(Model model, @PathVariable int id) {
        model.addAttribute("actor", actorRepository.findById(id));
        return "actor";
    }

    @GetMapping("/actors")
    public String getAllActors(Model model) {
        model.addAttribute("actors", actorRepository.findAll());
        return "actors";
    }

    @GetMapping("/movies")
    public String getAllMovies(Model model) {
        model.addAttribute("movies", movieRepository.findAll());
        return "movies";
    }
}
