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

    @Column(nullable = false)
    public String firstName;

    @Column(nullable = false)
    public String lastName;

    @Column(nullable = false)
    public String DoB;

    @Column(nullable = false)
    public String urlToImdb;

    @JsonGetter("movies")
    public List<String> actors() {
        return movies.stream()
                .map(movie -> {
                    return "/movies/" + movie.id;
                }).collect(Collectors.toList());
    }

    @ManyToMany(mappedBy = "actors", fetch = FetchType.LAZY)
    public Set<Movie> movies = new HashSet<Movie>();

    //@Column
    //public String mainMovie;


}
