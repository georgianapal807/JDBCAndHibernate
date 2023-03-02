package sda.hibernate.theory.relationship.onetoone;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class CNP {
// id | value
// 1  | "11111111"

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String value;

}
