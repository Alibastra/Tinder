package ru.JavaSeniors.Tinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.JavaSeniors.Tinder.model.SearchObject;

import java.util.List;

@Repository
public interface SearchObjectRepository extends JpaRepository<SearchObject,Long> {
    String GET_ID = "select so.id from SearchObject so where so.description = :description";
    String GET_DESCRIPTION = "select so.description from SearchObject so where so.id = :id";


    @Query(value = GET_ID)
    Long getId(@Param("description") String description);

    @Query(value = GET_DESCRIPTION)
    String getDesc(@Param("id") Long id);
}
