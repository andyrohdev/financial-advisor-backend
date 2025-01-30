import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import java.security.Key;

public class GenerateJwtSecret {
    public static void main(String[] args) {
        Key key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
        String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println("Your Secure JWT Key: " + base64Key);
    }
}
