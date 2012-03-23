/**
 * 
 */
package com.rachum.amir.cloud9;


/**
 * @author Rachum
 *
 */
public class MoveHandler {
    private Move move = null;
    
    public synchronized void move(final Move move) {
    	this.move = move;
        notifyAll();
    }

	/**
	 * @return the move
	 */
	public synchronized Move getMove() {
        while (move == null) {
        	try {
				wait();
			} catch (final InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		return move;
	}

}
