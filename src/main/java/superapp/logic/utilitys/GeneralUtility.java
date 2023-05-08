package superapp.logic.utilitys;

public class GeneralUtility {
    public boolean isStringEmptyOrNull(String userName) {
        if (userName == null || userName.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }
        return false;
    }
}
