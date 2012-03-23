package com.rachum.amir.cloud9;

public interface GameEventListener {
	public void handleEvent(GameEvent event);
    /*public void roundBegin();
    public void roundEnd();
    public void gameBegin(List<Player> players);
    public void gameEnd(Player player); //TODO: scoreboard parameter
    public void levelBegin(List<Player> players);
    public void levelEnd();
    public void diceRolled(Player pilot, Collection<Card> diceRoll);
    public void move(Player player, Move move);
    public void pay(Player pilot, boolean didPay, Collection<Card> cards);*/
}
