package org.pratim.sideburn;

import java.io.Serializable;


public class FIVE_TUPLE_DATA implements Serializable{
	
	private static final long serialVersionUID = 4622827504896108228L;
	String serverIP;
	long bytesRecv;
	long bytesSent;
	String clientIP; 
	int clientPort;
	String protocol;
	int serverPort;
	
	public FIVE_TUPLE_DATA(){
		
	}
	
	public FIVE_TUPLE_DATA(String serverIP,long bytesRecv,long bytesSent,String clientIP,int clientPort,int serverPort,String protocol){
		
		setServerIP(serverIP);
		setBytesRecv(bytesRecv);
		setBytesSent(bytesSent);
		setClientIP(clientIP);
		setClientPort(clientPort);
		setServerPort(serverPort);
		setProtocol(protocol);
		
	}
	
	@Override
	public String toString() {
		return serverIP + "|" + bytesRecv + "|" + bytesSent + "|" + clientIP + "|" + clientPort + "|" + serverPort + "|" + protocol;
	}

	public String getServerIP() {
		return serverIP;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public long getBytesRecv() {
		return bytesRecv;
	}

	public void setBytesRecv(long bytesRecv) {
		this.bytesRecv = bytesRecv;
	}

	public long getBytesSent() {
		return bytesSent;
	}

	public void setBytesSent(long bytesSent) {
		this.bytesSent = bytesSent;
	}

	public String getClientIP() {
		return clientIP;
	}

	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}

	public int getClientPort() {
		return clientPort;
	}

	public void setClientPort(int clientPort) {
		this.clientPort = clientPort;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}	
	
}
