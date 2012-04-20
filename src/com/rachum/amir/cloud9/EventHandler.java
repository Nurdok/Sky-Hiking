package com.rachum.amir.cloud9;

public class EventHandler {
	private boolean handlingDone = false;
	
	public synchronized void done() {
		handlingDone = true;
		notifyAll();
	}
	
	public synchronized boolean isDone() {
		return handlingDone;
	}
	
	public synchronized void waitUntilDone() {
		while (this.isDone() == false) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
