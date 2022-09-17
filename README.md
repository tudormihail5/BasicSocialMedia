# Basic Social Media

### What it does:

This is a basic client-server social media application, written in Java, but also using Scene Builder and SQL. After compiling the server, the user will compile the client as well to be able to use the app. Initially, the user can sing in using the name, or register, in case it is the first time using the app. The user can sign out too. After registering, two sections will appear: timeline and users to follow. The user can add anyone to see their posts, like them, and post something as well. The server threads allow the app to be used by more than one client at the same time, and the timeline and follow list are updated in real time.

### How I built it:

- I used Scene Builder to create the GUI.
- I created every functionality of the GUI in MainController, including the automatic refresh of the app, users being able to communicate in real time.
- MainController is also sending the requests to the server.
- The Main class starts the application, loading Main.fxml, setting the size of the GUI, and displaying it.
- JabberMessage eases the communication between the server and the client, and is used by MainController to send the messages, but also by ClientConnection.
- 

### Challenges I ran into:
