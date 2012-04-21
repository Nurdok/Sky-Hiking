package com.rachum.amir.skyhiking;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.rachum.amir.util.range.Range;

public class Dice {
	public static Card roll() {
		final List<Card> cubeSides = Arrays.asList(null, null,
									   Card.GREEN, Card.RED,
									   Card.PURPLE, Card.YELLOW);
		
		Collections.shuffle(cubeSides);
		return cubeSides.get(0);
	}
	
	public static Collection<Card> roll(final int numOfDice) {
		final Collection<Card> rollResults = new LinkedList<Card>();
		for (final int i : new Range(numOfDice)) {
            final Card card = roll();
            if (card != null) {
    			rollResults.add(card);
            }
		}
		return rollResults;
	}
}
