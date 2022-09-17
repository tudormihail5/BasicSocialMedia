package com.bham.fsd.assignments.jabberserver;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
public class MainController implements Initializable
{
	@FXML
	private TextField T1,T2;
	@FXML
	private Label L1;
	@FXML
	private Button B1,B2,B3,B6;
	private String message;
	private static final int PORT=44444;
	@FXML
	private VBox boxT,boxF;
	@FXML
	private AnchorPane P1,P2,P3;
	private Socket s;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private int i;
	private Timer t;
	public void setTimer()
	{
		t=new Timer();
		t.scheduleAtFixedRate(new TimerTask()
		{
			public void run()
			{
				Platform.runLater(()->showTimeline());
				Platform.runLater(new Runnable() 
				{
					@Override 
					public void run() 
					{
						try
						{
							boxF.getChildren().clear();
							message="users";
							JabberMessage jm5=new JabberMessage(message);
							message = String.valueOf(jm5.getMessage());
							oos.writeObject(jm5);
							oos.flush();
							TimeUnit.MILLISECONDS.sleep(500);
							JabberMessage jm6=(JabberMessage) ois.readObject();
							message=jm6.getMessage();
							if(message.equals("users"))
							{
								for(i=0;i<jm6.getData().size();++i)
								{
									HBox h1=new HBox();
									Image image=new Image(getClass().getResourceAsStream("plus.png"));
									Label l=new Label();
									l.setText(String.valueOf(jm6.getData().get(i).get(0)));
									l.setWrapText(true);
									l.setFont(new Font(17));
									h1.getChildren().add(l);
									Button B5=new Button();
									ImageView img=new ImageView(image);
									img.setFitHeight(20);
									img.setFitWidth(20);
									B5.setGraphic(img);
									B5.setOnAction(new EventHandler<ActionEvent>() 
									{
										@Override
										public void handle(ActionEvent e9) 
										{
											try
											{
												message="follow "+l.getText();
												JabberMessage jm7=new JabberMessage(message);
												message = String.valueOf(jm7.getMessage());
												oos.writeObject(jm7);
												oos.flush();
												TimeUnit.MILLISECONDS.sleep(500);
												JabberMessage jm8=(JabberMessage) ois.readObject();
												message=jm8.getMessage();
												if(message.equals("posted"))
												{
													boxF.getChildren().remove(h1);
													boxT.getChildren().clear();
													showTimeline();
												}
											}
											catch(Exception e3)
											{
												e3.printStackTrace();
											}
										}
									});
									h1.getChildren().add(B5);
									boxF.getChildren().add(h1);
								}
							}
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
					}
				});
			}
			
		},5000,5000);
	}
	public void signin(ActionEvent e) 
	{
		setTimer();
		try
		{
			boxF.getChildren().clear();
			P1.setVisible(true);
			P2.setVisible(true);
			P3.setVisible(true);
			s = new Socket("localhost", PORT);
			oos=new ObjectOutputStream(s.getOutputStream());
			message="signin "+T1.getText();
			JabberMessage jm1=new JabberMessage(message);
			message = String.valueOf(jm1.getMessage());
			oos.writeObject(jm1);
			oos.flush();
			TimeUnit.MILLISECONDS.sleep(500);
			ois = new ObjectInputStream(s.getInputStream());
			JabberMessage jm2=(JabberMessage) ois.readObject();
			message=jm2.getMessage();
			if(message.equals("signedin"))
			{	
				L1.setText("Signed in!");
				showTimeline();
				boxF.getChildren().clear();
				message="users";
				JabberMessage jm5=new JabberMessage(message);
				message = String.valueOf(jm5.getMessage());
				oos.writeObject(jm5);
				oos.flush();
				TimeUnit.MILLISECONDS.sleep(500);
				JabberMessage jm6=(JabberMessage) ois.readObject();
				message=jm6.getMessage();
				if(message.equals("users"))
				{
					for(i=0;i<jm6.getData().size();++i)
					{
						HBox h1=new HBox();
						Image image=new Image(getClass().getResourceAsStream("plus.png"));
						Label l=new Label();
						l.setText(String.valueOf(jm6.getData().get(i).get(0)));
						l.setWrapText(true);
						l.setFont(new Font(17));
						h1.getChildren().add(l);
						Button B5=new Button();
						ImageView img=new ImageView(image);
						img.setFitHeight(20);
						img.setFitWidth(20);
						B5.setGraphic(img);
						B5.setOnAction(new EventHandler<ActionEvent>() 
						{
							@Override
							public void handle(ActionEvent e9) 
							{
								try
								{
									message="follow "+l.getText();
									JabberMessage jm7=new JabberMessage(message);
									message = String.valueOf(jm7.getMessage());
									oos.writeObject(jm7);
									oos.flush();
									TimeUnit.MILLISECONDS.sleep(500);
									JabberMessage jm8=(JabberMessage) ois.readObject();
									message=jm8.getMessage();
									if(message.equals("posted"))
									{
										boxF.getChildren().remove(h1);
										boxT.getChildren().clear();
										showTimeline();
									}
								}
								catch(Exception e3)
								{
									e3.printStackTrace();
								}
							}
						});
						h1.getChildren().add(B5);
						boxF.getChildren().add(h1);
					}
				}
			}
			else
				if(message.equals("unknown-user"))
				{
					Stage stage = new Stage();
					FXMLLoader loader=new FXMLLoader();
					Pane root=loader.load(getClass().getResource("E1.fxml").openStream());
					Scene scene=new Scene(root);
					stage.setScene(scene);	
					stage.show();
				}
		}
		catch(Exception e1)
		{
			e1.printStackTrace();
		}
	}
	public void signout(ActionEvent e)
	{
		try
		{
			s = new Socket("127.0.0.1", PORT);
			oos=new ObjectOutputStream(s.getOutputStream());
			message="signout";
			JabberMessage jm1=new JabberMessage(message);
			message = String.valueOf(jm1.getMessage());
			oos.writeObject(jm1);
			oos.flush();
			((Node)e.getSource()).getScene().getWindow().hide();
		}
		catch(Exception e1)
		{
			e1.printStackTrace();
		}
	}
	public void register(ActionEvent e)
	{
		setTimer();
		try
		{
			P1.setVisible(true);
			P2.setVisible(true);
			P3.setVisible(true);
			s = new Socket("127.0.0.1", PORT);
			oos=new ObjectOutputStream(s.getOutputStream());
			message="register "+T1.getText();
			JabberMessage jm1=new JabberMessage(message);
			message = String.valueOf(jm1.getMessage());
			oos.writeObject(jm1);
			oos.flush();
			TimeUnit.MILLISECONDS.sleep(500);
			ois = new ObjectInputStream(s.getInputStream());
			JabberMessage jm2=(JabberMessage) ois.readObject();
			message=jm2.getMessage();
			if(message.equals("signedin"))
			{
				L1.setText("Registered!");
				showTimeline();
				boxF.getChildren().clear();
				message="users";
				JabberMessage jm5=new JabberMessage(message);
				message = String.valueOf(jm5.getMessage());
				oos.writeObject(jm5);
				oos.flush();
				TimeUnit.MILLISECONDS.sleep(500);
				JabberMessage jm6=(JabberMessage) ois.readObject();
				message=jm6.getMessage();
				if(message.equals("users"))
				{
					for(i=0;i<jm6.getData().size();++i)
					{
						HBox h1=new HBox();
						Image image=new Image(getClass().getResourceAsStream("plus.png"));
						Label l=new Label();
						l.setText(String.valueOf(jm6.getData().get(i).get(0)));
						l.setWrapText(true);
						l.setFont(new Font(17));
						h1.getChildren().add(l);
						Button B5=new Button();
						ImageView img=new ImageView(image);
						img.setFitHeight(20);
						img.setFitWidth(20);
						B5.setGraphic(img);
						B5.setOnAction(new EventHandler<ActionEvent>() 
						{
							@Override
							public void handle(ActionEvent e9) 
							{
								try
								{
									message="follow "+l.getText();
									JabberMessage jm7=new JabberMessage(message);
									message = String.valueOf(jm7.getMessage());
									oos.writeObject(jm7);
									oos.flush();
									TimeUnit.MILLISECONDS.sleep(500);
									JabberMessage jm8=(JabberMessage) ois.readObject();
									message=jm8.getMessage();
									if(message.equals("posted"))
									{
										boxF.getChildren().remove(h1);
										boxT.getChildren().clear();
										showTimeline();
									}
								}
								catch(Exception e3)
								{
									e3.printStackTrace();;
								}
							}
						});
						h1.getChildren().add(B5);
						boxF.getChildren().add(h1);
					}
				}
			}
		}
		catch(Exception e1)
		{
			e1.printStackTrace();
		}
	}
	public void post(ActionEvent e)
	{
		try
		{
			message="post "+T2.getText();
			JabberMessage jm1=new JabberMessage(message);
			message = String.valueOf(jm1.getMessage());
			oos.writeObject(jm1);
			oos.flush();
			TimeUnit.MILLISECONDS.sleep(500);
			JabberMessage jm2=(JabberMessage) ois.readObject();
			message=jm2.getMessage();
			message="posted";
			if(message.equals("posted"))
			{
				boxT.getChildren().clear();
				showTimeline();
			}
		}
		catch(Exception e1)
		{
			e1.printStackTrace();
		}
	}
	public void showTimeline()
	{
		try
		{
			boxT.getChildren().clear();
			message="timeline";
			JabberMessage jm3=new JabberMessage(message);
			message = String.valueOf(jm3.getMessage());
			oos.writeObject(jm3);
			oos.flush();
			TimeUnit.MILLISECONDS.sleep(500);
			JabberMessage jm4=(JabberMessage) ois.readObject();
			message=jm4.getMessage();
			if(message.equals("timeline"))
			{
				for(i=0;i<jm4.getData().size();++i)
				{
					HBox h1=new HBox();
					Image image=new Image(getClass().getResourceAsStream("Heart.png"));
					Label l=new Label();
					Label id=new Label();
					l.setText(String.valueOf(jm4.getData().get(i).get(0))+": "+String.valueOf(jm4.getData().get(i).get(1)));
					id.setText(String.valueOf(jm4.getData().get(i).get(2)));
					l.setWrapText(true);
					l.setFont(new Font(17));
					h1.getChildren().add(l);
					Button B4=new Button();
					ImageView img=new ImageView(image);
					img.setFitHeight(20);
					img.setFitWidth(20);
					B4.setGraphic(img);
					Label l1=new Label();
					l1.setText(String.valueOf(jm4.getData().get(i).get(3)));
					B4.setOnAction(new EventHandler<ActionEvent>() 
					{
						@Override
						public void handle(ActionEvent e2) 
						{
							try
							{
								message="like "+id.getText();
								JabberMessage jm7=new JabberMessage(message);
								message = String.valueOf(jm7.getMessage());
								oos.writeObject(jm7);
								oos.flush();
								TimeUnit.MILLISECONDS.sleep(500);
								JabberMessage jm8=(JabberMessage) ois.readObject();
								message=jm8.getMessage();
								if(message.equals("posted"))
								{
									boxT.getChildren().clear();
									showTimeline();
								}
							}
							catch(Exception e3)
							{
								e3.printStackTrace();
							}
						}
					});
					h1.getChildren().add(B4);
					h1.getChildren().add(l1);
					boxT.getChildren().add(h1);
				}
			}
		}
		catch(Exception e4)
		{
			e4.printStackTrace();
		}
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		P1.setVisible(false);
		P2.setVisible(false);
		P3.setVisible(false);
	}
}
