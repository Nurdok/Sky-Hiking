/**
 * 
 */
package com.rachum.amir.skyhiking.android;

import java.util.Arrays;

import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.rachum.amir.skyhiking.Card;
import com.rachum.amir.skyhiking.Game;
import com.rachum.amir.skyhiking.Move;
import com.rachum.amir.skyhiking.MoveHandler;
import com.rachum.amir.skyhiking.PayHandler;
import com.rachum.amir.skyhiking.players.Player;

/**
 * @author Rachum
 *
 */
public class HumanPlayer extends Player {

	private final Handler layoutHandler;
	private final Button stay, leave, pay, payWithWild, dontPay;



	public HumanPlayer(final String name, final Handler layoutHandler, final Button stay,
			final Button leave, final Button pay, final Button payWithWild, final Button dontPay) {
		super(name);
		this.layoutHandler = layoutHandler;
		this.stay = stay;
		this.leave = leave;
		this.pay = pay;
		this.payWithWild = payWithWild;
		this.dontPay = dontPay;
	}

	@Override
	public void play(final MoveHandler moveHandler, final Game game) {
		layoutHandler.post(new Runnable() {
			@Override
			public void run() {
				stay.setEnabled(true);
				leave.setEnabled(true);
				stay.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(final View v) {
						moveHandler.move(Move.STAY);
						disableMoveButtons();
					}
				});

				leave.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(final View v) {
						moveHandler.move(Move.LEAVE);
						disableMoveButtons();
					}
				});
			}
		});

	}

	@Override
	public void pay(final PayHandler handler, final Game context) {
		layoutHandler.post(new Runnable() {
			final boolean canPay = getHand().contains(context.diceRoll);
			final boolean hasWild = getHand().contains(Card.WILD);
            
			@Override
			public void run() {
				if (canPay) {
                    pay.setEnabled(true);
					pay.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(final View v) {
							hand.discard(context.diceRoll);
							handler.pay(true, context.diceRoll);
							disablePayButtons();
						}
					});
				} else {
					dontPay.setEnabled(true);
					dontPay.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(final View v) {
							handler.pay(false, null);
							disablePayButtons();
						}
					});
				}
				if (hasWild) {
					payWithWild.setEnabled(true);
					payWithWild.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(final View v) {
							hand.discard(Arrays.asList(Card.WILD));
							handler.pay(true, Arrays.asList(Card.WILD));
							disablePayButtons();
						}
					});
				}
			}
		});
	}
    
	private void disablePayButtons() {
		pay.setEnabled(false);
		dontPay.setEnabled(false);
		payWithWild.setEnabled(false);
	}
    
	private void disableMoveButtons() {
		stay.setEnabled(false);
		leave.setEnabled(false);
	}

}
