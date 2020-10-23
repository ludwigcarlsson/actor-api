package se.experis.lcarls.actorapi.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.experis.lcarls.actorapi.Models.CommonResponse;
import se.experis.lcarls.actorapi.Models.Movie;
import se.experis.lcarls.actorapi.Repositories.MovieRepository;
import se.experis.lcarls.actorapi.Utils.DatabaseConfiguration.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
public class MovieController {

    @Autowired
    private MovieRepository repository;

    @GetMapping("/movie/all")
    public ResponseEntity<CommonResponse> getAllMovies(HttpServletRequest req) {
        Command command = new Command(req);

        CommonResponse cr = new CommonResponse();
        cr.data = repository.findAll();
        cr.msg = "All movies";

        HttpStatus res = HttpStatus.OK;

        command.setRes(res);
        return new ResponseEntity<>(cr, res);
    }

    @GetMapping("/movie/{id}")
    public ResponseEntity<CommonResponse> getMovieById(HttpServletRequest req, @PathVariable("id") Integer id) {
        Command command = new Command(req);

        CommonResponse cr = new CommonResponse();
        HttpStatus res;

        if (repository.existsById(id)) {
            cr.data = repository.findById(id);
            cr.msg = "Movie with id: "+id;
            res = HttpStatus.OK;
        } else {
            cr.data = null;
            cr.msg = "No movie with id: "+id+" found.";
            res = HttpStatus.NOT_FOUND;
        }

        command.setRes(res);
        return new ResponseEntity<>(cr, res);
    }

    @PostMapping("/movie/")
    public ResponseEntity<CommonResponse> createMovie(HttpServletRequest req, HttpServletResponse response, @RequestBody Movie movie) {
        Command command = new Command(req);

        movie = repository.save(movie);

        CommonResponse cr = new CommonResponse();
        cr.data = movie;
        cr.msg = "All movies";

        HttpStatus res = HttpStatus.OK;

        response.addHeader("Location", "/movie/"+movie.id);

        command.setRes(res);
        return new ResponseEntity<>(cr, res);
    }

    @PatchMapping("/movie/{id}")
    public ResponseEntity<CommonResponse> updateMovie(HttpServletRequest req, HttpServletResponse response, @RequestBody Movie patchMovie, @PathVariable Integer id) {
        Command command = new Command(req);

        CommonResponse cr = new CommonResponse();
        HttpStatus res;

        if (repository.existsById(id)) {
            Optional<Movie> movieRepo  = repository.findById(id);
            Movie movie = movieRepo.get();

            if (patchMovie.title != null) {
                movie.title = patchMovie.title;
            }
            if (patchMovie.genre != null) {
                movie.genre = patchMovie.genre;
            }
            if (patchMovie.releaseDate != null) {
                movie.releaseDate = patchMovie.releaseDate;
            }
            if (patchMovie.actors != null) {
                movie.actors = patchMovie.actors;
            }
            repository.save(movie);

            cr.data = movie;
            cr.msg = "Updated movie with id: "+movie.id;
            res = HttpStatus.OK;
            response.addHeader("Location", "/movie/"+movie.id);

        } else {
            cr.msg = "Could not find movie with id: "+id;
            res = HttpStatus.NOT_FOUND;
        }

        command.setRes(res);
        return new ResponseEntity<>(cr, res);
    }

    @PutMapping("/movie/{id}")
    public ResponseEntity<CommonResponse> replaceMovie(HttpServletRequest req, HttpServletResponse response, @RequestBody Movie newMovie, @PathVariable Integer id) {
        Command command = new Command(req);

        CommonResponse cr = new CommonResponse();
        HttpStatus res;

        if (repository.existsById(id)) {
            Optional<Movie> movieRepo = repository.findById(id);
            Movie movie = movieRepo.get();

            movie.title = newMovie.title;

            movie.genre = newMovie.genre;

            movie.releaseDate = newMovie.releaseDate;

            movie.actors = newMovie.actors;

            repository.save(movie);

            cr.data = movie;
            cr.msg = "Replaced movie with id: "+movie.id;
            res = HttpStatus.OK;
            response.addHeader("Location", "/movie/"+movie.id);

        } else {
            cr.msg = "Could not find movie with id: "+id;
            res = HttpStatus.NOT_FOUND;
        }

        command.setRes(res);
        return new ResponseEntity<>(cr, res);
    }

    @DeleteMapping("/movie/{id}")
    public ResponseEntity<CommonResponse> deleteMovieById(HttpServletRequest req, @PathVariable Integer id) {
        Command command = new Command(req);

        CommonResponse cr = new CommonResponse();
        HttpStatus res;

        if (repository.existsById(id)) {
            repository.deleteById(id);

            cr.msg = "Deleted movie with id: "+id;
            res = HttpStatus.OK;
        } else {
            cr.msg = "Could not find movie with id: "+id;
            res = HttpStatus.NOT_FOUND;
        }

        command.setRes(res);
        return new ResponseEntity<>(cr, res);
    }
}
