package jp.co.sys.stub.ikeda;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Pass {
	public static void main(String[] args) throws Exception {
		String password = "P@ssw0rd123";

		// ソルト生成（ランダムな16バイト）
		SecureRandom sr = new SecureRandom();
		byte[] salt = new byte[16];
		sr.nextBytes(salt);

		// パラメータ設定
		int iterations = 65536;
		int keyLength = 256;

		PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		byte[] hash = skf.generateSecret(spec).getEncoded();

		// 結果出力（Base64で表示）
		System.out.println("Salt: " + Base64.getEncoder().encodeToString(salt));
		System.out.println("Hash: " + Base64.getEncoder().encodeToString(hash));
	}
}
