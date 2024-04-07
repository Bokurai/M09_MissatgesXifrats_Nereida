import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;

public class Cypher {
    public KeyPair generateKeys() throws NoSuchAlgorithmException {
        KeyPair keyPair = null;

        KeyPairGenerator k = KeyPairGenerator.getInstance("RSA");
        k.initialize(1024);
        keyPair = k.genKeyPair();

        return keyPair;
    }

    public byte[] cypher(PublicKey publicKey, byte[] content) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] cypherData = cipher.doFinal(content);
        return cypherData;
    }

    public byte[] decypher(PrivateKey privateKey, byte[] content) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] deCypherData = cipher.doFinal(content);
        return deCypherData;
    }
}
