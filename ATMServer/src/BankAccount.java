import java.util.Random;
import java.util.Scanner;
import java.io.*;
//Code created by Marcus Ramsey.
//Notes are step-by-step instructions

public class BankAccount {
	
	private String name;	//don't need this
	private double balance;	//don't need this
	private String [] accounts = new String[] {"Marcus"}; //don't need this
	
	public BankAccount(){		//don't need this either
		this.name = "";
		this.balance = 0;
	}
	
	public BankAccount(String name, double amt){		//or this
		this.name = name;
		this.balance = amt;
	}
	
	public String toString(){		//or this
		// TODO
		String temp1 = String.format("$%.2f",this.balance);
		//String temp2 = String.format("%s%%", this.yearlyRate);
		String f = "Account: " + this.name + "\nBalance: " + temp1;
		return f;
	}
	
	public static int randomNumber(){		//generates random number used as an account number / may substitute for pin
		Random r = new Random();
		int i = r.nextInt(90000000)+10000000;
		String number = Integer.toString(i);
		return i;
	}
	
	public static void appendAccount(String name, String number){//appends Account info into a text file
		BufferedWriter b = null; //writes into file
		
		try{
			b = new BufferedWriter(new FileWriter("ClassAccounts.txt", true));//try to initiate writing
			b.write(name); b.write(" "); b.write(number); 	//What is being written
			b.newLine();									//skip to new line
			b.flush();										//I forget what this means
		}													//blah blah exceptions
		catch(IOException E){								//If buffer fails
			E.printStackTrace();							//Catch it and print the stack
		}
		finally{											//This doesn't work
			if(b != null)									//if b is null you must be at the end of the text
				try{
					b.close();								//close the buffer
				}
			catch(IOException E){							//catch it if for some reason you can't close it
				
			}
			
		}
	}
	
	public static boolean check(String namefromfile){
		boolean exists = true;
	try(BufferedReader br = new BufferedReader(new FileReader("ClassAccounts.txt"))){
		String line = null;
		while((line = br.readLine()) != null){
			if(line.equals(namefromfile))
				return exists;
		}
		
	}
		catch(IOException ie){
			//catch
		}
	exists = false;
	return exists;
	}
	
	public static void newAccount(){		//method used to create a new account
		Scanner k = new Scanner(System.in);
		String name;
		System.out.print("Enter your first name: ");
		name = k.nextLine(); 
		check(name);
		int accountnum = randomNumber();	//creates random account number
		String Anum = Integer.toString(accountnum);		//convert number to a string to put into file
		if(check(name) == true){
			System.out.print("Name already exists in the database please select another\n");
			newAccount();
			while(check(Anum) == true){
				accountnum = randomNumber();
				Anum = Integer.toString(accountnum);
			}
		}
		else{
			appendAccount(name, Anum);
			try{
			File accountFile = new File(name+Anum+".txt");
			FileOutputStream oFile = new FileOutputStream(accountFile, false);

			if(!accountFile.exists()) {
				accountFile.createNewFile();
			}
			oFile.close();
			} catch(IOException e){
				e.printStackTrace();
			}
		}
		
	}
	public static void transactions(String filename){
		File f = new File(filename+".txt");
		System.out.println(f);
		BufferedWriter g = null;
		Scanner T = new Scanner(System.in);
		String select;

		if(f.exists()){
			System.out.println("Select Transaction: \n");
			System.out.print("(D)eposit");
			System.out.println("(W)ithdrawal");
			select = T.next();
			if(select == "D"){
				System.out.println("Enter deposit amount");
				
			}
		}
	}
	
	public static void deposit(String filename){
		File f = new File(filename+".txt");
		System.out.println(f);
		BufferedWriter g = null;

		if(f.exists()){
			System.out.println("It exists");
			try{
				g = new BufferedWriter(new FileWriter(f, true));
				g.write("Deposit");
				g.newLine();
				g.write("Withdrawl");
				g.flush();
			}
			catch(IOException E){
				E.printStackTrace();
			}
			finally{
				if(g != null)
					try{
						g.close();
					}
				catch(IOException E){
					
				}
				
			}
			
		}
		else
			System.out.println("Account not found");
		
	}
	
	public static void withdrawal(){
		//withdrawal money from account 
	}
	
	public static void menu(){
		Scanner j = new Scanner(System.in);
		String name;
		String answer;
		System.out.print("New Account?");
		answer = j.next();
		answer = answer.toUpperCase();
		if(answer.equals("YES"))
		{
			newAccount();
		}
		else
		System.out.println("\n Please enter your name, followed by your account number :");
		name = j.next();
		transactions(name);
		
		}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		menu();
	}
	
}

