import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JPasswordField;

public class ATMServer {

	int port_number;
	ServerSocket server_socket;
	Socket client_socket;
	private static SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");
	private static DecimalFormat df2 = new DecimalFormat("0.00");

		
		public static void main( String args[]) throws IOException, InterruptedException{
			int port = Integer.valueOf( args[0]);
			ServerSocket server = new ServerSocket(port);			
			Socket client_sock = server.accept();					//accept connection
			PrintWriter output = new PrintWriter(client_sock.getOutputStream(), true);
			BufferedReader input = new BufferedReader(new InputStreamReader(client_sock.getInputStream()));	   //get what the client says
			output.println("******************************\n"
					+ "DePaul University Bank\n"+
					"******************************");
			output.println("Enter your UserID: ");
			output.println("END");							//message to stop the loop in the client.	
			String userid = input.readLine();			//first line - userid
			File file = new File(userid);
			if ( file.exists() && file.canRead() )
				System.out.println( "Located user...." );			
			else
			{
				System.out.println( "User Not found" );	
				output.println("Invalid UserID.\n" + "EXIT");
				return;
			}
			output.println("Enter your pin: \n" + "END");
			File pas = new File(file+"/password.txt");
			BufferedReader br = new BufferedReader(new FileReader(pas) );
			String pin = br.readLine();
			String userpass = input.readLine();
			if(pin.equals(userpass)){
			}
			else{
				output.println("Invalid pin\n" + "EXIT");
				output.close();
				input.close();
				client_sock.close();
				server.close();
				return;
				}
			ATMScreen(output);
			
			String selection = input.readLine();
			if(selection.equals("1")){
				deposit(userid,client_sock,output,input);
			}
			if(selection.equals("2")){
				withdrawl(userid,client_sock,output,input);
			}
			if(selection.equals("3")){
				showStatements(userid,client_sock,output,input);
			}
			if(selection.equals("5")){			
				newAccount(userid,client_sock,output,input);
			}
			else if(selection.equals("4")){
				changePin(userid,client_sock,output,input);
			}
			client_sock.close();
			server.close();
			
			
			
		
		
		
	}
		
		public static void changePin(String userID, Socket client_sock, PrintWriter output, BufferedReader input2) throws IOException{
			File file = new File(userID);
			if(file.isDirectory()){
				String serc;
				output.println("Security Question : \n"
						+ "What is your favorite CDM class?\n" + "END");
				serc = input2.readLine();
				if(serc.equals("CSC376")){
				output.println("Enter 4 digits for your new pin\n" + "END");
				String ans = input2.readLine();
				PrintWriter change = new PrintWriter(file+"/password.txt");
				change.println(ans);
				change.flush();
				}
				if(serc.equals("CSC376") != true){
					output.println("That answer was incorrect\n" + "END");
				}
			}
			
			else{
				output.println("ERROR\n" + "END");
			}
			
			
		}
		
		
		
		public static void newAccount(String userID, Socket client_sock, PrintWriter output, BufferedReader input2) throws IOException{
			File file = new File(userID);
			String [] account = file.list();
			output.println("Which account would you like to create? \n"
					+"(1) Personal \n"
					+"(2) Savings \n" + "END");
			String s = input2.readLine();
				if(s.equals("1")){
					File newfile = new File(file+"/Account_Personal.txt");
						if(newfile.createNewFile()){
						}
						else
							output.println("You already have a personal account.");
						
						
					}
					if(s.equals("2")){
						File newfile1 = new File(file+"/Account_Savings.txt");
							if(newfile1.createNewFile()){
							}
							else
								output.println("You already have a savings account.");
						}
				
					
					
					}
			
			
		private static void ATMScreen(PrintWriter output){
			output.println("Please Choose A Transaction Below \n" +
					   "(1) Deposit \n" +
					   "(2) Withdrawl \n" + 
					   "(3) Check Balance \n" +
					   "(4) Change Pin \n" +
					   "(5) Create New Account \n" + "END" );
		
		}
		
		private static void deposit(String userID, Socket client_sock, PrintWriter output, BufferedReader input2) throws IOException{
			output.println("Which account would you like to deposit into?");
			output.println("(1) Primary\n" + "(2) Personal\n" + "(3) Savings\n" + "END");
			String account = input2.readLine();
			boolean found =checkAccounts(userID,account);
			if(found == true){
				output.println("How much would you like to deposit?\n" + "END");
				String amount = input2.readLine();	
				try{
					int am = Integer.parseInt(amount);
					if(am <= 0){
						output.println("Invalid numeric value.");
					}
				}
				catch(NumberFormatException e){
					output.println("Invalid numeric value.");
					return;
				}
				File file = new File(userID);
				Date date = new Date();			//for saving the time of transaction in file
				if(account.equals("1")){
					File primary = new File(file+"/Account_Primary.txt");
					PrintWriter okane = new PrintWriter(new FileWriter(primary, true));
					okane.println(ft.format(date) + " Deposit: " + amount+".00");
					okane.flush();
					okane.close();
					
					
				}
				if(account.equals("2") ){
					File pas = new File(file+"/Account_Personal.txt");
					PrintWriter dinero = new PrintWriter(new FileWriter(pas, true));
					dinero.println(ft.format(date) + " Deposit: " + amount+".00");
					dinero.flush();
					dinero.close();
				}
				if(account.equals("3") ){
					File save = new File(file+"/Account_Savings.txt");
					PrintWriter qian = new PrintWriter(new FileWriter(save, true));
					qian.println(ft.format(date) + " Deposit: " + amount+".00");
					qian.flush();
					qian.close();
				}
				
				output.println("Would you like a receipt? (Y/N)\n" + "END");
				String answer = input2.readLine();
				if(answer.toUpperCase().equals("Y")){
					recieptDeposit(amount,file,account,output);
				}
				
			}
			else
				output.print("You don't have that type of account.");
			
		}
		
