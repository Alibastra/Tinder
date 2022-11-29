package ru.JavaSeniors.Tinder.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.JavaSeniors.Tinder.model.Sympathy;
import ru.JavaSeniors.Tinder.model.SympathyInfo;
import ru.JavaSeniors.Tinder.repository.StatusRepository;
import ru.JavaSeniors.Tinder.repository.SympathyRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/sympathies")
public class SympathyController {
    public final String STATUS_LOVE = "Любит";
    public final String STATUS_RECIPROCITY = "Взаимность";
    @Autowired
    private SympathyRepository sympathyRepository;
    @Autowired
    private StatusRepository statusRepository;

    /**
     * Поиск подходящий фаворитов для текущего пользователя
     * @param id идентификтор текущего пользователя
     * @return лист идентификаторов акет подходящих людей
     */
    @GetMapping("/{id}/searchFavorite")
    public List<Long> getForSearch(@PathVariable("id") Long id) {
        return sympathyRepository.getForSearch(id);
    }

    /**
     * Доваленные фавориты текущего пользователя
     * @param id идентификтор текущего пользователя
     * @return лист идентификаторов акет фаворитов
     */
    @GetMapping("/{id}/currentFavorite")
    public List<Long> getFavorites(@PathVariable("id") Long id) {
        return sympathyRepository.getFavorites(id);
    }

    /**
     * Создание новой симпатии
     * @param sympathy объект класса с информауие о симпатии
     */
    @PostMapping("/createSympathies")
    public void createSympathy(@RequestBody SympathyInfo sympathy) {
        if (sympathy.getStatus().equals(STATUS_LOVE))
        {
            Optional<String> statusWhom = Optional.ofNullable(sympathyRepository.getStatusCurrentFavorite(sympathy.getWho(), sympathy.getWhom()));
            if (statusWhom.isPresent()) {
                if (statusWhom.get().equals(STATUS_LOVE)) {
                    sympathy.setStatus(STATUS_RECIPROCITY);
                    Long idWhom = sympathyRepository.getIdCurrentFavorite(sympathy.getWho(), sympathy.getWhom());
                    sympathyRepository.save(new Sympathy(idWhom, sympathy.getWhom(), sympathy.getWho(), statusRepository.getId(sympathy.getStatus())));
                }
            }
        }
        sympathyRepository.save(new Sympathy(null, sympathy.getWho(), sympathy.getWhom(), statusRepository.getId(sympathy.getStatus())));
    }

}
