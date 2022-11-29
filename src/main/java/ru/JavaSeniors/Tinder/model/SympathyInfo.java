package ru.JavaSeniors.Tinder.model;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class SympathyInfo{
    private Long who;
    private Long whom;
    private String status;
}
