/**
 * 
 */
package cloud9;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
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
	private final Deck deck;
	private final Iterator<Player> pilotIterator;
    
	private enum LevelOutcome {SUCCESS, CRASH;}
	
	public Game(final List<Player> players) {
		super();
		this.players = players;
		deck = new Deck();
		for (final Player player : players) {
			player.addCards(deck.draw(6));
		}
		pilotIterator = new InfiniteIterator(players);
	}


	public void play() {
		while (true) { //TODO: until the game ends
			final List<Player> remainingPlayers = new LinkedList<Player>(players);
			for (final CloudLevel level : CloudLevel.gameLevels()) {
				try {
					System.in.read();
				} catch (final IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                System.out.println("Playing level" + level);
                System.out.println("Current players are: " + remainingPlayers);
                System.out.println("Current scores: " + scoreboard());
				if (remainingPlayers.isEmpty()) {
					break; //Move to next level
				}
                
				if (level.getDiceNumber() == 0) {
					for (final Player player : remainingPlayers) {
						player.score(level.getScore());
						player.addCards(deck.draw(1));
					}
                    break;
				}
				Player pilot = pilotIterator.next();
				while (!remainingPlayers.contains(pilot)) {
					pilot = pilotIterator.next();
				}
                System.out.println("Current Pilot: " + pilot);
                
                final LevelOutcome outcome = playLevel(level, remainingPlayers, pilot);
                if (outcome == LevelOutcome.CRASH) {
                	break;
                }
                
				
			}
            for (final Player player : players) {
            	if (player.getScore() > 49) {
            		System.out.println("Game Over! " + player + "has a score of "
            				+ player.getScore());
                    return;
            	}
            }
		}
	}

	public LevelOutcome playLevel(final CloudLevel level,
			final Collection<Player> remainingPlayers, final Player pilot) {
		final Collection<Symbol> diceRoll = Dice.roll(level.getDiceNumber());
		//TODO: allow each player to decide...
		
		final List<Player> newRemainingPlayers = new LinkedList<Player>();
		newRemainingPlayers.addAll(remainingPlayers);
		for (final Player player : remainingPlayers) { //TODO: order important
			if (player != pilot) {
				final Move move = player.play(getState());
				if (move == Move.LEAVE) {
					player.score(level.getScore());
					newRemainingPlayers.remove(player);
					System.out.println("Player " + player.getName() +
							" leaves and scores " + level.getScore() +
							" points to a total of " + player.getScore());
				}
			}
		}
		remainingPlayers.retainAll(newRemainingPlayers);
		
		if (pilot.hasCards(diceRoll)) {
			pilot.pay(diceRoll, deck);
			System.out.println("The pilot has payed " + diceRoll +
			" and we are moving on the the next level.");
            return LevelOutcome.SUCCESS;
		} else {
			//We crash and move to next round
			System.out.println("Awwww... we crashed...");
			for (final Player player : players) {
				player.addCards(deck.draw(1));
			}
            return LevelOutcome.CRASH;
		}
		
	}
	
	private Map<Player, Integer> scoreboard() {
		final Map<Player, Integer> scoreboard = new HashMap<Player, Integer>();
		for (final Player player : players) {
			scoreboard.put(player, player.getScore());
        }
        return scoreboard;
	}


	private GameState getState() {
		// TODO Auto-generated method stub
		return null;
	}
}
