package org.rta.core.dao.customer.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.customer.CustomerDAO;
import org.rta.core.entity.applicant.CustIndividualDetailsEntity;
import org.rta.core.entity.user.AddressEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.utils.ObjectsUtil;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDAOImpl extends BaseDAO<CustIndividualDetailsEntity> implements CustomerDAO {

    public CustomerDAOImpl() {
        super(CustIndividualDetailsEntity.class);
    }

    public CustIndividualDetailsEntity getByVehicleRcId(long vehicleRcId) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("vehicleRcId.vehicleRcId", vehicleRcId));
        return (CustIndividualDetailsEntity) criteria.uniqueResult();
    }

    @Override
    public VehicleRCEntity getVehicleRcIdUsingAadharNo(long aadhaarNo) {
        Criteria criteria = getSession().createCriteria(VehicleRCEntity.class);
        criteria.add(Restrictions.eq("aadharNo", aadhaarNo));
		criteria.addOrder(Order.desc("modifiedOn"));
		criteria.setMaxResults(1);
        VehicleRCEntity vde = (VehicleRCEntity) criteria.uniqueResult();
        return vde;
    }

    @Override
    public VehicleRCEntity getVehicleRcIdUsingVehicleRcId(String vehicleRcId) {
        Criteria criteria = getSession().createCriteria(VehicleRCEntity.class);
        criteria.add(Restrictions.eq("vehicleRcId", Long.valueOf(vehicleRcId)));
        VehicleRCEntity vde = (VehicleRCEntity) criteria.uniqueResult();
        return vde;
    }

    @Override
    public AddressEntity getAddressDetailsByUserId(Long CustIndDtlId, String addressType) {
        Criteria criteria = getSession().createCriteria(AddressEntity.class);
		criteria.add(Restrictions.eq("userId", Long.valueOf(CustIndDtlId)));
        criteria.add(Restrictions.eq("type", addressType));
        AddressEntity vde = (AddressEntity) criteria.uniqueResult();
        return vde;
    }

	@Override
    public Map<String, Object> getByVehicleRcIds(List<Long> ids, Long from, Long to, String query) {
        Map<String, Object> mapObject = new HashMap<String, Object>();
        Integer totalCustIndiRecords = 0;
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.in("vehicleRcId.vehicleRcId", ids));
        if (!ObjectsUtil.isNull(from) && !ObjectsUtil.isNull(to))
            criteria.add(Restrictions.between("createdOn", from, to));
        if (!ObjectsUtil.isNull(query))
            criteria.add(Restrictions.ilike("firstName", query, MatchMode.ANYWHERE));

        totalCustIndiRecords = criteria.list().size();
        mapObject.put("custIndividualDetailsEntity", criteria.list());
        mapObject.put("totalCustIndiRecords", totalCustIndiRecords);
        return mapObject;
    }

    @Override
    public String getDateOfBirthByVehicleRcId(Long vehicleRcId) {
        Criteria criteria = getSession().createCriteria(getPersistentClass())
                .add(Restrictions.eq("vehicleRcId.vehicleRcId", vehicleRcId))
                .setProjection(Projections.property("dateOfBirth"));
        return (String) criteria.uniqueResult();

    }

    @Override
    public Boolean getIsCustomerDisabled(Long vehicleRcId) {
        CustIndividualDetailsEntity custIndividualDetailsEntity =
                (CustIndividualDetailsEntity) getSession().createCriteria(getPersistentClass())
                .add(Restrictions.eq("vehicleRcId.vehicleRcId", vehicleRcId)).uniqueResult();
        if (ObjectsUtil.isNull(custIndividualDetailsEntity)) {
            return false;
        }
        else if (custIndividualDetailsEntity.getIsDisabled() && custIndividualDetailsEntity.getIsInvalidCarriage()) {
            return true;
        }
        return false;
        
    }

    @Override
    public Boolean getIsCustomerPhysicallyHandicap(Long vehicleRcId) {
        Boolean physicallyHandicap = (Boolean) getSession().createCriteria(getPersistentClass())
                .add(Restrictions.eq("vehicleRcId.vehicleRcId", vehicleRcId))
                .setProjection(Projections.property("isDisabled")).uniqueResult();
        return physicallyHandicap;

    }

}
