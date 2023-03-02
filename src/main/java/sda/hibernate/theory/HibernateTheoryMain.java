package sda.hibernate.theory;

import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import sda.hibernate.theory.embedded.Address;
import sda.hibernate.theory.embedded.Person;
import sda.hibernate.theory.entity.Employee;
import sda.hibernate.theory.entity.EmployeeCopy;
import sda.hibernate.theory.relationship.manytomany.Game;
import sda.hibernate.theory.relationship.manytomany.Gamer;
import sda.hibernate.theory.relationship.onetomany.Author;
import sda.hibernate.theory.relationship.onetomany.Book;
import sda.hibernate.theory.relationship.onetoone.CNP;
import sda.hibernate.theory.relationship.onetoone.Persoana;

import java.util.Arrays;
import java.util.List;

public class HibernateTheoryMain {

    public static void insertEmployee() {
        System.out.println("Persist employee...");
        try (SessionFactory sf = new Configuration()    //create a new resource and with try it will close automatically
                .configure("hibernate.cfg.xml")
                .buildSessionFactory()) {

            try (Session session = sf.openSession()) {
                Transaction transaction = session.beginTransaction();
                Employee employee = new Employee();
                employee.setName("Adrian");
                employee.setPosition("Manager");
                employee.setSalary(3000);

                // insert into employee values(.....)
                session.persist(employee);

                transaction.commit();

            }

        }
    }

    static SessionFactory sf = new Configuration()    //create a new resource and with try it will close automatically
            .configure("hibernate.cfg.xml")
            .buildSessionFactory();

    public static void insertManyEmployees(Employee... e) {
        System.out.println("Persist employee");
        try (Session session = sf.openSession()) {
            Transaction transaction = session.beginTransaction();

            // session.persist(e);

            Arrays.stream(e).forEach(session::persist);
            transaction.commit();

        }
    }

    public static void InsertOneEmployee(Employee e) {
        try (SessionFactory sf = new Configuration()    //create a new resource and with try it will close automatically
                .configure("hibernate.cfg.xml")
                .buildSessionFactory()) {
            System.out.println("Persist employee");
            try (Session session = sf.openSession()) {
                Transaction transaction = session.beginTransaction();

                session.persist(e);

                transaction.commit();

            }
        }
    }

    public static Employee getEmployeeById(int id) {
        try (Session session = sf.openSession()) {
            return session.get(Employee.class, id);
        }
    }

    public static List<Employee> getEmployees() {
        try (Session session = sf.openSession()) {

            //SELECT * FROM EMPLOYEE ----> QUERY

            //Assemble query
            JpaCriteriaQuery<Employee> jpaCriteriaQuery = session.getCriteriaBuilder()
                    .createQuery(Employee.class);//result object will be of type Employee

            jpaCriteriaQuery.from(Employee.class); //*FROM EMPLOYEE

            //Create query
            TypedQuery<Employee> typedQuery = session.createQuery(jpaCriteriaQuery);

            //Execute query -> get ResultSet(JDBC analogy) -> transform resultSet into List<Employee>
            return typedQuery.getResultList();
        }
    }

    public static void updateEmployee(Employee e) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();

            session.merge(e);

            session.getTransaction().commit();
        }
    }

    public static void deleteEmployeeById(int id) {
        // Employee e = search employee with id 1
        // delete e
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Employee e = getEmployeeById(id);
            if (e == null) {
                System.out.println("Employee with id: " + id + " doesn't exist");
            } else {
                session.remove(e);
            }
            session.getTransaction().commit();
        }
    }

    public static void voidInsertPerson() {
        try (Session session = sf.openSession()) {
            session.beginTransaction();

            Address address = new Address();
            address.setCity("Iasi");
            address.setNumber(1);
            address.setCountry("Romania");

            Person p = new Person();
            p.setName("Matei");
            p.setAddress(address);

            session.persist(p);

            session.getTransaction().commit();
        }
    }

    // 1:1 -> Persoana : CNP

    public static void insertPersoanaWithCNP() {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            CNP cnp = new CNP();
            cnp.setValue("11111111111");

            Persoana persoana = new Persoana();
            persoana.setName("Matei");
            persoana.setCnp(cnp);

            session.persist(persoana);

            session.getTransaction().commit();
        }
    }

    //1:N -> Author : List<Book>
    public static void insertAuthorWithBooks() {
        try (Session session = sf.openSession()) {
            session.beginTransaction();

            Book b1 = new Book();
            Book b2 = new Book();
            b1.setTitle("Book n1");
            b2.setTitle("Book n2");

            List<Book> books = List.of(b1, b2);

            Author a1 = new Author();
            a1.setName("Mihai");

            b1.setAuthor(a1);
            b2.setAuthor(a1);

            a1.setBooks(books);

            session.persist(a1);

            session.getTransaction().commit();
        }
    }

    public static void insertGamersAndGames() {
        try (Session session = sf.openSession()) {
            session.beginTransaction();

            Gamer gamer1 = new Gamer();
            Gamer gamer2 = new Gamer();
            gamer1.setName("Matei");
            gamer2.setName("Luca");

            List<Gamer> gamers = List.of(gamer1, gamer2);
            Game game1 = new Game();
            Game game2 = new Game();
            game1.setTitle("Assassin's Creed");
            game2.setTitle("Spider Man");
            game1.setGamers(gamers);
            game2.setGamers(gamers);

            List<Game> games = List.of(game1, game2);
            gamer1.setGames(games);
            gamer2.setGames(games);

            session.persist(gamer1);
            session.persist(gamer2);

            session.getTransaction().commit();
        }
    }

    public static void main(String[] args) {
//
//        insertEmployee();
//        Employee e1 = new Employee();
//        e1.setName("Vlad");
//        Employee e2 = new Employee();
//        e2.setName("Andrei");
//        insertManyEmployees(e1, e2);
//        Employee e3 = new Employee();
//        e3.setName("Marius");
//        e3.setSalary(3400);
//        e3.setPosition("Developer");
//        InsertOneEmployee(e3);
//        System.out.println(getEmployeeById(1));
//        double var = getEmployeeById(1).getSalary();
//        System.out.println(var);
//        List<Employee> employees = getEmployees();
//        for (Employee e : employees) {
//            System.out.println(e);
//        }
//        Employee e = new Employee();
//        e.setId(102L);
//        e.setName("Alexandru");
//        e.setPosition("Software Engineer");
//        e.setSalary(3000);
//        updateEmployee(e);
//
//        Employee e = new Employee();
//        e.setName("Luca");
//        e.setSalary(1200);
//        e.setPosition("Accountant");
//        InsertOneEmployee(e);
//
//        System.out.println(getEmployeeById(152));
//        deleteEmployeeById(152);
//
//        Emedded si Emeddable
//        voidInsertPerson();
//
//        insertPersoanaWithCNP();
//
//        insertAuthorWithBooks();

        insertGamersAndGames();

    }
}

