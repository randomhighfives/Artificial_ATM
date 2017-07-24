import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import javax.swing.JPasswordField;

public class ReadFilesInts {

	
	private static JPasswordField password;
	private static DecimalFormat df2 = new DecimalFormat("0.00");

	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		
		double k = 21000.5;
		System.out.println(df2.format(k));
	
		/***************************************8
		String username;
		int i = 0;
		String f;
		String password = "password.txt";
		Scanner user = new Scanner(System.in);
		System.out.println("Enter your username: ");
		username = user.next();
		File file = new File(username);
		File pas = new File(file+"/Account_Primary.txt");
		BufferedReader br = new BufferedReader(new FileReader(pas));
		if(file.isDirectory()){
			while( (f = br.readLine()) != null ) {
			String s = f.substring(f.lastIndexOf(": ")+1);
			Date date = new Date();
			SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");
			//double m = Double.parseDouble(las);
			i++;
			System.out.println(f);
			//System.out.println(s + " " + ft.format(date) + " " + i);
			}
		}
		else{
			System.out.println("Invalid user");
			*******************************************************/
		}
		/**************************************************************
		new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		Scanner scan = new Scanner(System.in);
		int s = scan.nextInt();
		System.out.println(s);
			if(s == 2){
		        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			}
			Scanner scan2 = new Scanner(System.in);
			System.out.println("What is your name");
			String x = scan2.nextLine();
			System.out.println("Hello " + x);
			**************************************************************/
		
		
		
		
		
		//This is reading the last string in a file and converting it to an integer.
		/********************************************************
		String f;
		int total = 0;
		BufferedReader br = new BufferedReader(new FileReader("ClassAccounts.txt"));
		while( (f = br.readLine()) != null ) {
			String number = f.substring(f.lastIndexOf(" ")+1);
			int num = Integer.parseInt(number);
			total = total + num;
			Date date = new Date();
			System.out.println(date.toString() + " Deposit: " + number);
		}
		System.out.println(total);
		**************************************************************/
	}
	
	
