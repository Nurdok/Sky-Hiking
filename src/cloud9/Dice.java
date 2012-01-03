package cloud9;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.rachum.amir.util.range.Range;

public class Dice {
	public static Symbol roll() {
		final List<Symbol> cubeSides = Arrays.asList(Symbol.NONE, Symbol.NONE,
									   Symbol.GREEN, Symbol.RED,
									   Symbol.PURPLE, Symbol.YELLOW);
		
		Collections.shuffle(cubeSides);
		return cubeSides.get(0);
	}
	
	public static Collection<Symbol> roll(final int numOfDice) {
		final List<Symbol> rollResults = new LinkedList<Symbol>();
		for (final int i : new Range(numOfDice)) {
            final Symbol symbol = roll();
            if (symbol != Symbol.NONE) {
    			rollResults.add(symbol);
            }
		}
		return rollResults;
	}
}
