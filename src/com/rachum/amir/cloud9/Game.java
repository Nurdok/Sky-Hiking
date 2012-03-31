/**
 * 
 */
package com.rachum.amir.cloud9;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.rachum.amir.cloud9.GameEvent.Type;

/**
 * @author Amir Rachum
 *
 */
public class Game extends Thread {
	public final List<Player> players;
	public List<Player> remainingPlayers;
	private final Deck deck;
	private final Collection<GameEventListener> listeners = new LinkedList<GameEventListener>();
	public Collection<Card> diceRoll;
	final Iterator<Player> pilotIterator;
    public Player pilot;
    
	private enum LevelOutcome {SUCCESS, CRASH;}
	
	public Game(final List<Player> players) {
		super();
		this.players = players;
        for (final Player player : players) {
        	registerListener(player);
        }
		deck = new Deck();
		for (final Player player : players) {
            player.gameStart(deck);
		}
    	pilotIterator = new InfiniteIterator(players);
        remainingPlayers = new LinkedList<Player>(players);
	}

    public void registerListener(final GameEventListener listener) {
    	listeners.add(listener);
    }
    
	private void announce(final GameEvent event) {
		for (final GameEventListener listener : listeners) {
			listener.handleEvent(event);
		}
	}

	@Override
	public void run() {
        announce(new GameEvent(Type.GAME_BEGIN, this));
		while (true) {
			remainingPlayers = new LinkedList<Player>(players);
            announce(new GameEvent(Type.ROUND_BEGIN, this));
			for (final CloudLevel level : CloudLevel.gameLevels()) {
                final GameEvent event = new GameEvent(Type.LEVEL_BEGIN, this);
                event.level = level;
                announce(event);
                
				if (level.getDiceNumber() == 0) {
					for (final Player player : remainingPlayers) {
						player.score(level.getScore()); //TODO: move to listener
					}
                    announce(new GameEvent(Type.LEVEL_BEGIN, this));
                    announce(new GameEvent(Type.ROUND_END, this));
                    break;
				}
                
				setNextPilot();
                
                final LevelOutcome outcome = playLevel(level);
                if (outcome == LevelOutcome.CRASH) {
                    announce(new GameEvent(Type.LEVEL_END, this));
                    announce(new GameEvent(Type.ROUND_END, this));
                	break;
                }
                announce(new GameEvent(Type.LEVEL_END, this));
				if (remainingPlayers.isEmpty()) {
                    announce(new GameEvent(Type.ROUND_END, this));
					break; //Move to next level
				}
				
			}
            for (final Player player : players) {
            	if (player.getScore() >= 50) {
                    final GameEvent event = new GameEvent(Type.GAME_END, this);
                    event.winner = player;
                    announce(event);
                    return;
            	}
            }
		}
	}

	private void setNextPilot() {
        Player player = pilotIterator.next();
        while (!remainingPlayers.contains(player)) {
        	player = pilotIterator.next();
        }
        pilot = player;
	}

	public LevelOutcome playLevel(final CloudLevel level) {
		diceRoll = Dice.roll(level.getDiceNumber());
        announce(new GameEvent(Type.DICE_ROLLED, this));
        
		final List<Player> newRemainingPlayers = new LinkedList<Player>();
		newRemainingPlayers.addAll(remainingPlayers);
		for (final Player player : getRemainingPlayersInOrder()) {
			if (player != pilot) {
                final MoveHandler handler = new MoveHandler();
                player.play(handler, this);
				final Move move = handler.getMove();
				if (move == Move.LEAVE) {
					player.score(level.getScore());
					newRemainingPlayers.remove(player);
				}
				final GameEvent event = new GameEvent(Type.MOVE, this);
				event.move = move;
                event.currentPlayer = player;
				announce(event);
			}
		}
		remainingPlayers.retainAll(newRemainingPlayers);
		
		final PayHandler handler = new PayHandler();
		pilot.pay(handler, this);
		if (handler.didPay()) {
            return LevelOutcome.SUCCESS;
		}
        return LevelOutcome.CRASH;
		
	}
	
	private Collection<? extends Player> getRemainingPlayersInOrder() {
        final List<Player> newRemainingPlayers = new LinkedList<Player>();
        newRemainingPlayers.add(pilot);
        final Iterator<Player> iterator = new InfiniteIterator(remainingPlayers);
        while (iterator.next() != pilot) {}
        Player player = iterator.next();
        while (player != pilot) {
        	newRemainingPlayers.add(player);
            player = iterator.next();
        }
		return newRemainingPlayers;
	}

	private Map<Player, Integer> scoreboard() {
		final Map<Player, Integer> scoreboard = new HashMap<Player, Integer>();
		for (final Player player : players) {
			scoreboard.put(player, player.getScore());
        }
        return scoreboard;
	}
}
