package coms309.people;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

@Repository
public interface PeopleRepository extends CrudRepository<Person, Integer> {
    List<Person> findByFirstName (String firstName);
    Person findById (long id);
}
