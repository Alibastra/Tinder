package ru.JavaSeniors.Tinder.model;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
//Информация о человеке, которая приходит от клиента
public class PersonFromClient {
    private Long id;
    private String name;
    private String gender;
    private String searchObject;
    private String description;
}
