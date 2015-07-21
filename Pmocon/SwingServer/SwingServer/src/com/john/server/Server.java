package com.john.server;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;

public class Server extends JFrame implements Runnable{
	 
	private HashMap clients;
	private HashMap<String, Integer> clientsIndex;
	int start = 1;
	int current = 1;
	int end = 0;
	int user = 1;
	int port = 9000;
	String masterNumber;
	String msg = "";
	JTextField ipText;
	JTextField portText;
	JTextField phoneNumText;
	JTextField serverNameText;
	JLabel ipLabel;
	JLabel portLabel;
	JLabel phoneNumber;
	JLabel serverNameLabel;
	JPanel ipPanel;
	JPanel LogPanel;
	JPanel progressPanel;
	JPanel filePanel;
	JButton connectBtn;
	JButton fileBtn;
	JButton closeBtn;
	JTextArea serverLog;
	JProgressBar progressBar;
	InetAddress ip;

	int uCurrent =  0;
	
	ServerSocket serverSocket= null;
	Socket socket = null;
	static Server server;
	
	public Server(){
		super("Server");
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		clients = new HashMap();
		clientsIndex = new HashMap<>();
		Collections.synchronizedMap(clients);
		
		
		
		///// panel contatiner
//		Container c = getContentPane();
		
		/// ip panel
		ipPanel = new JPanel();
		ipPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		ipPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Server IP, Port"));
		ipPanel.setPreferredSize(new Dimension(350,140));
		
		ipPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		ipLabel = new JLabel("IP: ");
		ipText = new JTextField(12);
		try{
			ip = InetAddress.getLocalHost();
			ipText.setText(ip.getHostAddress());
		}catch(Exception e){
			e.printStackTrace();
		}
		ipText.setEditable(false);
		ipPanel.add(ipLabel);
		ipPanel.add(ipText);
		filePanel = new JPanel();
		
		// port panel
		portLabel = new JLabel("Port: ");
		portText = new JTextField(7);
		ipPanel.add(portLabel);
		ipPanel.add(portText);
		
		///// phone nubmer = master
		phoneNumber = new JLabel("Phone Number: ");
		phoneNumText = new JTextField(17);
		ipPanel.add(phoneNumber);
		ipPanel.add(phoneNumText);
		
		////// server name
		serverNameLabel = new JLabel("Server Name: ");
		serverNameText = new JTextField(17);
		ipPanel.add(serverNameLabel);
		ipPanel.add(serverNameText);
		
		//// connect button
		connectBtn = new JButton("연결");
		connectBtn.addActionListener(new ServerStart());
		ipPanel.add(connectBtn);
		
		//// close button
		closeBtn = new JButton("종료");
		closeBtn.addActionListener(new ServerClose());
		ipPanel.add(closeBtn);
		
		///// server log
		LogPanel = new JPanel();
		LogPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		LogPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Server Log"));
		LogPanel.setPreferredSize(new Dimension(350,130));
		serverLog = new JTextArea(5,30);
		serverLog.setEditable(false);
		JScrollPane s = new JScrollPane(serverLog);
		LogPanel.add(s);
		
		
		////// file panel
		filePanel.setLayout(new BorderLayout());
		filePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		filePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("pptx to image"));
		filePanel.setPreferredSize(new Dimension(350,70));
		fileBtn = new JButton("File");
		
		fileBtn.addActionListener(new FileOpen());
		
		filePanel.add(fileBtn);
		
		
		///////// progress panel
		
		progressPanel = new JPanel();
		progressPanel.setLayout(new BorderLayout());
		progressPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		progressPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("progress"));
		progressPanel.setPreferredSize(new Dimension(350,70));
		
		
		progressBar = new JProgressBar(0,100);
        progressBar.setVisible(true);
        
//        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressPanel.add(progressBar);
		
		
//		c.add(ipPanel);
//		c.add(portPanel);
		add(ipPanel, BorderLayout.NORTH);
		add(LogPanel);
		add(filePanel);
		add(progressPanel);
