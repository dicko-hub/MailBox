package com.localhost;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	DataOutputStream Out ;
	BufferedReader In ;
	Socket Socket;
	
	public Client(){
		Socket socket;
		try {
			socket = new Socket(InetAddress.getLocalHost(), 5000);
			Out = new DataOutputStream(socket.getOutputStream());
			In =  new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			Socket=socket;
			log("connection etablie");
		} catch (UnknownHostException e) {
			log("Probleme lie a l'accees a la marchine serveur");
		} catch (IOException e) {
			log("Probleme de connection au serveur");
		}
	}
	
	public void log(String msg){
		System.out.println(msg);
	}
	
	
	public void outMsg(String msg){
		try {
			Out.writeBytes(msg);
		} catch (IOException e) {
			log("Probleme lie a l'envoi du message au serveur");
		}
	}
	
	public void inMsg(){
		String msg ="";
		try {
			if(msg!=null){
				msg = In.readLine();
				log(msg);
			}
		} catch (IOException e) {
			log("Probleme lie a la recupertion du message entrant");
		}
	}
	
	public void closeAll(){
		try {
			Out.close();
			In.close();
			System.exit(0);
		} catch (IOException e) {
			log("Probleme lie a la fermeture des connections");
		}
	}
	
	public void run() throws IOException{
		Scanner in = new Scanner(System.in);
		log("veuillez entrer 'put x' x etant le nombre a mettre dans le box et stop pour arreter");
		log("veuillez entrer get pour recuperer le nbre suivant et stop pour arreter");
		String expression =in.nextLine();
		while(expression != null && !expression.equals("stop")){
			outMsg(expression+"\n");
			inMsg();
			expression = in.nextLine();
		}
		closeAll();
		in.close();
    	Socket.close();
		System.exit(0);
	}
	
	public static void main(String[] args) throws IOException {
		Client client = new Client();
		client.run();
	}
}
	
