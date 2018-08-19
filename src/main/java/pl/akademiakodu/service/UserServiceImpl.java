package pl.akademiakodu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import pl.akademiakodu.model.Role;
import pl.akademiakodu.model.User;
import pl.akademiakodu.other.UserData;
import pl.akademiakodu.repository.RoleRepository;
import pl.akademiakodu.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.akademiakodu.repository.UserRoleRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service("userService")

public class UserServiceImpl implements UserService {


    @Autowired
    UserData userData;
    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;
    @Qualifier("roleRepository")
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void updateUser(User user, Integer id) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setId(id);
        user.setActive(0);
        Role userRole = roleRepository.findByRole(userData.getUserRole());
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Integer id) {
        userRoleRepository.deleteById(id);
        userRepository.deleteById(id);
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.getOne(id);
    }

    @Override
    public boolean isEnable(String email) {
        User user = userRepository.findByEmail(email);
    return user.getActive() == 1;
    }

    @Override
    public List<User> notActive() {
        return userRepository.findByActive(0);
    }


}