//		add(connectBtn);
		setSize(400,480);
		
		
		setVisible(true);
	}
	
	public void run(){
		
		
		try{
			serverSocket = new ServerSocket(port);
			System.out.println("서버가 시작되었습니다.");
			serverLog.setText("서버가 시작되었습니다.");
			while(true){
				
				

				socket = serverSocket.accept();
				System.out.println("server connected");
				serverLog.setText("사용자와 연결되었습니다.");
				
				DataOutputStream cout = new DataOutputStream(socket.getOutputStream());
				DataInputStream cin = new DataInputStream(socket.getInputStream());
				cout.writeUTF("9000");
				cout.writeUTF(ip.getHostAddress());
				cout.writeUTF(serverNameText.getText());
				
//				DataOutputStream cout = new DataOutputStream(socket.getOutputStream());
//				
//				InetAddress ip = InetAddress.getLocalHost();
//				cout.writeUTF(ip.getHostAddress());
				
				
				//// ack 시에만 연결 
				String ack = cin.readUTF();
				if(ack.equals("ACK")){
					ServerReceiver thread = new ServerReceiver(socket);
					
					thread.start();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void sendToall(){
		Iterator it = clients.keySet().iterator();
		while(it.hasNext()){
			try{
				
				DataOutputStream out = (DataOutputStream) clients.get(it.next());
				
				
				File f = new File("C:\\Data\\Image\\" + current +".png");
				FileInputStream fis = new FileInputStream(f);
				BufferedInputStream bis = new BufferedInputStream(fis);
				
				
				int len;
				int size = (int)f.length();
				out.writeUTF("" + "1");
				out.writeUTF("" + size);
				
				byte[] data = new byte[8192];
				while((len = bis.read(data)) != -1){
					out.write(data, 0, len);
				}
				System.out.println(size);
				out.flush();
//				
			}catch(IOException e){
				e.printStackTrace();
			}
		} // while
	}
	
	public void sendToUser(String name, int changedCurrent){
		try{
			DataOutputStream out = (DataOutputStream) clients.get(name);
			
			
			File f = new File("C:\\Data\\Image\\" + changedCurrent +".png");
			FileInputStream fis = new FileInputStream(f);
			BufferedInputStream bis = new BufferedInputStream(fis);
			
			
			int len;
			int size = (int)f.length();
			out.writeUTF("" + name);
			out.writeUTF("" + size);
			
			byte[] data = new byte[8192];
			while((len = bis.read(data)) != -1){
				out.write(data, 0, len);
			}
			System.out.println(size);
			out.flush();
//			
		}catch(IOException e){
			e.printStackTrace();
		}
		
		
	}
	
	
	
	/////// main
	public static void main(String[] args) {
		//// server start
//		new Server().start();
		server = new Server();
		
		
		
	}
	
	class ServerReceiver extends Thread{
		Socket socket;
		DataInputStream in;
		DataOutputStream out;
		int tmp = 1; // setter method
		public ServerReceiver(Socket socket){
			this.socket = socket;
			try{
				System.out.println("connected sucess");
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
				
				if(in.readUTF().equals(masterNumber)){
					clients.put("1", out);
					out.writeUTF("1");
					sendToUser("1", current);
					System.out.println("user : 1 , current : " + current);
				}else{
					user++;					
					clients.put("" + user, out);
					out.writeUTF("" + user);
					sendToUser("" + user, current);
					System.out.println("user : " + user);
				}
				

//				System.out.println("user : " + user);
//				out.writeUTF("" + user);
				
			}catch(IOException e){
				e.printStackTrace();
			}
		} 
		
		public void run(){
			String userName = "" + user;
			try{
				while(in != null){
					String name = in.readUTF();
					String message = in.readUTF();
					int code = Integer.parseInt(message);
					
					if(name.equals("1")){
						setTmp(current);
//						System.out.println("uCurrent master: " + tmp );
						movePosition(code);
						sendToall();
					}else{
						
						moveUserPosition(code, userName);
//						sendToUser(userName, uCurrent);

//						System.out.println("uCurrent user: " + tmp );
					}
					
//					System.out.println(name + " : " + message);
					
				}
			}catch(IOException e){
//				e.printStackTrace();
			}finally{
				clients.remove(userName);
				System.out.println(userName + "님이 접속을 종료하셨습니다.");
			}
		}
		
		public void setTmp(int value){
			this.tmp = value;
		}
		
		public void movePosition(int code){
			Robot robot;
			try{
				robot = new Robot();
				
				switch(code){
				case 501 :
					robot.keyPress(KeyEvent.VK_RIGHT);
					robot.delay(10);
					current++;
					if(current > end){
						current = end;
					}
					tmp = current;
					break;
				case 502 :
					robot.keyPress(KeyEvent.VK_LEFT);
					robot.delay(10);
					current--;
					if( current < 1 ){
						current = start;
					}
					tmp = current;
					break;
				}
			}catch(AWTException e){}
		}
		
		public void moveUserPosition(int code, String userName){
			int username = Integer.parseInt(userName);
//			currentIndex[username] = current;
			switch(code){
			case 501:
				tmp +=1;
				if(tmp > end){
					tmp = end;
				}
				
				System.out.println("index : " + tmp);
				sendToUser(userName, tmp);
				
				break;
			case 502:
				tmp -=1;
				if(tmp < 1){
					tmp = start;
				}

				System.out.println("index : " + tmp);
				sendToUser(userName, tmp);
				break;
			}
			
			
		}
		
		
		
	}// ServerReceiver
	
	
	////// Server start class
	
	class ServerStart implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			String text = portText.getText();
			String phone = phoneNumText.getText();
			String serverName = serverNameText.getText();
			if(!text.trim().equals("") && !phone.trim().equals("") && !serverName.trim().equals("")){
				port = Integer.parseInt(text);
				masterNumber = phone;
				//// if trim 
//				System.out.println(port + " : " + masterNumber);
				
//				start();
				new Thread(server).start();
			}
			
			
		}
		
	}
	
	/////// server close class
	class ServerClose implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}
		
		
	}
	

	///// file open class
	class FileOpen extends JFrame implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			FileDialog fileOpen = new FileDialog(this, "파일열기", FileDialog.LOAD);
			fileOpen.setVisible(true);
			String path = fileOpen.getDirectory() + fileOpen.getFile();
			System.out.println(path);
	        msg += "처리중입니다..... \n";
	        serverLog.setText(msg);
			
			
			try {


				end = ConvertToImage(path);
			} catch (Exception e1) {
//				e1.printStackTrace();
//				System.out.println("cancel");
			}
			
			System.out.println(end);
			
			
		}
		
	}// FileOpen
	
	
	//////////// convert method
	public int ConvertToImage(String path) throws Exception {
        FileInputStream is = new FileInputStream(path);
        XMLSlideShow ppt = new XMLSlideShow(is);
        is.close();

        double zoom = 2; // magnify it by 2
        AffineTransform at = new AffineTransform();
        at.setToScale(zoom, zoom);

        Dimension pgsize = ppt.getPageSize();

        XSLFSlide[] slide = ppt.getSlides();
        
        /////// progress create
//        progressBar = new JProgressBar(0,slide.length);
//        progressBar.setVisible(true);
//        progressBar.setValue(0);
//        progressBar.setStringPainted(true);
//        progressPanel.add(progressBar);
        current = 1;
        int length = slide.length;
        for (int i = 0; i < length; i++) {
            
            BufferedImage img = new BufferedImage((int)Math.ceil(pgsize.width*zoom), (int)Math.ceil(pgsize.height*zoom), BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = img.createGraphics();
            graphics.setTransform(at);

            graphics.setPaint(Color.white);
            graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
            slide[i].draw(graphics);
            FileOutputStream out = new FileOutputStream( "C:\\Data\\Image\\"  + (i + 1) + ".png");
            javax.imageio.ImageIO.write(img, "png", out);
            out.close();
            progressBar.setValue((i+1)*100/length);
            msg += "convert...." + (i+1) + "/" + length + "\n";
            serverLog.setText(msg);
        }
        
        sendToall();
        
        return slide.length;
    }
	
	
 
}