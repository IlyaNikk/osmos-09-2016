package ru.mail.park.main;

import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.mail.park.json.*;
import ru.mail.park.json.RegistrationRequest;
import ru.mail.park.model.UserProfile;
import ru.mail.park.service.AccountService;

import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Objects.isNull;

@RestController
public class RegistrationController {

  @Autowired
  private HttpSession httpSession;

  @Autowired
  private AccountService accountService;

  ObjectMapper objectMapper = new ObjectMapper();


  @RequestMapping(value = "/api/users", method = RequestMethod.GET)
  public ResponseEntity<?> getAllUsers() {
      objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        String arrayToJson = null;
      try {
             arrayToJson = objectMapper.writeValueAsString(accountService.getAllUsers());
      }catch (JsonProcessingException e) {
          e.printStackTrace();
        final Logger log = Logger.getLogger(RegistrationController.class.getName());
        log.log(Level.INFO, "JsonProcessingException in getAllUsers");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Internal server error\"}");
      }
    return ResponseEntity.ok()
            .body(new Response<>("info",accountService.getAllUsers()));
  }

  @RequestMapping(value = "/api/users/{id}", method = RequestMethod.GET)
  public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {

    final UserProfile user = accountService.getUserById(id);
    if (user == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body(new Response<>("error",new ErrorMessage("User not exist")));
    }
    return ResponseEntity
            .ok(new Response<>("info",new SignUpMessage(user.getLogin())));
  }

  @RequestMapping(value = "/api/users/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<?> removeUserById(@PathVariable("id") Long id) {

    if (!isNull(httpSession.getAttribute("userId")) && !id.equals((Long)httpSession.getAttribute("userId"))) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
              .body(new Response<>("error",new ErrorMessage("Cannot remove other user")));
    }
    accountService.removeUserById(id);
    return ResponseEntity.ok(new Response<>("info", "User has removed"));
  }

  @RequestMapping(value = "/api/users", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> signup(@RequestBody RegistrationRequest body) {

    final String login = body.getLogin();
    final String password = body.getPassword();
    final String email = body.getEmail();
    final String name = body.getName();

    if (StringUtils.isEmpty(login) || StringUtils.isEmpty(password) || StringUtils.isEmpty(email)) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT)
              .body(new Response<>("error",new ErrorMessage("Invalid data")));
    }

    final UserProfile existingUser = accountService.existingUserByLogin(login);
    if (existingUser != null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .body(new Response<>("error",new ErrorMessage("User with this login already exist")));
    }

    final UserProfile existingUserByEmail = accountService.existingUserByEmail(email);
    if (existingUserByEmail != null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .body(new Response<>("error",new ErrorMessage("User with this email already exist")));
    }

    final Long id = accountService.addUser(login, name, password, email);

    return ResponseEntity.ok(new Response<>("info",new AuthMessage(id)));
  }

  @RequestMapping(value = "/api/sessions", method = RequestMethod.POST)
  public ResponseEntity signin(@RequestBody RegistrationRequest body) {
    final String login = body.getLogin();
    final String password = body.getPassword();

    if (StringUtils.isEmpty(login) || StringUtils.isEmpty(password)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body(new Response<>("error",new ErrorMessage("Invalid data")));
    }

    final UserProfile user = accountService.checkingUserByLoginPassword(login,password);
    if (user == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body(new Response<>("error",new ErrorMessage("Incorrect login/password")));
    }
    httpSession.setAttribute("userId", user.getId());
    httpSession.setAttribute("login",login);

    return ResponseEntity.ok()
            .body(new Response<>("info",new SessionMessage(httpSession.getId(),user.getId())));
  }


  @RequestMapping(value = "/api/sessions", method = RequestMethod.GET)
  public ResponseEntity<?> getSession() {
    if (httpSession.getAttribute("userId") == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body(new Response<>("error",new ErrorMessage("You aren't authenticated. Session is null!")));
    }
    return ResponseEntity.ok()
            .body(new Response<>("info",new GetSessionMessage(httpSession.getId())));
  }

  @RequestMapping(value = "/api/sessions", method = RequestMethod.DELETE)
  public ResponseEntity<?> deleteSession() {
    if (httpSession.getAttribute("userId") == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body(new Response<>("error",new ErrorMessage("You aren't authenticated. Session is null!")));
    }
    httpSession.invalidate();
    return ResponseEntity.ok()
            .body(new Response<>("info","You are log out!"));
  }

  @RequestMapping(value = "/api/auth", method = RequestMethod.GET)
  public ResponseEntity isauth(){
    if(isNull(httpSession.getAttribute("userId"))){
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"error\":\"Access allowed only for registered users\"}");
    }

    return ResponseEntity.ok().body("User logged in");
  }

}
