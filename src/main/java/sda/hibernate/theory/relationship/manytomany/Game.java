package sda.hibernate.theory.relationship.manytomany;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Game {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @ManyToMany
    private List<Gamer> gamers;

}
