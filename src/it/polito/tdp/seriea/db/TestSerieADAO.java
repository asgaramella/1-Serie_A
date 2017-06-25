package it.polito.tdp.seriea.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.seriea.model.FullMatch;
import it.polito.tdp.seriea.model.Match;
import it.polito.tdp.seriea.model.Season;
import it.polito.tdp.seriea.model.Team;

public class TestSerieADAO {

	public static void main(String[] args) {
		SerieADAO dao = new SerieADAO() ;
		
		List<Season> seasons = dao.listSeasons() ;
		Map<Integer,Season> mapSeason=new HashMap<>();
		for(Season stemp: seasons){
			mapSeason.put(stemp.getSeason(), stemp);
		}
		System.out.println(seasons);
		System.out.println(mapSeason);
		
		List<Team> teams = dao.listTeams() ;
		Map<String,Team> mapTeam=new HashMap<>();
		for(Team t:teams){
			mapTeam.put(t.getTeam(), t);
		}
		System.out.println(teams);
		System.out.println(mapTeam);
		
		
		List<FullMatch> matches= dao.getFullMachForSeason(seasons.get(0), mapTeam, mapSeason);
		System.out.println(matches.size());
		
		List<Season> ses=dao.getAllSeasonsforTeam(mapTeam.get("Juventus"),mapSeason);
		System.out.println(ses);
		
		List<Match> mat=dao.getMachForSeasonAndTeam(seasons.get(0), mapTeam.get("Juventus"), mapTeam, mapSeason);
		System.out.println(mat.size());
		
		List<Match> ma=dao.getAllMachForTeam(mapTeam.get("Inter"), mapTeam, mapSeason);
		System.out.println(ma.size());
		
		List<Season> se=dao.listCommonSeasonsForPairOfTeam(mapTeam.get("Inter"), mapTeam.get("Juventus"),mapSeason);
		System.out.println(se.size());
		
		List<Match> m=dao.getAllMachForPairOfTeam(mapTeam.get("Inter"),mapTeam.get("Juventus") , mapTeam, mapSeason);
		System.out.println(m.size());
		
		System.out.println(dao.getNumeroDiPartiteVinteTraDueTeam(mapTeam.get("Pescara"), mapTeam.get("Juventus")));
		

	}

}
