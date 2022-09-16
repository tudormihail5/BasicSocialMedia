package com.bham.fsd.assignments.jabberserver;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

//Paul Aldea 2210814
public class ClientConnection implements Runnable{
	
	private Socket clientSocket;
	private JabberDatabase database;
	
	
	public ClientConnection(Socket clientSocket, JabberDatabase database) {
		this.clientSocket = clientSocket;
		this.database = database;
		new Thread(this).start();
	}
	
	private String usr = null;
	
	@Override
	public void run() {
		try {
			System.out.print("RUN");
			//Message from client
			ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
			//Server output
			ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
			while(true) {
				JabberMessage request = (JabberMessage) ois.readObject();
				JabberMessage resp = req(request);
				
				if(resp != null) {
					oos.writeObject(resp);
					oos.flush();
				}
								
				Thread.sleep(100);
			}//end of while
		}//end of try
		catch(Exception e) {
			e.printStackTrace();
		}//end of catch
		
	}
	
	public JabberMessage req(JabberMessage request) {
		
		JabberMessage resp = null;
		String message = request.getMessage();
		//System.out.println(message);
		String[] arr = message.split(" ", 2);
		
		switch(arr[0]) {
			case "signin":
				resp = signin(arr[1]);
				break;
			case "register":
				resp = register(arr[1]);
				break;
			case "signout":
				usr = null;
				return null;
			case "timeline":
				resp = getTimeline();
				break;
			case "post":
				resp = postJab(arr[1]);
				break;
			case "like":
				resp = likeJab(arr[1]);
				break;
			case "follow":
				resp = followingUser(arr[1]);
				break;
			case "users":
				resp = getUsers();
				break;
		}//end of switch
		
		return resp;
	}
	
	public JabberMessage signin(String username){
		
		int id = database.getUserID(username);
		//System.out.println(id);
		if (id == -1)
			return new JabberMessage("unknown-user");
		usr = username;
		return new JabberMessage("signedin");
	}
	
	public JabberMessage register(String username) {
		int id = database.getUserID(username);
		if (id == -1) {
			database.addUser(username, username + "@newuser.com");
			usr = username;
		}
		return new JabberMessage("signedin");
	}
	
	public JabberMessage getTimeline() {
		ArrayList<ArrayList<String>> timeline;
		if(usr != null)
		{
			timeline = database.getTimelineOfUserEx(usr);
			return new JabberMessage("timeline", timeline);
		}
		return null;
	}
	
	
	public JabberMessage getUsers() {
		//System.out.println(usr);
		ArrayList<ArrayList<String>> gettingusers;
		if(usr != null)
		{
			int id = database.getUserID(usr);
			gettingusers = database.getUsersNotFollowed(id);
			return new JabberMessage("users", gettingusers);
		}
		return null;
	}
	
	public JabberMessage postJab(String jabtxt) {
		
		if(usr != null) {
			database.addJab(usr, jabtxt);
			return new JabberMessage("posted");
		}
		return null;
		
	}

	public JabberMessage likeJab(String jid) {
		
		if(usr != null) {
			int id = database.getUserID(usr);
			int jidint = Integer.parseInt(jid);
			database.addLike(id, jidint);
			return new JabberMessage("posted");
		}
		return null;
	}
	
	public JabberMessage followingUser(String username) {
		
		if (usr != null) {
			int userida = database.getUserID(usr);
			int useridb = database.getUserID(username);
			database.addFollower(userida, useridb);
			return new JabberMessage("posted");
		}
		return null;
	}
}