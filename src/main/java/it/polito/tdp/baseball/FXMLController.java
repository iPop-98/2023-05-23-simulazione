package it.polito.tdp.baseball;

import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import java.util.ResourceBundle;


import it.polito.tdp.baseball.model.Model;
import it.polito.tdp.baseball.model.People;
import it.polito.tdp.baseball.model.Range;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnConnesse;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnDreamTeam;

    @FXML
    private Button btnGradoMassimo;

    @FXML
    private TextArea txtResult;

    @FXML
    private TextField txtSalary;

    @FXML
    private TextField txtYear;

    
    
    @FXML
    void doCalcolaConnesse(ActionEvent event) {
    	this.txtResult.clear();
    	if(this.model.isGrafoLoaded()) {
    		this.txtResult.setText("Grafo creato correttamente!\n");
			this.txtResult.appendText("Esso è composto da:\n");
			this.txtResult.appendText("#Vertex = " + this.model.getVertexSize() +"\n");
			this.txtResult.appendText("#Edges  = " + this.model.getEdgesSize() + "\n");
			this.txtResult.appendText("E ci sono " + this.model.calcolaComponentiConnesse() + " componenti connesse \n");
    	} else 
    		this.txtResult.setText("Si prega di inizializzare il grafo prima");
    }

    
    
    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	this.txtResult.clear();
    	Range range = this.model.getRange();
    	this.btnConnesse.setDisable(true);
		this.btnGradoMassimo.setDisable(true);
		this.btnDreamTeam.setDisable(true);
    	
    	try {
    		int anno = Integer.parseInt(this.txtYear.getText());
    		double millions = Double.parseDouble(this.txtSalary.getText());
    		if(anno>= range.getInizio() && anno <= range.getFine() && millions>0) {
    			if(this.model.buildGraph(anno, millions)) {
    				this.txtResult.setText("Grafo creato correttamente!\n");
    				this.txtResult.appendText("Esso è composto da:\n");
    				this.txtResult.appendText("#Vertex = " + this.model.getVertexSize() +"\n");
    				this.txtResult.appendText("#Edges  = " + this.model.getEdgesSize() + "\n");
    				this.btnConnesse.setDisable(false);
    				this.btnGradoMassimo.setDisable(false);
    				this.btnDreamTeam.setDisable(false);
    				
    			}else
    				this.txtResult.setText("ERRORE: non è stato possibile creare un grafo per i valori richiesti.");
    					
    		}
    		else
    			this.txtResult.setText("Prego, inserire un anno tra " + range.getInizio() + " e " + range.getFine() + ". Inserire un valore numerico maggiore di zero per il campo 'Salario'.");
    	}catch (NumberFormatException ne) {
    		ne.printStackTrace();
    		this.txtResult.setText("Errore! Si prega di inserire un valore numerico valido nei campi Anno e Salario.");
    	}catch (RuntimeException rte) {
    		rte.printStackTrace();
    		this.txtResult.setText("Errore di sistema!");
    	}
    	
    }

    
    @FXML
    void doDreamTeam(ActionEvent event) {
    	int anno = Integer.parseInt(this.txtYear.getText());
    	this.txtResult.clear();
    	if(this.model.isGrafoLoaded()) {
    		List<People> dreamTeam = new ArrayList(this.model.getDreamTeam(anno));
    		this.txtResult.setText("Dato il grafo creato, rispetto ai paramentri richiesti, il dream team sarebbe composto dai seguenti " + dreamTeam.size() + " giocatori:\n");
    		for(People p : dreamTeam)
    			this.txtResult.appendText("- " + p +"\n");
    		this.txtResult.appendText("Per un costo complessivo stagionale di $" + this.model.getSalaryDreamTeam() + " a stagione.\n");
    	}else 
    		this.txtResult.setText("Si prega di inizializzare il grafo prima");
    }

    
    @FXML
    void doGradoMassimo(ActionEvent event) {
    	this.txtResult.clear();
    	if(this.model.isGrafoLoaded()) {
    		List<People> max = this.model.getGiocatoreGradoMassimo();
    		if(max.size()>1) {
    			this.txtResult.setText("I giocatori di grado massimo sono:\n ");
    			for(People p : max)
    				this.txtResult.appendText( p.toString() + " di grado " + this.model.getGrado(p));
    		}else {
    			this.txtResult.setText("Il giocatore di grado massimo è " + max.get(0).toString() + "\n");
    			this.txtResult.appendText("Il suo grado di connessione è: " + this.model.getGrado(max.get(0)));
    		}
    	}else 
    		this.txtResult.setText("Si prega di inizializzare il grafo prima");
    }

    
    @FXML
    void initialize() {
        assert btnConnesse != null : "fx:id=\"btnConnesse\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnDreamTeam != null : "fx:id=\"btnDreamTeam\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnGradoMassimo != null : "fx:id=\"btnGradoMassimo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtSalary != null : "fx:id=\"txtSalary\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtYear != null : "fx:id=\"txtYear\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }

}
