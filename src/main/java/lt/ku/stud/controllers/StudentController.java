package lt.ku.stud.controllers;

import lt.ku.stud.entities.Group;
import lt.ku.stud.entities.Student;
import lt.ku.stud.repositories.GroupRepository;
import lt.ku.stud.repositories.StudentRepository;
import lt.ku.stud.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class StudentController {
    @Autowired
    public StudentRepository studentRepository;

    @Autowired
    public GroupRepository groupRepository;

    @Autowired
    public FileStorageService fileStorageService;

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
            @RequestParam("group_id") Integer groupId,
            @RequestParam("agreement") MultipartFile agreement
     ){
        Group g=groupRepository.getReferenceById(groupId);
        Student s=new Student(name, surname, email, g);
        s.setAgreement(agreement.getOriginalFilename());
        studentRepository.save(s);

        fileStorageService.store(agreement, s.getId().toString() );

        return "redirect:/students";
     }

     @GetMapping("/students/{id}/agreement")
     @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable Integer id){
        Student s=studentRepository.getReferenceById(id);

        Resource r=fileStorageService.loadFile( s.getId().toString() );
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ s.getAgreement() +"\"")
                .body(r);
     }


}
