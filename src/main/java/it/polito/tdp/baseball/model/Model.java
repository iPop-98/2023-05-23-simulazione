package it.polito.tdp.baseball.model;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.DefaultEdge;

import it.polito.tdp.baseball.db.BaseballDAO;


public class Model {
	
	private BaseballDAO dao;
	private List<People> players;
	private Map<String, People> playerIdMap;
	private Graph<People, DefaultEdge> grafo;
	private List<People> dreamTeam;
	private double salaryDreamTeam;
	

	public Model() {
		super();
		this.dao = new BaseballDAO();
		this.players = new ArrayList<People>();
		this.playerIdMap = new HashMap<String, People>();
		this.dreamTeam = new LinkedList<People>();			//linkedList, così qualora servisse ordinare, l'operazione risulterà un po' più efficiente
		this.salaryDreamTeam = 0;
	}

	public void initializeMap() {
		this.dao.readAllPlayers(playerIdMap);
	}
	
	public boolean buildGraph(int anno, double millions) {
		this.initializeMap();
		this.grafo= new SimpleGraph<People, DefaultEdge>(DefaultEdge.class);
		
		this.players = this.dao.getAllVertex(anno, millions, this.playerIdMap);
		Graphs.addAllVertices(this.grafo, players);
		
		List<Edge> allEdges = this.dao.getAllEdges(anno, this.playerIdMap);
		for(Edge e : allEdges) {
			if(this.grafo.containsVertex(e.getPlayer1()) && this.grafo.containsVertex(e.getPlayer2()))
				this.grafo.addEdge(e.getPlayer1(), e.getPlayer2());
		}
		
		return this.isGrafoLoaded();
	}
	
	public Range getRange() {
		return this.dao.getRangeYear();
	}
	
	public List<People> getGiocatoreGradoMassimo() {
		List<People> best = new ArrayList<>();
		int massimo = 0;
		if(this.isGrafoLoaded())
			for(People p : this.grafo.vertexSet()) {
				int grado= this.getGrado(p);
				if(grado==massimo)
					best.add(p);
				if(grado>massimo) {
					massimo=grado;
					best.clear();
					best.add(p);
				}		
			}
		return best;
	}
	
	public Integer getGrado(People p) {
		return this.grafo.degreeOf(p);
	}
	
	public List<People> getDreamTeam(int anno){
		List<People> parziale = new ArrayList<>();
		this.cerca(parziale, 0, anno);
		return this.dreamTeam;
	}
	
	private void cerca(List<People> parziale, int livello, int anno ) {
		if(livello==this.players.size()) {
			double salary = this.calcolaSalario(parziale, anno);
			if(salary>this.salaryDreamTeam) {
				this.dreamTeam = new LinkedList<People>(parziale);
				this.salaryDreamTeam = salary;
				return;
			}
		}
		for(int i = livello; i<this.players.size(); i++) {
			People player = this.players.get(i);
			if(!this.inSquadra(parziale, player)) {
				parziale.add(player);
				this.cerca(parziale, i+1, anno);
				parziale.remove(parziale.size()-1);
			}
			
		}

	}
		

	private boolean inSquadra(List<People> team, People player) {
		boolean inSquadra = false;
		for(People pl : team)
			if(this.grafo.getEdge(player, pl)!=null)
				inSquadra = true;
		return inSquadra;
	}
	
	private double calcolaSalario(List<People> team, int anno) {
		double salario = 0.0;
		for(People pl: team)
			salario+=pl.getSalary(anno);
		return salario;
	}

	public Integer calcolaComponentiConnesse() {
		ConnectivityInspector<People, DefaultEdge> inspector = new ConnectivityInspector<>(grafo);
		return inspector.connectedSets().size();
			
	}
	
	public Integer calcolaConnessa(People p) {
			
		ConnectivityInspector<People, DefaultEdge> inspector = new ConnectivityInspector<>(grafo);
		Set<People> setConnesso = inspector.connectedSetOf(p);

		return setConnesso.size();
	}


	/**
	 * Metodo di controllo che serve a verificare se il grafo è stato inizializzato
	 * @return true se ha almeno 2 vertici o un arco
	 * @return false altrimenti
	 */
	public boolean isGrafoLoaded() {
		return this.grafo.vertexSet().size()>1;
	}
	
	
	public Integer getVertexSize() {
		// TODO Auto-generated method stub
		return this.grafo.vertexSet().size();
	}

	public Integer getEdgesSize() {
		// TODO Auto-generated method stub
		return this.grafo.edgeSet().size();
	}

	public double getSalaryDreamTeam() {
		
		return this.salaryDreamTeam;
	}
}