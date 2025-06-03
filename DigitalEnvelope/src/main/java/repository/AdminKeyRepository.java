package repository;

import java.security.KeyPair;
import java.util.concurrent.ConcurrentHashMap;

public class AdminKeyRepository {
	private static AdminKeyRepository instance = new AdminKeyRepository();
	private ConcurrentHashMap<String, KeyPair> adminKeyMap = new ConcurrentHashMap<>();

	private AdminKeyRepository() {
	}

	public static AdminKeyRepository getInstance() {
		return instance;
	}

	public void saveKeyPair(String adminId, KeyPair keyPair) {
		adminKeyMap.put(adminId, keyPair);
	}

	public KeyPair getKeyPair(String adminId) {
		return adminKeyMap.get(adminId);
	}

	public boolean isKeyPairExist(String adminId) {
		return adminKeyMap.containsKey(adminId);
	}
}
