package com.petHosting.service;

import com.petHosting.dto.ModifyUserDTO;
import com.petHosting.dto.ResponseUserDTO;
import com.petHosting.dto.UserDTO;
import com.petHosting.entity.Role;
import com.petHosting.entity.Team;
import com.petHosting.entity.User;
import com.petHosting.repository.UserRepository;
import com.petHosting.util.UserDTOBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    TeamService teamService;

    public User add(UserDTO dto, Set<Role> roles) {

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEnabled(true);
        user.setCreatedAt(new Date(System.currentTimeMillis()));
        user.setCreatedBy("system");
        user.setUpdatedAt(new Date(System.currentTimeMillis()));
        user.setUpdatedBy("system");
//        Set<Role> roles = Set.of(new Role("User"),new Role( "LeagueManager"));
        user.setRoles(roles);
        userRepository.save(user);
        return user;
    }

    public boolean emailAlreadyUsed(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null)
            return false;
        return true;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<ResponseUserDTO> findAll() {
        List<ResponseUserDTO> userDTOS = userRepository.findAll().stream()
                .filter(user -> !user.getEmail().equals("admin@yahoo.com"))
                .map(user -> {
                    ResponseUserDTO userDTO = new ResponseUserDTO();
                    userDTO.setFirstName(user.getFirstName());
                    userDTO.setLastName(user.getLastName());
                    userDTO.setEmail(user.getEmail());
                    userDTO.setRoles(user.getRoles().stream().map(roleObj -> roleObj.getRole()).collect(Collectors.toSet()));
                    userDTO.setId(user.getId());
                    return userDTO;
                }).collect(Collectors.toList());
        return userDTOS;
    }

    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    public ResponseUserDTO modifyUser(ModifyUserDTO modifyUserDTO) {
        User user = findById(modifyUserDTO.getId()).get();
        if (!modifyUserDTO.getFirstName().equals(""))
            user.setFirstName(modifyUserDTO.getFirstName());
        if (!modifyUserDTO.getLastName().equals(""))
            user.setLastName(modifyUserDTO.getLastName());
        if (!modifyUserDTO.getEmail().equals(""))
            user.setEmail(modifyUserDTO.getEmail());
        if (!modifyUserDTO.getRoles().isEmpty())
            user.setRoles(modifyUserDTO.getRoles().stream()
                    .map(roleObj -> {
                                Role role = new Role();
                                role.setRole(roleObj);
                                return role;
                            }

                    ).collect(Collectors.toSet()));
        if (!modifyUserDTO.getPassword().equals(""))
            user.setPassword(passwordEncoder.encode(modifyUserDTO.getPassword()));
        userRepository.save(user);
        ResponseUserDTO responseUserDTO = UserDTOBuilder.build(user);

        return responseUserDTO;
    }

    public ResponseUserDTO delete(User user) {
        Optional<Team> team = teamService.findByUser(user);
        if (team.isPresent())
            teamService.delete(team.get());
        this.userRepository.delete(user);
        return UserDTOBuilder.build(user);
    }

}
