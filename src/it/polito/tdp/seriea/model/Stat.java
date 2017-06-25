package it.polito.tdp.seriea.model;

public class Stat implements Comparable<Stat>{
	
	private Team team;
	private int punteggio;
	public Stat(Team team, int punteggio) {
		super();
		this.team = team;
		this.punteggio = punteggio;
	}
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	public int getPunteggio() {
		return punteggio;
	}
	public void setPunteggio(int punteggio) {
		this.punteggio = punteggio;
	}
	@Override
	public int compareTo(Stat other) {
		
		return -(this.punteggio-other.punteggio);
	}
	
	
	

}
