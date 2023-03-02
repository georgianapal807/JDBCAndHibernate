package sda.hibernate.theory.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class EmployeeCopy {
    public class Employee {

        private Long id;

        private String name;

        private double salary;

        private String position;

        @Override //suprascriem to String si ne va afisa doar id si position
        public String toString(){
            return "id ->" + id +  " position -> " + position;
        }
    }
}
