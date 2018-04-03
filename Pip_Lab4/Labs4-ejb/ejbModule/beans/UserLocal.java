package beans;

import java.util.List;

import javax.ejb.Local;

@Local
public interface UserLocal {
	List<User> findUser(String login);
	void createUser(User user);
}
