package se.experis.lcarls.actorapi.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.experis.lcarls.actorapi.Models.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
