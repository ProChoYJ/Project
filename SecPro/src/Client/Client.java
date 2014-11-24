package Client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import RSA.*;

public class Client extends JFrame implements WindowListener{
		JTextField text;
		static JTextArea area = new JTextArea();
		Socket socket = null;
		Receiver receiver = null;
		DataOutputStream out;
		DataInputStream in;
		String name, msg, ip, inmsg;
		JScrollPane scrollPane;
		boolean transKey = true;
		byte[] temp;
		int intest;
		RSA rsa = new RSA();
		DH dh = new DH();
		// server key
		BigInteger[] publicDHKey;
		BigInteger[] publicKey;
		BigInteger gToThePowerOfb;
		BigInteger DHKey;
		String k1, k2;
		CryptoBase64nSeed cryptobase64nseed;
		public Client(){
			super("Client");
			
		text = new JTextField();
		
		area.setEditable(false);	
		
		setSize(300,500);
		
		add(text, BorderLayout.SOUTH);
		add(area, BorderLayout.CENTER);
		
		scrollPane = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane,BorderLayout.CENTER);
		
		
		setVisible(true);
		text.requestFocus();
		
		text.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				cryptobase64nseed = new CryptoBase64nSeed(DHKey);
				inmsg = text.getText();
				msg = "" + rsa.encryptionMessage(inmsg, publicKey);
				area.append( name +  " : " + inmsg + "\n");
				text.setText("");
				text.requestFocus();
			
				try{
					out.write(cryptobase64nseed.encoding(msg));
					//out.writeUTF(msg);
				}catch(IOException ee){
					ee.printStackTrace();
				}
			}
		});
	

	// conn
		try{
			
			ip = JOptionPane.showInputDialog("IP를 입력하세요.");
			name = JOptionPane.showInputDialog("닉네임을 입력하세요.");
			//key input
			socket = new Socket(ip, 7777);
			
			publicKey = new BigInteger[2];
			publicDHKey = new BigInteger[2];
			
			publicDHKey = dh.getDHPublicKey();
			
			out = new DataOutputStream(socket.getOutputStream());
			
			System.out.println(rsa.getPublicKey()[0]);
			System.out.println(rsa.getPublicKey()[1]);
			
			// trans key

			System.out.println("key 교환중");
			in = new DataInputStream(socket.getInputStream());
				//DHKey
				// p
				out.writeUTF("" + publicDHKey[0]);
				// g
				out.writeUTF("" + publicDHKey[1]);
				out.writeUTF("" + dh.calcgToThePowerOfa());
				System.out.println("send DHB : " + dh.calcgToThePowerOfa());
				
				// publicKey
				// server -> client
				// if String
				k1 = in.readUTF();
				k2 = in.readUTF();
				System.out.println(k1);
				System.out.println(k2);
				
				publicKey[0] = new BigInteger(k1);
				publicKey[1] = new BigInteger(k2);
				System.out.println("- 받은 key - ");
				System.out.println(publicKey[0]);
				System.out.println(publicKey[1]);
				System.out.println("get Ok!!!");
				
				// client -> server
				out.writeUTF("" + rsa.getPublicKey()[0]);
				System.out.println("key1 send : " + rsa.getPublicKey()[0]);
				out.writeUTF("" + rsa.getPublicKey()[1]);
				System.out.println("key2 send : " + rsa.getPublicKey()[1]);
				out.writeUTF(name);
				System.out.println("send Ok!!!");
				
				
				gToThePowerOfb = new BigInteger("" + in.readUTF());
				System.out.println("DHKB : " + gToThePowerOfb);
				dh.calcDHKey(gToThePowerOfb);
				DHKey = dh.getDHKey();
				System.out.println("DH OK!!!");
				System.out.println("DHKEY : " + DHKey);
				area.append("연결 되었습니다.\n");
				JOptionPane.showMessageDialog(null, "연결되었습니다.");
				
				
				
			
				cryptobase64nseed = new CryptoBase64nSeed(dh.getDHKey());
			receiver = new Receiver(socket);
			receiver.start();
		
		
			}catch(IOException e){
				e.printStackTrace();
			}
	
		}
	
		class Receiver extends Thread{
			Socket rsocket;
			byte[] temp;
			//byte[] deco;
			int line;
			String sdeco = "";
			public Receiver(Socket socket){
					rsocket = socket;
					
					try{
						in = new DataInputStream(rsocket.getInputStream());
					}catch(IOException e){
						e.printStackTrace();
					}
		
		}
		
			public void run(){
				while(in != null){
					
					try{
						//System.out.println(temp);
						
					
						temp = new byte[200];
						
						line = in.read(temp);
						byte[] deco = new byte[line];
						
						System.out.println("read Line : " + line);
						for(int i=0; i<line; i++)
							deco[i] = temp[i];
						for(int i=0; i<line; i++)
							System.out.print(deco[i]);
						System.out.println("///deco : " + deco.length);
						sdeco = cryptobase64nseed.decoding(deco);
						System.out.println(sdeco);
						area.append("Server : " + rsa.decryptionMessage(new BigInteger(sdeco)) + "\n");
						
						
						//area.append("Server : " + rsa.decryptionMessage(new BigInteger(in.readUTF())) + "\n");
		
					}catch(IOException e){
						e.printStackTrace();
					}
				}
		
			}
		}
		
		public void windowOpened(WindowEvent e){}
		public void windowClosing(WindowEvent e){
			e.getWindow().setVisible(false);
			e.getWindow().dispose();
			try{
				socket.close();
			}catch(IOException ie){}
			System.exit(0);
		}
		public void windowClosed(WindowEvent e){}
		public void windowIconified(WindowEvent e){}
		public void windowDeiconified(WindowEvent e){}
		public void windowActivated(WindowEvent e){}
		public void windowDeactivated(WindowEvent e){}

}