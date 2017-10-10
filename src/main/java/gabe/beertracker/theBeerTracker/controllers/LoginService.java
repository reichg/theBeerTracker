package gabe.beertracker.theBeerTracker.controllers;

import gabe.beertracker.theBeerTracker.models.User;
import gabe.beertracker.theBeerTracker.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {

    @Autowired
    UserDao userDao;

    Iterable<User> users = userDao.findAll();

    public boolean validateUser(String user, String password) {

        return (user.equals("gabe") && password.equals("pass")) || (user.equals("ben") && password.equals("password"));
    }
}
