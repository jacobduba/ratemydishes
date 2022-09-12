package coms309.people;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    @Query(value="select * from person a where a.first_name= :firstName", nativeQuery=true)
    List<Person> getPersonByFirstName(String firstName);
}
