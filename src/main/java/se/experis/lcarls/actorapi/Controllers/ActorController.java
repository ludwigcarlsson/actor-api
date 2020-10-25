package se.experis.lcarls.actorapi.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.experis.lcarls.actorapi.Models.Actor;
import se.experis.lcarls.actorapi.Models.CommonResponse;
import se.experis.lcarls.actorapi.Repositories.ActorRepository;
import se.experis.lcarls.actorapi.Utils.DatabaseConfiguration.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;


@RestController
@RequestMapping(value = "/api/v1") // added prepending url to api-requests
public class ActorController {

    @Autowired
    private ActorRepository repository;

    @GetMapping("/")
    public ResponseEntity<CommonResponse> getRoot(HttpServletRequest req) {
        Command command = new Command(req);

        CommonResponse cr = new CommonResponse();
        cr.data = null;
        cr.msg = "Not implemented";

        HttpStatus res = HttpStatus.NOT_IMPLEMENTED;

        command.setRes(res);
        return new ResponseEntity<>(cr, res);
    }

    @GetMapping("/actor/all")
    public ResponseEntity<CommonResponse> getAllActors(HttpServletRequest req) {
        Command command = new Command(req);

        CommonResponse cr = new CommonResponse();
        cr.data = repository.findAll();
        cr.msg = "All actors";

        HttpStatus res = HttpStatus.OK;

        command.setRes(res);
        return new ResponseEntity<>(cr, res);
    }

    @GetMapping("/actor/{id}")
    public ResponseEntity<CommonResponse> getActorById(HttpServletRequest req, @PathVariable("id") Integer id) {
        Command command = new Command(req);

        CommonResponse cr = new CommonResponse();
        HttpStatus res;

        if (repository.existsById(id)) {
            cr.data = repository.findById(id);
            cr.msg = "Actor with id: "+id;
            res = HttpStatus.OK;
        } else {
            cr.data = null;
            cr.msg = "No actor with id: "+id+" found.";
            res = HttpStatus.NOT_FOUND;
        }

        command.setRes(res);
        return new ResponseEntity<>(cr, res);
    }

    @GetMapping("/actor/{id}/movies")
    public ResponseEntity<CommonResponse> getMoviesByActor(HttpServletRequest req, @PathVariable("id") Integer id) {
        Command command = new Command(req);

        CommonResponse cr = new CommonResponse();
        HttpStatus res;

        if (repository.existsById(id)) {
            Optional<Actor> actorRepo = repository.findById(id);
            Actor actor = actorRepo.get();
            if (!actor.movies.isEmpty()) {
                cr.data = actor.movies;
                cr.msg = "Movies by "+actor.firstName + " " + actor.lastName;
                res = HttpStatus.OK;
            } else {
                cr.msg = "This actor has no movies.";
                res = HttpStatus.NOT_FOUND;
            }

        } else {
            cr.data = null;
            cr.msg = "No actor with id: "+id+" found.";
            res = HttpStatus.NOT_FOUND;
        }

        command.setRes(res);
        return new ResponseEntity<>(cr, res);
    }

    @PostMapping("/actor/")
    public ResponseEntity<CommonResponse> createActor(HttpServletRequest req, HttpServletResponse response, @RequestBody Actor actor) {
        Command command = new Command(req);

        actor = repository.save(actor);

        CommonResponse cr = new CommonResponse();
        cr.data = actor;
        cr.msg = "All actors";

        HttpStatus res = HttpStatus.OK;

        response.addHeader("Location", "/actor/"+actor.id);

        command.setRes(res);
        return new ResponseEntity<>(cr, res);
    }

    @PatchMapping("/actor/{id}")
    public ResponseEntity<CommonResponse> updateActorById(HttpServletRequest req, HttpServletResponse response, @RequestBody Actor patchActor, @PathVariable Integer id) {
        Command command = new Command(req);

        CommonResponse cr = new CommonResponse();
        HttpStatus res;

        if (repository.existsById(id)) {
            Optional<Actor> actorRepo  = repository.findById(id);
            Actor actor = actorRepo.get();

            if (patchActor.firstName != null) {
                actor.firstName = patchActor.firstName;
            }
            if (patchActor.lastName != null) {
                actor.lastName = patchActor.lastName;
            }
            if (patchActor.DoB != null) {
                actor.DoB = patchActor.DoB;
            }
            if (patchActor.movies != null) {
                actor.movies = patchActor.movies;
            }
            repository.save(actor);

            cr.data = actor;
            cr.msg = "Updated actor with id: "+actor.id;
            res = HttpStatus.OK;
            response.addHeader("Location", "/actor/"+actor.id);

        } else {
            cr.msg = "Could not find actor with id: "+id;
            res = HttpStatus.NOT_FOUND;
        }

        command.setRes(res);
        return new ResponseEntity<>(cr, res);
    }

    @PutMapping("/actor/{id}")
    public ResponseEntity<CommonResponse> replaceActorById(HttpServletRequest req, HttpServletResponse response, @RequestBody Actor newActor, @PathVariable Integer id) {
        Command command = new Command(req);

        CommonResponse cr = new CommonResponse();
        HttpStatus res;

        if (repository.existsById(id)) {
            Optional<Actor> actorRepo = repository.findById(id);
            Actor actor = actorRepo.get();

            actor.firstName = newActor.firstName;

            actor.lastName = newActor.lastName;

            actor.DoB = newActor.DoB;

            actor.movies = newActor.movies;

            repository.save(actor);

            cr.data = actor;
            cr.msg = "Replaced actor with id: "+actor.id;
            res = HttpStatus.OK;
            response.addHeader("Location", "/actor/"+actor.id);

        } else {
            cr.msg = "Could not find actor with id: "+id;
            res = HttpStatus.NOT_FOUND;
        }

        command.setRes(res);
        return new ResponseEntity<>(cr, res);
    }

    @DeleteMapping("/actor/{id}")
    public ResponseEntity<CommonResponse> deleteActorById(HttpServletRequest req, @PathVariable Integer id) {
        Command command = new Command(req);

        CommonResponse cr = new CommonResponse();
        HttpStatus res;

        if (repository.existsById(id)) {
            repository.deleteById(id);

            cr.msg = "Deleted actor with id: "+id;
            res = HttpStatus.OK;
        } else {
            cr.msg = "Could not find actor with id: "+id;
            res = HttpStatus.NOT_FOUND;
        }

        command.setRes(res);
        return new ResponseEntity<>(cr, res);
    }

}
