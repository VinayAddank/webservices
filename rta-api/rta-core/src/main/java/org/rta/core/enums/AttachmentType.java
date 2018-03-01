package org.rta.core.enums;

public enum AttachmentType {
	
    JPG (1,".jpg"), JPEG(2, ".jpeg"), PNG(3,".png"), PDF(4, ".pdf"); 

    private int id;
    private String label;
    
    private AttachmentType() {}

    private AttachmentType(int id, String label) {
        this.id = id;
        this.label = label;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
