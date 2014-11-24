package RSA;

import java.math.BigInteger;
import java.security.SecureRandom;


public class RSA {
	final int bitLength = 128; // Ű�� ����
	final int certainty = 16; // �Ҽ��� Ȯ��
	BigInteger p, q, n, pi_n, one, publicKey[], d;
	SecureRandom sr;
	
	public RSA() {
		sr = new SecureRandom();
		
		// p, q ���
		p = new BigInteger(bitLength, certainty, sr);
		q = new BigInteger(bitLength, certainty, sr);

		// N, pi(n) ���
		n = p.multiply(q);
		one = new BigInteger("1");
		pi_n = p.subtract(one).multiply(q.subtract(one));

		
		// ����Ű ����
		publicKey = new BigInteger[2];
		do {
			publicKey[0] = new BigInteger(p.bitLength(), sr);
		} while(!publicKey[0].gcd(pi_n).equals(one));
		publicKey[1] = n;

		
		//����Ű ����
		d = publicKey[0].modInverse(pi_n);

	}
	
	// ����Ű ��������
	public BigInteger[] getPublicKey() { return publicKey; }	
	
	// �޽��� ��ȣȭ
	public BigInteger encryptionMessage(String msg, BigInteger publicKey[]) {
		byte[] byteArray = msg.getBytes();
		BigInteger m = new BigInteger(1, byteArray);
		
		return m.modPow(publicKey[0], publicKey[1]);
	}
	
	// �޽��� ��ȣȭ
	public String decryptionMessage(BigInteger encMsg) {
		BigInteger decMsg = encMsg.modPow(d, n);
		byte[] decBytes = decMsg.toByteArray();
		
		return new String(decBytes);
	}
}
