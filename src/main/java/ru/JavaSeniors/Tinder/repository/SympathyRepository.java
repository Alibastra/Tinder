package ru.JavaSeniors.Tinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import ru.JavaSeniors.Tinder.model.Sympathy;

import java.util.List;


@Repository
public interface SympathyRepository extends JpaRepository<Sympathy, Long> {
    String SEARCH_NEW_FAVORITES = "select p_find.id" +
            " from Person p_find," +
            " SearchObject so_find," +
            " Person p," +
            " SearchObject so" +
            " where p.id = :id" +
            " and p.searchObject  = so.id" +
            " and p_find.id != p.id" +
            " and p_find.searchObject  = so_find.id " +
            " and p_find.gender = case when so.description = 'Все' then p_find.gender else p.searchObject end" +
            " and p.gender = case when so_find.description = 'Все' then p.gender else p_find.searchObject end" +
            " and not exists (select 1" +
            "                from Sympathy s " +
            "                where ((s.who = p.id and s.whom = p_find.id) " +
            "                   or (s.who = p_find.id and s.whom = p.id)) " +
            "                        and s.status = (select id " +
            "                                        from Status st" +
            "                                        where st.description = 'Отверг'))";

    String CURRENT_FAVORITES = "select distinct case when s.whom = :id then s.who else s.whom end " +
            " from Person p" +
            " join Sympathy s on s.who = p.id" +
            " join Status st  on s.status  = st.id" +
            " where (p.id = :id or s.whom = :id)" +
            "    and (st.description = 'Любит' or st.description = 'Взаимность') " +
            "    and not exists (select 1" +
            "                    from Sympathy ss " +
            "                    where ss.who = s.whom  " +
            "                      and ss.whom = p.id " +
            "                      and ss.status = (select id " +
            "                                       from Status st" +
            "                                       where st.description = 'Отверг'))" ;
    String STATUS_CURRENT_FAVORITE = "select st.description" +
            " from Sympathy s " +
            " join Status st on st.id = s.status" +
            " where s.whom  = :idWhom" +
            "     and s.who = :idWho";

    String ID_CURRENT_FAVORITE = "select s.id" +
            " from Sympathy s " +
            " where s.whom  = :idWhom" +
            "     and s.who = :idWho";

    @Query(value = SEARCH_NEW_FAVORITES)
    List<Long> getForSearch(@PathVariable("id") Long id);

    @Query(value = CURRENT_FAVORITES)
    List<Long> getFavorites(@PathVariable("id") Long id);

    @Query(value = STATUS_CURRENT_FAVORITE)
    String getStatusSympathy(@Param("idWho") Long idWho,
                             @Param("idWhom") Long idWhom);
    @Query(value = ID_CURRENT_FAVORITE)
    Long getIdSympathy(@Param("idWho") Long idWho,
                       @Param("idWhom") Long idWhom);

}
