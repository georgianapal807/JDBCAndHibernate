package sda.hibernate.theory.relationship.manytomany;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Gamer {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    // Gamer
    // id   | name
    // 1    | Matei
    // 2    | Luca


    // Game
    // id   | title
    // 1    | "Assasin Cred"
    // 2    | "SpiderMan"


    // Table for join
    // gamer_id | game_id
    // 1        | 1
    // 1        | 2
    // 2        | 1
    // 2        | 2

    @JoinTable(
            name = "books",
            joinColumns = {@JoinColumn(name = "person_id")},
            inverseJoinColumns = {@JoinColumn(name = "book_id")}
    )
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Game> games;
}
