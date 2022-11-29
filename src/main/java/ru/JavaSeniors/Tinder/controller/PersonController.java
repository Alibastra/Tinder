package ru.JavaSeniors.Tinder.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.JavaSeniors.Tinder.model.PersonFromClient;
import ru.JavaSeniors.Tinder.model.PersonInfo;
import ru.JavaSeniors.Tinder.model.Person;
import ru.JavaSeniors.Tinder.repository.GenderRepository;
import ru.JavaSeniors.Tinder.repository.PersonRepository;
import ru.JavaSeniors.Tinder.repository.SearchObjectRepository;
import ru.JavaSeniors.Tinder.service.TextToImageService;
import ru.JavaSeniors.Tinder.service.TextTranslateService;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/persons")
public class PersonController {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private GenderRepository genderRepository;
    @Autowired
    private SearchObjectRepository searchObjectRepository;
    @Autowired
    private TextTranslateService textTranslateService;
    @Autowired
    private TextToImageService textToImageService;

    /**
     * Метод возращает переведнный  текст на старорусский
     * @param text входнй текст
     * @return переведенный текст
     */
    @GetMapping("/textTranslate")
    public String getTextTranslation(String text) {
        return textTranslateService.textTranslate(text);
    }

    /**
     * Переводит текст в картинку
     * @param text входной текст
     * @return картинка
     */
    @GetMapping("/textToImage")
    public ResponseEntity<byte[]> getImage(String text) {
        return textToImageService.textToImage(text);
    }

    /**
     * Возращает клиенту информацию о человеке(анкету)
     * @param id идентификатор человека
     * @return информация о человеке
     */
    @GetMapping("/{id}")
    public Optional<PersonInfo> getPerson(@PathVariable("id") Long id) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isPresent()) {
            Person p = person.get();
            String genderDesc = genderRepository.getDesc((long) p.getGender());
            String search_objectDesc = searchObjectRepository.getDesc((long) p.getSearchObject());
            return Optional.of(new PersonInfo(p.getId(), p.getName(), genderDesc, search_objectDesc));
        }
        return Optional.empty();

    }

    /**
     * Возврат описания в картинке
     * @param id идентификатор человека
     * @return картинка с описанием из анкеты
     */

    @GetMapping("/{id}/description")
    public ResponseEntity<byte[]> getPersonDesc(@PathVariable("id") Long id) {
        Optional<Person> person = personRepository.findById(id);
        String text = "";
        if (person.isPresent()) {
            text = getTextTranslation(person.get().getDescription());
        }
        return getImage(text);
    }

    /**
     * Создание новой анкеты о человеке или редактирование старой
     * @param person
     */
    @PostMapping("/createOrUpdatePerson")
    public void createOrUpdatePerson(@RequestBody PersonFromClient person) {
        Optional<Long> genderId = Optional.ofNullable(genderRepository.getId(person.getGender()));
        Optional<Long> search_objectId = Optional.ofNullable(searchObjectRepository.getId(person.getSearchObject()));
        if (search_objectId.isPresent() && genderId.isPresent()) {
            personRepository.save(new Person(person.getId(), person.getName(), genderId.get(), search_objectId.get(), person.getDescription()));
        }
    }
}
