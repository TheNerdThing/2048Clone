/**
 * 
 */

/**
 * @author A692456
 * @TODO I want to create a Block Object to make merging simpler
 */
public class Board {
	
	private class Pice{
		private int value;
		private boolean hasMerged;
		
		/**
		 * if empty is true, will create a blank pice
		 * @param empty
		 */
		public Pice(boolean empty) {
			value = empty? 0: 2;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + value;
			return result;
		}

		/**
		 * checks if this is a blank space
		 * @return
		 */
		public boolean isEmpty() {
			return value ==0;
		}
		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Pice other = (Pice) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (value != other.value)
				return false;
			return true;
		}

		/**
		 * @return the value
		 */
		public int getValue() {
			return value;
		}

		/**
		 * @return the hasMerged
		 */
		public boolean hasMerged() {
			return hasMerged;
		}

		public void delete() {
			this.value = 0;
		}
		public void merge(Pice p) {
			if(canMerge(p)) {
				this.value = p.getValue() + this.value;
				this.hasMerged = true;
				p.delete();
			}
		}
		public void clearHasMerge() {
			this.hasMerged = false;
		}
		public boolean canMerge(Pice p) {
			return p.getValue() == this.value;
				
		}

		private Board getOuterType() {
			return Board.this;
		}
	}
	private Pice[][] state; 
	private enum Direction{
		UP,
		DOWN,
		LEFT,
		RIGHT
	};
	
	private enum MoveMergResult{
		Move,
		Merge,
		None
	}
	
	public void moveUp() {
		//move each block up 1
		for(int x = 0; x < state.length; x++) {
			for(int y = state.length; y >=0; y--) {
				MoveMergResult r = moveMerg(x,y, Direction.UP);
//				System.out.println(r);
				// if we merge a tile we need to check the tile below
				if(r == MoveMergResult.Merge) {
					y = Math.max(state.length, y +1);
				}
				//if nothing happened continue on
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
	public void setBoard(Pice [][] set) {
		state= set;
	}
	
	/**
	 * returns true if the space at x, y is empty
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isEmpty(int x, int y) {
		return state[y][x].isEmpty();
	}
	
	/**
	 * try to move the tile in a direction, if they are the same, merge the pieces together
	 * returns true if a tile was merged
	 * @param x
	 * @param y
	 * @param dir
	 */
	public MoveMergResult moveMerg(int x, int y, Direction dir) {
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
		if(yCheck < 0  || xCheck < 0 || yCheck >= state.length || xCheck >= state.length ||
				y < 0  || x < 0 || y >= state.length || x >= state.length) {
			return MoveMergResult.None;
		}else { 
			// check if tiles are the same and are not empty tiles
			if(state[y][x].equals(state[yCheck][xCheck]) && !state[y][x].isEmpty()) {
				// merge the tiles and delete the last one 
				state[yCheck][xCheck].merge(state[y][x]);
				state[y][x].delete();
				return MoveMergResult.Merge;
			}
			// check if the space is empty
			if(state[yCheck][xCheck].isEmpty()) {
				// Swap the empty tile with the data tile
				state[yCheck][xCheck] = state[y][x];
				state[y][x] = state[yCheck][xCheck];
				return MoveMergResult.Move;
			}
			// last case is that we are trying to move 2 different tiles together
			// which means we do not do anything 
			return MoveMergResult.None;
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
					if(state[x][y].value == 2) {
						// check all adjacent squares next to it
						if(x + 1 < state.length && state[x + 1][y].equals(state[x][y])) {
							return false;
						}
						if(y + 1 < state.length && state[x][y+1].equals( state[x][y])) {
							return false;
						}
						if(x-1 >= 0&& state[x - 1][y].equals(state[x][y])) {
							return false;
						}
						if(y-1 >= 0 && state[x][y-1].equals(state[x][y])) {
							return false;
						}
					}
				}
			}
		}
		return false;
	}
	
	public Board(int size) {
		state = new Pice[size][size];
		for( int x = 0; x < state.length; x++) {
			for( int y = 0 ; y< state[x].length; y++ ) {
				state[x][y] = new Pice(true);
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
				if(isEmpty(x,y)) {
					flag = false;
					state[x][y] = new Pice(false);
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
				if(state[x][y].isEmpty()) {
					return false;
				}
			}
		}
		return true;
	}
}
