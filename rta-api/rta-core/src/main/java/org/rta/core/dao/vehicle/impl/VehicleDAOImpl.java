package org.rta.core.dao.vehicle.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.vehicle.VehicleDAO;
import org.rta.core.entity.vehicle.VehicleDetailsEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.enums.PRType;
import org.rta.core.enums.RegistrationCategoryType;
import org.rta.core.enums.RegistrationType;
import org.rta.core.enums.Status;
import org.rta.core.enums.UserType;
import org.rta.core.enums.VehicleCategory;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.ObjectsUtil;
import org.rta.core.utils.StringsUtil;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleDAOImpl extends BaseDAO<VehicleRCEntity> implements VehicleDAO {

    private static final Logger log = Logger.getLogger(VehicleDAOImpl.class);
    
    public VehicleDAOImpl() {
        super(VehicleRCEntity.class);
    }

    /*
     * @Override public void update(VehicleEntity entity) {
     * entity.setDealerId(entity.getDealerId());
     * entity.setDealerLocalRTA(entity.getDealerLocalRTA());
     * entity.setNumberChoice(entity.getNumberChoice());
     * entity.setDealerLocalRTA(entity.getDealerLocalRTA());
     * entity.setInvoiceDate(entity.getInvoiceDate());
     * entity.setInvoiceValue(entity.getInvoiceValue());
     * entity.setInvoiceNumber(entity.getInvoiceNumber()); }
     */


    @Override
    public VehicleRCEntity get(Long vehicleRcId) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("vehicleRcId", vehicleRcId));
        return (VehicleRCEntity) criteria.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<VehicleRCEntity> getApplicationStatus(Long userId, String appType, Integer status, Integer regCatId) {
        Criteria criteria = getSession().createCriteria(getPersistentClass(), "vehicleRCEntity");
        if (appType.equalsIgnoreCase(RegistrationType.TR.getLabel())) {
            if (status == Status.AADHAR_PENDING.getValue()) {
                criteria.add(Restrictions.eq("processStatus", Status.AADHAR_PENDING.getValue()));
                criteria.add(Restrictions.eq("trStatus", Status.APPROVED.getValue()));
            } else {
                criteria.add(Restrictions.eq("trStatus", status));
            }
            criteria.add(Restrictions.ne("prStatus", Status.APPROVED.getValue()));
            // criteria.add(Restrictions.in("prStatus", new Integer[] {0, 2}));
        } else if (appType.equalsIgnoreCase(RegistrationType.PR.getLabel()))
            criteria.add(Restrictions.eq("prStatus", status));
        criteria.add(Restrictions.eq("userId.userId", userId));
        if (regCatId != null) {
            criteria.add(Restrictions.eq("regCategory.registrationCategoryId", regCatId));
        }
        return criteria.list();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<VehicleRCEntity> getApplicationStatus4Users(List<Long> userIds, String appType, Integer status,
            Integer regCatId) {
        Criteria criteria = getSession().createCriteria(getPersistentClass(), "vehicleRCEntity");
        if (appType.equalsIgnoreCase(RegistrationType.TR.getLabel())) {
            if(status == Status.AADHAR_PENDING.getValue()){
                criteria.add(Restrictions.eq("processStatus", Status.AADHAR_PENDING.getValue()));
                criteria.add(Restrictions.eq("trStatus", Status.APPROVED.getValue()));
            } else {
                criteria.add(Restrictions.eq("trStatus", status));
            }
            criteria.add(Restrictions.ne("prStatus", Status.APPROVED.getValue()));
            // criteria.add(Restrictions.in("prStatus", new Integer[] {0, 2}));
        }
        else if (appType.equalsIgnoreCase(RegistrationType.PR.getLabel()))
            criteria.add(Restrictions.eq("prStatus", status));
        criteria.add(Restrictions.in("userId.userId", userIds));
        if (regCatId != null) {
            criteria.add(Restrictions.eq("regCategory.registrationCategoryId", regCatId));
        }
        return criteria.list();
    }
    
    @Override
    public Map<String, Object> getApplicationStatusSearchBasis(List<Long> userIds, String appType, Integer status,
            String regCat, Long from, Long to, Integer perPageRecords, Integer pageNumber) {
    	Map<String, Object> mapObject = new HashMap<String, Object>();
        Criteria criteria = getSession().createCriteria(getPersistentClass(), "vehicleRCEntity");
        criteria.add(Restrictions.in("userId.userId", userIds));
        criteria.addOrder(Order.desc("createdOn"));
        if (appType.equalsIgnoreCase(RegistrationType.TR.getLabel())) {
            if(status == Status.AADHAR_PENDING.getValue()){
                criteria.add(Restrictions.eq("processStatus", Status.AADHAR_PENDING.getValue()));
                criteria.add(Restrictions.eq("trStatus", Status.APPROVED.getValue()));
            } else {
                criteria.add(Restrictions.eq("trStatus", status));
            }
            criteria.add(Restrictions.ne("prStatus", Status.APPROVED.getValue()));
            if(status == Status.AADHAR_PENDING.getValue() || status == Status.APPROVED.getValue()){
            	if (!ObjectsUtil.isNull(from) && !ObjectsUtil.isNull(to))
                    criteria.add(Restrictions.between("trIssueTime", from, to));
            }else{
            	if (!ObjectsUtil.isNull(from) && !ObjectsUtil.isNull(to))
                    criteria.add(Restrictions.between("createdOn", from, to));
            }
        }
        else if (appType.equalsIgnoreCase(RegistrationType.PR.getLabel())){
            criteria.add(Restrictions.eq("prStatus", status));
            if(status == Status.APPROVED.getValue()){
            	if (!ObjectsUtil.isNull(from) && !ObjectsUtil.isNull(to))
                    criteria.add(Restrictions.between("prIssueTime", from, to));
            }else{
            	// need to be change after discussion with BA or Abhishek 
            	if (!ObjectsUtil.isNull(from) && !ObjectsUtil.isNull(to))
                    criteria.add(Restrictions.between("createdOn", from, to));
            }
        }
        if (!StringsUtil.isNullOrEmpty(regCat)) {
            criteria.createAlias("vehicleRCEntity.regCategory", "regCat");
            criteria.add(Restrictions.eq("regCat.code", regCat));
        }
        Integer totalRecords = criteria.list().size();
        if (perPageRecords != null && pageNumber != null) {
            criteria.setFirstResult((pageNumber - 1) * perPageRecords);
            criteria.setMaxResults(perPageRecords);
        }
        mapObject.put("vehicleRCEntities", criteria.list());
        mapObject.put("totalRecords", totalRecords);
        return mapObject;
    }

    @Override
    public Long getApplicationCount(Long userId, String type, Status status, Integer regCatId) {

        Criteria criteria = getSession().createCriteria(getPersistentClass());

        if (type.equalsIgnoreCase(RegistrationType.TR.getLabel())) {
            if(status == Status.AADHAR_PENDING){
                criteria.add(Restrictions.eq("processStatus", Status.AADHAR_PENDING.getValue()));
                criteria.add(Restrictions.eq("trStatus", Status.APPROVED.getValue()));
            } else {
                criteria.add(Restrictions.eq("trStatus", status.getValue()));
            }
            criteria.add(Restrictions.ne("prStatus", Status.APPROVED.getValue()));
            // criteria.add(Restrictions.in("prStatus", new Integer[] {0, 2}));
        } 
        else if (type.equalsIgnoreCase(RegistrationType.PR.getLabel()))
            criteria.add(Restrictions.eq("prStatus", status.getValue()));
        criteria.add(Restrictions.eq("userId.userId", userId));
        if (regCatId != null) {
            criteria.add(Restrictions.eq("regCategory.registrationCategoryId", regCatId));
        }
        return (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
    }

    @Override
    public Long getPendingApplicationsCount(Long rtaOfficeId, UserType userRole, Integer regType) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("rtaOfficeId.rtaOfficeId", rtaOfficeId));
        criteria.add(Restrictions.eq("trStatus", Status.APPROVED.getValue()));
        criteria.add(Restrictions.eq("prStatus", Status.PENDING.getValue()));
        
        if (userRole.equals(UserType.ROLE_CCO)) {
            criteria.add(Restrictions.eq("ccoActionStatus", Status.PENDING.getValue()));
        } else if (userRole.equals(UserType.ROLE_MVI)) {
            criteria.add(Restrictions.eq("mviActionStatus", Status.PENDING.getValue()));
        } else if (userRole.equals(UserType.ROLE_AO)) {
            Criterion c1 = Restrictions.eq("ccoActionStatus", Status.APPROVED.getValue());
            Criterion c2 = Restrictions.eq("ccoActionStatus", Status.REJECTED.getValue());
            Criterion c = Restrictions.or(c1, c2);
            Criterion c3 = Restrictions.eq("mviActionStatus", Status.APPROVED.getValue());
            Criterion c4 = Restrictions.eq("mviActionStatus", Status.REJECTED.getValue());
            Criterion d = Restrictions.or(c3, c4);
            Criterion e = Restrictions.eq("aoActionStatus", Status.PENDING.getValue());
            criteria.add(Restrictions.and(c, d, e));
        } else if (userRole.equals(UserType.ROLE_RTO)) {
            Criterion c1 = Restrictions.eq("aoActionStatus", Status.APPROVED.getValue());
            Criterion c2 = Restrictions.eq("aoActionStatus", Status.REJECTED.getValue());
            criteria.add(Restrictions.or(c1, c2));
            criteria.add(Restrictions.eq("rtoActionStatus", Status.PENDING.getValue()));
        }
        if (regType != null) {
            criteria.add(Restrictions.eq("regCategory.registrationCategoryId", regType));
        }
        criteria.add(Restrictions.ne("processStatus", Status.AADHAR_PENDING.getValue()));
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }
    
    @Override
    public VehicleRCEntity getPendingApplication(Long rtaOfficeId, UserType userRole, Integer regType, VehicleCategory vc) {
        //Criteria criteria = getSession().createCriteria(getPersistentClass());
        Criteria criteria = getSession().createCriteria(VehicleRCEntity.class,"vehicleRC");
        criteria.add(Restrictions.eq("rtaOfficeId.rtaOfficeId", rtaOfficeId));
        criteria.add(Restrictions.eq("trStatus", Status.APPROVED.getValue()));
        criteria.add(Restrictions.eq("prStatus", Status.PENDING.getValue()));
        
        if (userRole.equals(UserType.ROLE_CCO)) {
            criteria.add(Restrictions.eq("ccoActionStatus", Status.PENDING.getValue()));
        } else if (userRole.equals(UserType.ROLE_MVI)) {
            criteria.add(Restrictions.eq("mviActionStatus", Status.PENDING.getValue()));
        } else if (userRole.equals(UserType.ROLE_AO)) {
            Criterion c1 = Restrictions.eq("ccoActionStatus", Status.APPROVED.getValue());
            Criterion c2 = Restrictions.eq("ccoActionStatus", Status.REJECTED.getValue());
            Criterion c = Restrictions.or(c1, c2);
            Criterion c3 = Restrictions.eq("mviActionStatus", Status.APPROVED.getValue());
            Criterion c4 = Restrictions.eq("mviActionStatus", Status.REJECTED.getValue());
            Criterion d = Restrictions.or(c3, c4);
            Criterion e = Restrictions.eq("aoActionStatus", Status.PENDING.getValue());
            criteria.add(Restrictions.and(c, d, e));
        } else if (userRole.equals(UserType.ROLE_RTO)) {
            Criterion c1 = Restrictions.eq("aoActionStatus", Status.APPROVED.getValue());
            Criterion c2 = Restrictions.eq("aoActionStatus", Status.REJECTED.getValue());
            criteria.add(Restrictions.or(c1, c2));
            criteria.add(Restrictions.eq("rtoActionStatus", Status.PENDING.getValue()));
        }
        if (regType != null) {
            criteria.add(Restrictions.eq("regCategory.registrationCategoryId", regType));
            if(regType == RegistrationCategoryType.TRANSPORT.getValue() && userRole.equals(UserType.ROLE_MVI) && null != vc) {
                DetachedCriteria vehicleCriteria = DetachedCriteria.forClass(VehicleDetailsEntity.class,"vehicle_details");
                vehicleCriteria.add(Restrictions.eq("vehicle_details.vehicleCategory",vc));
                vehicleCriteria.add(Property.forName("vehicle_details.vehicleRcId.vehicleRcId").eqProperty("vehicleRC.vehicleRcId"));
                criteria.add(Subqueries.exists(vehicleCriteria.setProjection(Projections.property("vehicle_details.vehicleRcId.vehicleRcId"))));
            }
        }
        criteria.add(Restrictions.ne("processStatus", Status.AADHAR_PENDING.getValue()));
        criteria.addOrder(Order.asc("trIssueTime"));
        criteria.setMaxResults(1);
        if(null != criteria.list() && criteria.list().size() > 0) {
            return (VehicleRCEntity) criteria.list().get(0);
        }
        return null;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<VehicleRCEntity> getVehicleRcEntityPending(Long rtaOfficeId, UserType userRole, Integer regCatId) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.ne("prStatus", Status.APPROVED.getValue()));
        criteria.add(Restrictions.eq("rtaOfficeId.rtaOfficeId", rtaOfficeId));
        if (userRole.equals(UserType.ROLE_CCO) || userRole.equals(UserType.ROLE_MVI)) {
            criteria.add(Restrictions.eq("trStatus", Status.APPROVED.getValue()));
            // Pending => processStatus Pending or MVI
            Criterion c1 = Restrictions.eq("processStatus", Status.PENDING.getValue());
            Criterion c2 = Restrictions.eq("processStatus", Status.MVI.getValue());
            Criterion c3 = Restrictions.eq("processStatus", Status.CCO.getValue());
            Criterion c4 = Restrictions.eq("processStatus", Status.CCO_MVI.getValue());
            criteria.add(Restrictions.or(c1, c2, c3, c4));
            criteria.addOrder(Order.asc("trIssueTime"));
        } else if (userRole.equals(UserType.ROLE_AO)) {
            Criterion c1 = Restrictions.eq("processStatus", Status.CCO_MVI.getValue());
            Criterion c2 = Restrictions.eq("processStatus", Status.AO.getValue());
            criteria.add(Restrictions.or(c1, c2));
            criteria.addOrder(Order.asc("trIssueTime"));
        } else if (userRole.equals(UserType.ROLE_RTO)) {
            Criterion c1 = Restrictions.eq("processStatus", Status.AO.getValue());
            Criterion c2 = Restrictions.eq("processStatus", Status.RTO.getValue());
            criteria.add(Restrictions.or(c1, c2));
            criteria.addOrder(Order.asc("trIssueTime"));
        }
        if (regCatId != null) {
            criteria.add(Restrictions.eq("regCategory.registrationCategoryId", regCatId));
        }
        return criteria.list();
    }

    @Override
    public HashMap<String, Object> getApplications(Long rtaOfficeId, Status status, String query, Long from, Long to,
            Integer perPageRecords, Integer pageNumber, Integer regCatId) {
        HashMap<String, Object> mapObject = new HashMap<String, Object>();
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("rtaOfficeId.rtaOfficeId", rtaOfficeId));
        criteria.add(Restrictions.eq("prStatus", status.getValue()));
        if (regCatId != null) {
            criteria.add(Restrictions.eq("regCategory.registrationCategoryId", regCatId));
        }
        if (!ObjectsUtil.isNull(query)) {
            Criterion trNumber = Restrictions.ilike("trNumber", query, MatchMode.ANYWHERE);
            Criterion prNumber = Restrictions.ilike("prNumber", query, MatchMode.ANYWHERE);
            criteria.add(Restrictions.or(trNumber, prNumber));
        }
        if (!ObjectsUtil.isNull(from) && !ObjectsUtil.isNull(to))
            criteria.add(Restrictions.between("trIssueTime", from, to));
        Integer totalRecords = criteria.list().size();
        if (perPageRecords != null && pageNumber != null) {
            criteria.setFirstResult((pageNumber - 1) * perPageRecords);
            criteria.setMaxResults(perPageRecords);
        }
        mapObject.put("VehicleRCEntity", criteria.list());
        mapObject.put("totalRecords", totalRecords);
        return mapObject;
    }
    
    @Override
    public HashMap<String, Object> getUserApplications(Long rtaOfficeId, String query, Long from, Long to, Integer perPageRecords,
            Integer pageNumber) {
        HashMap<String, Object> mapObject = new HashMap<String, Object>();
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("rtaOfficeId.rtaOfficeId", rtaOfficeId));
        if (!ObjectsUtil.isNull(query)) {
            Criterion trNumber = Restrictions.ilike("trNumber", query, MatchMode.ANYWHERE);
            Criterion prNumber = Restrictions.ilike("prNumber", query, MatchMode.ANYWHERE);
            criteria.add(Restrictions.or(trNumber, prNumber));
        }
        if (!ObjectsUtil.isNull(from) && !ObjectsUtil.isNull(to))
            criteria.add(Restrictions.between("trIssueTime", to, from));
        Integer totalRecords = criteria.list().size();
        if (perPageRecords != null && pageNumber != null) {
            criteria.setFirstResult((pageNumber - 1) * perPageRecords);
            criteria.setMaxResults(perPageRecords);
        }
        mapObject.put("VehicleRCEntity", criteria.list());
        mapObject.put("totalRecords", totalRecords);
        return mapObject;
    }
    
    @Override
    public Long countApplications(Long rtaOfficeId, Status status, Integer regCatId) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("rtaOfficeId.rtaOfficeId", rtaOfficeId));
        criteria.add(Restrictions.eq("prStatus", status.getValue()));
        if (regCatId != null) {
            criteria.add(Restrictions.eq("regCategory.registrationCategoryId", regCatId));
        }
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

    @Override
    public VehicleRCEntity getChassisNoByVehicleRc(String ChassisNo) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("chassisNumber", ChassisNo));

        return (VehicleRCEntity) criteria.uniqueResult();
    }

    @Override
    public VehicleRCEntity getVehicleRCByPRNumber(String prNumber) {
        Criteria criteria =
                getSession().createCriteria(getPersistentClass()).add(Restrictions.eq("prNumber", prNumber.toUpperCase()));
        return (VehicleRCEntity) criteria.uniqueResult();
    }
    
    @Override
    public VehicleRCEntity getVehicleRCByTRNumber(String trNumber) {
        Criteria criteria =
                getSession().createCriteria(getPersistentClass()).add(Restrictions.eq("trNumber", trNumber.toUpperCase()));
        return (VehicleRCEntity) criteria.uniqueResult();
    }
    
    @Override
    public VehicleRCEntity getVehicleRCByPRNumber(String prNumber, Long rtaOfficeId, Boolean skipTransport) {
        Criteria criteria =
                getSession().createCriteria(getPersistentClass()).add(Restrictions.eq("prNumber", prNumber))
                        .add(Restrictions.eq("rtaOfficeId.rtaOfficeId", rtaOfficeId));
        if(skipTransport) {
            criteria.add(Restrictions.ne("regCategory.registrationCategoryId", RegistrationCategoryType.TRANSPORT.getValue()));
        }
        return (VehicleRCEntity) criteria.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<VehicleRCEntity> getVehicleRcByFromToDate(Long from, Long to, Long rtaOfficeId, Boolean isPrinted, Boolean skipTransport) {
        Criteria criteria =
                getSession().createCriteria(getPersistentClass())
                        .add(Restrictions.between("prIssueTime", from, to))
                        .add(Restrictions.eq("rtaOfficeId.rtaOfficeId", rtaOfficeId))
                        .add(Restrictions.neOrIsNotNull("prNumber", "")).add(Restrictions.eq("isPrinted", isPrinted));
        if(skipTransport) {
            criteria.add(Restrictions.ne("regCategory.registrationCategoryId", RegistrationCategoryType.TRANSPORT.getValue()));
        }
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<VehicleRCEntity> getVehicleRcByFromToDate(Long from, Long to) {
        Criteria criteria = getSession().createCriteria(getPersistentClass())
                .add(Restrictions.between("prIssueTime", from, to)).add(Restrictions.neOrIsNotNull("prNumber", ""));
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<VehicleRCEntity> getTrDetailsVehicleRc(Long from, Long to) {
        Criteria criteria = getSession().createCriteria(getPersistentClass())
                .add(Restrictions.between("trIssueTime", from, to)).add(Restrictions.neOrIsNotNull("trNumber", ""));
        return criteria.list();
    }

    
    @Override
    public Integer updatePrintStatusAndPrintDate(Map<String, Object> printStatusMap) {

        return getSession()
                .createQuery(
                        "update VehicleRCEntity v set v.isPrinted = :isPrinted, v.rcPrintDate = :rcPrintDate where v.prNumber = :prNumber")
                .setBoolean("isPrinted", true).setLong("rcPrintDate", (Long) printStatusMap.get("rcPrintDate"))
                .setString("prNumber", (String) printStatusMap.get("prNumber")).executeUpdate();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<VehicleRCEntity> getVehicleRCCreatedBtwDate(Long from, Long to) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		if (from != null && from.longValue() > 0l)
			criteria = criteria.add(Restrictions.ge("createdOn", from));
		if (to != null && to.longValue() > 0l)
			criteria = criteria.add(Restrictions.le("createdOn", to));
		return criteria.list();
    }


    @Override
    public Map<String, Object> getPrPendingApplications(Long userId, UserType userType,
            String trNumber, Integer perPageRecords, Integer pageNumber, Integer regCatId) {
        Map<String, Object> map = new HashMap<String, Object>();
        Criteria criteria = getSession().createCriteria(getPersistentClass())
                .add(Restrictions.eq("processStatus", Status.PR_PENDING.getValue()))
                .add(Restrictions.eq("trStatus", Status.APPROVED.getValue()))
                .add(Restrictions.eq("prType", PRType.SPECIAL.getId()));
                
        if (ObjectsUtil.isNull(userType) || (userType != UserType.ROLE_AO && userType != UserType.ROLE_RTO)) {
            log.error("getting pr_pending applications, invalid user role is provided : " + userType);
            map.put("vehicleRCEntity", criteria.list());
            map.put("totalRecords", 0);
            return map;
        }
        
        if (userType == UserType.ROLE_AO) {
            criteria.add(Restrictions.eq("aoUserId.userId", userId));
            criteria.add(Restrictions.eq("aoActionStatus", Status.PR_PENDING.getValue()));
        } else if (userType == UserType.ROLE_RTO) {
            criteria.add(Restrictions.eq("rtoUserId.userId", userId));
            criteria.add(Restrictions.eq("rtoActionStatus", Status.PR_PENDING.getValue()));
        }
        if (regCatId != null) {
            criteria.add(Restrictions.eq("regCategory.registrationCategoryId", regCatId));
        }
        if (!ObjectsUtil.isNull(trNumber)) {
            criteria.add(Restrictions.eq("trNumber", trNumber).ignoreCase());
        }
        Integer totalRecords = criteria.list().size();
        if (perPageRecords != null && pageNumber != null) {
            criteria.setFirstResult((pageNumber - 1) * perPageRecords);
            criteria.setMaxResults(perPageRecords);
        }
        map.put("vehicleRCEntity", criteria.list());
        map.put("totalRecords", totalRecords);
        return map;
    }

    @Override
    public Integer getPrPendingApplicationsCount(Long userId, UserType userType, Integer regCatId) {
        Criteria criteria = getSession().createCriteria(getPersistentClass())
                .add(Restrictions.eq("processStatus", Status.PR_PENDING.getValue()))
                .add(Restrictions.eq("trStatus", Status.APPROVED.getValue()))
                .add(Restrictions.eq("prType", PRType.SPECIAL.getId()));
        if (ObjectsUtil.isNull(userType) || (userType != UserType.ROLE_AO && userType != UserType.ROLE_RTO)) {
            log.error("getting pr_pending applications, invalid user role is provided : " + userType);
            return 0;
        }
        
        if (userType == UserType.ROLE_AO) {
            criteria.add(Restrictions.eq("aoUserId.userId", userId));
            criteria.add(Restrictions.eq("aoActionStatus", Status.PR_PENDING.getValue()));
        } else if (userType == UserType.ROLE_RTO) {
            criteria.add(Restrictions.eq("rtoUserId.userId", userId));
            criteria.add(Restrictions.eq("rtoActionStatus", Status.PR_PENDING.getValue()));
        }
        if (regCatId != null) {
            criteria.add(Restrictions.eq("regCategory.registrationCategoryId", regCatId));
        }
        return criteria.list().size();
    }
    
    
    public VehicleRCEntity vehicleBelongToUserOrItsChild(Long vehicleRc,Long userId){
    	/*VehicleRCEntity vehicleRCEntity=(VehicleRCEntity)getSession().createQuery("select v from VehicleRCEntity v where v.vehicleRcId = :vehicleRc and v.userId "
    			+ "in(select user from DealerEntity where parentId=:dealerId or dealerId=:dealerId)").
    			setParameter("vehicleRc", vehicleRc).
    			setParameter("dealerId", dealerId).uniqueResult();*/
        VehicleRCEntity vehicleRCEntity=(VehicleRCEntity)getSession().createQuery("select v from VehicleRCEntity v where v.vehicleRcId = :vehicleRc and v.userId.userId = :userId").
                setParameter("vehicleRc", vehicleRc).
                setParameter("userId", userId).uniqueResult();
    	return vehicleRCEntity;
    }
	@Override
	public VehicleRCEntity getByPROrTrNumber(String prOrTrNumber) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.disjunction().add(Restrictions.eq("trNumber", prOrTrNumber))
				.add(Restrictions.eq("prNumber", prOrTrNumber)));
		return (VehicleRCEntity) criteria.uniqueResult();
	}
    public VehicleRCEntity getVehicleRc4Pr(String prNumber){
    	  Criteria criteria = getSession().createCriteria(getPersistentClass())
                  .add(Restrictions.eq("prNumber",prNumber));
    	  VehicleRCEntity vehicleRc=(VehicleRCEntity)criteria.uniqueResult();
    	  return vehicleRc;
    
	}
    /*
     * select vrc.* from vehicle_rc as vrc inner join application_slots as slots on vrc.vehicle_rc_id=slots.vehicle_rc_id

where (vrc.prStatus !='approved' and vrc.rta_office_id=:rtaOfficeId) and 

if CCO || MVI -> 
    
    (processStatus in ('PENDING','MVI','CCO','CCO_MVI'))
    
if AO -> 

    (processStatus in ('CCO_MVI','AO'))
    
if RTO -> 

    (processStatus in ('AO','RTO'))
    
order by trIssueTime and 

if (regCatId != null) -> 

    registrationCategoryId=:regCatId and scheduled_time<:currentTime
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<VehicleRCEntity> getVehicleRcEntityPending(Long rtaOfficeId, UserType userRole, Integer regCatId, Long currentTime) {
        StringBuilder queryString = new StringBuilder("SELECT vrc.* FROM vehicle_rc AS vrc INNER JOIN application_slots AS slots ON vrc.vehicle_rc_id=slots.vehicle_rc_id");
        queryString.append(" WHERE (vrc.pr_status!=:prStatus AND vrc.rta_office_id=:rtaOfficeId) AND ");
        List<Integer> processStatus = null;
        if (userRole.equals(UserType.ROLE_CCO) || userRole.equals(UserType.ROLE_MVI)) {
            processStatus = Arrays.asList(Status.PENDING.getValue(),Status.CCO.getValue(),Status.MVI.getValue(),Status.CCO_MVI.getValue());
            queryString.append("(process_status IN (:processStatus))");
        } else if (userRole.equals(UserType.ROLE_AO)) {
            processStatus = Arrays.asList(Status.AO.getValue(),Status.CCO_MVI.getValue());
            queryString.append("(process_status IN (:processStatus))");
        } else if (userRole.equals(UserType.ROLE_RTO)) {
            processStatus = Arrays.asList(Status.AO.getValue(),Status.RTO.getValue());
            queryString.append("(process_status IN (:processStatus))");
        }
        if (!ObjectsUtil.isNull(regCatId)) {
            queryString.append(" AND ").append("reg_category=").append(regCatId).append(" AND ");
        } else {
            queryString.append(" AND ");
        }
        queryString.append("scheduled_time<:currentTime order by tr_issue_time");
        SQLQuery sqlQuery = getSession().createSQLQuery(queryString.toString());
        if (!ObjectsUtil.isNull(processStatus)) {
            sqlQuery.setParameterList("processStatus", processStatus);
        }
        sqlQuery.setParameter("prStatus", Status.APPROVED.getValue());
        sqlQuery.setParameter("currentTime", currentTime);
        sqlQuery.setParameter("rtaOfficeId", rtaOfficeId);
        sqlQuery.addEntity(VehicleRCEntity.class);
        return sqlQuery.list();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<VehicleRCEntity> getByTRApprovedNdFromToDate(Long from, Long to) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria = criteria.add(Restrictions.eq("trStatus", Status.APPROVED.getValue()));
		if (from != null && from.longValue() > 0l){
			criteria = criteria.add(Restrictions.ge("trIssueTime", from));
		}else{
			criteria = criteria.add(Restrictions.ge("trIssueTime", DateUtil.reduceDays(DateUtil.toCurrentUTCTimeStamp(), 1)));
		}
		
		if (to != null && to.longValue() > 0l){
			criteria = criteria.add(Restrictions.le("trIssueTime", to));
		}else{
			criteria = criteria.add(Restrictions.le("trIssueTime", DateUtil.toCurrentUTCTimeStamp()));
		}
		return criteria.list();
    }
    
    @Override
    public VehicleRCEntity getOpenApplicationByUser(Long userId, UserType userRole) {        
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("trStatus", Status.APPROVED.getValue()));
        criteria.add(Restrictions.eq("prStatus", Status.PENDING.getValue()));
        
        if (userRole.equals(UserType.ROLE_CCO)) {
            criteria.add(Restrictions.eq("ccoUserId.userId", userId));
            criteria.add(Restrictions.eq("ccoActionStatus", Status.OPEN.getValue()));
        } else if (userRole.equals(UserType.ROLE_MVI)) {
            criteria.add(Restrictions.eq("mviUserId.userId", userId));
            criteria.add(Restrictions.eq("mviActionStatus", Status.OPEN.getValue()));
        } else if (userRole.equals(UserType.ROLE_AO)) {
            criteria.add(Restrictions.eq("aoUserId.userId", userId));
            criteria.add(Restrictions.eq("aoActionStatus", Status.OPEN.getValue()));
        } else if (userRole.equals(UserType.ROLE_RTO)) {
            criteria.add(Restrictions.eq("rtoUserId.userId", userId));
            criteria.add(Restrictions.eq("rtoActionStatus", Status.OPEN.getValue()));
        }
        criteria.addOrder(Order.asc("trIssueTime"));
        
        return (VehicleRCEntity) criteria.uniqueResult();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<VehicleRCEntity> getPrPendingApplications() {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("trStatus", Status.APPROVED.getValue()));
        criteria.add(Restrictions.eq("prStatus", Status.PENDING.getValue()));
        
        /*criteria.add(Restrictions.isNull("ccoActionStatus"));
        criteria.add(Restrictions.isNull("mviActionStatus"));*/
        
        criteria.addOrder(Order.asc("trIssueTime"));
        return criteria.list();
    }
    @Override
    public List<VehicleRCEntity> getOpenedApplications() {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.gt("modifiedOn", DateUtil.dateSeconds(/*DateUtil.toCurrentUTCTimeStamp()*/1488459391L * 1000)));
        criteria.add(Restrictions.eq("trStatus", Status.APPROVED.getValue()));
        criteria.add(Restrictions.eq("prStatus", Status.PENDING.getValue()));
        criteria.add(Restrictions.or(Restrictions.eq("ccoActionStatus", Status.OPEN.getValue()), Restrictions.eq("mviActionStatus", Status.OPEN.getValue()),
        Restrictions.eq("aoActionStatus", Status.OPEN.getValue()), Restrictions.eq("rtoActionStatus", Status.OPEN.getValue())));
        return (List<VehicleRCEntity>) criteria.list();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<VehicleRCEntity> getSuspensionExpiredVehicle(Long currentTimestamp) {
        String q = new StringBuilder("SELECT vrc.*,svrc.* FROM vehicle_rc AS vrc INNER JOIN suspended_rc_no AS svrc ON vrc.pr_number=svrc.pr_number WHERE svrc.end_date<:timestamp and vrc.pr_status!=:prStatus and svrc.is_revoked=:isRevoked").toString();
        SQLQuery query = getSession().createSQLQuery(q);
        query.setParameter("timestamp", currentTimestamp);
        query.setParameter("prStatus", Status.APPROVED.getValue());
        query.setParameter("isRevoked", Boolean.FALSE);
        query.addEntity(VehicleRCEntity.class);
        return query.list();
    }
    
}
