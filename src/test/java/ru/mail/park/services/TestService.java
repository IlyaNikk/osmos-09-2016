package ru.mail.park.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.mail.park.model.UserProfile;
import ru.mail.park.service.AccountService;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TestService {

    @Autowired
    private AccountService accountService;

    @Before
    public void init() {
        UserProfile user = new UserProfile("name","login","email","password");
        UserProfile user1 = new UserProfile("name1","login1","email1","password1");
        UserProfile user2 = new UserProfile("name2","login2","email2","password2");
        accountService.addUser(user.getName(),user.getLogin(),user.getPassword(),user.getEmail());
        accountService.addUser(user1.getName(),user1.getLogin(),user1.getPassword(),user1.getEmail());
        accountService.addUser(user2.getName(),user2.getLogin(),user2.getPassword(),user2.getEmail());

    }

    @Test
    @SuppressWarnings("unchecked")
    public void testServiceGetAllUsers() {
        UserProfile user = new UserProfile("name3","login3","email3","password3");
        accountService.addUser(user.getName(),user.getLogin(),user.getPassword(),user.getEmail());
        // Now in database 4 elements //
        List<UserProfile> users = accountService.getAllUsers();
        assertEquals(users.size(),4);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testServiceGetLimitUsers() {
        accountService.addUser("name3","login3","password3","email3");
        accountService.addUser("name4","login4","password4","email4");
        accountService.addUser("name5","login5","password5","email5");
        accountService.addUser("name6","login6","password6","email6");
        accountService.addUser("name7","login7","password7","email7");
        List<UserProfile> users = accountService.getAllUsers(5);
        // Test limit of values in database //
        assertEquals(users.size(),5);
    }


    @Test
    public void testServiceCreateUser() {
        UserProfile user = new UserProfile("login3","name3","email3","password3");
        assertNotNull(user);
        accountService.addUser(user.getLogin(), user.getName(),user.getPassword(),user.getEmail());
        UserProfile test = accountService.existingUserByLogin("login3");
        assertNotNull(test);
        assertEquals(user.getPassword(),test.getPassword());
        assertEquals(user.getName(),test.getName());
        assertEquals(user.getEmail(),test.getEmail());
        assertEquals(user.getLogin(),test.getLogin());
    }

    @Test
    public void testServiceExistsByLogin() {
        UserProfile user = accountService.existingUserByLogin("login");
        assertNotNull(user);
        assertEquals(user.getPassword(),"password");
        assertEquals(user.getName(),"name");
        assertEquals(user.getEmail(),"email");
    }

    @Test
    public void testServiceExistsByEmail() {
        UserProfile user = accountService.existingUserByEmail("email");

        assertNotNull(user);
        assertEquals(user.getPassword(),"password");
        assertEquals(user.getName(),"name");
        assertEquals(user.getLogin(),"login");
    }

    @Test
    public void testServiceCheckLoginAndPassword() {
        UserProfile user = accountService.checkingUserByLoginPassword("login1","password1");
        assertNotNull(user);
    }

    @Test
    public void testServiceUserById() {
        UserProfile user = accountService.existingUserByEmail("email");
        assertNotNull(accountService.getUserById(user.getId()));
        assertTrue(accountService.removeUserById(user.getId()));
        assertNull(accountService.getUserById(user.getId()));
        assertFalse(accountService.removeUserById(user.getId()));
    }


}
