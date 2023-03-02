package sda.hibernate.theory.embedded;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity //clasa care e adnotata cu entity o sa fie tabela in baza de date
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    // Address
    @Embedded
    private Address address;

}
