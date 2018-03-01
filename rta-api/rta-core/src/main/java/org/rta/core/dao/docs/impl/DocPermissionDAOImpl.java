package org.rta.core.dao.docs.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.docs.DocPermissionDAO;
import org.rta.core.entity.docs.DocPermissionEntity;
import org.springframework.stereotype.Repository;

/**
 * @Author sohan.maurya created on Aug 25, 2016.
 */
@Repository("docPermissionDAO")
public class DocPermissionDAOImpl extends BaseDAO<DocPermissionEntity> implements DocPermissionDAO {

    public DocPermissionDAOImpl() {
        super(DocPermissionEntity.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DocPermissionEntity> getExactlyDocTypes(Integer ownershipType, Integer regCategory,
            Integer vehicleClass) throws HibernateException {
        List<DocPermissionEntity> docPermissionEntities = new ArrayList<DocPermissionEntity>();
       
            Query query = getSession()
                    .createQuery(
                            "from DocPermissionEntity d where d.ownershipType in ( 100, :ownershipType ) and d.registrationCategoryType in ( 100, :regCategory )"
                                    + " and d.vehicleClass in ( 100, :vehicleClass ) order by d.docTypesEntity")
                    .setParameter("ownershipType", ownershipType).setParameter("regCategory", regCategory)
             .setParameter("vehicleClass", vehicleClass);

        docPermissionEntities = query.list();
        
        return docPermissionEntities;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> getMandatoryDocTypesIds(Integer ownershipType, Integer regCategory, Integer vehicleClass)
            throws HibernateException {
        List<Integer> docTypesIds = new ArrayList<Integer>();

        Query query = getSession()
                .createQuery(
                        "select docTypesEntity.docTypeId from DocPermissionEntity d where d.ownershipType in ( 100, :ownershipType ) and d.registrationCategoryType in ( 100, :regCategory )"
                                + " and d.vehicleClass in ( 100, :vehicleClass ) and d.isMandatory=:isMandatory")
                .setParameter("ownershipType", ownershipType).setParameter("regCategory", regCategory)
                .setParameter("vehicleClass", vehicleClass).setParameter("isMandatory", true);

        docTypesIds = query.list();

        return docTypesIds;
    }


}