		private static void recieptDeposit(String origAmt, File file, String account, PrintWriter output) throws NumberFormatException, IOException{
			String readString;
			String last;
			double num = 0;
			Date date = new Date();
			if(account.equals("1")){
				File primary = new File(file+"/Account_Primary.txt");
				BufferedReader read = new BufferedReader(new FileReader(primary));
				while((readString = read.readLine()) != null ){
					String s = readString.substring(readString.lastIndexOf(": ")+1);
					num = Double.parseDouble(s) + num;
				}
				output.println("Transaction: \n Deposit : " + origAmt + "\n Date: " + ft.format(date));
				
				output.println("Balance: $" +df2.format(num) + "\n");
				output.println("END");
			}
			if(account.equals("2")){
				File primary = new File(file+"/Account_Personal.txt");
				BufferedReader read = new BufferedReader(new FileReader(primary));
				while((readString = read.readLine()) != null ){
					String s = readString.substring(readString.lastIndexOf(": ")+1);
					num = Double.parseDouble(s) + num;
				}
				output.println("Transaction: \n Deposit : " + origAmt + "\n Date: " + ft.format(date));
				
				output.println("Balance: $" +df2.format(num) + "\n");
				output.println("END");
			}
			if(account.equals("3")){
				File primary = new File(file+"/Account_Savings.txt");
				BufferedReader read = new BufferedReader(new FileReader(primary));
				while((readString = read.readLine()) != null ){
					String s = readString.substring(readString.lastIndexOf(": ")+1);
					num = Double.parseDouble(s) + num;
				}
				output.println("Transaction: \n Deposit : " + origAmt + "\n Date: " + ft.format(date));
				
				output.println("Balance: $" +df2.format(num) + "\n");
				output.println("END");
			}
			
		}
		
		
		private static void recieptW(String origAmt, File file, String account, PrintWriter output) throws NumberFormatException, IOException{
			String readString;
			String last;
			double num = 0;
			Date date = new Date();
			if(account.equals("1")){
				File primary = new File(file+"/Account_Primary.txt");
				BufferedReader read = new BufferedReader(new FileReader(primary));
				while((readString = read.readLine()) != null ){
					String s = readString.substring(readString.lastIndexOf(": ")+1);
					num = Double.parseDouble(s) + num;
				}
				output.println("Transaction: \n Deposit : " + origAmt + "\n Date: " + ft.format(date));
				
				output.println("Balance: $" +df2.format(num) + "\n");
				output.println("END");
			}
			if(account.equals("2")){
				File primary = new File(file+"/Account_Personal.txt");
				BufferedReader read = new BufferedReader(new FileReader(primary));
				while((readString = read.readLine()) != null ){
					String s = readString.substring(readString.lastIndexOf(": ")+1);
					num = Double.parseDouble(s) + num;
				}
				output.println("Transaction: \n Deposit : " + origAmt + "\n Date: " + ft.format(date));
				
				output.println("Balance: $" +df2.format(num) + "\n");
				output.println("END");
			}
			if(account.equals("3")){
				File primary = new File(file+"/Account_Savings.txt");
				BufferedReader read = new BufferedReader(new FileReader(primary));
				while((readString = read.readLine()) != null ){
					String s = readString.substring(readString.lastIndexOf(": ")+1);
					num = Double.parseDouble(s) + num;
				}
				output.println("Transaction: \n Withdrawl : " + origAmt + "\n Date: " + ft.format(date));
				
				output.println("Balance: $" +df2.format(num) + "\n");
				output.println("END");
			}
			
		}
		
		
		
