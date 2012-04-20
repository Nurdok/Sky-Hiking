package com.rachum.amir.cloud9;

import java.util.Arrays;
import java.util.List;

public class CloudLevel {
	private final int score;
	private final int diceNumber;

	public CloudLevel(final int score, final int diceNumber) {
		super();
		this.score = score;
		this.diceNumber = diceNumber;
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}
	
	public static List<CloudLevel> gameLevels() {
		return Arrays.asList(new CloudLevel(1, 2), new CloudLevel(2, 2),
							 new CloudLevel(4, 2), new CloudLevel(6, 3),
							 new CloudLevel(9, 3), new CloudLevel(12, 3),
							 new CloudLevel(15, 4), new CloudLevel(20, 4),
							 new CloudLevel(25, 0));
	}

	public int getDiceNumber() {
		return diceNumber;
	}
    
	@Override
	public String toString() {
        return "Level " + this.getScore();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + diceNumber;
		result = prime * result + score;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CloudLevel other = (CloudLevel) obj;
		if (diceNumber != other.diceNumber)
			return false;
		if (score != other.score)
			return false;
		return true;
	}
}
