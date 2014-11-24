package Server;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import RSA.*;

public class Server extends JFrame implements WindowListener, Runnable{
		JTextField text;
		static JTextArea area = new JTextArea();
		HashMap clients = null;
		ServerSocket serverSocket = null;
		Socket socket = null;
		Receiver receiver = null;
		DataOutputStream out;
		DataInputStream in;
		String name, msg, Clname, inmsg;
		JScrollPane scrollPane;
		Choice choice;
		RSA rsa = new RSA();
		DH dh;
		// Client key
		BigInteger publicKey[]; // 0 = N , 1 = e
		BigInteger selectKey[];
		BigInteger publicDHKey[];
		BigInteger gToThePowerOfb;
		boolean sendKey = false, getKey = false, getDH=false;
		String key1, key2;
		CryptoBase64nSeed cryptobase64nseed;
		KeySet keyset;
		// find Cl
		public Server(){
				super("Server");
				
				clients = new HashMap();
				//Collections.synchronizedMap(clients);
				
				text = new JTextField();
				choice = new Choice();
				
				choice.add("All");
				
				setSize(300,500);
				add(text, BorderLayout.SOUTH);
				add(area, BorderLayout.CENTER);
				
				add(choice, BorderLayout.NORTH);
				
				scrollPane = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
						JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				add(scrollPane,BorderLayout.CENTER);
				
				setVisible(true);
				text.requestFocus();
		
				text.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						inmsg = text.getText();
						area.append( name + " : " + inmsg + "\n");
						text.setText("");
						text.requestFocus();
						try{
							if("All".equals(choice.getSelectedItem())){
								sendToAll(inmsg);
								
							}else{
								msg = "" + rsa.encryptionMessage(inmsg, selectKey);
								System.out.println("msg:" + msg);
//								area.append( name + " : " + inmsg + "\n");
//								text.setText("");
//								text.requestFocus();
							
								out.write(cryptobase64nseed.encoding(msg));
								System.out.println("\n\n\n");
								
							}
						}catch(IOException ee){
							ee.printStackTrace();
						}
					}
				});
				
				choice.addItemListener(new ItemListener(){
					public void itemStateChanged(ItemEvent e){
						System.out.println("Selected : " + choice.getSelectedItem());
						if(choice.getSelectedItem() != "All"){
							keyset = (KeySet)clients.get(choice.getSelectedItem());
							System.out.println(((BigInteger[])keyset.getpublicKey())[0]);
							selectKey =  keyset.getpublicKey();
							out = keyset.getOut();
							cryptobase64nseed = new CryptoBase64nSeed(keyset.getDHKey());
						}
					}
				});
				
				area.setEditable(false);
				// temp
				System.out.println(rsa.getPublicKey()[0]);
				System.out.println(rsa.getPublicKey()[1]);
				
				name = "Server";		
				// receive thread

		}// server();
		
		public void run(){
			// connec
			try{
				// key input
				serverSocket = new ServerSocket(7777);
				JOptionPane.showMessageDialog(this, "연결중입니다");
				// Accept -> to thread???
					
				while(true){	
					socket = serverSocket.accept();
					publicKey = new BigInteger[2];
					publicDHKey = new BigInteger[2];
					
					
					// publickey send				
					out = new DataOutputStream(socket.getOutputStream());				
					// strans key
					in = new DataInputStream(socket.getInputStream());
						// DHKey
						publicDHKey[0] = new BigInteger(in.readUTF());
						publicDHKey[1] = new BigInteger(in.readUTF());
						gToThePowerOfb = new BigInteger(in.readUTF());
						
						System.out.println("DHK : " + publicDHKey[0]);
						System.out.println("DHK : " + publicDHKey[1]);
						System.out.println("DHKB : " + gToThePowerOfb);
						
						// publicKey
						// server -> client
						// if String
						out.writeUTF("" + rsa.getPublicKey()[0]);
						System.out.println("key1 send : " + rsa.getPublicKey()[0]);
						out.writeUTF("" + rsa.getPublicKey()[1]);
						System.out.println("key2 send : " + rsa.getPublicKey()[1]);
						System.out.println("send Ok!!!");
						sendKey = true;
						// client -> server
						key1 = in.readUTF();
						key2 = in.readUTF();
						Clname = in.readUTF();
						publicKey[0] = new BigInteger(key1);
						System.out.println("get key1 : " + publicKey[0]);
						publicKey[1] = new BigInteger(key2);
						System.out.println("get key2 : " + publicKey[1]);
						System.out.println("get Ok!!!");
						getKey = true;
						choice.add(Clname);
						
						JOptionPane.showMessageDialog(null, "연결되었습니다.");
						area.append(Clname + "님이 접속하였습니다.\n");	
						
					if(sendKey && getKey){
						
						System.out.println("add client");
						
						dh = new DH(publicDHKey[0], publicDHKey[1]);	
						out.writeUTF("" + dh.calcgToThePowerOfa());
						System.out.println("send DHB : " + dh.calcgToThePowerOfa());
						dh.calcDHKey(gToThePowerOfb);
						System.out.println("DH OK!!!");
						System.out.println("DHKEY : " + dh.getDHKey());
						addClient(Clname, publicKey, out, dh.getDHKey());
						sendKey = false; getKey = false;
					}
					
					
					
					
					receiver = new Receiver(socket);

					receiver.start();
					
				}
				
				

			}catch(IOException e){
				e.printStackTrace();
			}
			
		} // run
		
		void addClient(String Clname, BigInteger[] cpublicKey, DataOutputStream out, BigInteger DHKey){
			clients.put(Clname, new KeySet(out,cpublicKey,DHKey));
			
		} // func
		
		
		
		void allprint(){
			Set set = clients.entrySet();
			Iterator it = set.iterator();
			
			while(it.hasNext()){
				Map.Entry e = (Map.Entry)it.next();
				Set subset = ((HashMap)e.getValue()).entrySet();
				Iterator subit = subset.iterator();
				System.out.println("==============\n" + e.getKey());
				while(subit.hasNext()){
					Map.Entry subE = (Map.Entry)subit.next();
					System.out.println(((BigInteger[])subE.getKey())[0]);
					System.out.println(((BigInteger[])subE.getKey())[1]);
				} // while
			} // while
		} // func
		
		void sendToAll(String msg){
			Set set = clients.entrySet();
			Iterator it = set.iterator();
			String temp = "";
			while(it.hasNext()){
				Map.Entry e = (Map.Entry)it.next();
				temp = "" + rsa.encryptionMessage(msg, ((KeySet)e.getValue()).getpublicKey());
				System.out.println("원문 : " + temp);
				System.out.println("DHK : " + ((KeySet)e.getValue()).getDHKey());
				cryptobase64nseed = new CryptoBase64nSeed(((KeySet)e.getValue()).getDHKey());
					try{
						out = ((KeySet)e.getValue()).getOut();
						System.out.println("========================\n" + cryptobase64nseed.encoding(temp).length + "========================\n");
						
						System.out.println(cryptobase64nseed.encoding(temp)[cryptobase64nseed.encoding(temp).length-1]);
						System.out.println(cryptobase64nseed.encoding(temp)[cryptobase64nseed.encoding(temp).length-2]);
						
						System.out.println(cryptobase64nseed.encoding(temp)[0]);
						System.out.println(cryptobase64nseed.encoding(temp)[1]);
						
						for(int i=0; i<100; i++)
							System.out.print(cryptobase64nseed.encoding(temp)[i]);
						
//						System.out.println("========================\n" + cryptobase64nseed.encoding(temp).getBytes().length + "========================\n");
						System.out.println("========================\n" + cryptobase64nseed.decoding((cryptobase64nseed.encoding(temp))) + "========================\n");
						out.write(cryptobase64nseed.encoding(temp));
						
					}catch(Exception ed){}

			} // while
			
		}// func
		
	
		class Receiver extends Thread{
			Socket rsocket;
			byte[] temp;
			int line;
			String sdeco = "";
			public Receiver(Socket socket){
				rsocket = socket;
				temp = new byte[200];
				try{
					in = new DataInputStream(rsocket.getInputStream());
				}catch(IOException e){}
	
			}
			
			public void run(){
				while(in != null){
					try{
						
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
						area.append(Clname + " : " +  rsa.decryptionMessage(new BigInteger(sdeco)) + "\n");
						
						
						//System.out.println(temp);
//						System.out.println("read line: " + in.read(temp));
//						area.append(" : " + cryptobase64nseed.decoding(temp) + "\n");
						//area.append( Clname + " : " + rsa.decryptionMessage(new BigInteger(in.readUTF())) + "\n");
					}catch(IOException e){
					}catch(Exception e){
						//JOptionPane.showMessageDialog(null, "다시 접속 하세요.");
					}
				}
	
			}// run
		}// Receiver
		public void windowOpened(WindowEvent e){}
		public void windowClosing(WindowEvent e){
			e.getWindow().setVisible(false);
			e.getWindow().dispose();
			try{
				serverSocket.close();
			}catch(IOException ie){}
			System.exit(0);
		}
		public void windowClosed(WindowEvent e){}
		public void windowIconified(WindowEvent e){}
		public void windowDeiconified(WindowEvent e){}
		public void windowActivated(WindowEvent e){}
		public void windowDeactivated(WindowEvent e){}
	

}