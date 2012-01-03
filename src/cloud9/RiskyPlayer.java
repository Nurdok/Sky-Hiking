package cloud9;

import java.util.Collection;
import java.util.LinkedList;

public class RiskyPlayer implements Player {
	private final String name;
	public RiskyPlayer(final String name) {
		super();
		this.name = name;
	}

	private int score;
	private final Collection<Card> cards = new LinkedList<Card>();

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Move play(final GameState state) {
		return Move.STAY;
	}

	@Override
	public void addCards(final Collection<Card> cards) {
		this.cards.addAll(cards);
	}

	@Override
	public boolean hasCards(final Collection<Symbol> diceRoll) {
        final Collection<Card> neededCards = new LinkedList<Card>();
        for (final Symbol symbol : diceRoll) {
        	neededCards.add(new Card(symbol));
        }
		if (cards.containsAll(neededCards)) {
			return true;
		} else {
			return cards.contains(new Card(Symbol.WILD));
		}
	}

	@Override
	public void pay(final Collection<Symbol> diceRoll, final Deck deck) {
        for (final Symbol symbol : diceRoll) {
            final Card card = new Card(symbol);
        	cards.remove(card);
        	deck.discard(card);
        }
	}

	@Override
	public void score(final int points) {
        score += points;
	}

	@Override
	public int getScore() {
		return score;
	}
    
	@Override
	public String toString() {
		return getName();
	}

}
