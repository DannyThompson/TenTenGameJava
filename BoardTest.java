import java.io.*;

public class BoardTest {

	public static void main(String args[]) throws IOException {
	/* create a file containing some pieces and check that the board has been correctly
	initialized and the score has been correctly computed. */
	boolean success = true; int failedTests = 0;
	boolean placed = false;
	Board B1 = new Board("board2.txt"); 
	int tests = 0;
	/* Case 1: place a few pieces one by one and check that the board and the score 
	have been correctly updated. */
	if (B1.score != 11) {
		failedTests++;
		System.out.println("line 22: B1.score = " +  B1.score + " " + " but should be 11");
	 }
	tests++;
    success = success && (B1.score == 11);
    
	/* case 2:
	Place piece 3 at position (3, 0) and check that the score is 16 */
	Piece P1 = new Piece(3);
    placed = B1.place(3, 0, P1);
    if (B1.score != 16) {
		failedTests++;
		B1.printBoard();
		System.out.println("line 33: B1.score = " + B1.score + " " + " but should be 16");
		System.out.println("The value of placed in line 32 is " + placed + " " + "; it should be true");
	}
	tests++;
	success = success && (B1.score == 16);

	Piece[] plist2 = new Piece[1];
	plist2[0] = new Piece(2);
 
	/* case 3:
	Test game over with list containing single piece 2. should return true */
	if (!B1.gameOver(plist2)) {
		failedTests++;
		System.out.println("Reported game not over in line 47, but 2 can't be placed in B1 currently");
	}
	tests++;
	success = success && (B1.gameOver(plist2));

	/* case 4:
	Rotate piece 5 clockwise and place it at (1, 5). */
    Piece P2 = new Piece(5);
    P2 = P2.rotateClockwise();
    placed = B1.place(1, 5, P2);
    if (B1.score != 18) {
		failedTests++;
		System.out.println("line 46: B1.score = " + B1.score + " " + "but should be 18");
	}
    tests++;
    success = success && (B1.score == 18);

	/* case 5:
	Test game over with list of pieces pieces 1, 4 and 5. should return false  */
	Piece[] plist = new Piece[3];
	plist[0] = new Piece(1); plist[1] = new Piece(4); plist[2] = new Piece(5);
	if (B1.gameOver(plist)) {
		failedTests++;
		System.out.println("Reported game over in line 59, but 1, 4 and 5 can all be placed in B1 currently");
	}
	tests++;
	success = success && !(B1.gameOver(plist));

	/* case 6:
	Place piece 4 at (2, 1): new score = 18 + 4 + 2*6 = 34 */
    Piece P3 = new Piece(7);
    placed = B1.place(2, 1, P3);
    if (B1.score != 34) {
		failedTests++;
		System.out.println("line 82: placed is " + placed + " " + " but should be true");
		System.out.println("line 82: B1.Score = " + B1.score + " " + "but should be 34");
	}
    tests++;
    success = success && (B1.score == 34);

    /* case 7: 
    rotate P2 and place at (2, 1); score = 34 + 4 = 38*/
    placed = B1.place(1, 1, new Piece(2).rotateClockwise()); 
    if (B1.score != 38) {
		failedTests++;
		System.out.println("line 89: B1.Score = " + B1.score + " " + " but should be 38");
	}
    tests++;
    success = success && (B1.score == 38);
    
    /* case 8: 
    Place piece 3 at (0, 1). since not possible to place, score remains 38 */
    placed = B1.place(0, 1, P1);
    if (B1.score != 38) {
		failedTests++;
		System.out.println("line 99: B1.Score = " + B1.score + " " + " but should be 38");
	}
	tests++;
    success = success && (B1.score == 38);

    /* case 9: 
    Rotate piece 7 three times and place at (2, 3). Score = 38 + 4 = 42 */
    Piece P4 = new Piece(7);
    P4 = P4.rotateClockwise().rotateClockwise().rotateClockwise();
    placed = B1.place(2, 3, P4);
    if (B1.score != 42) {
		failedTests++;
		System.out.println("line 111: B1.Score = " + B1.score + " " + " but should be 42");
	}
	tests++;
    success = success && (B1.score == 42);

    /* case 10:
	Test game over with list of pieces pieces 2, 3 and 4. should return true */
	Piece[] plist3 = new Piece[3];
	plist3[0] = new Piece(2); plist3[1] = new Piece(3); plist3[2] = new Piece(4);
	if (!B1.gameOver(plist3)) {
		failedTests++;
		System.out.println("Reported game not over in line 125, but none of 2, 3 or 4 can be placed on B1 currently");
	}
	tests++;
	success = success && (B1.gameOver(plist3));

    /* case 11  
    create a test case in which more than one row needs clearance 
    A 10 by 10 Board B2 is filled in 8 row and 8 columns in rows 0 and 1, columns 0 and 1 using 8 white pieces. Then 
    a counter-clockwise rotated piece 4 is placed that results in two rows and two columns simultaneously filled*/

    Board B2 = new Board("board3.txt");
    if (B2.score != 32) {
    	failedTests++;
    	System.out.println("line 124: B2.Score = " + B2.score + " " + " but should be 32");
    }
    tests++;
    success = success && (B2.score == 32);
    placed = B2.place(0, 0, new Piece(1));
    if (B2.score != 76) {
    	failedTests++;
    	System.out.println("line 132: B2.Score = " + B2.score + " " + " but should be 76");
    }
    tests++;
    success = success && (B2.score == 76);
   
    if (success)
    	System.out.println("All tests passed successfully");
    else {
    	System.out.println("Not all tests passed successfully");
    	System.out.println("The number of failed tests = " + failedTests + " out of " + tests);
	     }
	}     
}

