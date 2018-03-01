/**
 * 
 */
package org.rta.core.dao.insurance.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.insurance.InsuranceDetailsDAO;
import org.rta.core.entity.insurance.InsuranceDetailsEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.springframework.stereotype.Repository;

/**
 * @author arun.verma
 *
 */
@Repository
public class InsuranceDetailsDAOImpl extends BaseDAO<InsuranceDetailsEntity> implements InsuranceDetailsDAO {

    public InsuranceDetailsDAOImpl() {
        super(InsuranceDetailsEntity.class);
    }

    @Override
    public Long getInsuranceDtlsIdByVehicleRcId(Long vehicleRcId) {
        Criteria criteria = getSession().createCriteria(getPersistentClass())
                .add(Restrictions.eq("vehicleRcEntity.vehicleRcId", vehicleRcId))
                .setProjection(
                        Projections.projectionList().add(Projections.property("insuranceDtlId"), "insuranceDtlId"))
                .setResultTransformer(Transformers.aliasToBean(InsuranceDetailsEntity.class));
        InsuranceDetailsEntity entity = (InsuranceDetailsEntity) criteria.uniqueResult();
        if (entity == null) {
            return null;
        }
        return entity.getInsuranceDtlId();
    }

    @Override
    public InsuranceDetailsEntity getInsuranceDtlsEntityByVehicleRcId(Long vehicleRcId) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("vehicleRcEntity.vehicleRcId", vehicleRcId));
        return (InsuranceDetailsEntity) criteria.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<InsuranceDetailsEntity> getInsuranceDtlsEntitiesByVehicleRCEntities(
            List<VehicleRCEntity> vehicleRCEntities) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.in("vehicleRcEntity", vehicleRCEntities));
        return criteria.list();
    }

    @Override
    public InsuranceDetailsEntity getInsuranceDtlsByPolicyNo(String policyNo) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("policyNo", policyNo));
        return (InsuranceDetailsEntity) criteria.uniqueResult();
    }

}
