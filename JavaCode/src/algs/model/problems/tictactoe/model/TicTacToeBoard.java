package algs.model.problems.tictactoe.model;

/**
 * Represents the state of a 3x3 TicTacToe board. Each element
 * within the board is a char, either 'x' or 'o'. Note that an
 * empty square should be represented by the char ' ';
 * 
 * <ul><li>A typical TicTacToe board consists of a 3x3 arrangement
 *         of cells, each of which is either empty or contains a mark.
 *     <li>AF (tttb) = { a 3x3 two dimensional array 'cells' of char } + 
 *                     { if the cell at column c and r is empty, then 
 *                       cells[c][r] == ' '; otherwise it contains the mark
 *                       for a player. } 
 * </ul>
 * 
 * <ul><li>cells != null</li>
 *     <li>cells[c][r] is either ' ' (EMPTY) or 'X' or 'O'
 * </ul>
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class TicTacToeBoard {
    /** Store data as a 3x3 array of cells. */
    protected char[][] cells;
    
    /** Empty Marker. */
    public static final char EMPTY = ' ';
    
    /**
     * Return the number of cells on the board.
     * @return   number of columns in the board.
     */
    public int numColumns() {
    	return MaxC+1;
    }
    
    /** Max number of columns. */
    public static final int MaxC = 2;
    
    /** Max number of rows. */
    public static final int MaxR = 2;
    
    /**
     * Return the number of rows on the board.
     * @return  number of rows in the board.
     */
    public int numRows() {
    	return MaxR+1;
    }
    
    /**
     * Constructor for TicTacToe Board.
     */
    public TicTacToeBoard () {
        // Make 3x3 array
        cells = new char [3][3];
        
        // enter in values.
        for (int c = 0; c <= MaxC; c++) {
            for (int r = 0; r <= MaxR; r++) {
                cells[c][r] = EMPTY;
            }
        }
    }
    
    /**
     * Helper constructor for initializing boards to arbitrary configurations.
     * 
     * Exercise suitable error control to avoid BAD patterns.
     *
     * @param test     2D array of three rows, where each row contains three values one for each column.
     */
    public TicTacToeBoard(char [][]test) {
    	int numX = 0;
    	int numO = 0;
    	
    	if (test.length != MaxC+1 || test[0].length != MaxR+1) {
    		throw new IllegalArgumentException ("Invalid array parameters to TicTacToeBoard.");
    	}
    	
    	// test to make sure all are acceptable.
        for (int c = 0; c <= MaxC; c++) {
            for (int r = 0; r <= MaxR; r++) {
                if (test[c][r] == Player.XMARK) {
                	numX++;
                	continue;
                }
                
                if (test[c][r] == Player.OMARK) {
                	numO++;
                	continue;
                }
                
                if (test[c][r] == EMPTY) {
                	continue;
                }
                
                throw new IllegalArgumentException ("Array to initialize TicTacToe board has illegal character:" + test[c][r]);
            }
        }
        
        if (numX < numO) {
        	throw new IllegalArgumentException ("Board state has too few Xs");
        }
        if (numX > numO+1) {
        	throw new IllegalArgumentException ("Board state has too many Xs");
        }
        
        // note that cells is Internally stored as column-dominant, which is different from the
        // input.
        cells = new char[][] {
        		{test[0][0], test[1][0], test[2][0]},
        		{test[0][1], test[1][1], test[2][1]},
        		{test[0][2], test[1][2], test[2][2]},
        };
    }
    
    /**
     * Copy Constructor for TicTacToe Board.
     * 
     * @param board    board to be copied.
     */
    public TicTacToeBoard (TicTacToeBoard board) {
        // Make 3x3 array
        cells = new char [3][3];
        
        // enter in values.
        for (int c = 0; c <= MaxC; c++) {
            for (int r = 0; r <= MaxR; r++) {
                cells[c][r] = board.cells[c][r];
            }
        }
    }
    
    /**
     * Determine if two tic tac toe board states are equal.
     * 
     * @param o    potential tic tac toe board state against which to compare.
     * @return true if tic tac toe board states are equal.
     */
    public boolean equals (Object o) {
    	if (o == null) return false;

    	if (o instanceof TicTacToeBoard) {
    		TicTacToeBoard board = (TicTacToeBoard) o;

    		for (int c = 0; c <= MaxC; c++) {
    			for (int r = 0; r <= MaxR; r++) {
    				if (cells[c][r] != board.cells[c][r]) {
    					return false;
    				}
    			}
    		}
    		
    		// cells check out.
    		return true;
    	}

    	return false;
    }
    
    /** 
     * To enable this board to be a key in a Hashtable.
     * 
     * @return integer value as hashcode.
     */
    public int hashCode() {
    	int hash = 1;
        for (int c = 0; c <= MaxC; c++) {
            for (int r = 0; r <= MaxR; r++) {
                hash += (c*3+r)*cells[c][r];
            }
        }
        
        return hash;
    }
    
    /**
     * Return the state of the board as a String.
     * @return board state as a String. 
     */
    public String toString () {
        String s = "";
        
        // note that must go ROW by ROW
        for (int r = 0; r <= MaxR; r++) {
            for (int c = 0; c <= MaxC; c++) {
               s = s + cells[c][r];
               if (c < cells.length-1) {
                   s = s + "|";
               }
            }
            
            if (r < cells[0].length-1) {
                s = s + "\n-+-+-\n"; 
            }
        }
        
        // fill in the calculation here
        return s;
    }
    
    /**
     * Determines whether game has been won.
     * 
     * @return true if some player has won game.
     */
    public boolean gameWon () {
    	return gameWon (Player.XMARK) || gameWon (Player.OMARK);
    }

    
    /**
     * Determines whether game has been won by given mark.
     * 
     * Winning is determine by three in a row: vertical, horizontal, diagonal too.
     * 
     *    <i>REQUIRES</i>: mark != EMPTY
     *                     
     *
     * @param mark    character of player mark to be investigated.
     * @return        true if player with given mark has won game.
     */
    public boolean gameWon (char mark) {
    	// reasonable compromise to protected against claiming
    	// an empty board as winning configuration when EMPTY passed in.
    	if (mark == EMPTY) {
    		return false;
    	}
    	
        // try vertical
    	for (int c = 0; c <= MaxC; c++) {
    		boolean victory = true;
    		
    		if (cells[c][0] != mark) continue;   // ensures check is only for given mark.
    		
    		for (int r = 1; r <= MaxR; r++) {
    			if (cells[c][r] != cells[c][0]) {
    				victory = false;
    			}
    		}
    		
    		if (victory) return true;   // some column found match
    	}
    	
    	// try horizontal
    	for (int r = 0; r <= MaxR; r++) {
    		boolean victory = true;
    		
    		if (cells[0][r] != mark) continue;   // ensures check is only for given mark.
    		
    		for (int c = 1; c <= MaxC; c++) {
    			if (cells[c][r] != cells[0][r]) {
    				victory = false;
    			}
    		}
    		
    		if (victory) return true;   // some row found match
    	}
    	
    	// try diagonal (upper left to lower right)
    	// prevent check for sequence of empty spaces!
		if (cells[0][0] == mark) {
			boolean victory = true;
			for (int i = 1; i <= MaxC; i++) {
	    		if (cells[i][i] != cells[0][0]) {
	    			victory = false;
	   			}
	   		}
	    	if (victory) return true;   // some diagonal matched
		}
		
    	// try diagonal (lower left to upper right)
    	if (cells[0][cells.length-1] == mark) {
    		boolean victory = true;
	    	for (int i = 1; i <= MaxC; i++) {
	    		if (cells[i][cells.length-i-1] != cells[0][cells.length-1]) {
	    			victory = false;
	   			}
	   		}
	    	if (victory) return true;   // some diagonal matched
    	}
    	
    	// no win registered!
    	return false;
    }
    
    /**
     * Returns marker at given spot.
     * 
     *    <i>REQUIRES</i>: 0 &le; col &le; MaxC AND 
     *                     0 &le; row &le; MaxR
     * 
     * @param col    desired column
     * @param row    desired row
     * @return       char marker in given location
     */
    public char get (int col, int row) {
        return cells[col][row];
    }    
    
    /**
     * Determines if cell is empty.
     * 
     *    <i>REQUIRES</i>: 0 &le; col &le; MaxC AND ) AND 
     *                     0 &le; row &le; MaxR
     *
     * @param col   desired column
     * @param row   desired row
     * @return      <code>true</code> if cell is empty; <code>false</code> otherwise
     */
    public boolean isClear (int col, int row) {
        return (cells[col][row] == EMPTY);
    }
    
 	/**
	 * Determine if a game is a draw because all cells are occupied but the game
	 * is not won.
	 *
	 * @return    true if there is no winning position AND all cells are occupied.
	 */
	public boolean isDraw() {
		// winning games are not draws.
		if (gameWon()) return false;
		
		for (int c = 0; c <= MaxC; c++) {
			for (int r = 0; r <= MaxR; r++) {
				if (cells[c][r] == EMPTY)
					return false;  // we can play!
			}
		}
		
		// nothing to be done.
		return true;
	}

	/**
	 * Clears the cell at given location.
	 * 
     *    <i>REQUIRES</i>: 0 &lt; col &lt; numColumns() AND 
     *                     0 &lt; row &lt; numRows()
     *
	 * @param col   column of cell to clear
	 * @param row   row of cell to clear
	 */
	public void clear(int col, int row) {
		cells[col][row] = EMPTY;
	}

	/**
	 * Sets the cell at given location to contain mark.
	 * 
     *    <i>REQUIRES</i>: 0 &lt; col &lt; numColumns() AND 
     *                     0 &lt; row &lt; numRows() AND
     *                     mark != EMPTY
     * 
	 * @param col    column of cell to set
	 * @param row    row of cell to set
	 * @param mark   mark to be inserted.
	 * @return       <code>true</code> if cell was empty, otherwise returns <code>false</code>.
	 */
	public boolean set(int col, int row, char mark) {
		if (cells[col][row] != EMPTY) {
			return false;
		}
		
		// ok to move.
		cells[col][row] = mark;
		return true;
	}

	/**
	 * Return true if entire board is empty.
	 * 
	 * @return <code>true</code> if all cells are set to EMPTY.
	 */
	public boolean isClear() {
		for (int c = 0; c <= MaxC; c++) {
			for (int r = 0; r <= MaxR; r++) {
				// found one
				if (!isClear (c,r)) {
					return false;
				}
			}
		}
		
		// nada!
		return true;
	}
	
	/** Interpret string of digits into Cells. */
	private static Cell[] interpret(String string) {
		Cell[] cells = new Cell[9];
		for (int i = 0; i < string.length(); i++) {
			int dig = string.charAt(i) - '1';
			
			int col = dig%3;
			int row = dig/3;
			cells[i] = new Cell(col, row);
		}
		
		return cells;
	}
	
		/** These will reflect the cell ordering. */
	public static final Cell[][] filters = new Cell[][] {
		interpret("123456789"),
		
		interpret("741852963"),
		interpret("987654321"),
		interpret("369258147"),
		
		interpret("147258369"),
		interpret("789456123"),
		interpret("963852741"),
		interpret("321654987"),
	};
	
	/**
	 * Determine if two Boards are the same (including rotations and reflections). 
	 * 
	 *    123    741    987    369     (ROTATIONS)
	 *    456    852    654    258
	 *    789    963    321    147
	 * 
	 *    147    789    963    321     (Diagonal reflection)
	 *    258    456    852    654
	 *    369    123    741    987
	 *    
	 * @param board2   other board against which to compare
	 * @return true if the boards are essentiall equivalent (because of reflection and rotations); false otherwise.
	 */
	public boolean sameBoard (TicTacToeBoard board2) {
		for (int i = 0; i < filters.length; i++) {
			boolean isSame = true;
			for (int k = 0; k <9; k++) {
				// note that the 0th one is the base; covers equals.
				if (get(filters[0][k].col, filters[0][k].row) !=
					board2.get(filters[i][k].col, filters[i][k].row)) {
					isSame = false;
					break;
				}
			}
			
			if (isSame) {
				return true;
			}
		}
		
		return false;
	}
}
