/**
 * 
 */

/**
 * @author A692456
 *
 */
public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Board b = new Board(4);
		Pice [][] board = new Pice[4][4];
		for( int i = 0; i < board.length; i++) {
			for(int y = 0; y < board[i].length; y ++) {
				board[i][y] = 2;
			}
		}
		b.setBoard(board);
		b.printBoard();
		System.out.println("-------------");
		b.moveUp();
		b.printBoard();
		System.out.println("-------------");
		b.moveUp();
		b.printBoard();
		System.out.println("-------------");
		b.moveUp();
		b.printBoard();
		System.out.println("-------------");
		

	}

}
