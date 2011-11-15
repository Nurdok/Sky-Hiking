package cloud9;

import java.util.Collection;

public interface Player {
	public String getName();
	public void play(GameState state);
	public void addCards(Collection<Card> cards);
	
}
