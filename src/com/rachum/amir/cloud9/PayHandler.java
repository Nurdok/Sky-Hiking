/**
 * 
 */
package com.rachum.amir.cloud9;

import java.util.Collection;
import java.util.concurrent.locks.Condition;

/**
 * @author Rachum
 *
 */
public class PayHandler {
    private final Condition cond;
    private Boolean didPay = null;
	private Collection<Card> cards;
    
    public PayHandler(final Condition cond) {
        this.cond = cond;
	}
	
    void pay(final boolean pay, final Collection<Card> cards) {
    	this.didPay = pay;
        this.cards = cards;
        cond.notifyAll();
    }

	/**
	 * @return the move
	 */
	public Boolean didPay() {
		return didPay;
	}

	/**
	 * @return the cards
	 */
	public Collection<Card> getCards() {
		return cards;
	}

}
