import org.apache.commons.codec.binary.Hex;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Hasher {

    private final int iterations;
    private final int keyLength;

    public Hasher(){
        this.iterations = 10000;
        this.keyLength = 512;
    }
    public static String hashPassword(String username, String password){

        // Create hash class and salt
        Hasher h = new Hasher();
        char[] passwordChars = password.toCharArray();
        byte[] saltBytes = username.getBytes();

        // Password-hashing
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            PBEKeySpec spec = new PBEKeySpec(passwordChars, saltBytes, h.iterations, h.keyLength);
            SecretKey key = skf.generateSecret(spec);

            return String.valueOf(Hex.encodeHex(key.getEncoded()));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException( e );
        }
    }

    public static boolean checkPassword(String username, String password, String passwordDB){

        // Hash given password
        String hashedString = hashPassword(username, password);

        // Compare hashed password with stored password
        return (hashedString.equals(passwordDB));
    }

}
