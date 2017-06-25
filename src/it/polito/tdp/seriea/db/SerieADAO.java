package it.polito.tdp.seriea.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.seriea.model.FullMatch;
import it.polito.tdp.seriea.model.Match;
import it.polito.tdp.seriea.model.Season;
import it.polito.tdp.seriea.model.Team;

public class SerieADAO {
	
	public List<Season> listSeasons() {
		String sql = "SELECT season, description FROM seasons" ;
		
		List<Season> result = new ArrayList<>() ;
		
		Connection conn = DBConnect.getConnection() ;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				result.add( new Season(res.getInt("season"), res.getString("description"))) ;
			}
			
			conn.close();
			return result ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Team> listTeams() {
		String sql = "SELECT team FROM teams" ;
		
		List<Team> result = new ArrayList<>() ;
		
		Connection conn = DBConnect.getConnection() ;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				result.add( new Team(res.getString("team"))) ;
			}
			
			conn.close();
			return result ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
    //occhio se avessi già caricato tutti i team allora devi passare mappa
	public List<Team> getTeamForSeason(Season s) {
		String sql = "SELECT DISTINCT m.AwayTeam " + 
				"FROM matches AS m " + 
				"WHERE m.Season=?" ;
		
		List<Team> result = new ArrayList<>() ;
		
		Connection conn = DBConnect.getConnection() ;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, s.getSeason());
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				result.add( new Team(res.getString("m.AwayTeam"))) ;
			}
			
			conn.close();
			return result ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
	}

	public List<Match> getMachForSeason(Season s,Map<String,Team> teams,Map<Integer,Season> seasons) {
		String sql = "SELECT * " + 
				"FROM matches AS m " + 
				"WHERE m.Season=?" ;
		
		List<Match> result = new ArrayList<>() ;
		
		Connection conn = DBConnect.getConnection() ;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, s.getSeason());
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				result.add(new Match(res.getInt("match_id"),seasons.get(res.getInt("Season")),res.getString("Div"),res.getDate("Date").toLocalDate(),teams.get(res.getString("HomeTeam")),teams.get(res.getString("AwayTeam")),res.getInt("FTHG"),res.getInt("FTAG"),res.getString("FTR")));
			}
			
			conn.close();
			return result ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
	}

	
	public List<FullMatch> getFullMachForSeason(Season s,Map<String,Team> teams,Map<Integer,Season> seasons) {
		String sql = "SELECT * " + 
				"FROM matches AS m " + 
				"WHERE m.Season=?" ;
		
		List<FullMatch> result = new ArrayList<>() ;
		
		Connection conn = DBConnect.getConnection() ;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, s.getSeason());
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				result.add(new FullMatch(res.getInt("match_id"),seasons.get(res.getInt("Season")),res.getString("Div"),res.getDate("Date").toLocalDate(),teams.get(res.getString("HomeTeam")),teams.get(res.getString("AwayTeam")),
						res.getInt("FTHG"),res.getInt("FTAG"),res.getString("FTR"),
						res.getInt("HTHG"),res.getInt("HTAG"),res.getString("HTR"),
						res.getInt("HS"),res.getInt("AS"),
						res.getInt("HST"), res.getInt("AST"),
						res.getInt("HF"), res.getInt("AF"),
						res.getInt("HC"), res.getInt("AC"),
						res.getInt("HY"), res.getInt("AY"),
						res.getInt("HR"), res.getInt("AR")
						));
						
							
						
			}
			
			conn.close();
			return result ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
	}
	
	//se le season le hai gia caricate tutte passa mappa, no controlli
	//se le stai caricando team per team ->ARTE 
	public List<Season> getAllSeasonsforTeam(Team t,Map<Integer,Season> seasons ){
		String sql = "SELECT DISTINCT m.Season " + 
				"FROM matches as m " + 
				"WHERE m.HomeTeam=?" ;
		
		List<Season> result = new ArrayList<>() ;
		
		Connection conn = DBConnect.getConnection() ;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			
			st.setString(1, t.getTeam());
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				result.add( seasons.get(res.getInt("m.Season"))) ;
			}
			
			conn.close();
			return result ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	/**
	 *tutte le partite giocate da una squadra in una stagione (38 o 34)
	 **/
	public List<Match> getMachForSeasonAndTeam(Season s,Team t,Map<String,Team> teams,Map<Integer,Season> seasons) {
		String sql = "SELECT * " + 
				"FROM matches AS m " + 
				"WHERE m.Season=? " + 
				"AND ( m.HomeTeam=? OR m.AwayTeam=?)" ;
		
		List<Match> result = new ArrayList<>() ;
		
		Connection conn = DBConnect.getConnection() ;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, s.getSeason());
			st.setString(2, t.getTeam());
			st.setString(3,t.getTeam());
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				result.add(new Match(res.getInt("match_id"),seasons.get(res.getInt("Season")),res.getString("Div"),res.getDate("Date").toLocalDate(),teams.get(res.getString("HomeTeam")),teams.get(res.getString("AwayTeam")),res.getInt("FTHG"),res.getInt("FTAG"),res.getString("FTR")));
			}
			
			conn.close();
			return result ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
	}
	
	
	/**
	 * partite giocate da una squadra in tutte le  stagioni
	 **/
	public List<Match> getAllMachForTeam(Team t,Map<String,Team> teams,Map<Integer,Season> seasons) {
		String sql = "SELECT * " + 
				"FROM matches AS m " + 
				"WHERE ( m.HomeTeam=? OR m.AwayTeam=?)" ;
		
		List<Match> result = new ArrayList<>() ;
		
		Connection conn = DBConnect.getConnection() ;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setString(1, t.getTeam());
			st.setString(2,t.getTeam());
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				result.add(new Match(res.getInt("match_id"),seasons.get(res.getInt("Season")),res.getString("Div"),res.getDate("Date").toLocalDate(),teams.get(res.getString("HomeTeam")),teams.get(res.getString("AwayTeam")),res.getInt("FTHG"),res.getInt("FTAG"),res.getString("FTR")));
			}
			
			conn.close();
			return result ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
	}
	
	/**
	 * tutte le stagioni che hanno giocato insieme due squadre
	 */
	public List<Season> listCommonSeasonsForPairOfTeam(Team t1,Team t2, Map<Integer,Season> seasons) {
		String sql = "SELECT DISTINCT m.Season " + 
				"FROM matches as m " + 
				"WHERE m.HomeTeam=? " + 
				"AND m.AwayTeam=?" ;
		
		List<Season> result = new ArrayList<>() ;
		
		Connection conn = DBConnect.getConnection() ;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			
			st.setString(1, t1.getTeam());
			st.setString(2, t2.getTeam());
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				result.add( seasons.get(res.getInt("m.Season"))) ;
			}
			
			conn.close();
			return result ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	/**
	 * Tutte le partite di due squadre in tutte le stagioni
	 */
	public List<Match> getAllMachForPairOfTeam(Team t1,Team t2,Map<String,Team> teams,Map<Integer,Season> seasons) {
		String sql = "SELECT * " + 
				"FROM matches as ma, matches as mh " + 
				"WHERE (ma.HomeTeam=? or ma.HomeTeam=?) " + 
				"AND (mh.AwayTeam=? or mh.AwayTeam=?) " + 
				"AND ma.match_id=mh.match_id" ;
		
		List<Match> result = new ArrayList<>() ;
		
		Connection conn = DBConnect.getConnection() ;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setString(1, t1.getTeam());
			st.setString(2,t2.getTeam());
			st.setString(3, t2.getTeam());
			st.setString(4, t1.getTeam());
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				result.add(new Match(res.getInt("match_id"),seasons.get(res.getInt("Season")),res.getString("Div"),res.getDate("Date").toLocalDate(),teams.get(res.getString("HomeTeam")),teams.get(res.getString("AwayTeam")),res.getInt("FTHG"),res.getInt("FTAG"),res.getString("FTR")));
			}
			
			conn.close();
			return result ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
	}
	
	/**
	 * nr di partite vinte da team 1 su team 2 in tutte le stagioni
	 */
	public int getNumeroDiPartiteVinteTraDueTeam(Team t1, Team t2){
		String sql ="select count(m.match_id) as cnt " + 
				"from matches as m " + 
				"where ( m.HomeTeam= ? and m.AwayTeam=? and m.FTR='H' ) or ( m.AwayTeam= ? and m.HomeTeam=? and m.FTR='A' )";
		int volte = 0;
		
		Connection conn = DBConnect.getConnection() ;
		
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setString(1, t1.getTeam());
			st.setString(2,t2.getTeam());
			st.setString(3, t1.getTeam());
			st.setString(4, t2.getTeam());
			
			ResultSet res = st.executeQuery() ;
			
			if(res.next()) {
				
				volte = res.getInt("cnt");
				
			}
			
			conn.close();
			return volte ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return volte;
		}	
	}
	
	
	

}
