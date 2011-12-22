package cloud9;

import java.util.Collection;

public class RiskyPlayer implements Player {
	private String name;
	private int score;
	private Collection<Card> cards;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Move play(GameState state) {
		return Move.STAY;
	}

	@Override
	public void addCards(Collection<Card> cards) {
		cards.addAll(cards);
	}

	@Override
	public boolean hasCards(Collection<Symbol> diceRoll) {
		cards.containsAll(diceRoll);
		return false;
	}

	@Override
	public void pay(Collection<Symbol> diceRoll, Deck deck) {
		// TODO Auto-generated method stub

	}

	@Override
	public void score(int points) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getScore() {
		// TODO Auto-generated method stub
		return 0;
	}

}
