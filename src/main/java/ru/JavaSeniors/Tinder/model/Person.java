package ru.JavaSeniors.Tinder.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="persons", schema = "tinder")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person implements Serializable {
    @Id
    @Column
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "gender")
    private Long gender;
    @Column(name = "search_object")
    private Long searchObject;
    @Column(name = "description")
    private String description;

}
