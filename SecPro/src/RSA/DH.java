package RSA;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;

public class DH {
	final int bitLength = 128; // 키의 길이
	final int certainty = 16; // 소수일 확률
	BigInteger p, g, a, dhKey;
	SecureRandom sr;

	public DH() {
		sr = new SecureRandom();

		// p계산
		p = new BigInteger(bitLength, certainty, sr);

		// g, a 계산
		while (true) {
			g = new BigInteger(bitLength, certainty, sr);
			if (p.compareTo(g) > 0) {
				break;
			}
		}
		while (true) {
			a = new BigInteger(bitLength, certainty, sr);
			if (p.compareTo(a) > 0) {
				break;
			}
		}
	}
	
	public DH(BigInteger p, BigInteger g) {
		sr = new SecureRandom();
		this.p = p;
		this.g = g;
		
		while (true) {
			a = new BigInteger(bitLength, certainty, sr);
			if (p.compareTo(a) > 0) {
				break;
			}
		}
	}

	public BigInteger[] getDHPublicKey() {
		BigInteger[] publicKey = new BigInteger[2];
		publicKey[0] = p; publicKey[1] = g;
		
		return publicKey;
	}
	
	public BigInteger getDHKey() {
		return dhKey;
	}
	
	public BigInteger calcgToThePowerOfa() {
		return g.modPow(a, p);
	}
	
	public void calcDHKey(BigInteger gToThePowerOfb) {
		dhKey = gToThePowerOfb.modPow(a, p);
	}

	/*public static void main(String[] args) {
		DH dh = new DH();
		BigInteger x = new BigInteger(128, 16, new SecureRandom());
		dh.calcDHKey(x.modPow(x, p));
		System.out.println(dhKey);
	}*/
}
