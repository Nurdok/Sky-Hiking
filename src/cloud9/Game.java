/**
 * 
 */
package cloud9;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Rachum
 *
 */
public class Game {
	private final List<Player> players;
	private Map<Player, Integer> scoreboard;
	private final Deck deck;
	private final Iterator<Player> pilotIterator;
	
	public Game(final List<Player> players) {
		super();
		this.players = players;
		deck = new Deck();
		for (final Player player : players) {
			player.addCards(deck.draw(6));
			scoreboard.put(player, 0);
		}
		pilotIterator = new InfiniteIterator(players);
	}


	public void play() {
		while (true) { //TODO: until the game ends
			final List<Player> remainingPlayers = new LinkedList<Player>(players);
			for (final CloudLevel level : CloudLevel.gameLevels()) {
				if (remainingPlayers.isEmpty()) {
					break; //Move to next level
				}
				Player pilot = pilotIterator.next();
				while (!remainingPlayers.contains(pilot)) {
					pilot = pilotIterator.next();
				}
				
				final Collection<Symbol> diceRoll = Dice.roll(level.getDiceNumber());
				//TODO: allow each player to decide...
				
				for (final Player player : remainingPlayers) { //TODO: order important
					if (player != pilot) {
						if (!player.stay()) {
							scoreboard.addPoints(player, level.getScore());
							remainingPlayers.remove(player);
						}
					}
				}
				
				if (pilot.hasCards(diceRoll)) {
					pilot.pay(diceRoll, deck);
				}
				else {
					//We crash and move to next round
					for (final Player player : players) {
						player.addCards(deck.draw(1));
					}
				}
			}
		}
	}
}
