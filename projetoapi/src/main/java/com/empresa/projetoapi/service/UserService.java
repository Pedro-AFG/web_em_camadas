package com.empresa.projetoapi.service;

import com.empresa.projetoapi.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private List<User> userList;

    public UserService() {
        userList = new ArrayList<>();

        User user1 = new User(1, "Maria", 25, "maria@gmail.com");
        User user2 = new User(2, "João", 21, "joao@gmail.com");
        User user3 = new User(3, "Renan", 29, "renan@gmail.com");
        User user4 = new User(4, "Luigi", 23, "luigi@gmail.com");
        User user5 = new User(5, "Pedro", 22, "pedro@gmail.com");

        userList.addAll(Arrays.asList(user1, user2, user3, user4, user5));
    }

    public User getUser(Integer id) {
        for (User user : userList) {
            if (id == user.getId()){
               return user;
            }
        }
        return null;
    }

    public List<User> getAllUsers() {
        return userList;
    }

    public void postUser(User user) {
        userList.add(user);
    }

    public void deleteUser(Integer id) {
        userList.removeIf(user -> id == user.getId());
    }

    public void putUser(User user) {
        for (User users : userList) {
            if (user.getId() == users.getId()) {
                userList.remove(users);
                User newUser = new User(user.getId(), user.getName(), user.getAge(), user.getEmail());
                userList.add(newUser);
            }
        }
    }
}
