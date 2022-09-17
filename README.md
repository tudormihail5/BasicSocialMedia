# Basic Social Media

### What it does:

This is a basic client-server social media application, written in Java, but also using Scene Builder and SQL. After compiling the server, the user will compile the client as well to be able to use the app. Initially, the user can sing in using the name, or register, in case it is the first time using the app. The user can sign out too. After registering, two sections will appear: timeline and users to follow. The user can add anyone to see their posts, like them, and post something as well. The server threads allow the app to be used by more than one client at the same time, and the timeline and follow list are updated in real time.

### How I built it:

- I used Scene Builder to create the GUI.
- I created every functionality of the GUI in MainController, including the automatic refresh of the app (every 5 seconds), users being able to communicate in real time.
- MainController is also sending the requests to the server, using sockets.
- The Main class starts the application, loading Main.fxml, setting the size of the GUI, and displaying it.
- JabberMessage eases the communication between the server and the client, and is used by MainController to send the messages, but also by ClientConnection.
- ClientConnection receives the messages from the client, and completes the task, using the settled protocol.
- JabberDatabase deals with the database, adding things to it, or reading information requested by the server.
- JabberServer runs the server, delivering a 'Waiting' (for a client) message after compiling.

### Challenges I ran into:

It took me a while to find out how to create the automatic refresh. Also, making every button functional without knowing how many they are was a new thing for me. But the first and the most important challenge was to make the server and the client communicate properly.

![SocialMedia1](https://github.com/tudormihail5/BasicSocialMedia/blob/main/Screenshot1.png)

![SocialMedia2](https://github.com/tudormihail5/BasicSocialMedia/blob/main/Screenshot2.png)

![SocialMedia3](https://github.com/tudormihail5/BasicSocialMedia/blob/main/Screenshot3.png)

![SocialMedia4](https://github.com/tudormihail5/BasicSocialMedia/blob/main/Screenshot4.png)
