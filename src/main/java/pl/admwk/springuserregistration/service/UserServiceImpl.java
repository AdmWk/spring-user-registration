package pl.admwk.springuserregistration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.admwk.springuserregistration.model.Role;
import pl.admwk.springuserregistration.model.Roles;
import pl.admwk.springuserregistration.model.User;
import pl.admwk.springuserregistration.repository.UserRepository;
import pl.admwk.springuserregistration.web.dto.UserRegistrationDto;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    UserRepository userRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(UserRegistrationDto registrationDto) {
        System.out.println(registrationDto);
        User user = new User(registrationDto.getFirstName(),registrationDto.getLastName(),
                            registrationDto.getEmail(),bCryptPasswordEncoder.encode(registrationDto.getPassword()), Arrays.asList(new Role(Roles.ROLE_USER.name())));
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(s);
        if(user ==null){
            throw new UsernameNotFoundException("Invalid username or password");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
       return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
