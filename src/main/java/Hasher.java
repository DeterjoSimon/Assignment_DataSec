import org.apache.commons.codec.binary.Hex;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Hasher {

    private final int iterations = 10000;
    private final int keyLength = 512;

    public static byte[] hashPassword(String name, String password){

        // Create hash class and salt
        Hasher h = new Hasher();
        char[] passwordChars = password.toCharArray();
        byte[] saltBytes = name.getBytes();

        // Password-hashing
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            PBEKeySpec spec = new PBEKeySpec(passwordChars, saltBytes, h.iterations, h.keyLength);
            SecretKey key = skf.generateSecret(spec);
            return key.getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException( e );
        }
    }

    public static boolean checkPassword(String name, String password, String passwordDB){

        // Hash given password
        byte[] hashedBytes = hashPassword(name, password);
        String hashedString = String.valueOf(Hex.encodeHex(hashedBytes));

        // Compare hashed password with stored password
        return (hashedString.equals(passwordDB));
    }
}
