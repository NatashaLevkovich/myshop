package services;

import entities.User;

import java.io.Serializable;
import java.util.List;

public interface UserService extends BaseService<User>{

    List<User> getAll();

    User getUserByEmail(String email);
}
