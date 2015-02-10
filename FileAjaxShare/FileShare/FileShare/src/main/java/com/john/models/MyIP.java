package com.john.models;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.springframework.stereotype.Component;

@Component("myIp")
public class MyIP {
	// if window
//	public String windowIp() {
//
//	}

	// if linux
	public String linuxIp() {
		String tmp = "";
		Enumeration<NetworkInterface> ne;
		try {
			ne = NetworkInterface.getNetworkInterfaces();
			while (ne.hasMoreElements()) {
				NetworkInterface ni = ne.nextElement();
				Enumeration<InetAddress> inets = ni.getInetAddresses();
				while (inets.hasMoreElements()) {
					InetAddress iaddr = inets.nextElement();
//					System.out.println("IP 주소이름 : " + iaddr.getHostName());
//					System.out.println("IP 주소 : " + iaddr.getHostAddress());
//					System.out.println("IP 로컬주소여부 : "
//							+ iaddr.isSiteLocalAddress());
//					System.out.println("IP isLoopbackAddress : "
//							+ iaddr.isLoopbackAddress());
					if(!iaddr.isLoopbackAddress()){
						 tmp = iaddr.getHostAddress();
						 
					}
				}
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tmp;
	}
}
