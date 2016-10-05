package ru.mail.park.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import sun.plugin.util.UserProfile;
import org.springframework.web.bind.annotation.PathVariable;
import ru.mail.park.FakeDB.UserDao;
import ru.mail.park.model.UserProfile;

import java.nio.file.attribute.UserPrincipal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;


/**
 * Created by SergeyCheremisin on 22/09/16.
 */

@Service
public class AccountService {

    @Autowired
    private UserDao userDao;

    public Collection<UserProfile> getAllUsers(){
        return this.userDao.getAllUsers();
    }

    public UserProfile getUserById(Integer id){
        return this.userDao.getUserById(id);
    }

    public boolean removeUserById(Integer id){
        return this.userDao.removeUserById(id);
    }

    public UserProfile existingUserByLogin(String user){

        return userDao.existingUserByLogin(user);
    }

    public int[] addSession(String user){

        return userDao.addSession(user);
    }


    public Integer addUser(String login, String password, String email){
         return this.userDao.addUser(login, password, email);
    }

    public Collection getSessions(){
        return this.userDao.getSessions();
    }

    public boolean  removeSessions(int id){
        return this.userDao.removeSessions(id);
    }






}
