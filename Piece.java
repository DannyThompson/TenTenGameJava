
import java.io.*;
import java.util.*;

class Piece {

// private static final int numPieces = 4;
private char[][] shape; 
private int size;

public Piece(int j) throws IOException {

size = 0;
if (j < 1 || j > 10){
   shape = null;
 }
else {
	String fileName = "piece" + j +".txt";
  size = 0;
	shape = readFromFile(fileName);
  int rowNum = shape.length;
  int colNum = shape[0].length;
  for (int i = 0; i < rowNum; i++)
    for (int k = 0; k < colNum; k++)
      if (shape[i][k]!= ' ')
        size++;
  }
}

public Piece(char[][] box) {
  size = 0;
	int row = box.length;
	int col = box[0].length;
	shape = new char[row][col];
	for (int i = 0; i < row; i++)
		for (int j = 0; j < col; j++) {
			shape[i][j] = box[i][j];
      if (shape[i][j]!= ' ')
          size++;
    }
}

public char[][] getShape() {
	return shape;
}

public int getSize() {
  return size;
}

public void printPiece() {
	// prints the piece as a 2-d shape
	int row = shape.length;
	int col = shape[0].length;
	for (int j = 0; j < row; j++) {
	   for (int k = 0; k < col; k++)
	      System.out.print(shape[j][k] + "  ");
     System.out.println();
     }
  // System.out.println("The size of the piece is " + size);
}	

public Piece rotateClockwise() {
// rotate the piece clockwise by 90 degrees
	int rows = shape[0].length;
	int cols = shape.length;
	char[][] temp = new char[rows][cols];
	for (int i = cols-1; i > -1; i--)
		for (int j = 0; j < rows; j++)
			temp[j][i] = shape[cols-i-1][j];
	return new Piece(temp); 
}

public static char[][] readFromFile(String fileName) throws IOException {
	// read from the file the description of a piece in the format:
	// line 1: row#, col#. line 2: a sequence of chars. Ex: 2 by 2 white tromino
	// will be represented as: line 1: 2 2 line 2: wwww
   FileInputStream in = null;
   char[][] temp;
   try {
   in = new FileInputStream(fileName);
   Scanner sc = new Scanner(in);
   int row, col;
   row = sc.nextInt(); col = sc.nextInt();
   //System.out.println("The row num is " + row);
   //System.out.println("The column num is " + col);
   temp = new char[row][col];
   String str = sc.nextLine();
   str = sc.nextLine(); int cur = 0;
   // System.out.println("The string " + str + "  read ");
   for (int j = 0; j < row; j++)
      for (int k = 0; k < col; k++)
        temp[j][k] = str.charAt(cur++);
   }
    
    finally {
    	if (in != null)
    		in.close();
    }
   return temp;
 }

 public static void main(String[] args) throws IOException {
    Piece p1 = new Piece(1);
    Piece p2 = new Piece(2);
    Piece p3 = new Piece(3);
    Piece p4 = new Piece(4);
    p1.printPiece();
    p2.printPiece();
    p3.printPiece();
    p4.printPiece();
    char[][] temp = new char[1][3];
    temp[0][0] = 'y'; temp[0][1]= 'y'; temp[0][2]= 'y';
    Piece p6 = new Piece(temp);
    p6.printPiece();
    Piece p5 = p3.rotateClockwise();
    p5.printPiece();
    Piece p7 = p2.rotateClockwise();
    p7.printPiece();
    System.out.println("rotating again");
    Piece p10 = p7.rotateClockwise();
    p10.printPiece();
    Piece p8 = p4.rotateClockwise();
    p8.printPiece();
    Piece p9 = p8.rotateClockwise();
    p9.printPiece();
 }
}












