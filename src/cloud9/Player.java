package cloud9;

import java.util.Collection;

public interface Player {
	public String getName();
	public void play(GameState state);
	public void addCards(Collection<Card> cards);
	public boolean hasCards(Collection<Symbol> diceRoll);
	public void pay(Collection<Symbol> diceRoll, Deck deck);
	public void score(int points);
	public int getScore();
}
