/**
 * Sample Skeleton for 'MainScreen.fxml' Controller Class
 */

package tagProject.address.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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

    @FXML // fx:id="buscarBtn"
    private Button buscarBtn; // Value injected by FXMLLoader

    @FXML // fx:id="idTabBusqueda"
    private Tab idTabBusqueda; // Value injected by FXMLLoader
    
    @FXML
    private TreeView tagsTree;
    
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
    
    

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert idTabIndice != null : "fx:id=\"idTabIndice\" was not injected: check your FXML file 'MainScreen.fxml'.";
        assert idLabelEtiquetados != null : "fx:id=\"idLabelEtiquetados\" was not injected: check your FXML file 'MainScreen.fxml'.";
        assert buscarBtn != null : "fx:id=\"buscarBtn\" was not injected: check your FXML file 'MainScreen.fxml'.";
        assert idTabBusqueda != null : "fx:id=\"idTabBusqueda\" was not injected: check your FXML file 'MainScreen.fxml'.";

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
		
		tagsTree.setCellFactory(CheckBoxTreeCell.<String>forTreeView()); // Set list with checkboxes.
		
		CheckBoxTreeItem<String> tagsFound = new CheckBoxTreeItem<String>("Resultado búsqueda");
		tagsFound.setExpanded(true);
		
		for(String tag: getTagsByTerm(termSearch.getText())) {
			tagsFound.getChildren().add(new CheckBoxTreeItem<String>(tag));    					
		}
		
		tagsTree.setRoot(tagsFound);
		tagsTree.setEditable(true);
		tagsTree.setShowRoot(true);
    }
    
    private void showFilesByTags() {
    	
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
    				
    				showFilesByTags();
    			}
    		};
    		
    	}
    }
}
