package sda.hibernate.theory.relationship.onetoone;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Persoana {
// id | name | person_cnp
// 1 | "Alex" | 1


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    //daca introducem o persoana cu id 1 si name si cnp automat cnpul de aici se va insera si in tabla cnp
    @JoinColumn(name = "person_cnp", referencedColumnName = "id") //e foregin key
    private CNP cnp;
}
