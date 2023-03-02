package sda.hibernate.theory.relationship.onetomany;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author") //(AuthorToList<Book>)
    List<Book> books;

}
