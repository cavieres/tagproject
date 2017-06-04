/**
 * Sample Skeleton for 'MainScreen.fxml' Controller Class
 */

package tagProject.address.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import eu.hansolo.enzo.notification.Notification.Notifier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import tagProject.address.model.IR;
import tagProject.address.model.beans.FileIndexed;

public class MainScreenControler {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="idTabIndice"
    private Tab idTabIndice; // Value injected by FXMLLoader

    @FXML // fx:id="idLabelEtiquetados"
    private Label idLabelEtiquetados; // Value injected by FXMLLoader
    
    @FXML
    private Label idLabelDocuments;

    @FXML // fx:id="buscarBtn"
    private Button buscarBtn; // Value injected by FXMLLoader

    @FXML // fx:id="idTabBusqueda"
    private Tab idTabBusqueda; // Value injected by FXMLLoader
    
	@FXML
	private BarChart<Integer, String> tagChart;
    
    @FXML
    private TextField termSearch;
    
    @FXML
    private TableView<FileIndexed> filesTable;
    
    @FXML
	private TableColumn<?, ?> titleCol;
	@FXML
	private TableColumn<?, ?> descriptionCol;
	@FXML
	private TableColumn<?, ?> tagsCol;
    
	public void setTabContent(Node n){
		idTabIndice.setContent(n);
	}

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

        EventosMainScreen eventosMainScreen = new EventosMainScreen();
        
        buscarBtn.setOnMousePressed(eventosMainScreen.getEventoBuscarTags());
        
    }
    
    private List<String> getTagsByTerm(String term){
    	List<String> tags = new ArrayList<String>();
    	
    	tags.add("animales");
    	tags.add("casas");
    	tags.add("ciencia");
    	tags.add("alquimia");
    	
    	return tags;
    }
    
    private void searchTagsByTerm() {
    	idLabelEtiquetados.setText("Etiquetas encontradas:");
		
    	tagChart.getData().clear();
    	
    	XYChart.Series<Integer, String> series = new XYChart.Series<>();
    	
		
		for(String tag: getTagsByTerm(termSearch.getText())) {

			Data<Integer, String> nodo = new XYChart.Data<>((int) (Math.random()*500), tag);
			
			series.getData().add(nodo);
			
		}

		tagChart.getData().add(series);
		
		for (final Data<Integer, String> dt : series.getData()) {
	        final Node n = dt.getNode();
	        n.setStyle("-fx-background-color: #ff6666");//plomo
	        
	        n.setOnMouseClicked(new EventosMainScreen().getEventoChart(dt, series));
	        
		}
    }
    
    private void showFilesByTags(List<String> tagsSelected) {    	
    	
    	ObservableList<FileIndexed> filesIndexed = FXCollections.observableArrayList();
    	
    	filesIndexed = filesTable.getItems();
    	filesTable.setItems(filesIndexed);
    	
    	titleCol.setCellValueFactory(
                new PropertyValueFactory<>("title"));
    	descriptionCol.setCellValueFactory(
                new PropertyValueFactory<>("description"));
    	tagsCol.setCellValueFactory(
                new PropertyValueFactory<>("tags"));
    	
    	filesIndexed.addAll(GetFilesByTags(tagsSelected));
    	
    }
    
