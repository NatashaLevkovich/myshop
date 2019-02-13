package web.command;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class Encoder {

        private static final int logRounds = 10;

        public static String hash(String password) {
            return BCrypt.hashpw(password, BCrypt.gensalt(logRounds));
        }

        public static boolean verifyHash(String password, String hash) {
            return BCrypt.checkpw(password, hash);
        }

}

