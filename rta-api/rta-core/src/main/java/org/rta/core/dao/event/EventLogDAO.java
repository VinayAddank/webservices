package org.rta.core.dao.event;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.docs.AttachmentDetailsEntity;
import org.rta.core.entity.event.EventLogEntity;

public interface EventLogDAO extends GenericDAO<EventLogEntity>{
	
	public List<EventLogEntity>  getPendingNotificationForPr();
	
	public List<EventLogEntity>  getPendingNotificationForTr();
	
	public List<Long> getVehicleRcsNotInLogs();
	
	public EventLogEntity getVehicleRcsInLog(Long vehicleRcId);

}
