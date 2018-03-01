package org.rta.core.dao.event.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.event.EventLogDAO;
import org.rta.core.entity.event.EventLogEntity;
import org.springframework.stereotype.Repository;

@Repository
public class EventLogDAOImpl extends BaseDAO<EventLogEntity> implements EventLogDAO {

	 private Criteria criteria = null;
	
	public EventLogDAOImpl(Class<EventLogEntity> persistentClass) {
		super(persistentClass);
		
	}
	public EventLogDAOImpl(){
		super(EventLogEntity.class);
	 }
	
	public List<EventLogEntity>  getPendingNotificationForPr(){
		 criteria = getSession().createCriteria(getPersistentClass());
		 criteria.add(Restrictions.or(
        		 Restrictions.eq("prSmsNotified", false),
        		 Restrictions.eq("prEmailNotified", false),
        		 Restrictions.eq("prAttachement","")
        		 ));
		 List<EventLogEntity> eventLogs= criteria.list();
		 return eventLogs;
	}

	@Override
	public List<EventLogEntity> getPendingNotificationForTr() {
		criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(
				Restrictions.or(Restrictions.eq("trSmsNotified", false),
				Restrictions.eq("trEmailNotified", false),
       		 	Restrictions.eq("trAttachement","")
				));
		List<EventLogEntity> eventLogs = criteria.list();
		return eventLogs;
	}

	public List<Long> getVehicleRcsNotInLogs() {

		String query = "select vehicleRcId from VehicleRcEntity where vehicleRcId not in(select vehicleRcId from EventLogEntity )";
		List<Long> vehicleRcs = (List<Long>) getSession().getNamedQuery(query).list();
		return vehicleRcs;
	}

	public EventLogEntity getVehicleRcsInLog(Long vehicleRcId) {

		criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.eq("vehicleRcId", vehicleRcId));
		EventLogEntity eventLog = (EventLogEntity)criteria.uniqueResult();
		return eventLog;
	}
}
