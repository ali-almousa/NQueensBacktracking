import java.util.Arrays;



//
/******************************************************************************
 *  @(#)NQueens.java
 *
 *  Solves the N-queens problem using an n-by-n chess board.
 *
 *	Compilation:  javac NQueens.java
 *  Execution:    java NQueens n d
 *  Dependencies: ChessBoard.class
 *                Std2Draw.zip
 *
 *	@author Ali Mostafa Almousa 
 *	@instructor: Dr. Abdulghani M. Al-Qasimi
 *  @version 2.10 2022/03/18
 *
 ******************************************************************************/

/******************************************************************************
 *                  I M P O R T A N T
 *
 * Do not delete any thing after this line. You can only add.
 *
 ******************************************************************************/
public class NQueens {
    private static int n;           // dimension of chess board
    private static int d;			// amount of delay (speed) in tenth seconds
    protected static ChessBoard cb; // n x n chess board object

	// The NQueens constructor
	// generate n x n chess board and displays it
    public NQueens(int n, int d) {
        this.n = n;
        this.d = d;
    }

    // a main method to test the program
    public static void main(String[] args) {
        // Check the command-line arguments
    	if (args.length < 2) {
			System.out.printf("\nNo arguments! ...  Using default values ..");
        	System.out.printf("\nUsage: > java <prog_name> [n] [d]");
			System.out.printf("\n Where n, No. queens:  5 .. 15");
			System.out.printf("\n       d, Speed delay: 0 .. 10");
			System.out.println();
			n = 6;					// default size
			d = 5;					// default delay
    	} else {
        	n = Integer.parseInt(args[0]);
        	d = Integer.parseInt(args[1]);
    	}
        cb = new ChessBoard(n, d);	// create the chess board
        cb.drawBoard();				// display the chess board
        solve(n);					// solve the n-queens problem
        cb.finish(true);			// finalize the board
    }

    // Write the solve(n) method to show in animation on the generated
    // n x n chess board, how a recursive algorithm can find a solution
    // to the n-queens problem. You can also write any helper methods
    // you need.
    //
    // At every step the included queen images must appear or disappear
    // as the algorithm advances to its end. Use flashing red images to
    // show conflicts only.
    //
    // The following methods are provided:
    //  - drawBoard():        Draws n-by-n chess board, with extra column
    //                        Rows start at 0, columns start at 1
    //  - drawQueen(i, j, c): Put image c(0=black or 1=red) at cell(i,j)
    //  - clearQueen(i, j):   Erase the image from cell(i,j)
    //  - display():          Refresh the display
    //  - finish(flag):       Close graphical system, flag: (true or false)
    //  - delay(t):           Impose time delay t, in one tenth of a second
    public static void solve(int n) {
    	// create a matrix to keep track
    	// of queens on the board
    	// 0 --> empty cell
    	// 1 --> Queen
    	int[][] board = new int[n][n];
    	// Initialize the board with empty cells
    	for (int[] arr : board) Arrays.fill(arr, 0);
    	
    	// Draw queens on leftmost column
    	for (int i = 0; i < n; i++) cb.drawQueen(0, i, 0);
    	cb.delay(d);
    	
    	// start positioning the queens 
    	// refresh the display if solved
    	if (backtracking(board, board.length - 1) == true ) cb.display();
    	// otherwise close graphical system with 
    	// false flag
    	else cb.finish(false);
    }
    
    private static boolean backtracking(int[][] board, int row) {
    	/* base case: If all queens are placed
		then return true */
		if (row < 0)
			return true;

		/* Consider this row and try placing
		this queen in all columns one by one */
		for (int i = 0; i < board.length; i++) {
			/* Check if the queen can be placed on
			board[row][i] */
			if (isValid(board, row, i)) {
				/* Place this queen in board[row][i] */
				board[row][i] = 1;
				cb.drawQueen(i + 1, (board.length - 1) - row, 0);
				cb.delay(d);

				/* recur to place rest of the queens */
				if (backtracking(board, row - 1) == true)
					return true;

				/* If placing queen in board[row][i]
				doesn't lead to a solution then
				remove queen from board[row][i] */
				board[row][i] = 0; // BACKTRACK
				cb.clearQueen(i + 1, (board.length - 1) - row);
			}
		}

		/* If the queen can not be placed in any column in
		this row, then return false */
		return false;
	}
    
    /* A utility function to check if a queen can
    be placed on board[row][col]. This
    function is called when "row" queens are
    already placed in rows from row - 1 to 0.
    So we need to check for attacking queens
    form the following sides only:
    - Lower rows at the same column
    - Lower left diagonal 
    - Lower right diagonal 
    - */
	static boolean isValid(int board[][], int row, int col){
		int i, j;
		// clear the queen to move
		// from the leftmost column
		cb.clearQueen(0, (board.length - 1) - row);
		cb.delay(d);
		
		// draw a queen on current
		// cell being tested
		cb.drawQueen(col + 1, (board.length - 1) - row, 0);
		
		/* Check this col on from bottom */
		for (i = board.length - 1; i > row; i--)
			if (board[i][col] == 1) {
				// flash the attacking and 
				// attacked queens and keep
				// the attacking one on board
				flash2QueensKTimes(3, i, col, row, col);
				// Move the attacked queen
				// back to leftmost column
				cb.drawQueen(0, (board.length - 1) - row, 0);
				return false;
			}
		
		/* Check lower diagonal on right side */
		for (i = row, j = col; i < board.length && j < board.length; i++, j++)
			if (board[i][j] == 1) {
				// flash the attacking and 
				// attacked queens and keep
				// the attacking one on board
				flash2QueensKTimes(3, i, j, row, col);
				// Move the attacked queen
				// back to leftmost column
				cb.drawQueen(0, (board.length - 1) - row, 0);
				return false;
			}
		/* Check lower diagonal on left side */
		for (i = row, j = col; j >= 0 && i < board.length; i++, j--)
			if (board[i][j] == 1) {
				// flash the attacking and 
				// attacked queens and keep
				// the attacking one on board
				flash2QueensKTimes(3, i, j, row, col);
				// Move the attacked queen
				// back to leftmost column
				cb.drawQueen(0, (board.length - 1) - row, 0);
				return false;
			}

		return true;
	}
	
	 /* This method will flash two queens k times then clear
	 the queen under attack and keep the attacking queen 
	 in black color. 
	 (row1, col1) --> position of the attacking queen
	 (row2, col2) --> position of the queen under attack*/
	private static void flash2QueensKTimes(int k, int row1, int col1, int row2, int col2) {
		// flash the 2 queens k times
		for (int i = 0; i < k; i++) {
			cb.clearQueen(col1 + 1, (n - 1) - row1);
			cb.clearQueen(col2 + 1, (n - 1) - row2);
			cb.delay(d);
			cb.drawQueen(col1 + 1, (n - 1) - row1, 1);
			cb.drawQueen(col2 + 1, (n - 1) - row2, 1);
			cb.delay(d);
		}
		
		// clear the attacked queen
		cb.clearQueen(col2 + 1, (n - 1) - row2);
		cb.delay(d);

		// draw the attacking queen in black 
		cb.clearQueen(col1 + 1, (n - 1) - row1);
		cb.drawQueen(col1 + 1, (n - 1) - row1, 0);
		cb.delay(d);
	
	}

}
