/**
 * 
 */
package com.rachum.amir.cloud9;

import java.util.concurrent.locks.Condition;

/**
 * @author Rachum
 *
 */
public class MoveHandler {
    private final Condition cond;
    private Move move = null;
    
    public MoveHandler(final Condition cond) {
        this.cond = cond;
	}
	
    void move(final Move move) {
    	this.move = move;
        cond.notifyAll();
    }

	/**
	 * @return the move
	 */
	public Move getMove() {
		return move;
	}

}
