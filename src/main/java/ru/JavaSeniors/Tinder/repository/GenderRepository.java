package ru.JavaSeniors.Tinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.JavaSeniors.Tinder.model.Gender;

import java.util.List;

@Repository
public interface GenderRepository extends JpaRepository<Gender,Long> {
    String GET_ID = "select g.id from Gender g  where g.description = :description";
    String GET_DESCRIPTION = "select g.description from Gender g where g.id = :id";

    @Query(value = GET_ID)
    Long getId(@Param("description") String description);

    @Query(value = GET_DESCRIPTION)
    String getDesc(@Param("id") Long id);

}
