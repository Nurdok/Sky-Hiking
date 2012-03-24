package com.rachum.amir.cloud9.android;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rachum.amir.cloud9.Game;
import com.rachum.amir.cloud9.GameEvent;
import com.rachum.amir.cloud9.GameEventListener;
import com.rachum.amir.cloud9.Move;
import com.rachum.amir.cloud9.MoveHandler;
import com.rachum.amir.cloud9.PayHandler;
import com.rachum.amir.cloud9.Player;
import com.rachum.amir.cloud9.RiskyPlayer;
import com.rachum.amir.util.range.Range;

public class MainGamePanel extends LinearLayout implements GameEventListener {
    private final TextView scoreboard;
    private final TextView log;
    private GameEvent event;
    private final Handler handler;
    
	public MainGamePanel(final Context context) {
		super(context);
        this.setOrientation(VERTICAL);
        handler = new Handler();
        scoreboard = new TextView(context);
        addView(scoreboard);
        log = new TextView(context);
        addView(log);
        final Button stay = new Button(context);
        stay.setText("Stay");
        addView(stay);
        final Button leave = new Button(context);
        leave.setText("Leave");
        addView(leave);
        
        final List<Player> players = new LinkedList<Player>();
        for (final int i : new Range(3)) {
        	players.add(new RiskyPlayer("Risky" + i));
        }
        //players.add(new HumanPlayer("Amir", context));
        players.add(new Player("Amir") {
			
			@Override
			public void play(final MoveHandler handler, final Game game) {
				stay.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(final View v) {
						handler.move(Move.STAY);
					}
				});

				leave.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(final View v) {
						handler.move(Move.LEAVE);
					}
				});
                //MainGamePanel.this.addView(stay);
                //MainGamePanel.this.addView(leave);
			}
			
			@Override
			public void pay(final PayHandler handler, final Game context) {
				// TODO Auto-generated method stub
                handler.pay(false, null);
			}
		});
        final Game game = new Game(players);
        game.registerListener(this);
        game.start();
	}
    
	@Override
	public void handleEvent(final GameEvent event) {
        handler.post(new Runnable() {
			@Override
			public void run() {
                handleEventAux(event);
                updateScores(event.context.players);
			}
		});
	}
    
	private void handleEventAux(final GameEvent event){
		switch (event.type) {
		case DICE_ROLLED:
			log.append(event.context.pilot + " rolled " + event.context.diceRoll + "\n");
			break;
		case GAME_BEGIN:
			log.setText("Starting a new game!\n");
			log.append("Players are: ");
			for (final Player player : event.context.players) {
				log.append(player + " ");
			}
			log.append("\n");
			break;
		case GAME_END:
			log.append("Game is over! the winner is " + event.winner);
			break;
		case LEVEL_BEGIN:
			log.setText("Starting a new level with " + event.context.remainingPlayers + "\n");
			break;
		case LEVEL_END:
			break;
		case MOVE:
			log.append(event.currentPlayer + " is ");
			if (event.move == Move.STAY) {
				log.append("staying!\n");
			} else {
				log.append("leaving :(\n");
			}
			break;
		case PAY:
			log.append(event.context.pilot + " ");
			if (event.didPay) {
				log.append("payed " + event.cardsPayed + " and we are moving on to " +
				"the next level.\n");
			}
			else {
				log.append("didn't have the cards. We're crashing...\n");
			}
			break;
		case ROUND_BEGIN:
			log.setText("New Round Begins!\n");
			break;
		case ROUND_END:
			log.append("Round is over!\n");
			break;
		}
	}

	private void updateScores(final List<Player> players) {
		scoreboard.setText("");
		for (final Player player : players) {
			scoreboard.append(player + ": " + player.getScore() + "\n");
		}
	}
}
