package it.polito.tdp.baseball.model;

public class Range {
	
	private int inizio;
	private int fine;
	
	
	public Range(int inizio, int fine) {
		super();
		this.inizio = inizio;
		this.fine = fine;
	}


	public int getInizio() {
		return inizio;
	}


	public int getFine() {
		return fine;
	}


	@Override
	public String toString() {
		return "Range: " + inizio + " - " + fine;
	}
	
	

}
