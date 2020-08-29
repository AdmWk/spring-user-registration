package pl.admwk.springuserregistration.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.admwk.springuserregistration.model.User;
import pl.admwk.springuserregistration.web.dto.UserRegistrationDto;

public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto registrationDto);
}
