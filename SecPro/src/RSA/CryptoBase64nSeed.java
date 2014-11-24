package RSA;

import java.math.BigInteger;


public class CryptoBase64nSeed {
	// User secret key
	private byte pbUserKey[]  = new byte[16];
	// Round keys for encryption or decryption
	private int pdwRoundKey[] = new int[32];
	
	public CryptoBase64nSeed(BigInteger dhKey) {
		pbUserKey = dhKey.toByteArray();
		System.out.println("!!!!!!!!!!!!!seed creat!!!!!!!!!!!!!!!!!!!!!" + dhKey);
	}
	
	public byte[] encoding(String txt){
		try{
			//System.out.println("[원문]\n"+ txt);
			String strenc = Base64.encodeBytes(txt.getBytes());		
			
			//System.out.println("\n[base64 encode]\n"+strenc);		
			
			//byte 수 채우기
			int max = 0;
			if(strenc.getBytes().length % 16 != 0){
				max = 16-(strenc.getBytes().length % 16);	
			}
			for(int i=0; i<max ; i++){
				strenc += " ";
			}	
			
			int size = strenc.getBytes().length;

			
			
			// input plaintext to be encrypted
			byte pbData[]     = strenc.getBytes();

			byte pbCipher[]   = new byte[ 16 ];
			byte reData[][]   = new byte[ size/16 ][16];
			byte reCipher[]   = new byte[ size ];		

			// Derive roundkeys from user secret key
			seedx.SeedRoundKey(pdwRoundKey, pbUserKey);		

			// Encryption			
			// Encryption
			int b1=0, b2=0;	
			for(int i=0; i < size; i++){
				b1 = (i / 16);
				b2 = (i % 16);					
				reData[ b1 ][ b2 ] = pbData[i];				
			}
			int rf = 0;
			//System.out.println("\n[seed encoding]");
			for(int i=0; i<reData.length; i++){
				seedx.SeedEncrypt(reData[i], pdwRoundKey, pbCipher);				
				for(int j=0; j<pbCipher.length; j++){
					//System.out.print(pbCipher[j]);
					reCipher[rf++] = pbCipher[j];
				}
			}
			//System.out.println("rf : " + rf);
			return reCipher;
		}catch(Exception e){
			//System.out.println(e);
		}
		return null;
	}
	public String decoding(byte[] reCipher){
		try{
			int size = reCipher.length;
			System.out.println("Size: " + size);
			System.out.println("Size: " + size/16);
			
			byte pbPlain[]    = new byte[ 16 ];
			byte reData[][]   = new byte[ size/16 ][16];			
			byte rePlain[]    = new byte[ size ];
			System.out.println("in method");
			// Decryption		
			int b1=0, b2=0;
			for(int i=0; i<size; i++){
				b1 = (i / 16);
				b2 = (i % 16);							
				reData[ b1 ][ b2 ] = reCipher[i];
				System.out.print(reCipher[i]);
			}		
			int rf = 0;	
			//System.out.println("\n\n[seed decoding]");
			for(int i=0; i<reData.length; i++){
				seedx.SeedDecrypt(reData[i], pdwRoundKey, pbPlain);	
				for(int j=0; j<pbPlain.length; j++){
					//System.out.print( pbPlain[j] );
					rePlain[rf++] = pbPlain[j];
					//System.out.print(j);
				}
			}
			String strdec = new String(rePlain);
			//System.out.println("\n\n[base64 decode]\n"+strdec);

			byte[] basedec = Base64.decode(strdec);
			String strresult = new String(basedec);			
			//System.out.println("\n[결과]\n"+strresult);

			return strresult;
		}catch(Exception e){
			//System.out.println(e);
			e.printStackTrace();
			return null;
		}
	}
}
