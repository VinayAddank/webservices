package org.rta.core.service.certificate.impl;

import javax.transaction.Transactional;

import org.rta.core.dao.certificate.PucDetailsDAO;
import org.rta.core.entity.certificate.PucDetailsEntity;
import org.rta.core.exception.DataMismatchException;
import org.rta.core.exception.NotFoundException;
import org.rta.core.helper.certificate.PucDetailsHelper;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.certificate.PucDetailsModel;
import org.rta.core.service.certificate.PucDetailsService;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.ObjectsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *	@Author sohan.maurya created on Dec 15, 2016.
 */
@Service("pucDetailsService")
public class PucDetailsServiceImpl implements PucDetailsService {

    @Autowired
    private PucDetailsDAO pucDetailsDAO;

    @Autowired
    private PucDetailsHelper pucDetailsHelper;

    @Value("${rta.puc.validity}")
    private Integer pucValidity;

    @Transactional
    @Override
    public PucDetailsModel getPucDetails(Long vehicleRcId) throws NotFoundException {

        return pucDetailsHelper.convertToModel(pucDetailsDAO.getPucDetails(vehicleRcId));
    }

    @Transactional
    @Override
    public SaveUpdateResponse saveOrUpdate(PucDetailsModel model, String userName) throws DataMismatchException {
        PucDetailsEntity entity = pucDetailsDAO.getPucDetails(model.getVehicleRcId());
        String msg="";
        if(ObjectsUtil.isNull(entity)){
            msg = "PUC Details Saved Successfull..!!";
            entity = pucDetailsHelper.convertToEntity(model);
            entity.setCreatedBy(userName);
        }else {
            entity.setCo(model.getCo());
            entity.setHc(model.getHc());
            entity.setkAvg(model.getkAvg());
            entity.setHsu(model.getHsu());
            msg = "PUC Details Update Successfull..!!";
        }
        entity.setIssueDate(DateUtil.toCurrentUTCTimeStamp());
        entity.setExpiryDate(DateUtil.addMonths(DateUtil.toCurrentUTCTimeStamp(), pucValidity));
        entity.setModifiedBy(userName);
        entity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());

        pucDetailsDAO.saveOrUpdate(entity);
        return new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, msg, String.valueOf(model.getVehicleRcId()));
    }

}
