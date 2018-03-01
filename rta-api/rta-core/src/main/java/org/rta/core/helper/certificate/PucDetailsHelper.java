package org.rta.core.helper.certificate;

import java.util.Collection;

import org.rta.core.dao.vehicle.VehicleDAO;
import org.rta.core.entity.certificate.PucDetailsEntity;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.certificate.PucDetailsModel;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.ObjectsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *	@Author sohan.maurya created on Dec 15, 2016.
 */
@Component
public class PucDetailsHelper implements BaseConverter<PucDetailsEntity, PucDetailsModel> {

	@Autowired
	private VehicleDAO vehicleDAO;
	
    @Override
    public PucDetailsEntity convertToEntity(PucDetailsModel source) {
        if (ObjectsUtil.isNull(source)) {
            return null;
        }
        PucDetailsEntity entity = new PucDetailsEntity();
        entity.setCo(source.getCo());
        entity.setHc(source.getHc());
        entity.setkAvg(source.getkAvg());
        entity.setHsu(source.getHsu());
        entity.setStatus(Boolean.TRUE);
        entity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
        entity.setVehicleRcId(vehicleDAO.get(source.getVehicleRcId()));
        return entity;
    }

    @Override
    public PucDetailsModel convertToModel(PucDetailsEntity source) {
        if (ObjectsUtil.isNull(source)) {
            return null;
        }
        PucDetailsModel model = new PucDetailsModel();
        model.setCo(source.getCo());
        model.setHc(source.getHc());
        model.setkAvg(source.getkAvg());
        model.setHsu(source.getHsu());
        model.setIssueDate(source.getIssueDate());
        model.setExpiryDate(source.getExpiryDate());
        model.setStatus(source.getStatus());
        return model;
    }

    @Override
    public Collection<PucDetailsModel> convertToModelList(Collection<PucDetailsEntity> source) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<PucDetailsEntity> convertToEntityList(Collection<PucDetailsModel> source) {
        // TODO Auto-generated method stub
        return null;
    }


}
