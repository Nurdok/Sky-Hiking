/**
 * 
 */
package com.rachum.amir.cloud9.android;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.rachum.amir.cloud9.Card;
import com.rachum.amir.cloud9.Game;
import com.rachum.amir.cloud9.GameEventListener;
import com.rachum.amir.cloud9.Move;
import com.rachum.amir.cloud9.Player;
import com.rachum.amir.cloud9.RiskyPlayer;
import com.rachum.amir.util.range.Range;

/**
 * @author Rachum
 *
 */
public class GameActivity extends Activity implements GameEventListener {
    private TextView logView;
    private final boolean continuePressed = false;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        
        final List<Player> players = new LinkedList<Player>();
        for (final int i : new Range(4)) {
        	players.add(new RiskyPlayer("Risky" + i));
        }
        final Game game = new Game(players);
        game.registerListener(this);
        logView = (TextView) findViewById(R.id.log);
        //final Thread thread = new Thsead(game);
        //thread.start();
    }
    
    private void waitForContinuePress() {
        try {
			Thread.sleep(1000);
		} catch (final InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	@Override
	public void roundBegin() {
        logView.setText("New Round Begins!\n");
	}

	@Override
	public void roundEnd() {
        logView.append("Round is over!\n");
        waitForContinuePress();
	}

	@Override
	public void gameBegin(final List<Player> players) {
        logView.setText("Starting a new game!\n");
        logView.append("Players are: ");
        for (final Player player : players) {
        	logView.append(player + " ");
        }
        logView.append("\n");
        waitForContinuePress();
		
	}

	@Override
	public void gameEnd(final Player winner) {
		logView.append("Game is over! the winner is " + winner);
        waitForContinuePress();
	}

	@Override
	public void levelBegin(final List<Player> players) {
        logView.setText("Starting a new level with " + players);
        waitForContinuePress();
	}

	@Override
	public void levelEnd() {
        waitForContinuePress();
	}

	@Override
	public void diceRolled(final Player pilot, final Collection<Card> diceRoll) {
        logView.append(pilot + " rolled " + diceRoll);
	}

	@Override
	public void move(final Player player, final Move move) {
        logView.append(player + " is ");
        if (move == Move.STAY) {
        	logView.append("staying!");
        } else {
        	logView.append("leaving :(");
        }
	}

	@Override
	public void pay(final Player pilot, final boolean didPay, final Collection<Card> cards) {
        logView.append(pilot + " ");
        if (didPay) {
        	logView.append("payed " + cards + " and we are moving on to " +
        			"the next level.");
        }
        else {
            logView.append("didn't have the cards. We're crashing...");
        }
	}
}