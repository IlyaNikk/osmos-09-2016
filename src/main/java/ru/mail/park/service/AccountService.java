package ru.mail.park.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.mail.park.dao.UserRequestsDao;
import ru.mail.park.model.UserProfile;


@Transactional
@Service
public class AccountService {

    @Autowired
    private UserRequestsDao userDao;

    public List getAllUsers() {
        return userDao.getAllUsers();
    }

    public List getAllUsers(Integer limit_number) {
        return userDao.getAllUsers(limit_number);
    }

    public UserProfile getUserById(Long id) {
        return this.userDao.getUserById(id);
    }

    public boolean removeUserById(Long id) {
        return this.userDao.removeUser(id);
    }

    public UserProfile existingUserByEmail(String email){
        return userDao.existingUserByEmail(email);
    }

    public UserProfile checkingUserByLoginPassword(String login,String password) {
        return userDao.checkingUserByLoginPassword(login,password);
    }

    public UserProfile existingUserByLogin(String user) {

        return userDao.existingUserByLogin(user);
    }

    public Long addUser(String login, String name, String password, String email) {
        return userDao.addNewUser(login, name, password, email);
    }

}
