package ru.JavaSeniors.Tinder.enums;

import lombok.AllArgsConstructor;

/**
 * Пол
 */
@AllArgsConstructor
public enum Gender {
    Man("Сударь"),
    Female("Сударыня");
    private final String gender;

    public String getGender() {
        return gender;
    }
}
