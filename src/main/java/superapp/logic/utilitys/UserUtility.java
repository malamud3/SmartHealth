package superapp.logic.utilitys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import superapp.Boundary.User.NewUserBoundary;
import superapp.Boundary.User.UserId;
import superapp.dal.UserCrud;
import superapp.data.UserEntity;
import superapp.data.UserRole;
import superapp.logic.Exceptions.UserNotFoundException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserUtility {
    private UserCrud userCrud;


    @Autowired
    public UserUtility( UserCrud userCrud) {

        this.userCrud = userCrud;
    }


    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    );
    public static boolean isValidEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    public boolean isUserRoleValid(String role) {
        try {
            UserRole.valueOf(role);
            return true;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("role is not ok");
        }
    }

    public UserEntity checkUserExist(UserId userId){
        System.err.println(userId.toString());
        return userCrud.findByUserId(userId)
                .orElseThrow(()->new UserNotFoundException("inserted id: "
                        + userId.getEmail() + userId.getSuperapp() + " does not exist"));

    }


    /**
     * Validates the given NewUserBoundary object.
     *
     * @param newUser the NewUserBoundary object to validate
     * @throws IllegalArgumentException if the email address is invalid, role is invalid, username is null or empty, or avatar is null or empty
     * @throws RuntimeException         if a user with the same email address already exists in the database
     */
    public void validateUser(NewUserBoundary newUser,String springAppName) throws RuntimeException {
        GeneralUtility generalUtility = new GeneralUtility();
        System.err.println(springAppName);

        // Check if email is valid
        if (!superapp.logic.utilitys.UserUtility.isValidEmail(newUser.getEmail())) {
            throw new IllegalArgumentException("Invalid email address: " + newUser.getEmail());
        }

        // Check if role is valid
        if (!isUserRoleValid(newUser.getRole())) {
            throw new IllegalArgumentException("Invalid role: " + newUser.getRole());
        }

        // Check if the user already exists
        UserId userId = new UserId(springAppName,newUser.getEmail());
        if (userCrud.findByUserId(userId).isPresent()) {
            throw new RuntimeException("User with email " + newUser.getEmail() + " already exists");
        }

        //check if userName is not empty or null
        if (generalUtility.isStringEmptyOrNull(newUser.getUsername())) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        //check if avatar is not empty or null
        if (generalUtility.isStringEmptyOrNull(newUser.getAvatar())) {
            throw new IllegalArgumentException("Avatar cannot be null or empty");
        }

    }



}
