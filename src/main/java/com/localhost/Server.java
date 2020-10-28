package com.localhost;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
	DataOutputStream Out ;
	BufferedReader In ;
	ServerSocket Socket;
	MailBox box;
	
	public Server() throws IOException{
		Socket = new ServerSocket(5000);
		box = new MailBox();
	}
	
	public void outMsg(String msg) {
		try {
			Out.writeBytes(msg);
		} catch (IOException e) {
			log("Probleme lie a l'envoi du message au serveur");
		}
	}
	
	public String inMsg() {
		String msg="";
		try {
			if(msg!=null){
				msg = In.readLine();
				log(msg);
			}
		}catch (IOException e) {
			log("Probleme lie a la recupertion du message entrant");
		}
		return msg;
	}
	
	public synchronized String BlackBox(String expression){
		String res="";
		log("le message est "+expression);
		if(("get").equals(expression)){
			try {
				log("le message est "+expression);
				res = Integer.toString(box.get());
				log(res);
				if(res.equals("-1")) res = "box vide";
			} catch (InterruptedException e) {
				log("vous etes mis en attente pour le moment");
			}
		}else{
			String[] valeurs = expression.split(" ");
			if("put".equals(valeurs[0]))
			expression = valeurs[1];
			try {
				res = Integer.toString(box.put(Integer.parseInt(expression)));
				if(res.equals("-1")) res = "box pleine";
				else res = "element inserer";
			} catch (NumberFormatException e) {
				log("vous n'avez pas entrer un nombre ");
			} catch (InterruptedException e) {
				log("vous etes mis en attente pour le moment");
			}
		}
		return res;
	}
	
	 public void log(String msg){
			System.out.println(msg);
		}
	 
	 public void closeAll() {
			try {
				Out.close();
				In.close();
				System.exit(0);
			} catch (IOException e) {
				log("Probleme lie a la fermeture des connections");
			}
			
		}
	 
	public void run(){
		while(true){
			try {
				Socket socket = Socket.accept();
				log("connection etablie");
				Out = new DataOutputStream(socket.getOutputStream());
				In =  new BufferedReader(
						new InputStreamReader(socket.getInputStream()));
				String msg =inMsg();
				while(msg!=null && !msg.equals("stop\n")){
					String res = BlackBox(msg);
					outMsg(res+"\n");
					msg =inMsg();
				}
			} catch (IOException e) {
				log("probleme lie a l'ecoute sur le port");
			}
			
		}
		
	}
	
	public static void main(String[] args) {
		Server server;
		try {
			server = new Server();
			server.run();
		} catch (IOException e) {
			System.out.println("probleme d'initialisation du server" + e.getMessage());
		}
		
	}
}
