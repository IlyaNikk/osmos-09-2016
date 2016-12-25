package ru.mail.park.dao;

import ru.mail.park.model.UserProfile;

import java.util.List;

public interface UserRequestsDao {

    Long addNewUser(String login, String name, String email, String password);

    List<UserProfile> getAllUsers();

    List<UserProfile> getAllUsers(Integer limit_number);

    UserProfile existingUserByEmail(String email);

    UserProfile existingUserByLogin(String login);

    UserProfile getUserById(Long id);

    Boolean removeUser(Long id);

    UserProfile checkingUserByLoginPassword(String login, String password);

}
