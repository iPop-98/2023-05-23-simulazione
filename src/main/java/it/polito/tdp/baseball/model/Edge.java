package it.polito.tdp.baseball.model;

import java.util.Objects;

public class Edge {
	
	private People player1;
	private People player2;
	
	
	public Edge(People player1, People player2) {
		super();
		this.player1 = player1;
		this.player2 = player2;
	}


	public People getPlayer1() {
		return player1;
	}


	public People getPlayer2() {
		return player2;
	}


	@Override
	public int hashCode() {
		return Objects.hash(player1, player2);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge other = (Edge) obj;
		return Objects.equals(player1, other.player1) && Objects.equals(player2, other.player2);
	}


	@Override
	public String toString() {
		return "Player1= " + player1 + ", Player2 = " + player2;
	}
	
	

}
