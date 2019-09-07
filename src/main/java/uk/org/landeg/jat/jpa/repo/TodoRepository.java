package uk.org.landeg.jat.jpa.repo;

import org.springframework.data.repository.CrudRepository;

import uk.org.landeg.jat.jpa.domain.TodoJpa;

public interface TodoRepository extends CrudRepository<TodoJpa, String>{

}
