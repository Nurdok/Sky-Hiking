package com.rachum.amir.cloud9.android;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rachum.amir.cloud9.Game;
import com.rachum.amir.cloud9.GameEvent;
import com.rachum.amir.cloud9.GameEventListener;
import com.rachum.amir.cloud9.Move;
import com.rachum.amir.cloud9.Player;
import com.rachum.amir.cloud9.RiskyPlayer;
import com.rachum.amir.util.range.Range;

public class MainGamePanel extends LinearLayout implements GameEventListener {
    private final TextView log;
    private GameEvent event;
    private final Handler handler;
    
	public MainGamePanel(final Context context) {
		super(context);
        handler = new Handler();
        log = new TextView(context);
        this.addView(log);
        
        final List<Player> players = new LinkedList<Player>();
        for (final int i : new Range(3)) {
        	players.add(new RiskyPlayer("Risky" + i));
        }
        //players.add(new HumanPlayer(context));
        final Game game = new Game(players);
        game.registerListener(this);
        game.start();
	}
    
    @Override
    public void handleEvent(final GameEvent event) {
		switch (event.type) {
		case DICE_ROLLED:
            handler.post(new Runnable() {
				@Override
				public void run() {
        			log.append(event.context.pilot + " rolled " + event.context.diceRoll);
				}
            });
		    break;
		case GAME_BEGIN:
            handler.post(new Runnable() {
				@Override
				public void run() {
					log.setText("Starting a new game!\n");
					log.append("Players are: ");
					for (final Player player : event.context.players) {
						log.append(player + " ");
					}
					log.append("\n");
				}
			});
			break;
		case GAME_END:
            handler.post(new Runnable() {
            	@Override
                public void run() {
        			log.append("Game is over! the winner is " + event.winner);
            	}
            });
			break;
		case LEVEL_BEGIN:
            handler.post(new Runnable() {
            	@Override
                public void run() {
            		log.setText("Starting a new level with " + event.context.remainingPlayers);
            	}
            });
			break;
		case LEVEL_END:
			break;
		case MOVE:
            handler.post(new Runnable() {
            	@Override
                public void run() {
            		log.setText("Starting a new level with " + event.context.remainingPlayers);
            	}
            });
			log.append(event.currentPlayer + " is ");
			if (event.move == Move.STAY) {
				log.append("staying!");
			} else {
				log.append("leaving :(");
			}
			break;
		case PAY:
            handler.post(new Runnable() {
            	@Override
                public void run() {
            		log.append(event.context.pilot + " ");
            		if (event.didPay) {
            			log.append("payed " + event.cardsPayed + " and we are moving on to " +
            			"the next level.");
            		}
            		else {
            			log.append("didn't have the cards. We're crashing...");
            		}
            	}
            });
            break;
		case ROUND_BEGIN:
			handler.post(new Runnable() {
				@Override
				public void run() {
					log.setText("New Round Begins!\n");
            	}
            });
			break;
		case ROUND_END:
            handler.post(new Runnable() {
            	@Override
                public void run() {
            		log.append("Round is over!\n");
            	}
            });
			break;
		}
    }

}
