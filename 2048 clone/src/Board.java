/**
 * 
 */

/**
 * @author A692456
 * @TODO Works for the most part. Just need to make sure to check for merges after we move all Pices
 * @TODO also need to make sure that the parts are merged in the correct order
 */
public class Board {
	
	private class Pice{
		private int value;
		private boolean hasMerged;
		
		public Pice(Pice p) {
			this.value= p.value;
			this.hasMerged = p.hasMerged;
		}
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
			return value == 0;
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
//				System.out.println("merging with " + p.getValue());
				this.value += p.getValue();
				this.hasMerged = true;
//				System.out.println("this.value = " + this.value);
//				System.out.println("Merge: deleteing a " + this.value);
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
		//move each block down 1
				for(int x = 0; x < state.length; x++) {
					for(int y = 0; y <=state.length; y++) {
						MoveMergResult r = moveMerg(x,y, Direction.DOWN);
						// if we merge a tile we need to check the tile below
						if(r == MoveMergResult.Merge) {
							y = Math.max(0, y -1);
						}
						//if nothing happened continue on
					}
					
				}
		step();
	}
	public void moveLeft() {
		//move each block up 1
				for(int x = 0; x < state.length; x++) {
					for(int y = state.length; y >=0; y--) {
						MoveMergResult r = moveMerg(x,y, Direction.LEFT);
						// if we merge a tile we need to check the tile below
						if(r == MoveMergResult.Merge) {
							y = Math.max(state.length, y +1);
						}if(r == MoveMergResult.Move) {
							// if a Piece moved we need to check if it could merge with the one behind it.  
						}
						//if nothing happened continue on
					}
					
				}
		step();
	}
	public void moveRight() {
		//move each block up 1
				for(int x = 0; x < state.length; x++) {
					for(int y = state.length; y >=0; y--) {
						MoveMergResult r = moveMerg(x,y, Direction.RIGHT);
						// if we merge a tile we need to check the tile below
						if(r == MoveMergResult.Merge) {
							y = Math.max(state.length, y +1);
						}
						//if nothing happened continue on
					}
					
				}
		step();
	}
	
	public void printBoard() {
		for(int x = 0; x < state.length; x ++) {
			for(int y = 0; y< state.length; y ++) {
				System.out.print(state[x][y].getValue() + " ");
			}
			System.out.println();
		}
	}
	
	/**
	 * for debugging 
	 * @param set
	 */
	public void setBoard() {
		for(int x = 0; x < state.length; x ++) {
			for(int y = 0; y < state.length; y++) {
				state[x][y] = new Pice(Math.random() > .5 ? false: true);
			}
		}
	}
	
	/**
	 * returns true if the space at x, y is empty
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isEmpty(int x, int y) {
		return state[x][y].isEmpty();
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
				// check if either tile has been merged
				if(state[y][x].hasMerged() || state[yCheck][xCheck].hasMerged()) {
					System.out.println("unit has already merged");
					return MoveMergResult.None;
				}
				// merge the tiles and delete the last one 
				state[yCheck][xCheck].merge(state[y][x]);
				return MoveMergResult.Merge;
			}
			// check if the space is empty
			if(state[yCheck][xCheck].isEmpty()) {
				// Swap the empty tile with the data tile
				state[yCheck][xCheck] = new Pice(state[y][x]);
				state[y][x].delete();
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
		// reset has merged
		for(int x = 0; x < state.length; x++) {
			for(int y = 0; y< state.length; y++) {
				state[x][y].clearHasMerge();
			}
		}
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
				int x = (int) (Math.random() * state.length);
				int y = (int)(Math.random() * state.length);
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
