package superapp.logic.utilitys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import superapp.Boundary.User.UserId;
import superapp.dal.SuperAppObjectRepository;
import superapp.dal.UserRepository;
import superapp.data.UserEntity;
import superapp.data.UserRole;
import superapp.logic.Exceptions.UserNotFoundException;
import superapp.logic.Mongo.ObjectServiceRepo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserUtility {
    private UserRepository userRepository;

    @Autowired
    public UserUtility( UserRepository userRepository) {

        this.userRepository = userRepository;
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
        return userRepository.findByUserId(userId)
                .orElseThrow(()->new UserNotFoundException("inserted id: "
                        + userId.getEmail() + userId.getSuperapp() + " does not exist"));
    }





}
