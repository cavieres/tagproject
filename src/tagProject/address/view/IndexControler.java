package tagProject.address.view;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

import eu.hansolo.enzo.notification.Notification.Notifier;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import tagProject.address.MainTag;
import tagProject.address.model.IR;
import tagProject.address.model.beans.IndexDataTable;

public class IndexControler {

	@FXML
	private TableColumn<?, ?> nombreCol;
	@FXML
	private TableColumn<?, ?> ubicacionCol;
	@FXML
	private TableColumn<?, ?> cantDocCol;
	@FXML
	private TableView<IndexDataTable> indicesTbl;
	@FXML
	private TextField rutaTxt;
	@FXML
	private TextField idIndexTxt;
	@FXML
	private Label indiceSeleccionadoLbl;
	@FXML
	private Button crearIndexBtn;
	@FXML
	private AnchorPane mainIndexPane;
	
	private ObservableList<IndexDataTable> dataIndex = FXCollections.observableArrayList();

	@FXML
	private void initialize() {
		
		setTableProperties();
		
		EventosIndex eventosIndex = new EventosIndex();
		
		rutaTxt.setOnMousePressed(eventosIndex.getEventoRuta());
		
		crearIndexBtn.setOnMousePressed(eventosIndex.getEventoCrearIndex());
		
		indicesTbl.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            //Check whether item is selected and set value of selected item to Label
            if (indicesTbl.getSelectionModel().getSelectedItem() != null) {
            	IR.getInstance().setIndexName(newValue.getNombreIndice());
            	indiceSeleccionadoLbl.setText("Indice Seleccionado: " + IR.getInstance().getIndexName());
            	
            }
        });
		
		//Llenar tabla de indices
		llenarTablaIndices();
	}
	

    
    public void setTableProperties(){
    	
    	indicesTbl.setItems(dataIndex);
    	nombreCol.setCellValueFactory(
                new PropertyValueFactory<>("nombreIndice"));
    	ubicacionCol.setCellValueFactory(
                new PropertyValueFactory<>("ubicacion"));
    	cantDocCol.setCellValueFactory(
                new PropertyValueFactory<>("cantidadDocumentos"));
    }
	
	private void llenarTablaIndices(){
		dataIndex.clear();
		//Llenar tabla de indices
		IR ir = IR.getInstance();
		
		HashMap<String, String> results = ir.getAllIndex();
		
		Set<String> keys = results.keySet();
		
		for(String nombreIndice : keys){
			
			String ubicacion = results.get(nombreIndice).split("\\♠")[0];
			String cantidadDocumentos = results.get(nombreIndice).split("\\♠")[1];

			dataIndex.add(new IndexDataTable(new SimpleStringProperty(nombreIndice),
											 new SimpleStringProperty(ubicacion), 
											 new SimpleStringProperty(cantidadDocumentos)));
		}
	}


	private class EventosIndex {

		private EventHandler<Event> getEventoRuta() {

			return new EventHandler<Event>() {

				@Override
				public void handle(Event event) {
					final DirectoryChooser directoryChooser = new DirectoryChooser();
					final File selectedDirectory = directoryChooser.showDialog(MainTag.mainStage);
					if (selectedDirectory != null) {
						rutaTxt.setText(selectedDirectory.getAbsolutePath());
					}

				}

			};
		}

		private EventHandler<Event> getEventoCrearIndex() {

			return new EventHandler<Event>() {

				@Override
				public void handle(Event event) {
					
					IR ir = IR.getInstance();
					
					if(rutaTxt.getText().isEmpty() || idIndexTxt.getText().isEmpty()){
						Notifier.setPopupLocation(null, Pos.CENTER);
						Notifier.INSTANCE.setPopupLifetime(new Duration(200));
						Notifier.INSTANCE.notifyError("Error", "Nombre del indice y ruta son requeridos...");
						return;
					}else{

						HashMap<String, String> results = ir.getAllIndex();
						
						if(results.containsKey(idIndexTxt.getText())){
							Notifier.setPopupLocation(null, Pos.CENTER);
							Notifier.INSTANCE.notifyError("Error", "Ya existe el indice " + idIndexTxt.getText());
							return;	
						}
					}
					
					ir.createIndex(rutaTxt.getText(), idIndexTxt.getText());
					
					//Recalcular tabla indices
					llenarTablaIndices();
					//Borrar datos de los campos de texto
					rutaTxt.clear();
					idIndexTxt.clear();
				}

			};
		}
	}

}
