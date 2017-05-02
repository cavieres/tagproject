/**
 * Sample Skeleton for 'MainScreen.fxml' Controller Class
 */

package tagProject.address.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;

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

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert idTabIndice != null : "fx:id=\"idTabIndice\" was not injected: check your FXML file 'MainScreen.fxml'.";
        assert idLabelEtiquetados != null : "fx:id=\"idLabelEtiquetados\" was not injected: check your FXML file 'MainScreen.fxml'.";
        assert buscarBtn != null : "fx:id=\"buscarBtn\" was not injected: check your FXML file 'MainScreen.fxml'.";
        assert idTabBusqueda != null : "fx:id=\"idTabBusqueda\" was not injected: check your FXML file 'MainScreen.fxml'.";

        EventosMainScreen eventosMainScreen = new EventosMainScreen();
        
        buscarBtn.setOnMousePressed(eventosMainScreen.getEventoBuscarTags());
        
    }
    
    private class EventosMainScreen {
    	private EventHandler<Event> getEventoBuscarTags() {
    		
    		return new EventHandler<Event>() {
    			
    			@Override
				public void handle(Event event) {
    				idLabelEtiquetados.setText("Etiquetas encontradas:");
    			}
    		};
    		
    	}
    }
}
