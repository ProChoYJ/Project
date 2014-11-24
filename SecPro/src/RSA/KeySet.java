package RSA;

import java.io.DataOutputStream;
import java.math.BigInteger;

public class KeySet {
	DataOutputStream out;
	BigInteger[] publicKey;
	BigInteger DHkey;
	
	public KeySet(DataOutputStream out, BigInteger[] publicKey, BigInteger DHKey){
		this.out = out; this.publicKey = publicKey; this.DHkey = DHKey;
	}
	
	public DataOutputStream getOut(){
		return out;
	}
	
	public BigInteger[] getpublicKey(){
		return publicKey;
	}
	
	public BigInteger getDHKey(){
		return DHkey;
	}
}
