package tagProject.address.model.beans;

import javafx.beans.property.SimpleStringProperty;

public class FileIndexed {
	   private final SimpleStringProperty title = new SimpleStringProperty("");
	   private final SimpleStringProperty description = new SimpleStringProperty("");
	   private final SimpleStringProperty tags = new SimpleStringProperty("");

	public FileIndexed() {
	        this("", "", "");
	    }
	 
	    public FileIndexed(String title, String description, String tags) {
	        setTitle(title);
	        setDescription(description);
	        setTags(tags);
	    }

	    public String getTitle() {
	        return title.get();
	    }
	 
	    public void setTitle(String fName) {
	        title.set(fName);
	    }
	        
	    public String getDescription() {
	        return description.get();
	    }
	    
	    public void setDescription(String fName) {
	        description.set(fName);
	    }
	    
	    public String getTags() {
	        return tags.get();
	    }
	    
	    public void setTags(String fName) {
	        tags.set(fName);
	    }
	}
