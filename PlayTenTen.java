import java.util.*;
import java.io.*;
import java.lang.*;
//Daniel Thompson

public class PlayTenTen{


    //Below is a welcome message and then a thank you message after.
    public static void welcome(){
	System.out.println("Welcome to TenTen.");
    }
    
    public static void thanks(){
	System.out.println("Thank you for playing TenTen");
    }
    

    //Generates and returns an array of exclusively random numbers.
    //Is then passed to the function that actually returns the array of pieces.
    public static int [] getRandomPieces( int k ){
	int []A = new int[k];
	int rand;
	boolean check = false;
	Random r = new Random();

	//Generates a UNIQUE random number.
	//Got some help with this on Stack overflow
	for(int i = 0; i < k; i++){
	    do{
		rand = r.nextInt(10) % 10 + 1;
		check = false;
		for(int j = 0; j < i; j++){
		    if(A[j] == rand) //If the random number is already in the array, check is true.
			check = true;
		}
		
		if(!check){ //If rand isn't in the array, add it and break.
		    A[i] = rand;
		    break;
		}
		
	    }while(true); //Continues until reaches the Break statement
	}
	    
	return A;
    }

    //Returns a random array of pieces. The random numbers are generated from the above function
    public static Piece [] getPieces(){
	 int [] arr = new int[3];
	 Piece[] pp = new Piece[3];

	    try{
		arr = getRandomPieces(3);
		System.out.println("Pieces: ");
		
		for(int x = 0; x < pp.length; x++){
		    Piece q = new Piece( arr[x] );
		    pp[x] = q;
		    System.out.println(x+1);
		    pp[x].printPiece();
		    System.out.println("");
		}
	    }
	    
	    catch(Exception e){
		System.out.println(e);
	    }
	    return pp;
    }

    //Switch case printing various appropriate messages
    public static void printMessages( int k ){
	switch(k){

	    
	case 1: System.out.println("Enter 'a' to rotate a piece. Enter 'b' to place a piece.");
	    System.out.println("");
	    break;

	case 2: System.out.println("Enter the piece number and the amount of degrees you wish you rotate it: ");
	    System.out.println("");
	    break;

	case 3: System.out.println("Enter row number, column number, and piece number you wish to place: ");
	    System.out.println("");
	    break;
	    
	case 4: System.out.println("Illegal move. Try again!");
	    System.out.println("");
	    break;

	case 5: System.out.println("Illegal entry. Choose A or B.");
	    System.out.println("");
	    break;
	}
    }

    //Additional function to check if all the current pieces have been used.
    public static  boolean isEmpty( Piece[]  p ){
		
	for(int i = 0; i < p.length; i++){
	    if(p[i] != null){
		return false;
	    }
	}	
	return true;
	
    }

    //Main where all the game logic is
    public static void main( String args[] ) throws IOException{

	Scanner scan = new Scanner(System.in);
	int rows = Integer.parseInt(args[0]);
	int cols = Integer.parseInt(args[1]);
	Board b = new Board(rows, cols);
	
	welcome();
	
	//Print the initial empty Board.
	b.printBoard();
	System.out.println("");

	
       
	try{
	    Piece [] p = new Piece [3];
	    
	    //Get the Initial pieces for the game.
	    p = getPieces();

	    //Keep playing until the game is over.
	    //You can chooseb between placing and rotation pieces.
	    //When you're out of pieces, 3 new ones are generated.
	    //Multiple checks are placed through here to check if the piece array is empty.
	    while( !b.gameOver( p ) ){

		if(isEmpty(p)) {
		    p = getPieces();
		}

		//Print the message to tell the user to choose to rotate or place a piece.
		printMessages(1);
		
		//Takes in next char, A or B.
		char in = scan.next().charAt(0);
		
		//If User wants to rotate;
		 if(in == 'a') {
		    printMessages(2);
		    int piece = scan.nextInt();
		    int degrees = scan.nextInt();

		    for(int j = 0; j < p.length; j++) {
			if(p[j] != null)
			    p[j].printPiece();
		    }

		    //Rotate the Piece by factors of 90.
		    for(int i = 0; i < degrees/90; i++) {
				if(p[piece - 1] != null)
			   		p[piece - 1] = p[piece - 1].rotateClockwise();
			 }

		    //Reprints the pieces since one was rotated so the user can see the new orientation.
		    System.out.println("");
		    b.printBoard();
		    System.out.println("");

		    //Reprint the pieces after one's been rotated.
		    for(int j = 0; j < p.length; j++) {
				if(p[j] != null) {
			    	System.out.println(j + 1);
			    	p[j].printPiece();
			    	System.out.println("");
				}
		    }
		 } else if( in == 'b' ) { //If the user wants to place a piece.
		    printMessages(3);

		    //Next 3 inputs are the following values.
		    int row = scan.nextInt();
		    int col = scan.nextInt();
		    int pieceNumber = scan.nextInt();
		     
		    if( pieceNumber > 0 && b.canPlace(row, col, p[pieceNumber - 1]) ) {
				b.place(row, col, p[pieceNumber - 1]);
				p[pieceNumber - 1] = null;
		    } else {
				printMessages(4);
			}

		    //Check if the last piece placed made the piece array empty.
		    if(isEmpty(p)) {
				p = getPieces();
		    }
		    
		    System.out.println("");
		    b.printBoard();
		    System.out.println("");
		    
		    //Reprint the current pieces left after placing one.
		     for(int j = 0; j < p.length; j++){
			 	if(p[j] != null){
			   	  	System.out.println(j + 1);
			    	p[j].printPiece();
			     	System.out.println("");
			     }
		     }
		     
		     if(isEmpty(p)){
			 	p = getPieces();
			}
		 } else { //Print an error message if A or B aren't entered
		     printMessages(5);
		 }
	    
		 //Print the current score
		System.out.println("Score: " + b.score);
		

		if(isEmpty(p))
		    p = getPieces();
	    }

	    //Game is over. Print Thanks for playing message.
	    thanks();
	}
	
	//Catch any IOExceptions.
	catch(Exception e){
	    System.out.println(e);
	}
  }
}
    
