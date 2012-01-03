/**
 * 
 */
package cloud9;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.rachum.amir.util.range.Range;

/**
 * @author Rachum
 *
 */
public class Deck {
    private final List<Card> cards;
    private final List<Card> discard;
    
	public Deck() {
		cards = new LinkedList<Card>();
		discard = new LinkedList<Card>();
		for (final int i : new Range(4)) {
			cards.add(new Card(Symbol.WILD));
		}
		for (final int i : new Range(18)) {
			cards.add(new Card(Symbol.RED));
			cards.add(new Card(Symbol.GREEN));
			cards.add(new Card(Symbol.PURPLE));
			cards.add(new Card(Symbol.YELLOW));
		}
		Collections.shuffle(cards);
	}
	
	public void discard(final Card card) {
        discard.add(card);
	}
    
	public Collection<Card> draw(final int numOfCards) {
        final Collection<Card> cardsDrawn = new LinkedList<Card>();
        for (final int i : new Range(numOfCards)) {
            if (cards.isEmpty()) {
                cards.addAll(discard);
                discard.clear();
        		Collections.shuffle(cards);
                assert(cards.size() + discard.size() == 76);
            }
        	cardsDrawn.add(cards.remove(0));
		}
        System.out.println("Current deck: " + this);
        return cardsDrawn;
	}
    
	@Override
	public String toString() {
        final Map<Card, Integer> display = new HashMap<Card, Integer>();
		for (final Symbol symbol : Symbol.values()) {
			final Card card = new Card(symbol);
			final int count = Collections.frequency(cards, card);
			display.put(card, count);
		}
        return display.toString();
	}
}
