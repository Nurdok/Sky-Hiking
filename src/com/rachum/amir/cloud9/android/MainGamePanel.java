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
    private final TextView hand;
    private final TextView log;
    private GameEvent event;
    private final Handler handler;
    private final Player humanPlayer;
    
	public MainGamePanel(final Context context) {
		super(context);
        this.setOrientation(VERTICAL);
        handler = new Handler();
        scoreboard = new TextView(context);
        addView(scoreboard);
        hand = new TextView(context);
        addView(hand);
        log = new TextView(context);
        addView(log);
        final Button stay = new Button(context);
        stay.setText("Stay");
        addView(stay);
        final Button leave = new Button(context);
        leave.setText("Leave");
        addView(leave);
        final Button pay = new Button(context);
        pay.setText("Pay");
        addView(pay);
        final Button payWithWild = new Button(context);
        payWithWild.setText("Pay Wild");
        addView(payWithWild);
        final Button dontPay = new Button(context);
        dontPay.setText("Don't Pay");
        addView(dontPay);
        
        final List<Player> players = new LinkedList<Player>();
        for (final int i : new Range(3)) {
        	players.add(new RiskyPlayer("Risky" + i));
        }
        //players.add(new HumanPlayer("Amir", context));
        humanPlayer = new Player("Amir") {
			
			@Override
			public void play(final MoveHandler handler, final Game game) {
				stay.setVisibility(VISIBLE);
				leave.setVisibility(VISIBLE);
				stay.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(final View v) {
						handler.move(Move.STAY);
						stay.setVisibility(INVISIBLE);
						leave.setVisibility(INVISIBLE);
					}
				});

				leave.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(final View v) {
						handler.move(Move.LEAVE);
						stay.setVisibility(INVISIBLE);
						leave.setVisibility(INVISIBLE);
					}
				});
			}
			
			@Override
			public void pay(final PayHandler handler, final Game context) {
                if (humanPlayer.getHand().contains(context.diceRoll)) {
                	pay.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(final View v) {
                            handler.pay(true, context.diceRoll);
						}
					});
                }
			}
		};
        players.add(humanPlayer);
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
                updateHand();
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
			log.setText("Starting " + event.level + " with " +
					event.context.remainingPlayers + "\n");
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
    
	private void updateHand() {
		hand.setText("Hand: " + humanPlayer.getHand().getCards());
	}
}
