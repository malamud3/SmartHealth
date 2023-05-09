package superapp.logic.utilitys;

import superapp.data.Enum.UserRole;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserUtility {

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





}
