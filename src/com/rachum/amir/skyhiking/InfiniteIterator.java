package com.rachum.amir.skyhiking;

import java.util.Collection;
import java.util.Iterator;

import com.rachum.amir.skyhiking.players.Player;

public class InfiniteIterator implements Iterator<Player> {
	private final Collection<Player> players;
	private Iterator<Player> iterator;
	
	public InfiniteIterator(final Collection<Player> players) {
		this.players = players;
		this.iterator = players.iterator();
	}

	@Override
	public boolean hasNext() {
		return true;
	}

	@Override
	public Player next() {
		if (!iterator.hasNext()) {
			iterator = players.iterator();
		}
		return iterator.next();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
