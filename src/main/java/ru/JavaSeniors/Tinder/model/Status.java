package ru.JavaSeniors.Tinder.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="statuses", schema = "tinder")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Status implements Serializable {
    @Id
    @Column
    private Long id;
    @Column(name = "description")
    private String description;

}
