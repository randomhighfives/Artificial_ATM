import java.awt.TextField;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ATMClient{
	
	int port_number;
	InetAddress loopback;
	Socket client_socket;
	PrintWriter output;
	BufferedReader input;
	/******************************
	public ATMClient( int port ) throws IOException {
		port_number= port;
		loopback= InetAddress.getLoopbackAddress();
		client_socket= new Socket( loopback, port );
		output= new PrintWriter( client_socket.getOutputStream(), true );
		input= new BufferedReader( new InputStreamReader(client_socket.getInputStream()) );				
	}
	 * @throws InterruptedException **************************************/
	
	
	public static void main(String args[]) throws IOException, InterruptedException{
		int number = args.length;
		String inputreader;
		if(number < 1){
			System.err.println("Add a port number");
			return;
		}
		int port = Integer.valueOf( args[0]);
		Socket socket = new Socket( (String)null,port);
		
		PrintWriter output = new PrintWriter(socket.getOutputStream(), true);		//send to the server what the client writes
		BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));	//what the server responses with
		BufferedReader userOutput = new BufferedReader(new InputStreamReader(System.in));  //what the user writes as their user id.
		
		while((inputreader = input.readLine()) != null)	//the first thing you'll see from the server. DePaul Bank & stuff.
		{												//asking you for username
		    if (inputreader.equals("END")) {
		        break;
		    }
		    System.out.println(inputreader);
		}
		String user = userOutput.readLine();				//client giving username
		output.println(user);							//sending username to server
		
		inputReader(inputreader,input);		//server input asking you to enter your password

		
		String userpassword = userOutput.readLine();			//client entering password
		output.println(userpassword);						//sending password to server
									
		
		new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();    //clears the screen. For when the password is accepted.
		
		inputReader(inputreader,input);								//the selection screen?
		
		String selection = userOutput.readLine();
		if(selection.equals("1")){
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			output.println(selection);				//you selected deposit
			inputReader(inputreader,input);			//which account do you want to deposit to
			String daccount = userOutput.readLine();
			output.println(daccount);
			inputReader(inputreader,input);			//how much
			String money = userOutput.readLine();
			output.println(money);
			
		}
		if(selection.equals("2")){
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			output.println(selection);				//selected withdrawl
			inputReader(inputreader,input);			//which account
			String wfrom = userOutput.readLine();		//answer - withdrawl from which account
			output.println(wfrom);				
			inputReader(inputreader,input);				//how much
			String withdrawl = userOutput.readLine();
			output.println(withdrawl);
			inputReader(inputreader,input);				//confirm or decline?

		}
		
		if(selection.equals("3")){
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			output.println(selection);				//selected show statement
			inputReader(inputreader,input);			//which account
			String sfrom = userOutput.readLine();		//answer - statement from which account
			output.println(sfrom);				
			displayStatement	(inputreader,input);
			
		}
		
		if(selection.equals("4")){					//if you want to change your PIN.
			output.println(selection);				//you selected change pin.
			inputReader(inputreader,input);			//Security question
			String answer = userOutput.readLine();
			output.println(answer);					//send your answer
			inputReader(inputreader,input);			//answer accepted, give new pin.
			String answer2 = userOutput.readLine();			//using the same variable for the new pin.
			output.println(answer2);
		}
		if(selection.equals("5")){
		output.println(selection);
		
		inputReader(inputreader,input);				//again for whatever you pick.
		
		String selection2 = userOutput.readLine();
		output.println(selection2);
		
		inputReader(inputreader,input);				//again for confirmation(pin changed, account created etc.)
		
		}
		
		inputReader(inputreader,input);	
		String yesno = userOutput.readLine();
		output.println(yesno);
		inputReader(inputreader,input);	

		socket.close();
		
		
		
	}
	
	
		private static void displayStatement(String inputreader,BufferedReader input) throws IOException{
			Scanner n = new Scanner(System.in);
			int i = 1;
			while(( inputreader = input.readLine() )!= null && inputreader.equals("END") == false)
			{
				System.out.println(inputreader);
				i++;
				if(i >= 10){
					System.out.println("Press N for next 10 or any other key to quit.");
					String a =n.nextLine();
					if(a.toUpperCase().equals("N")){
						i = 1;
					}
					else
						return;

				}
				
			}
			
			
			
		}
	
		
		private static void inputReader(String inputreader,BufferedReader input) throws IOException{
			while((inputreader = input.readLine()) != null)		
			{
			    if (inputreader.equals("END")) {
			        break;
			    }
			    if(inputreader.equals("EXIT")){
			    	System.exit(0);
			    }
			    System.out.println(inputreader);
			}
		}
        
		}
	
	
	
