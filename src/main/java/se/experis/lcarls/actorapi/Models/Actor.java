package se.experis.lcarls.actorapi.Models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    public String firstName;

    public String lastName;

    public String DoB;

    public String urlToImdb;

    @JsonGetter("movies")
    public List<String> actors() {
        return movies.stream()
                .map(movie -> {
                    return "/api/v1/movie/" + movie.id;
                }).collect(Collectors.toList());
    } // create a list of movies that the actor has starred in

    @ManyToMany(mappedBy = "actors", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    public Set<Movie> movies = new HashSet<Movie>();

}
