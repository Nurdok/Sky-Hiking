/**
 * 
 */
package com.rachum.amir.cloud9.android;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.rachum.amir.cloud9.Game;
import com.rachum.amir.cloud9.GameEvent;
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
        game.start();
        logView = (TextView) findViewById(R.id.log);
    }
    
	@Override
	public void handleEvent(final GameEvent event) {
		switch (event.type) {
		case DICE_ROLLED:
			logView.append(event.pilot + " rolled " + event.diceRoll);
		    break;
		case GAME_BEGIN:
            logView.setText("Starting a new game!\n");
            logView.append("Players are: ");
            for (final Player player : event.context.players) {
            	logView.append(player + " ");
            }
            logView.append("\n");
            //TODO: wait
			break;
		case GAME_END:
			logView.append("Game is over! the winner is " + event.winner);
			//TODO: wait
			break;
		case LEVEL_BEGIN:
			logView.setText("Starting a new level with " + event.remainingPlayers);
			//TODO: wait
			break;
		case LEVEL_END:
			//TODO: wait
			break;
		case MOVE:
			logView.append(event.currentPlayer + " is ");
			if (event.move == Move.STAY) {
				logView.append("staying!");
			} else {
				logView.append("leaving :(");
			}
			break;
		case PAY:
			logView.append(event.pilot + " ");
			if (event.didPay) {
				logView.append("payed " + event.cardsPayed + " and we are moving on to " +
				"the next level.");
			}
			else {
				logView.append("didn't have the cards. We're crashing...");
			}
			break;
		case ROUND_BEGIN:
			logView.setText("New Round Begins!\n");
			break;
		case ROUND_END:
			logView.append("Round is over!\n");
            //TODO: wait
			break;
		}
	}

}