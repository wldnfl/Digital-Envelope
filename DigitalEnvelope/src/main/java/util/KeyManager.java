package util;

import java.io.*;
import java.security.*;

import jakarta.servlet.ServletContext;
import exception.KeyNotExistException;

public class KeyManager {
    private static final String RSA_ALGORITHM = "RSA";
    private static final int RSA_KEY_SIZE = 1024;

    private final File pubFile;
    private final File priFile;

    public KeyManager(ServletContext context, UserType userType) {
        String subDir = switch (userType) {
            case REPORTER -> "reporter";
            case ADMIN -> "admin";
        };

        String baseDir = "/Users/jiwoo/Desktop/keys/" + subDir;
        File dir = new File(baseDir);
        if (!dir.exists()) dir.mkdirs();

        this.pubFile = new File(dir, "public.key");
        this.priFile = new File(dir, "private.key");
    }

    public KeyPair getOrCreateKeyPair() throws Exception {
        if (pubFile.exists() && priFile.exists()) {
            return loadKeyPair();
        } else {
            KeyPair keyPair = generateKeyPair();
            saveKeyPair(keyPair);
            return keyPair;
        }
    }

    private KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        keyGen.initialize(RSA_KEY_SIZE);
        return keyGen.generateKeyPair();
    }

    public void saveKeyPair(KeyPair keyPair) throws IOException {
        try (ObjectOutputStream oosPub = new ObjectOutputStream(new FileOutputStream(pubFile));
             ObjectOutputStream oosPri = new ObjectOutputStream(new FileOutputStream(priFile))) {
            oosPub.writeObject(keyPair.getPublic());
            oosPri.writeObject(keyPair.getPrivate());
        }
    }

    public KeyPair loadKeyPair() throws IOException, ClassNotFoundException {
        if (!pubFile.exists() || !priFile.exists()) {
            throw new KeyNotExistException();
        }

        try (ObjectInputStream oisPub = new ObjectInputStream(new FileInputStream(pubFile));
             ObjectInputStream oisPri = new ObjectInputStream(new FileInputStream(priFile))) {
            PublicKey publicKey = (PublicKey) oisPub.readObject();
            PrivateKey privateKey = (PrivateKey) oisPri.readObject();
            return new KeyPair(publicKey, privateKey);
        }
    }

    public boolean isKeyPairExist() {
        return pubFile.exists() && priFile.exists();
    }

    public String getKeyStatus() {
        return isKeyPairExist() ? "키가 존재합니다." : "키가 없습니다.";
    }

    public void deleteKeyPair() {
        if (pubFile.exists()) pubFile.delete();
        if (priFile.exists()) priFile.delete();
    }

    public void createKeyPair() throws Exception {
        KeyPair keyPair = generateKeyPair();
        saveKeyPair(keyPair);
    }
}
