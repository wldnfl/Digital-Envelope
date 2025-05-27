package model;

import java.io.Serializable;

public class Envelope implements Serializable {
	private static final long serialVersionUID = 1L;
	private byte[] encryptedReport;
    private byte[] encryptedAESKey;

    public Envelope(byte[] encryptedReport, byte[] encryptedAESKey) {
        this.encryptedReport = encryptedReport;
        this.encryptedAESKey = encryptedAESKey;
    }

    public byte[] getEncryptedReport() {
        return encryptedReport;
    }

    public byte[] getEncryptedAESKey() {
        return encryptedAESKey;
    }
}