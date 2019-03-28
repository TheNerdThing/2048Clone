/**
 * 
 */

/**
 * @author A692456
 *
 */
public class Board {

	private int[][] state; 
	private enum Direction{
		UP,
		DOWN,
		LEFT,
		RIGHT
	};
	public void moveUp() {
		//move each block up 1
		for(int x = 0; x < state.length; x++) {
			for(int y = 0; y <state.length; y++) {
				//if the tile is empty do nothing
				// if we merge a tile 
				if(!isEmpty(x,y) && moveMerg( x, y, Direction.UP)) {
					
				}
				
			}
		}
		
		step();
	}
	public void moveDown() {
		step();
	}
	public void moveLeft() {
		step();
	}
	public void moveRight() {
		step();
	}
	
	public void printBoard() {
		for(int x = 0; x < state.length; x ++) {
			for(int y = 0; y< state.length; y ++) {
				System.out.print(state[x][y] + " ");
			}
			System.out.println();
		}
	}
	
	/**
	 * for debugging 
	 * @param set
	 */
	public void setBoard(int [][] set) {
		state= set;
	}
	
	/**
	 * returns true if the space at x, y is empty
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isEmpty(int x, int y) {
		return state[y][x] == 0;
	}
	
	/**
	 * try to move the tile in a direction, if they are the same, merge the pieces together
	 * returns true if a tile was merged
	 * @param x
	 * @param y
	 * @param dir
	 */
	public boolean moveMerg(int x, int y, Direction dir) {
		int xCheck = x;
		int yCheck = y;
		switch(dir){
			case UP:
				yCheck--;
				break;
			case DOWN:
				yCheck++;
				break;
			case LEFT:
				xCheck--;
				break;
			case RIGHT:
				xCheck++;
				break;
		}
		// check if we are trying to move/merge out of bounds 
		if(yCheck < 0  || xCheck < 0 || yCheck >= state.length || xCheck >= state.length) {
			return false;
		}else { 
			// check if tiles are the same and are not empty tiles
			if(state[y][x] == state[yCheck][xCheck] && state[y][x] != 0) {
				// merge the tiles and delete the last one 
				state[yCheck][xCheck] = state[y][x] + state[yCheck][xCheck];
				state[y][x] = 0;
				return true;
			}
			// check if the space is empty
			if(state[yCheck][xCheck] == 0) {
				state[yCheck][xCheck] = state[y][x];
				state[y][x] = 0;
				return false;
			}
			// last case is that we are trying to move 2 different tiles together
			// which means we do not do anything 
			return false;
		}
	}
	
	/**
	 * should happen after each move.
	 * returns true if there is another move possible 
	 */
	private boolean step() {
		if(gameOver()) {
			return false;
		}else {
			spawnNewBlock();
			return true;
		}
	}
	
	public boolean gameOver() {
		if(boardFull()) {
			// we only need to check if there are (2) 2 blocks next to each other
			for(int x = 0; x < state.length; x++) {
				for(int y = 0; y < state[x].length; y++) {
					if(state[x][y] == 2) {
						// check all adjacent squares next to it
						if(x + 1 < state.length && state[x + 1][y] == state[x][y]) {
							return false;
						}
						if(y + 1 < state.length && state[x][y+1] == state[x][y]) {
							return false;
						}
						if(x-1 >= 0&& state[x - 1][y] == state[x][y]) {
							return false;
						}
						if(y-1 >= 0 && state[x][y-1] == state[x][y]) {
							return false;
						}
					}
				}
			}
		}
		return false;
	}
	
	public Board(int size) {
		state = new int[size][size];
		for( int x = 0; x < state.length; x++) {
			for( int y = 0 ; y< state[x].length; y++ ) {
				state[x][y] = 0;
			}
		}
	}
	
	/**
	 * after each round, we will place a 2 in a new spot
	 */
	public void spawnNewBlock() {
		if(!boardFull()) {
			boolean flag = true;
			do {
				int x = (int) (Math.random() * 4);
				int y = (int)(Math.random() * 4);
				if(state[x][y] == 0) {
					flag = false;
					state[x][y] = 2;
				}
			}while(flag);
		}
	}
	/**
	 * check if there is a space open to spawn a new block
	 * @return
	 */
	public boolean boardFull() {
		for( int x = 0; x < state.length; x++) {
			for( int y = 0 ; y< state[x].length; y++ ) {
				if(state[x][y] == 0) {
					return false;
				}
			}
		}
		return true;
	}
}
