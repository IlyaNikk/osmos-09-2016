package ru.mail.park.DAO2;

import ru.mail.park.model.UserForResponse;
import ru.mail.park.model.UserProfile;

import java.util.List;


public interface UserRequestsDAO {

    public Long addNewUser(String login, String name, String email, String password);

    public List<UserForResponse> getAllUsers();

    public UserProfile existingUserByLogin(String login);

    public UserProfile getUserById(Long id);

    public Boolean removeUser(Long id);

}