private void hideFilesByTags(List<String> tagsSelected) {    	
    	
    	ObservableList<FileIndexed> filesIndexed = FXCollections.observableArrayList();
    	
    	filesIndexed = filesTable.getItems();
    	
    	titleCol.setCellValueFactory(
                new PropertyValueFactory<>("title"));
    	descriptionCol.setCellValueFactory(
                new PropertyValueFactory<>("description"));
    	tagsCol.setCellValueFactory(
                new PropertyValueFactory<>("tags"));
    	
    	for(FileIndexed tag: GetFilesByTags(tagsSelected)) {
    		filesIndexed.removeIf(p -> p.getTags() == tag.getTags());
    	}
    	
    }
    
    private void showFilesByAllTags() {
    	
    	List<String> tagsSelected = getTagsSelected();
    	
    	ObservableList<FileIndexed> filesIndexed = FXCollections.observableArrayList();
    	
    	filesTable.setItems(filesIndexed);
    	
    	titleCol.setCellValueFactory(
                new PropertyValueFactory<>("title"));
    	descriptionCol.setCellValueFactory(
                new PropertyValueFactory<>("description"));
    	tagsCol.setCellValueFactory(
                new PropertyValueFactory<>("tags"));
    	
    	filesIndexed.addAll(GetFilesByTags(tagsSelected));
    }
    
    private List<String> getTagsSelected() {
    	List<String> tagsSelected = new ArrayList<String>();
    	tagsSelected.add("animales");
    	tagsSelected.add("casas");
    	tagsSelected.add("ciencia");
    	
    	return tagsSelected;
    }
    
    private List<FileIndexed> GetFilesByTags(List<String> tags) {
    	
    	List<FileIndexed> files = new ArrayList<FileIndexed>();
    	
    	if (tags.contains("animales")) {
    		files.add(new FileIndexed("peces.pdf", "Diccionario de peces", "animales, aves"));
    	}
    	
    	if (tags.contains("casas")) {
    		files.add(new FileIndexed("parcela.jpg", "La parcela de Los Vilos", "construcción, casas"));
    		files.add(new FileIndexed("casa-papas.jpg", "Casa de mis papas", "vivienda, casas"));
    	}
    	
    	if (tags.contains("ciencia")) {
    		files.add(new FileIndexed("torpedo-prueba-optimizacion.pdf", "Apuntes para pasar el ramo", "materia, pilleria, ciencia"));
    	}
    	
    	
    	
    	return files;
    }
    
    private class EventosMainScreen {
    	private EventHandler<Event> getEventoBuscarTags() {
    		
    		return new EventHandler<Event>() {
    			
    			@Override
				public void handle(Event event) {
    				searchTagsByTerm();
    				//dentro de evento check
    				showFilesByAllTags();
    			}
    		};
    		
    	}

		private EventHandler<MouseEvent> getEventoChart( Data<Integer, String> dt, XYChart.Series<Integer, String> series) {
			
			return new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent e) {
	            	filesTable.getItems().clear();
	            	if(dt.getNode().getStyle().equalsIgnoreCase("-fx-background-color: #ff6666")){////Si ya ha sido precionado
	            		dt.getNode().setStyle("-fx-background-color: #9f9f9f");

	            		
	            	}else if(dt.getNode().getStyle().equalsIgnoreCase("-fx-background-color: #9f9f9f")){ //si no ha sido marcado
	            		
	            		dt.getNode().setStyle("-fx-background-color: #ff6666");

	            	}
	            	
	            	List<String> listaTagsMarcados = new ArrayList<>();
	            	
	        		for(Data<Integer, String> it : tagChart.getData().get(0).getData()) {

	        	        final Node n = it.getNode();
	        	        if(n.getStyle().equalsIgnoreCase("-fx-background-color: #ff6666")){
	        	        	
	        	        	listaTagsMarcados.add(it.getYValue());
	        	        }if(n.getStyle().equalsIgnoreCase("-fx-background-color: #9f9f9f")){
	        	        	listaTagsMarcados.remove(it.getYValue());
	        	        }
	        	        
	        	        
	        		}
	        		
	            	showFilesByTags(listaTagsMarcados);
	            	
					Notifier.setPopupLocation(null, Pos.CENTER);
					Notifier.setWidth(600);
					Notifier.INSTANCE.setPopupLifetime(new Duration(200));
					Notifier.INSTANCE.notifyInfo("Info", "La cantidad de documentos son " + String.valueOf(dt.getXValue()).split("\\.")[0]);
					
	            }
	        };
		}
		
    }
}
