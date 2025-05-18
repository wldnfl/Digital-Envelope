package envelope;

public class Envelope {
	public byte[] encryptedData;
	public byte[] encryptedKey;
	public byte[] signature;

	public Envelope(byte[] encryptedData, byte[] encryptedKey, byte[] signature) {
		this.encryptedData = encryptedData;
		this.encryptedKey = encryptedKey;
		this.signature = signature;
	}
}
