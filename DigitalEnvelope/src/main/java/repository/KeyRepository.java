package repository;

import java.security.KeyPair;
import java.util.concurrent.ConcurrentHashMap;

public class KeyRepository {
	private static KeyRepository instance;
	private ConcurrentHashMap<String, KeyPair> keyStore;

	private KeyRepository() {
		keyStore = new ConcurrentHashMap<>();
	}

	public static synchronized KeyRepository getInstance() {
		if (instance == null) {
			instance = new KeyRepository();
		}
		return instance;
	}

	public void saveKeyPair(String id, KeyPair keyPair) {
		keyStore.put(id, keyPair);
	}

	public KeyPair getKeyPair(String id) {
		return keyStore.get(id);
	}

	public boolean isKeyPairExist(String id) {
		return keyStore.containsKey(id);
	}
}
