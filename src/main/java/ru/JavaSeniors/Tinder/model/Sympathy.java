package ru.JavaSeniors.Tinder.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="sympathies", schema = "tinder")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sympathy implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column(name = "who")
    private Long who;
    @Column(name = "whom")
    private Long whom;
    @Column(name = "status")
    private Long status;
}
