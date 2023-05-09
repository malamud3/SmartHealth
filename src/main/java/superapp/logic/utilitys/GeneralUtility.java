package superapp.logic.utilitys;

public class GeneralUtility {
    public boolean isStringEmptyOrNull(String s){
        return s == null || s.trim().isEmpty();
    }
}
