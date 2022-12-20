package sg.edu.nus.iss.day14revision.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import sg.edu.nus.iss.day14revision.models.Person;

@Service
public class PersonService {

    private List<Person> persons = new ArrayList<Person>();

    public PersonService() {
        persons.add(new Person("Mark", "Zuckerberg"));
        persons.add(new Person("Elon", "Musk"));
    }

    // Returns the List of Person
    public List<Person> getPersons() {
        return this.persons;
    }

    // Adds a new person into the List<Person>
    public void addPerson(Person p) {
        //persons.add(new Person(p.getFirstName(), p.getLastName()));
        persons.add(p);
    }
}
