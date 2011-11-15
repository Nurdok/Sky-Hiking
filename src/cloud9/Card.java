package cloud9;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.rachum.amir.util.range.Range;

public class Card {
	private Symbol symbol;

	public Card(final Symbol symbol) {
		super();
		this.symbol = symbol;
	}

	/**
	 * @param symbol the symbol to set
	 */
	public void setSymbol(final Symbol symbol) {
		this.symbol = symbol;
	}
	
	public static List<Card> generateDeck() {
		final List<Card> deck = new LinkedList<Card>();
		for (final int i : new Range(4)) {
			deck.add(new Card(Symbol.WILD));
		}
		for (final int i : new Range(18)) {
			deck.add(new Card(Symbol.RED));
			deck.add(new Card(Symbol.GREEN));
			deck.add(new Card(Symbol.PURPLE));
			deck.add(new Card(Symbol.YELLOW));
		}
		Collections.shuffle(deck);
		return deck;
	}
}
