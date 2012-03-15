/**
 * 
 */
package com.rachum.amir.cloud9;

import java.util.LinkedList;
import java.util.List;

import com.rachum.amir.util.range.Range;

/**
 * @author Rachum
 *
 */
public class Cloud9 {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
        final List<Player> players = new LinkedList<Player>();
        for (final int i : new Range(4)) {
        	players.add(new RiskyPlayer("Risky" + i));
        }
        final Game game = new Game(players);
        game.play();
	}

}
