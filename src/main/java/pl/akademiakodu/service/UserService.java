package pl.akademiakodu.service;


import pl.akademiakodu.model.User;

import javax.jws.soap.SOAPBinding;
import javax.persistence.criteria.CriteriaBuilder;

public interface UserService  {
    public User findUserByEmail(String email);
    public void updateUser(User user, Integer id);
    public void saveUser(User user);
    public void deleteUser(Integer id);
    public User getUserById(Integer id);
    public boolean isEnable(String email);
    public void setActive(Integer userId);
    public void updatePassword(String password, Integer userId);
}
