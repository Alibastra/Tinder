package ru.JavaSeniors.Tinder.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.JavaSeniors.Tinder.enums.Relations;
import ru.JavaSeniors.Tinder.model.PersonInfo;
import ru.JavaSeniors.Tinder.model.Sympathy;
import ru.JavaSeniors.Tinder.model.SympathyInfo;
import ru.JavaSeniors.Tinder.repository.PersonRepository;
import ru.JavaSeniors.Tinder.repository.StatusRepository;
import ru.JavaSeniors.Tinder.repository.SympathyRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/sympathies")
public class SympathyController {
    public final String MAN = "Сударь";
    @Autowired
    private SympathyRepository sympathyRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PersonController personController;

    /**
     * Поиск подходящий фаворитов для текущего пользователя
     *
     * @param id идентификтор текущего пользователя
     * @return лист идентификаторов акет подходящих людей
     */
    @GetMapping("/{id}/searchFavorites")
    public List<Long> getForSearch(@PathVariable("id") Long id) {
        return sympathyRepository.getForSearch(id);
    }

    /**
     * Доваленные фавориты текущего пользователя
     *
     * @param id идентификтор текущего пользователя
     * @return лист идентификаторов акет фаворитов
     */
    @GetMapping("/{id}/currentFavorite")
    public List<Long> getFavorite(@PathVariable("id") Long id) {
        return sympathyRepository.getFavorites(id);
    }

    @GetMapping("/currentSympathyString/{idWho}/{idWhom}")
    public String getSympathyString(@PathVariable("idWho") Long idWho,
                                    @PathVariable("idWhom") Long idWhom) {
        System.out.println("idWho " + idWho);
        System.out.println("idWhom " + idWhom);
        Optional<String> status = Optional.ofNullable(sympathyRepository.getStatusSympathy(idWho, idWhom));
        Optional<PersonInfo> person = personController.getPerson(idWhom);

        if (status.isPresent()) {
            switch (Relations.getByValue(status.get())) {
                case Reciprocity:
                    return Relations.Reciprocity.getStatus();
                case Love:
                    if (person.get().getGender().equals(MAN)) {
                        return Relations.LovedYouM.getStatus();
                    } else return Relations.LovedYouF.getStatus();
                default:
                    return "тру-ля-ля! да тра-ля-ля! Не любит, ну и не нужно!";
            }
        } else if (person.get().getGender().equals(MAN)) {
            return Relations.YouLovedM.getStatus();
        } else return Relations.YouLovedF.getStatus();
    }

    @GetMapping("/currentSympathyBoolean/{whoId}/{whomId}")
    public boolean getSympathyBoolean(@PathVariable("whoId") Long whoId,
                                      @PathVariable("whomId") Long whomId) {
        Optional<String> status = Optional.ofNullable(sympathyRepository.getStatusSympathy(whoId, whomId));
        System.out.println(status.filter(s -> !(s.equals(Relations.Hate.getStatus()))).isPresent());
        return status.filter(s -> !(s.equals(Relations.Hate.getStatus()))).isPresent();
    }


    /**
     * Создание новой симпатии
     *
     * @param sympathy объект класса с информауие о симпатии
     */
    @PostMapping("/createSympathy")
    public void createSympathy(@RequestBody SympathyInfo sympathy) {
        String status_who = Relations.Hate.getStatus();
        if (sympathy.getStatus()) {
            status_who = Relations.Love.getStatus();
            Optional<String> statusWhom = Optional.ofNullable(sympathyRepository.getStatusSympathy(sympathy.getWhom(), sympathy.getWho()));
            if (statusWhom.isPresent()) {
                if (statusWhom.get().equals(Relations.Love.getStatus())) {
                    status_who = Relations.Reciprocity.getStatus();
                    Long idWhom = sympathyRepository.getIdSympathy(sympathy.getWhom(), sympathy.getWho());
                    sympathyRepository.save(new Sympathy(idWhom, sympathy.getWhom(), sympathy.getWho(), statusRepository.getId(status_who)));
                }
            }
        }
        sympathyRepository.save(new Sympathy(null, sympathy.getWho(), sympathy.getWhom(), statusRepository.getId(status_who)));
    }
}
