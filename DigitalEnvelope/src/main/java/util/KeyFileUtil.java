package util;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

// 키 파일 저장 및 복구용 유틸
public class KeyFileUtil {

    public static void saveKeyToFile(File file, byte[] keyBytes) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(keyBytes);
        }
    }

    public static byte[] loadKeyFromFile(File file) throws Exception {
        return Files.readAllBytes(file.toPath());
    }

    public static void savePublicKey(File file, PublicKey key) throws Exception {
        saveKeyToFile(file, key.getEncoded());
    }

    public static void savePrivateKey(File file, PrivateKey key) throws Exception {
        saveKeyToFile(file, key.getEncoded());
    }

    public static PublicKey loadPublicKey(File file) throws Exception {
        byte[] keyBytes = loadKeyFromFile(file);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    public static PrivateKey loadPrivateKey(File file) throws Exception {
        byte[] keyBytes = loadKeyFromFile(file);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }
}
