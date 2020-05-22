package ru.mcx73.gis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mcx73.gis.entity.Role;
import ru.mcx73.gis.entity.User;
import ru.mcx73.gis.repository.RoleRepository;
import ru.mcx73.gis.repository.UserRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl {
    private List<Role> list;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<Role> AllRole() {
        list = roleRepository.findAll();
        return list;
    }

    public boolean saveUserEndRoles() {

        roleRepository.saveAndFlush(new Role(1L, "ADMIN"));
        roleRepository.saveAndFlush(new Role(2L, "MODERATOR"));
        roleRepository.saveAndFlush(new Role(3L, "USER"));

        return true;
    }



}
