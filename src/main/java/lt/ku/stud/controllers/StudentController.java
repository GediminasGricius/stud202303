package lt.ku.stud.controllers;

import lt.ku.stud.entities.Group;
import lt.ku.stud.entities.Student;
import lt.ku.stud.repositories.GroupRepository;
import lt.ku.stud.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class StudentController {
    @Autowired
    public StudentRepository studentRepository;

    @Autowired
    public GroupRepository groupRepository;

    @GetMapping("/students")
    public String students(Model model){
        List<Student> students=studentRepository.findAll();
        model.addAttribute("students", students);
        return "students_list";
    }

     @GetMapping("/students/new")
    public String newStudent(Model model){
        List<Group> groups=groupRepository.findAll();
        model.addAttribute("groups",groups);
        return "student_new";
     }

     @PostMapping("/students/new")
    public String storeStudent(
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("email") String email,
            @RequestParam("group_id") Integer groupId
     ){
        Group g=groupRepository.getReferenceById(groupId);
        Student s=new Student(name, surname, email, g);
        studentRepository.save(s);
        return "redirect:/students";
     }


}
