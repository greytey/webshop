package ch.bbbaden.lernatelier.simpleClasses;


import java.util.regex.Pattern;

public class LoginPolicy {
    private String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    public boolean loginPolicy(String firstname, String lastname, String email, String password) {

        if (!noWhiteSpaces(firstname) && !noWhiteSpaces(lastname) && !noWhiteSpaces(email) && !noWhiteSpaces(password)) {
            if (containsNumber(password)) {
                if (containsLettersOnly(firstname) && containsLettersOnly(lastname)) {
                    if (containsLetter(password)) {
                        if (containsUpperCase(password)) {
                            if (containsLowerCase(password)) {
                                if (emailPolicy(email)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean noWhiteSpaces(String toValidate) {
        return toValidate.contains(" ");
    }

    private boolean containsNumber(String toValidate) {
        char[] charToValidate = toValidate.toCharArray();
        for (int i = 0; i < charToValidate.length; i++) {
            if (Character.isDigit(charToValidate[i])) {
                return true;
            }
        }
        return false;
    }

    private boolean containsLettersOnly(String toValidate) {
        char[] charToValidate = toValidate.toCharArray();
        for (int i = 0; i < charToValidate.length; i++) {
            if (Character.isAlphabetic(charToValidate[i])) {
                return true;
            }
        }
        return false;
    }

    private boolean containsLetter(String toValidate) {
        char[] charToValidate = toValidate.toCharArray();
        for (int i = 0; i < charToValidate.length; i++) {
            if (Character.isAlphabetic(charToValidate[i])) {
                return true;
            }
        }
        return false;
    }

    private boolean containsUpperCase(String toValidate) {
        char[] charToValidate = toValidate.toCharArray();
        for (int i = 0; i < charToValidate.length; i++) {
            if (Character.isUpperCase(charToValidate[i])) {
                return true;
            }
        }
        return false;
    }

    private boolean containsLowerCase(String toValidate) {
        char[] charToValidate = toValidate.toCharArray();
        for (int i = 0; i < charToValidate.length; i++) {
            if (Character.isLowerCase(charToValidate[i])) {
                return true;
            }
        }
        return false;
    }

    public boolean emailPolicy(String emailToValidate) {
        return Pattern.compile(regexPattern).matcher(emailToValidate).matches();
    }
}
