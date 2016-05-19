package app.controllers;

import app.models.*;
import app.helpers.Serialization;
import app.listeners.*;
import app.views.indicator.*;

import java.net.*;

import java.io.*;

public class WelcomeControllerClient {
  private WelcomeListenerClient listener;

  private String url;
  private int port;
  private static Socket socket;
  private PrintWriter out = null;
  private BufferedReader in = null;
  private Serialization s;

  private Advisor userConnect;
  
  public WelcomeControllerClient(String url, int port) {
    this.url = url;
    this.port = port;
    this.s = new Serialization();
  }

  public void addListener(WelcomeListenerClient l) {
    listener = l;
  }

  public void createSocket(){
    String answer;
    try {
      socket = new Socket(url, port);
      out = new PrintWriter(socket.getOutputStream());
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      listener.authenticationIhm();
    } catch (Exception e) {
      listener.setErrorSocket();
    }
  }

  public void getConnection(String login, String pwd) {
    
    String serverAnswer;
    try {
      Advisor user = new Advisor(login, pwd);
      //Send information in Json format to server
      out.println("AUTH/User/" + s.serializeUser(user));
      out.flush();

      //Waiting for the answer (answer = "authentic" if success)
      serverAnswer = in.readLine();
      String[] splitedAnswer = serverAnswer.split("/");
      String response = splitedAnswer[0];
      String other = splitedAnswer[1];
      
      if (response.equals("Success")) {
        userConnect = s.unserializeUser(other);
        listener.setButtonBackMenu();
        listener.setMenu();
      } else {
        listener.updateAnswerLabel(other);
      }
    } catch (Exception e) {
      javax.swing.JOptionPane.showMessageDialog(null,"Le serveur ne répond plus");
    }
  }
  
  public void testAddNewAdress()  {
    /*
    try {
      Adress newAdress = new Adress(55555, 10, "route de chabanais", "CHASSENON", "16150");
      out.println("INSERT/Adress/" + s.serializeAdress(newAdress));
      out.flush();
      String response = in.readLine();
      if(response.equals("success")) {
          javax.swing.JOptionPane.showMessageDialog(null,"Adresse ajoutée avec succes");
      }else {
          javax.swing.JOptionPane.showMessageDialog(null,"Erreur : " + response);
      }
    } catch (Exception e) {
      javax.swing.JOptionPane.showMessageDialog(null,"Le serveur ne répond plus");
    }
    */
    javax.swing.JOptionPane.showMessageDialog(null,"Utilisateur connecté : " + userConnect.getFirstName() + " " + userConnect.getLastName());
  }
  
  public void menuBack()    {
      listener.setMenu();
  }
  
  public void goIndicator() {
    ControllerClientIndicator cci = new ControllerClientIndicator(socket);
    IndicatorView ihm = new IndicatorView(cci, listener.getBody(), listener.getContainer());
    cci.addListener(ihm);    
  }
  
}
