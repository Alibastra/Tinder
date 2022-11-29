package ru.JavaSeniors.Tinder.model;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
//Инфоомация о человеке, которая приходит к клиенту(описание отдельно картинкой)
public class PersonInfo {
    private Long id;
    private String name;
    private String gender;
    private String searchObject;
}
