package se.experis.lcarls.actorapi.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.experis.lcarls.actorapi.Models.Actor;

public interface ActorRepository extends JpaRepository<Actor, Integer> {
}
