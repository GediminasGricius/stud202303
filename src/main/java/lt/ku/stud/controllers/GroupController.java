package lt.ku.stud.controllers;

import lt.ku.stud.entities.Group;
import lt.ku.stud.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class GroupController {
    @Autowired
    public GroupRepository groupRepository;

    @GetMapping("/")
    public String groups(Model model){
        List<Group> groups= groupRepository.findAll();
        model.addAttribute("groups", groups);
        return "groups_list";
    }

    @GetMapping("/new")
    public String newGroup(){
        return "groups_new";
    }

    @PostMapping("/new")
    public String storeGroup(
            @RequestParam("name") String name,
            @RequestParam("year") Integer year
    ){
        Group g=new Group(name, year);
        groupRepository.save(g);
        return "redirect:/";
    }

    @GetMapping("/update/{id}")
    public String update(
        @PathVariable("id") Integer id,
        Model model
    ){
        Group g=groupRepository.getReferenceById(id);
        model.addAttribute("group", g);
        return "groups_update";
    }

    @PostMapping("/update/{id}")
    public String save(
            @PathVariable("id") Integer id,
            @RequestParam("name") String name,
            @RequestParam("year") Integer year
    ){
        Group g=groupRepository.getReferenceById(id);
        g.setName(name);
        g.setYear(year);
        groupRepository.save(g);

        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public  String delete(
            @PathVariable("id") Integer id
    ){
        groupRepository.deleteById(id);
        return "redirect:/";
    }

}
