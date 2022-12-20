package sg.edu.nus.iss.day14revision.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import sg.edu.nus.iss.day14revision.models.Person;
import sg.edu.nus.iss.day14revision.models.PersonForm;
import sg.edu.nus.iss.day14revision.services.PersonService;

// This is done with RequestMapping to show that just using RequestMapping is possible but quite tedious
// RequestMapping should be placed here too but we are just showing other possibilities for now
@Controller
public class PersonController {
    
    private List<Person> personList = new ArrayList<>();
    
    // Autowired will do new PersonService();
    // This will be the end result, PersonService perSvc = new PersonService(); 
    // Which creates a List<Person> that contains Mark and Elon, see PersonService.java
    @Autowired
    PersonService perSvc;

    // The welcome and error messages are set in the application properties file
    // With @Value the end result will be
    // private String message = welcome.message; 
    // End result is (private String message = Spring Boot & Thymeleaf Revision;)
    @Value("${welcome.message}")
    private String message;

    @Value("${error.message}")
    private String errorMessage;

    // To run http://localhost:8085/ or /home or /index
    // Will bring user to index.html with the welcome message
    @RequestMapping(value={"/", "/home", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("message", message);
        return "index";
    }

    // Not part of the application
    // This is to test that we are able to retrieve List<Person> and display it on personList.html
    @RequestMapping(value="/testRetrieve", method = RequestMethod.GET, produces = "application/json")
    // @ResponseBody tells a controller that the object returned is automatically serialized into JSON 
    // and passed back into the HttpResponse object
    // @ResponseBody returns a json and usually used to check if we could get the values
    // return a List<Person> to the response body, without the @ResponseBody it will fail. Try yourself
    public @ResponseBody List<Person> getAllPersons() {
        // Get the List of Person from PersonService
        personList = perSvc.getPersons();
        // This returns the object personList and not the html cause it returns JSON
        return personList;
    }

    // To get the List of Person from PersonService then pass it over to personList.html using model
    @RequestMapping(value="/personList", method=RequestMethod.GET)
    public String personList(Model model) {
        // Get the List of Person from PersonService
        personList = perSvc.getPersons();
        
        model.addAttribute("persons", personList);

        return "personList";
    }

    // addPerson GETMapping
    @RequestMapping(value="/addPerson", method=RequestMethod.GET)
    public String showAddPersonPage(Model model) {
        // Added a PersonForm object so we can set the values of the object in addPerson.html
        PersonForm pForm = new PersonForm();
        model.addAttribute("personForm", pForm);
        // This will go to addPerson.html then once from there it submits, 
        // it will go to the RequestMapping /addPerson below
        return "addPerson"; 
    }

    // addPerson POSTMapping
    @RequestMapping(value="/addPerson", method=RequestMethod.POST)
    // We'll populate the personForm model attribute with data from the form in addPerson.html submitted to /addPerson using @ModelAttribute
    // ModelAttribute works like RequestParam to retrieve all the values and then call the setters on personForm object located in the argument here
    // It can also pass the values to subsequent html page addPerson.html
    public String savePerson(Model model, @ModelAttribute("personForm") PersonForm personForm) {

        String fName = personForm.getFirstName2();
        String lName = personForm.getLastName2();

        if (fName != null && fName.length() > 0 && lName != null && lName.length() > 0) {
            Person newPerson = new Person(fName, lName);
            perSvc.addPerson(newPerson);

            return "redirect:/personList";
        }
        model.addAttribute("errorMessage", errorMessage);
        return "addPerson";

        // Another way of doing it
        // if (!(fName != null && fName.length() > 0 && lName != null && lName.length() > 0)) {
        //     model.addAttribute("errorMessage", errorMessage);
        //     return "addPerson";
        // }
        //     Person newPerson = new Person(fName, lName);
        //     perSvc.addPerson(newPerson);

        //     return "personList";
    }

}
