package com.bham.fsd.assignments.jabberserver;

import java.net.ServerSocket;
import java.net.Socket;

import com.bham.fsd.assignments.jabberserver.ClientConnection;

//Paul Aldea 2210814

public class JabberServer implements Runnable{
	
	private static final int PORT_NUMBER = 44444;
	private ServerSocket serverSocket;
	
	public JabberServer() {
		
		try {
			serverSocket = new ServerSocket(PORT_NUMBER);
			//serverSocket.setSoTimeout(300);
			new Thread(this).start();
			System.out.println("Waiting");
		}
		catch(Exception e) {
			e.printStackTrace();
		}//end catch
	}//end JabberServer
	
	@Override
	public void run() {
		try{
			while(true) {
				Socket clientSocket = serverSocket.accept();
				ClientConnection client = new ClientConnection(clientSocket, new JabberDatabase());
				Thread.sleep(100);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}//end of run
	
	public static void main(String[] args) {
		JabberServer jab = new JabberServer();
	}
}
