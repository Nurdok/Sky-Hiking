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
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.rachum.amir.cloud9.GameEvent.Type;

/**
 * @author Amir Rachum
 *
 */
public class Game extends Thread {
	public final List<Player> players;
	public final List<Player> remainingPlayers;
	private final Deck deck;
	private final Collection<GameEventListener> listeners = new LinkedList<GameEventListener>();
	public Collection<Card> diceRoll;
	final Iterator<Player> pilotIterator;
    
	private enum LevelOutcome {SUCCESS, CRASH;}
	
	public Game(final List<Player> players) {
		super();
		this.players = players;
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
			final List<Player> remainingPlayers = new LinkedList<Player>(players);
            announce(new GameEvent(Type.ROUND_BEGIN, this));
			for (final CloudLevel level : CloudLevel.gameLevels()) {
                announce(new GameEvent(Type.LEVEL_BEGIN, this));
                
				if (level.getDiceNumber() == 0) {
					for (final Player player : remainingPlayers) {
						player.score(level.getScore()); //TODO: move to listener
					}
                    announce(new GameEvent(Type.LEVEL_BEGIN, this));
                    announce(new GameEvent(Type.ROUND_END, this));
                    break;
				}
                
				final Player pilot = getNextPilot();
                
                final LevelOutcome outcome = playLevel(level, remainingPlayers, pilot);
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
                    announce(new GameEvent(Type.GAME_END, this));
                    return;
            	}
            }
		}
	}

	private Player getNextPilot() {
        Player player = pilotIterator.next();
        while (!remainingPlayers.contains(player)) {
        	player = pilotIterator.next();
        }
		return player;
	}

	public LevelOutcome playLevel(final CloudLevel level,
			final Collection<Player> remainingPlayers, final Player pilot) {
		diceRoll = Dice.roll(level.getDiceNumber());
        announce(new GameEvent(Type.DICE_ROLLED, this));
        
		final List<Player> newRemainingPlayers = new LinkedList<Player>();
		newRemainingPlayers.addAll(remainingPlayers);
		for (final Player player : getRemainingPlayersInOrder()) {
			if (player != pilot) {
				final Lock lock = new ReentrantLock();
                final Condition cond = lock.newCondition();
                final MoveHandler handler = new MoveHandler(cond);
                player.play(handler, this);
                try {
					cond.wait();
				} catch (final InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				final Move move = handler.getMove();
				if (move == Move.LEAVE) {
					player.score(level.getScore());
					newRemainingPlayers.remove(player);
                    announce(new GameEvent(Type.MOVE, this));
				}
			}
		}
		remainingPlayers.retainAll(newRemainingPlayers);
		
		final Lock lock = new ReentrantLock();
		final Condition cond = lock.newCondition();
		final PayHandler handler = new PayHandler(cond);
		pilot.pay(handler, this);
		try {
			cond.wait();
		} catch (final InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (handler.didPay()) {
            return LevelOutcome.SUCCESS;
		}
        return LevelOutcome.CRASH;
		
	}
	
	private Collection<? extends Player> getRemainingPlayersInOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	private Map<Player, Integer> scoreboard() {
		final Map<Player, Integer> scoreboard = new HashMap<Player, Integer>();
		for (final Player player : players) {
			scoreboard.put(player, player.getScore());
        }
        return scoreboard;
	}
}
