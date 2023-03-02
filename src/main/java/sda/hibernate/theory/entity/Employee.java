package sda.hibernate.theory.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Entity: class Employee() -> table Employee(DB)
 */
@Entity
@Data
public class Employee {
    @Id //primary key
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 32, nullable = false)
    private String name;

    private double salary;

    private String position;

    // id | name | salary | position
    // name | position | id | salary

}
