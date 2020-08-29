package pl.admwk.springuserregistration.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.admwk.springuserregistration.service.UserService;
import pl.admwk.springuserregistration.service.UserServiceImpl;
import pl.admwk.springuserregistration.web.dto.UserRegistrationDto;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    UserService userService;
    @Autowired
    public UserRegistrationController(UserServiceImpl userService) {
        this.userService = userService;
    }
    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto(){
        return new UserRegistrationDto();
    }
    @GetMapping
    public String showForm(){
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto){
        userService.save(registrationDto);
        return "redirect:/registration?success";
    }
}
