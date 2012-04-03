package com.rachum.amir.cloud9.android;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import android.app.Activity;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.rachum.amir.cloud9.Card;
import com.rachum.amir.cloud9.CloudLevel;
import com.rachum.amir.cloud9.Game;
import com.rachum.amir.cloud9.GameEvent;
import com.rachum.amir.cloud9.GameEventListener;
import com.rachum.amir.cloud9.Move;
import com.rachum.amir.cloud9.Player;
import com.rachum.amir.cloud9.RiskyPlayer;
import com.rachum.amir.util.range.Range;

public class GameActivity extends Activity implements GameEventListener {
    private TextView scoreboard;
    private TextView log;
    private Handler handler;
    private Player humanPlayer;
    private List<String> names = 
    	Arrays.asList("Phil", "Johnny", "Sharon", "Tammy", "Dan", "George",
    			"Joel", "Jeff", "Arnold", "Jack", "Gary", "Ben", "Fred");
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        handler = new Handler();
        scoreboard = (TextView) findViewById(R.id.scoreboard);
        log = (TextView) findViewById(R.id.log);
        final Button stay = (Button) findViewById(R.id.stay);
        final Button leave = (Button) findViewById(R.id.leave);
        final Button pay = (Button) findViewById(R.id.pay);
        final Button payWithWild = (Button) findViewById(R.id.payWithWild);
        final Button dontPay = (Button) findViewById(R.id.dontPay);
        
        stay.setEnabled(false);
        leave.setEnabled(false);
        pay.setEnabled(false);
        dontPay.setEnabled(false);
        payWithWild.setEnabled(false);
        
        Collections.shuffle(names);
        final List<Player> players = new LinkedList<Player>();
        for (final int i : new Range(3)) {
        	players.add(new RiskyPlayer(names.get(i)));
        }
        humanPlayer = new HumanPlayer("Amir", handler, stay, leave, pay, payWithWild, dontPay);
        players.add(humanPlayer);
        final Game game = new Game(players);
        game.registerListener(this);
        game.start();
	}
    
	@Override
	public void handleEvent(final GameEvent event) {
		final Collection<Card> cards = new LinkedList<Card>(humanPlayer.getHand().getCards());
        handler.post(new Runnable() {
			@Override
			public void run() {
                handleEventAux(event);
                updateScores(event.context.players);
                updateHand(cards);
                updateLevelInfo(event.level);
			}
		});
	}
    
	private void updateLevelInfo(CloudLevel level) {
		if (level != null) {
			((TextView) findViewById(R.id.level)).setText(level.toString());
		}
	}

	private void handleEventAux(final GameEvent event){
		switch (event.type) {
		case DICE_ROLLED:
			log.append(event.context.pilot + " rolled " + event.context.diceRoll + "\n");
			break;
		case GAME_BEGIN:
			log.append("Starting a new game!\n");
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
			log.append("\nStarting " + event.level + " with " +
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
			log.append("\nNew Round Begins!\n");
			break;
		case ROUND_END:
			log.append("Round is over!\n");
			break;
		}
		final ScrollView logParent = (ScrollView) findViewById(R.id.log_scrollview);
		logParent.post(new Runnable() {
			@Override
			public void run() {
				logParent.fullScroll(View.FOCUS_DOWN);
			}
		});
	}

	private void updateScores(final List<Player> players) {
		scoreboard.setText("");
		for (final Player player : players) {
			scoreboard.append(player + ": " + player.getScore() + "\n");
		}
	}
    
	private void updateHand(Collection<Card> cards) {
		@SuppressWarnings("serial")
		Map<Card, Integer> counterMap = new HashMap<Card, Integer>() {{
			put(Card.GREEN, R.id.greencount);
			put(Card.RED, R.id.redcount);
			put(Card.PURPLE, R.id.purplecount);
			put(Card.YELLOW, R.id.yellowcount);
			put(Card.WILD, R.id.wildcount);
		}};
		
		for (Card cardType : counterMap.keySet()) {
			((TextView) findViewById(counterMap.get(cardType)))
				.setText(((Integer)Collections.frequency(cards, cardType)).toString());
		}
	}
}