		private static void withdrawl(String userID, Socket client_sock, PrintWriter output, BufferedReader input2) throws IOException{
			String tracker;
			double start = 0;
			output.println("Which account would you like to withdraw from?");
			output.println("(1) Primary\n" + "(2) Personal\n" + "(3) Savings\n" + "END");
			String account = input2.readLine();
			boolean found =checkAccounts(userID,account);
			if(found == true){
				output.println("Enter withdrawl amount. (Multiples of 20)\n" + "END");
				String amount = input2.readLine();	
				try{
					int am = Integer.parseInt(amount);
					if(am <= 0 || am%20 != 0){
						output.println("Invalid numeric value.");
					}
				}
				catch(NumberFormatException e){
					output.println("Invalid numeric value.");
					return;
				}
				
				File file = new File(userID);
				Date date = new Date();			//for saving the time of transaction in file
				if(account.equals("1")){
					File primary = new File(file+"/Account_Primary.txt");
					BufferedReader br = new BufferedReader(new FileReader(primary));
					while( (tracker = br.readLine()) != null ) {
						String number = tracker.substring(tracker.lastIndexOf(": ")+1);
						start = Double.parseDouble(number) + start;
						
					}
					double req = Double.parseDouble(amount);
					if(start < req){
						output.println("Insufficient funds");
						return;
					}
					PrintWriter okane = new PrintWriter(new FileWriter(primary, true));
					okane.println(ft.format(date) + " Withdrawl: " + "-"+amount+".00");
					okane.flush();
					okane.close();
					
					
				}
				if(account.equals("2") ){
					File pas = new File(file+"/Account_Personal.txt");
					BufferedReader br = new BufferedReader(new FileReader(pas));
					while( (tracker = br.readLine()) != null ) {
						String number = tracker.substring(tracker.lastIndexOf(": ")+1);
						start = Double.parseDouble(number) + start;
					}
					double req = Double.parseDouble(amount);
					if(start < req){
						output.println("Insufficient funds");
						return;
					}
					PrintWriter dinero = new PrintWriter(new FileWriter(pas, true));
					dinero.println(ft.format(date) + " Withdrawl: " + "-"+amount+".00");
					dinero.flush();
					dinero.close();
				}
				if(account.equals("3") ){
					File save = new File(file+"/Account_Savings.txt");
					BufferedReader br = new BufferedReader(new FileReader(save));
					while( (tracker = br.readLine()) != null ) {
						String number = tracker.substring(tracker.lastIndexOf(": ")+1);
						start = Double.parseDouble(number) + start;
					}
					double req = Double.parseDouble(amount);
					if(start < req){
						output.println("Insufficient funds");
						return;
					}
					PrintWriter qian = new PrintWriter(new FileWriter(save, true));
					qian.println(ft.format(date) + " Withdrawl: " + "-"+amount+".00");
					qian.flush();
					qian.close();
				}
				output.println("Would you like a receipt? (Y/N)\n" + "END");
				String answer = input2.readLine();
				if(answer.toUpperCase().equals("Y")){
					recieptW(amount,file,account,output);
				}
			}
			else
				output.print("You don't have that type of account.");


			
			
		}
		
		private static void showStatements(String userID, Socket client_sock, PrintWriter output, BufferedReader input2) throws IOException{
			output.println("Which statement would you like to see?");
			output.println("(1) Primary\n" + "(2) Personal\n" + "(3) Savings\n" + "END");
			String account = input2.readLine();
			String readString;
			double num = 0;
			boolean found =checkAccounts(userID,account);
			if(found == true){
				File file = new File(userID);
				if(account.equals("1")){
					File primary = new File(file+"/Account_Primary.txt");
					BufferedReader read = new BufferedReader(new FileReader(primary));
					while((readString = read.readLine()) != null ){
						String s = readString.substring(readString.lastIndexOf(": ")+1);
						output.println(readString);
						num = Double.parseDouble(s) + num;
					}
					output.println("Balance: $" +df2.format(num) + "\n");
					output.println("END");
				}
				if(account.equals("2")){
					File primary = new File(file+"/Account_Personal.txt");
					BufferedReader read = new BufferedReader(new FileReader(primary));
					while((readString = read.readLine()) != null ){
						String s = readString.substring(readString.lastIndexOf(": ")+1);
						output.println(readString);
						num = Double.parseDouble(s) + num;
					}
					output.println("Balance: $" +df2.format(num) + "\n");
					output.println("END");
				}
				if(account.equals("3")){
					File primary = new File(file+"/Account_Savings.txt");
					BufferedReader read = new BufferedReader(new FileReader(primary));
					while((readString = read.readLine()) != null ){
						String s = readString.substring(readString.lastIndexOf(": ")+1);
						output.println(readString);
						num = Double.parseDouble(s) + num;
					}
					output.println("Balance: $" +df2.format(num) + "\n");
					output.println("END");
				}
				
			}
			else{
				output.println("You don't have that type of account");
			}
			
		}
		
		
		private static boolean checkAccounts(String userID,String userResponse){
			//checks the user for secondary accounts. If they have the account they try withdrawl/deposit from return true
			File file = new File(userID);
			String [] account = file.list();
			if(file.isDirectory()){
		        File[] fList = file.listFiles();
		        for (File files : fList){
		            if(files.getName().equals(userResponse));{
		            System.out.println("Located file.");
		            return true;
		            }
		        }
		            
		        }
		     System.out.println("error.");
		     return false;
		

		}

	
			}
