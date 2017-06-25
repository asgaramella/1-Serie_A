package it.polito.tdp.seriea.model;

import java.time.LocalDate;

public class FullMatch {
	
	private int id ;
	private Season season ;
	private String div ;
	private LocalDate date ;
	private Team homeTeam ;
	private Team awayTeam ;
	private int fthg ; //full results
	private int ftag ; 
	private String ftr ; 
	private int hthg;// risultati metà partita
	private int htag;
	private String htr;
	private int hs; // tiri
	private int as;
	private int hst; //tiri in porta
	private int ast;
	private int hf; //falli commessi
	private int af;
	private int hc; //calci d'angolo
	private int ac;
	private int hy; // cartellini gialli
	private int ay;
	private int hr; //cartellini rossi
	private int ar;
	
	public FullMatch(int id, Season season, String div, LocalDate date, Team homeTeam, Team awayTeam, int fthg,
			int ftag, String ftr, int hthg, int htag, String htr, int hs, int as, int hst, int ast, int hf, int af,
			int hc, int ac, int hy, int ay, int hr, int ar) {
		super();
		this.id = id;
		this.season = season;
		this.div = div;
		this.date = date;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.fthg = fthg;
		this.ftag = ftag;
		this.ftr = ftr;
		this.hthg = hthg;
		this.htag = htag;
		this.htr = htr;
		this.hs = hs;
		this.as = as;
		this.hst = hst;
		this.ast = ast;
		this.hf = hf;
		this.af = af;
		this.hc = hc;
		this.ac = ac;
		this.hy = hy;
		this.ay = ay;
		this.hr = hr;
		this.ar = ar;
	}
	
	
	
	

}
