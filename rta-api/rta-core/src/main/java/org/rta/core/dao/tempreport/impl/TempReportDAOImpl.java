/**
 * 
 */
package org.rta.core.dao.tempreport.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.tempreport.TempReportDAO;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.enums.Status;
import org.springframework.stereotype.Repository;

/**
 * @author arun.verma
 *
 */

@Repository
public class TempReportDAOImpl extends BaseDAO<VehicleRCEntity> implements TempReportDAO {

    TempReportDAOImpl() {
        super(VehicleRCEntity.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> getByDate4TR(Long from, Long to, Status status) {
        SQLQuery query = getSession().createSQLQuery(
                "select count(vehicle_rc_id) as release_count, to_timestamp(tr_issue_time)\\:\\:timestamp\\:\\:date, rta.name from vehicle_rc rc inner join rta_office rta on rc.rta_office_id = rta.rta_office_id where tr_status= :status and tr_issue_time >= :from and tr_issue_time < :to group by to_timestamp(tr_issue_time)\\:\\:timestamp\\:\\:date, rta.name");
        query.setInteger("status", status.getValue());
        query.setLong("from", from);
        query.setLong("to", to);
        return query.list();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> getByDate4PR(Long from, Long to, Status status) {
        SQLQuery query = getSession().createSQLQuery(
                "select count(vehicle_rc_id) as release_count, to_timestamp(pr_issue_time)\\:\\:timestamp\\:\\:date, rta.name from vehicle_rc rc inner join rta_office rta on rc.rta_office_id = rta.rta_office_id where pr_status= :status and pr_issue_time >= :from and pr_issue_time < :to group by to_timestamp(pr_issue_time)\\:\\:timestamp\\:\\:date, rta.name");
        query.setInteger("status", status.getValue());
        query.setLong("from", from);
        query.setLong("to", to);
        return query.list();
    }
    
    
//    
    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> getByDateFromHist(Long from, Long to, Status status) {
        SQLQuery query = getSession().createSQLQuery(
                "select count(his.vehicle_rc_id) as release_count, to_timestamp(his.created_on)\\:\\:timestamp\\:\\:date, rta.name  from vehicle_rc_history his inner join vehicle_rc v on v.vehicle_rc_id=his.vehicle_rc_id inner join rta_office rta on v.rta_office_id = rta.rta_office_id where  his.created_on between :from and :to and his.status = :status group by to_timestamp(his.created_on)\\:\\:timestamp\\:\\:date, rta.name;");
        query.setInteger("status", status.getValue());
        query.setLong("from", from);
        query.setLong("to", to);
        return query.list();
    }
    
    
    @Override
    public List<Object[]> getRejHistory(Long from, Long to) {
    	
        SQLQuery query = getSession().createSQLQuery("select concat(u.first_name, u.last_name) as officer,"
        		+ " u.user_type , d.name as district,o.code as office_code ,o.name as office_name, rej.vehicle_rc_id , "
        		+ "to_timestamp(rej.created_on),rej.title Document_Title, rej.comment as Comment from rejection_history rej inner join users u on rej.user_id=u.user_id"
        		+ " inner join rta_employee r on u.user_id=r.user_id inner join rta_office o on "
        		+ "r.rta_office_id=o.rta_office_id inner join district d on o.district_code=d.code where rej.modified_on between :from and :to order by rej.vehicle_rc_id,d.code asc");
        query.setParameter("from", from).setParameter("to", to);	       
        return query.list();
        	        }
    
}
