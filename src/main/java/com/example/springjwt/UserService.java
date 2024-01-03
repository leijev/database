package com.example.springjwt;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserService {
    private List<User> users = new ArrayList<>();

    public UserService() {
        users.add(new User(1l, "progman", "test", 9999));
        users.add(new User(2l, "yana", "123312", 100));
        users.add(new User(3l, "olya", "1csdfcsd", 500));
        users.add(new User(4l, "itr1stan", "tristan", 2300));
    }

    public List<User> sortUsers() {
        return users.stream().filter(x -> x.getCoins() > 1000).collect(Collectors.toList());
    }

    public List<User> getAllUsers() {
        return users;
    }

}
