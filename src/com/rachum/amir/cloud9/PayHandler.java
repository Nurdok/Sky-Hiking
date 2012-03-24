/**
 * 
 */
package com.rachum.amir.cloud9;

import java.util.Collection;

/**
 * @author Rachum
 *
 */
public class PayHandler {
    private Boolean didPay = null;
	private Collection<Card> cards;
    
    public synchronized void pay(final boolean pay, final Collection<Card> cards) {
    	this.didPay = pay;
        this.cards = cards;
    }

	/**
	 * @return the move
	 */
	public synchronized Boolean didPay() {
        while (didPay == null) {
        	try {
				wait();
			} catch (final InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		return didPay;
	}

	/**
	 * @return the cards
	 */
	public synchronized Collection<Card> getCards() {
        while (didPay == null) {
        	try {
				wait();
			} catch (final InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		return cards;
	}

}
