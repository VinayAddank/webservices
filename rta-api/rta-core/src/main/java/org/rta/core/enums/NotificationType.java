package org.rta.core.enums;

import org.rta.core.utils.ObjectsUtil;

public enum NotificationType {
	NOTIFICATION_FOR_FINANCER_FRESH("NOTIFICATION FOR PR GEN",5),
	NOTIFICATION_FOR_PR_GEN("NOTIFICATION FOR PR GEN",1),NOTIFICATION_FOR_TR_GEN("NOTIFICATION FOR TR GEN",2)
	,NOTIFICATION_FOR_APP_REJECTION("NOTIFICATION FOR APP REJECTION",3)
	,NOTIFICATION_FOR_UPLOAD_CONSENT("NOTIFICATION FOR UPLOAD CONSENT",4);
	NotificationType(){}
	
	NotificationType( String l,int v){
		value=v;
		label=l;
	}
	int value;
	String label;
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	
	public static NotificationType getNotificationType(Integer value) {
	        if (ObjectsUtil.isNull(value)) 
	            return null;
	            
	        if (value == NOTIFICATION_FOR_PR_GEN.getValue()) 
	                return NOTIFICATION_FOR_PR_GEN;
	                
	        if (value == NOTIFICATION_FOR_TR_GEN.getValue()) 
		                return NOTIFICATION_FOR_TR_GEN;
	        if (value == NOTIFICATION_FOR_APP_REJECTION.getValue()) 
                return NOTIFICATION_FOR_APP_REJECTION;
	        if (value == NOTIFICATION_FOR_UPLOAD_CONSENT.getValue()) 
                return NOTIFICATION_FOR_UPLOAD_CONSENT;
	        return null;
	        
	        }

}
