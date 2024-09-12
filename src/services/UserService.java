package services;

import models.User;
import repositories.UserRepository;

import java.util.Optional;

public class UserService {
    private UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    public boolean addUser(String name, int age, String id) {
        User user = new User(name, age, id);
        return userRepository.addUser(user);
    }

    public boolean updateUser(String id, String newName, int newAge) {
        Optional<User> optionalUser = userRepository.findUserById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setName(newName);
            user.setAge(newAge);
            return userRepository.updateUser(user);
        } else {
            System.out.println("Utilisateur non trouvé.");
            return false;
        }
    }

    public boolean removeUser(String id) {
        return userRepository.deleteUser(id);
    }

    public void displayUser(String id) {
        Optional<User> optionalUser = userRepository.findUserById(id);
        optionalUser.ifPresentOrElse(
                user -> System.out.println("ID: " + user.getId() + "\tName: " + user.getName() + "\tAge: " + user.getAge()),
                () -> System.out.println("Utilisateur non trouvé.")
        );
    }

    public boolean userExists(String id) {
        return userRepository.findUserById(id).isPresent();
    }
}
