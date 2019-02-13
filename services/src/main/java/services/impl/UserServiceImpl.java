package services.impl;

import dao.repository.UserRepository;
import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import services.UserService;
import java.io.Serializable;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User get(Serializable id) {
        User user = userRepository.getOne((Long) id);
        user.getStatus();
       return user;
    }

    @Override
    public User update(User user) {
      return userRepository.save(user);
    }

    @Override
    public void delete(User user) {
      userRepository.delete(user);
    }

    @Override
    public List<User> getAll() {
       return userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
     return userRepository.findByEmail(email);
    }
}
