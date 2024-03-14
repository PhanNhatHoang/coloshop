package com.web.coloshop.Service;

import com.web.coloshop.Repository.UserRepository;
import com.web.coloshop.model.Role;
import com.web.coloshop.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    public List<User> findAll() {
        return repo.findAll();
    }
    public User save(User user){
        return repo.save(user);
    }
    public User findByEmail(String email) {
        return repo.findByEmail(email);
    }
    public User findByPass(String pass) {
        return repo.findByPass(pass);
    }
    public void deleteById(Long id) {
        User user = repo.getById(id);
        user.set_activated(false);
        user.set_deleted(true);
        repo.save(user);
    }
    public void enabledById(Long id) {
        User user = repo.getById(id);
        user.set_activated(true);
        user.set_deleted(false);
        repo.save(user);
    }
    public void updateUserRole(Long userId, Long roleId) {
        User user = repo.findById(userId).orElse(null);
        if (user != null) {
            Role role = new Role();
            role.setId(roleId);
            user.setRole(role);
            repo.save(user);
        }
    }
}
