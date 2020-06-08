package domain;

import java.util.Random;

public class PasswordCreator {
    public static String createPassword() {
        Random random = new Random();
        char[] chars = {'!', '@', '#', '$', '%', '^', '&'};
        String password = "";
        password += chars[random.nextInt(7)];
        password += (char) (random.nextInt(91 - 65) + 65);
        while (password.length() != 4) password += (char) (random.nextInt(123 - 97) + 97);

        password += (char) (random.nextInt(58 - 48) + 48);
        return password;
    }
}
