package tagProject.address.view;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;

public class MainScreenController {

    @FXML
    private Tab idTabIndice;

    @FXML
    private Label idLabelEtiquetados;

    @FXML
    private Button buscarBtn;

    @FXML
    private Tab idTabBusqueda;
	
	@FXML
	private void initialize()
	{
		buscarBtn.setOnMousePressed(new EventosMain().getEventoBuscar());
	}
	
	private class EventosMain
	{
		private EventHandler<Event> getEventoBuscar()
		{
			return new EventHandler<Event>() {
			
				@Override
				public void handle(Event event)
				{
					idLabelEtiquetados.setText("holi");
				}
				
			};
		}
	}
}
