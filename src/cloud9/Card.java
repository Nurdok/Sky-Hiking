package cloud9;


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
    
	public Symbol getSymbol() {
		return symbol;
	}
    
	@Override
	public String toString() {
		return symbol.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Card other = (Card) obj;
		if (symbol != other.symbol) {
			return false;
		}
		return true;
	}
    
}
