package pl.akademiakodu.service;


import pl.akademiakodu.model.User;

import java.util.List;

public interface UserService  {
    public User findUserByEmail(String email);
    public void updateUser(User user, Integer id);
    public void saveUser(User user);
    public void deleteUser(Integer id);
    public User getUserById(Integer id);
    public boolean isEnable(String email);
    public List<User> notActive();
}
