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
		b.setBoard();
		b.printBoard();
		System.out.println("-------------");
		for(int i = 0; i < 25; i++) {
			b.moveLeft();
			b.printBoard();
			System.out.println("-------------");
			
		}

	}

}
