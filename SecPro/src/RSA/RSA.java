package RSA;

import java.math.BigInteger;
import java.security.SecureRandom;


public class RSA {
	final int bitLength = 128; // 키의 길이
	final int certainty = 16; // 소수일 확률
	BigInteger p, q, n, pi_n, one, publicKey[], d;
	SecureRandom sr;
	
	public RSA() {
		sr = new SecureRandom();
		
		// p, q 계산
		p = new BigInteger(bitLength, certainty, sr);
		q = new BigInteger(bitLength, certainty, sr);

		// N, pi(n) 계산
		n = p.multiply(q);
		one = new BigInteger("1");
		pi_n = p.subtract(one).multiply(q.subtract(one));

		
		// 공개키 생성
		publicKey = new BigInteger[2];
		do {
			publicKey[0] = new BigInteger(p.bitLength(), sr);
		} while(!publicKey[0].gcd(pi_n).equals(one));
		publicKey[1] = n;

		
		//개인키 생성
		d = publicKey[0].modInverse(pi_n);

	}
	
	// 공개키 가져오기
	public BigInteger[] getPublicKey() { return publicKey; }	
	
	// 메시지 암호화
	public BigInteger encryptionMessage(String msg, BigInteger publicKey[]) {
		byte[] byteArray = msg.getBytes();
		BigInteger m = new BigInteger(1, byteArray);
		
		return m.modPow(publicKey[0], publicKey[1]);
	}
	
	// 메시지 복호화
	public String decryptionMessage(BigInteger encMsg) {
		BigInteger decMsg = encMsg.modPow(d, n);
		byte[] decBytes = decMsg.toByteArray();
		
		return new String(decBytes);
	}
}
