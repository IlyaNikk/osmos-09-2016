package ru.mail.park.FakeDB;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.mail.park.model.UserProfile;

import java.util.*;

/**
 * Created by SergeyCheremisin on 26/09/16.
 */




@Repository
public class UserDao {

    private static Map<Integer, UserProfile> userProfiles;
    private Map<Integer, Integer> userSessions = new HashMap<Integer, Integer>();


    static {

        userProfiles = new HashMap<Integer, UserProfile>(){
            {
                put(0, new UserProfile("Sergey", "cheremisin.sergey@yandex.ru", "password11"));
                put(1, new UserProfile("Ilya", "nikitin.ilya@mail.ru", "password22"));
                put(2, new UserProfile("Ephrosiniya", "zerminova.phrosia@gmial.com", "password33"));
            }
        };
    }

    @JsonProperty(required = true)
    @ApiModelProperty(notes = "Return all users", required = true)
    public Collection<UserProfile> getAllUsers(){
        return userProfiles.values();
    }

    @JsonProperty(required = true)
    @ApiModelProperty(notes = "Return all user by id", required = true)
    public UserProfile getUserById(Integer id){
        return userProfiles.get(id);
    }

    public boolean removeUserById(Integer id){
        if(userProfiles.remove(id) == null)
            return true;

        return false;
    }

    public boolean existingUserById(Integer id){
        UserProfile userProfile = userProfiles.get(id);
        if(userProfile != null)
            return true;

        return false;
    }

    public UserProfile existingUserByLogin(String login){
        for(Map.Entry<Integer, UserProfile> entry: userProfiles.entrySet()){
            Integer key = entry.getKey();
            UserProfile value = entry.getValue();
            if(login == value.getLogin()){
                return value;
            }
        }
        return null;
    }

    public Integer existingSessionByLogin(String login){
        for(Map.Entry<Integer, UserProfile> entry: userProfiles.entrySet()) {
            Integer key = entry.getKey();
            UserProfile value = entry.getValue();
            if (login.equals(value.getLogin())) {
                return key;
            }
        }
        return 101;
    }


    public Integer addUser(String login, String password, String email){
        final UserProfile userProfile = new UserProfile(login, email, password);
        Random rn = new Random();
        for(int i=0; i<userProfiles.size(); i++) {
            Integer key = rn.nextInt(100);
            if (userProfiles.get(key) == null) {
                userProfiles.put(key, userProfile);
                return key;
            }
        }

        return 101;
    }


    public int[] addSession(String login) {


        int session[] = new int[2];

        Integer key = existingSessionByLogin(login);
        Random rn = new Random();
        Integer keySession = rn.nextInt(100);
        userSessions.put(keySession, key);
        session[0] = keySession;
        session[1] = key;
        return session;
    }


    public Collection getSessions() {
        return userSessions.values();
    }

    public boolean removeSessions(int id){
        if(userSessions.containsKey(id)){
            userSessions.remove(id);
            return true;
        }
        return false;
    }

}






