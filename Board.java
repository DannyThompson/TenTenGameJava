//Daniel Thompson

import java.util.*;
import java.lang.*;
import java.io.*;

public class Board {
    
    private char [][] theBoard;
    public int score;
    
    //Constructor below sets all pieces blank initially.
    public Board(int m, int n) {
	theBoard = new char[m][n];
	for( int i = 0; i < m; i++ ){
	    for( int j = 0; j < n; j++ ){
		theBoard[i][j] = ' ';
	    }
	}	    
    }
    
    // This additional constructor was left out of the spec even though its pretty much mandatory and I guess we were supposed to just figure it out
    public Board( String file ){
	try{
	    readFromFile(file);
	}
	catch(Exception e){
	    System.out.println(e);
	}
    }

    //Another constructor that takes a board
    public Board( char[][] b ) {
	int m = b.length;
	int n = b[0].length;
	theBoard = new char[m][n];
	    //Copy everything into new board
	for( int i = 0; i < m; i++ ){
	    for( int j = 0; j < n; j++ )
		theBoard[i][j] = b[i][j];
	}
    }
    
    public Boolean place( int i, int j, Piece p ) {
	
	//First, check if the piece doesn't fit. If it doesn't, return false.	
	for( int r = 0; r < p.getShape().length; r++ ){
	    for( int c = 0; c < p.getShape()[0].length; c++ )
		if( p.getShape()[r][c] != ' ' ){
		    if(i + r >= theBoard.length || j + c >= theBoard[0].length ){
			return false;
		    }
		    
		    else if( theBoard[i+r][j+c] != ' ' )
			return false;
		}
	}
    
	
	//Place pieces
	for( int r = 0; r < p.getShape().length; r++){
	    for( int c = 0; c < p.getShape()[0].length; c++ )
		if( p.getShape()[r][c] != ' ' )
		    theBoard[r + i][c + j] = p.getShape()[r][c];
	}
	
	updateScore(p); //Score is updated according to the size of the piece that was just placed.
	return true;
	
    }
    
    //This checks if a piece CAN be placed, but if it can, doesn't actually place it
    //This is used only in gameOver() to see if the game should end or not.
    public Boolean canPlace( int i, int j, Piece p ) {
	
	for( int r = 0; r < p.getShape().length; r++ ){
	    for( int c = 0; c < p.getShape()[0].length; c++ )
		if( p.getShape()[r][c] != ' ' ){
		    if(i + r >= theBoard.length || j + c >= theBoard[0].length ){
			return false;
		    }
		    
		    else if(theBoard[i+r][j+c] != ' ')
			return false;
		}   
	}
	
	//The piece CAN be placed, but isn't.
	return true;	
    }

    //If no current pieces can be placed, return true game is over. Otherwise a piece can still be placed, and thus gameOver() == false.
    public Boolean gameOver( Piece[] C ){
		
	//If a piece can be placed, the game isn't over
	for( int i = 0; i < C.length; i++ ){
	    for( int j = 0; j < theBoard.length;  j++ ){
		for( int k = 0; k < theBoard[0].length; k ++ ){
		    if(C[i] != null){
			if( canPlace( j, k, C[i] ) ){
			    return false;
			}
		    }
		}
	    }
	}

	//If a piece can't be placed, the game is over.
	System.out.println("Game Over!");
	return true;
    }


    //Checks if any rows are entiely full and returns an array of every full row, for clearRow() to take.
    public int[] rowFull(){
	
	ArrayList<Integer> ListRows = new ArrayList<Integer>(); 

	for( int i = 0; i < theBoard.length; i++ ){
	    for( int j = 0; j < theBoard[0].length; j++ ){
		if( theBoard[i][j] == ' ' )
		    break;
		else if( j == theBoard[0].length - 1 ){
		    ListRows.add(i);
		}
	    }
	}

	int rowCount = ListRows.size();
	int[] array = new int[rowCount];

	for(int i = 0; i < rowCount; i++ ){
	    array[i] = ListRows.get(i);
	}
	    
	return array;		    	       
    }

    //Same as rowFull() but same with cols instead.
    public int[] colFull(){
	ArrayList<Integer> ListCols = new ArrayList<Integer>();

	for( int i = 0; i < theBoard[0].length; i++ ){
	    for( int j = 0; j < theBoard.length; j++ ){
		if( theBoard[j][i] == ' ' )
		    break;
		else if( j == theBoard.length - 1 ){
		    ListCols.add(i);
		}
	    }
	}

	int colCount = ListCols.size();
	int[] array = new int[colCount];

	for(int i = 0; i < colCount; i++ ){
	    array[i] = ListCols.get(i);
	}

	return array;
    }    

    //Takes an array of full rows from rowFull() and clears those rows, and updates the score accordingly
    public void rowClear(int [] rows){

	for( int i = 0; i < rows.length; i++ ){
	    for( int j = 0; j < theBoard[0].length; j++){
		theBoard[rows[i]][j] = ' ';
		score++;
	    }
	}
	//	System.out.println("Row cleared!");
	
    }

    //Same as rowClear, but for columns.
    public void columnClear(int [] cols){
	
	for( int i = 0; i < cols.length; i++ ){
	    for( int j = 0; j < theBoard.length; j++){
		theBoard[j][cols[i]] = ' ';
		score++;
	    }
	}
	//	System.out.println("Column cleared!");
    }	


    //Updates the score according to the piece that's being placed.
    public void updateScore(Piece p){
	
	score+= p.getSize(); //Increase the score by the size of the piece that was just placed

	//Now make arrays of the full rows and columns and clear that at the same time to increase score accordingly.
	int []r = rowFull();
	int []c = colFull();
	rowClear(r);
	columnClear(c);
	
    }

    //This updates the boards default score in the case where we're given a board with pieces already placed.
    public void updateBoardScore()
    {
	int x = theBoard.length;
	int y = theBoard[0].length;
	for( int i = 0; i < x; i ++)
	    for( int j = 0; j < y; j++ )
		if( theBoard[i][j] != ' ' )
		    score++;
    }

    
    //Not used, but normally it would be good programming practice to have a getter, and make the score member private.
    public int getScore(){
	return score;
    }

    
    //This portion is *mostly* copied from the Piece.java file, but changed accordingly to fit this class.
    public void readFromFile(String fileName) throws IOException {
	
	FileInputStream in = null;
  
	try {
	    in = new FileInputStream(fileName);
	    Scanner sc = new Scanner(in);
	    int row, col;
	    row = sc.nextInt(); col = sc.nextInt();
	    theBoard = new char[row][col];
	    String str = sc.nextLine();
	    str = sc.nextLine(); int cur = 0;
	    for (int j = 0; j < row; j++)
		for (int k = 0; k < col; k++)
		    theBoard[j][k] = str.charAt(cur++);
	}

	finally {
	    if (in != null)
		in.close();
	}
	System.out.println("Game Started.");
	updateBoardScore();
    }
    
    //Prints the board.
    public void printBoard(){
	for( int i = 0; i < theBoard.length; i++ ){
	    for( int j = 0; j < theBoard[0].length; j++ ){
		if( theBoard[i][j] == ' ' )
		    System.out.print('_' + " ");
		else
		    System.out.print(theBoard[i][j] + " ");
	    }
	    System.out.println();
	}
    }
		   
}




