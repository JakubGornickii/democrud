package pl.akademiakodu.service;


import pl.akademiakodu.model.User;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
}
