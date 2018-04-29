package gilianmarques.dev.picpay_test.utils;

import android.annotation.SuppressLint;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Helper para proteger com um certo nível de segurança os dados inseridos nesse teste
 * Metodos são auto-comentados por isso não contém javadocs
 */
@SuppressLint("GetInstance")
public class AESHelper {

    private static final String ALGORITHM = "AES";
    private static final String ENCODING = "UTF-8";
    private static final String HEX = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALGORITHM_PARAMS = "AES";

    public static SecretKey generateKey() {
        KeyGenerator kg;
        try {
            kg = KeyGenerator.getInstance(ALGORITHM);
            return new SecretKeySpec((kg.generateKey()).getEncoded(), ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static String encryptMsg(String message, SecretKey secret) {

        Cipher cipher;
        try {
            cipher = Cipher.getInstance(ALGORITHM_PARAMS);
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            return toHex(cipher.doFinal(message.getBytes(ENCODING)));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | UnsupportedEncodingException | BadPaddingException | InvalidKeyException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decryptMsg(String cipherText, SecretKey secret) {
        try {
            byte[] bytes = toByte(cipherText);
            Cipher cipher = Cipher.getInstance(ALGORITHM_PARAMS);
            cipher.init(Cipher.DECRYPT_MODE, secret);
            return new String(cipher.doFinal(bytes), ENCODING);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | UnsupportedEncodingException | IllegalBlockSizeException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static String keyToString(SecretKey mKeySpec) {
        return Base64.encodeToString(mKeySpec.getEncoded(), Base64.DEFAULT);
    }

    public static SecretKey stringToKey(String mKeySpec) {
        return new SecretKeySpec(Base64.decode(mKeySpec, Base64.DEFAULT), ALGORITHM);
    }

    private static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuilder result = new StringBuilder(2 * buf.length);
        for (byte aBuf : buf) {
            result.append(HEX.charAt((aBuf >> 4) & 0x0f)).append(HEX.charAt(aBuf & 0x0f));
        }
        return result.toString();
    }

    private static byte[] fromHex(String hex) {
        return toByte(hex);
    }

    private static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        return result;
    }


}



