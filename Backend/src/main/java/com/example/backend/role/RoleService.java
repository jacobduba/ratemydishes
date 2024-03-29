package com.example.backend.role;

import com.example.backend.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role createNewRole(String name) {
        if (roleRepository.findByName(name) != null) return null;
        Role role = new Role(name);
        roleRepository.save(role);
        return role;
    }

    public void giveRole(User user, Role role) {
        if (user == null || role == null) return;

        role.addUser(user);
        user.addRole(role);
        roleRepository.save(role);
    }
}
