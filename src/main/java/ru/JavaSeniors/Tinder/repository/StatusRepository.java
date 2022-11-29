package ru.JavaSeniors.Tinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.JavaSeniors.Tinder.model.Status;

import java.util.List;

@Repository
public interface StatusRepository extends JpaRepository<Status,Long> {
    String GET_ID = "select s.id from Status s where s.description = :description";
    String GET_DESCRIPTION = "select s.description from Status s where s.id = :id";


    @Query(value = GET_ID)
    Long getId(@Param("description") String description);

    @Query(value = GET_DESCRIPTION)
    String getDesc(@Param("id") Long id);
}
