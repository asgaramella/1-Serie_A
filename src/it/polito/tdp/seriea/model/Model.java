package it.polito.tdp.seriea.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.seriea.db.SerieADAO;

public class Model {
	private SerieADAO dao;
	private List<Season> seasons;
	private Map<Integer,Season> mapSeason;
	private List<Team> teams;
	private Map<String,Team> mapTeam;
	private SimpleDirectedWeightedGraph<Team,DefaultWeightedEdge> graph;
	
	
	

	public Model() {
		super();
		dao=new SerieADAO();
	}
	
	

   public List<Season> getAllSeason() {
		if(seasons==null){
			seasons=dao.listSeasons();
			mapSeason=new HashMap<>();
			for (Season s:seasons){
				
				mapSeason.put(s.getSeason(), s);
			}
		}
		
	    return seasons;
		}
   
   public void creaGrafo(Season s){
	   graph=new SimpleDirectedWeightedGraph<Team,DefaultWeightedEdge>(DefaultWeightedEdge.class);
	   teams=dao.getTeamForSeason(s);
	   mapTeam=new HashMap<>();
	   for(Team t:teams){
		  
		   mapTeam.put(t.getTeam(),t);
	   }
	   
	   Graphs.addAllVertices(graph, teams);
	   
	   for(Match mtemp: dao.getMachForSeason(s,mapTeam,mapSeason)){
		   
		   switch(mtemp.getFtr()){
		   
		   case "H":
			   Graphs.addEdgeWithVertices(graph,mtemp.getHomeTeam(), mtemp.getAwayTeam(), 1);
			   
			   break;
			   
		   case "A":
			   
			   Graphs.addEdgeWithVertices(graph,mtemp.getHomeTeam(), mtemp.getAwayTeam(), -1);
			   
			   break;
			   
			   
		   case "D":
			   
			   Graphs.addEdgeWithVertices(graph,mtemp.getHomeTeam(), mtemp.getAwayTeam(), 0);
			   
		   	   break;
		   
		   
		   }
		   
	   }
	   
   }
   
   
   public List<Stat> getClassificaForSeason(Season s){
	   this.creaGrafo(s);
	   Map<Team,Integer> punteggi=new HashMap<>();
	   for(Team ttemp:graph.vertexSet()){
		   punteggi.put(ttemp,0);
	   }
	   List<Stat> stats=new ArrayList<>();
	   
	   for(DefaultWeightedEdge e:graph.edgeSet()){
		   
		   Team home=graph.getEdgeSource(e);
		   Team away=graph.getEdgeTarget(e);
		   
		   switch((int)graph.getEdgeWeight(e)){
		   case 1:
			   punteggi.put(home, punteggi.get(home)+3);
			   break;
		   case 0:
			   punteggi.put(home, punteggi.get(home)+1);
			   punteggi.put(away, punteggi.get(away)+1);
			   
			   break;
		   case -1:
			   punteggi.put(away, punteggi.get(away)+3);
			   break;
		   
		   }
		   
	   }
	   
	   for(Team ttemp: punteggi.keySet()){
		   stats.add(new Stat(ttemp,punteggi.get(ttemp)));
	   }
	   
	   
	   Collections.sort(stats);
	   return stats;
   }

}
