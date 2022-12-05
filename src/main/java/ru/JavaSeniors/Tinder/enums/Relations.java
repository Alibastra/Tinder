package ru.JavaSeniors.Tinder.enums;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.*;

/**
 * Отношения
 */
@AllArgsConstructor
public enum Relations {
    Hate("Отверг"),
    Love("Любит"),
    Reciprocity("Взаимность"),
    LovedYouM("Вы любимы"),
    LovedYouF("Вы любима"),
    YouLovedM("Любима вами"),
    YouLovedF("Любим вами");
    private final String status;

    public String getStatus() {
        return status;
    }

    public static Relations getByValue(String status) {
        for (Relations rl : Relations.values()) {
            if (rl.getStatus().equals(status))
                return rl;
        }
        throw new IllegalArgumentException("No Relations with status " + status);
    }

}
