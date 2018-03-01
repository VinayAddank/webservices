package org.rta.core.service.certificate.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.rta.core.dao.certificate.NocDetailsDAO;
import org.rta.core.dao.master.DistrictDAO;
import org.rta.core.dao.office.NocAddressDAO;
import org.rta.core.dao.vehicle.VehicleDAO;
import org.rta.core.entity.certificate.NocDetailsEntity;
import org.rta.core.entity.office.NocAddressEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.enums.ServiceType;
import org.rta.core.exception.DataMismatchException;
import org.rta.core.exception.NotFoundException;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.certificate.NocDetailsModel;
import org.rta.core.model.office.RTAOfficeModel;
import org.rta.core.model.user.AddressModel;
import org.rta.core.service.certificate.NocDetailsService;
import org.rta.core.service.vehicle.VehicleService;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.ObjectsUtil;
import org.rta.core.utils.StringsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *	@Author sohan.maurya created on Dec 13, 2016.
 */

@Service("nocDetailsService")
public class NocDetailsServiceImpl implements NocDetailsService {

    @Autowired
    private NocDetailsDAO nocDetailsDAO;

    @Autowired
    private DistrictDAO districtDAO;

    @Autowired
    private NocAddressDAO nocAddressDAO;

    @Autowired
    private VehicleDAO vehicleDAO;

    @Autowired
    private VehicleService vehicleService;

    @Override
    @Transactional
    public NocDetailsModel getNocDetails(Long vehicleRcId, String prNumber) throws NotFoundException {

        NocDetailsEntity entity = null;
        if (StringsUtil.isNullOrEmpty(prNumber)) {
            entity = nocDetailsDAO.getNocDetails(vehicleRcId);
        } else {
            VehicleRCEntity vehicleRCEntity = vehicleDAO.getVehicleRc4Pr(prNumber);
            if (ObjectsUtil.isNull(vehicleRCEntity)) {
                throw new NotFoundException();
            }
            entity = nocDetailsDAO.getNocDetails(vehicleRCEntity.getVehicleRcId());
        }

        if (ObjectsUtil.isNull(entity)) {
            throw new NotFoundException();
        }
        return convertIntoModel(entity);
    }

    @Override
    @Transactional
    public SaveUpdateResponse saveOrUpdate(NocDetailsModel model, String userName) throws DataMismatchException {

        NocDetailsEntity entity = nocDetailsDAO.getNocDetails(model.getVehicleRcId());
        String msg = "";
        if (ObjectsUtil.isNull(entity)) {
            entity = new NocDetailsEntity();
            entity.setAppliedDate(model.getAppliedDate());
            entity.setCancellationDate(model.getCancellationDate());
            entity.setIssueDate(model.getIssueDate());
            entity.setAddressId(nocAddressDAO.getNocAddressDetails(model.getNocAddressCode()));
            entity.setVehicleRcId(vehicleDAO.get(model.getVehicleRcId()));
            entity.setCreatedBy(userName);
            entity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
            msg = "NOC Save Successfully...";
        } else {
            if (ObjectsUtil.isNotNull(model.getAppliedDate())) {
                entity.setAppliedDate(model.getAppliedDate());
            }
            entity.setCancellationDate(model.getCancellationDate());
            if(ServiceType.getServiceType(model.getServiceCode()) == ServiceType.NOC_ISSUE){
                entity.setAddressId(nocAddressDAO.getNocAddressDetails(model.getNocAddressCode()));
            }
            if (ObjectsUtil.isNotNull(model.getIssueDate())) {
                entity.setIssueDate(model.getIssueDate());
            }
            msg = "NOC Update Successfully...";
        }
        entity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
        entity.setStatus(model.getStatus());
        entity.setModifiedBy(userName);
        nocDetailsDAO.saveOrUpdate(entity);
        
        vehicleService.updateRcApproverUserId(model.getActionModelList(), model.getVehicleRcId());
        
        return new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, msg, String.valueOf(model.getVehicleRcId()));
    }

    private NocDetailsModel convertIntoModel(NocDetailsEntity entity) {

        NocDetailsModel model = new NocDetailsModel();
        model.setAppliedDate(entity.getAppliedDate());
        model.setCancellationDate(entity.getCancellationDate());
        model.setIssueDate(entity.getIssueDate());
        model.setStatus(entity.getStatus());
        NocAddressEntity nae = entity.getAddressId();
        model.setNocAddressCode(nae.getNocAddressCode());
        model.setRtaOffice(new RTAOfficeModel());
        model.getRtaOffice().setCode(nae.getRtaOfficeId().getCode());
        model.getRtaOffice().setName(nae.getRtaOfficeId().getName());
        model.setAddress(new AddressModel());
        model.getAddress().setCity(nae.getNocAddress());
        model.getAddress().setDistrictName(nae.getDistrictId().getName());
        model.getAddress().setStateName(nae.getDistrictId().getStateEntity().getName());
        model.getAddress().setCountryName("India");// nae.getDistrictId().getStateEntity().getCountryEntity().getName());
        return model;
    }

    @Override
    @Transactional
    public List<NocDetailsModel> getNocAddressList(String districtCode) throws NotFoundException {

        List<NocAddressEntity> entities = nocAddressDAO.getNocAddressDetails(districtDAO.getDistrictByCode(districtCode).getDistrictId());
        if (ObjectsUtil.isNull(entities)) {
            throw new NotFoundException();
        }
        List<NocDetailsModel> models = new ArrayList<NocDetailsModel>();
        entities.forEach((entity) -> {
            NocDetailsModel model = new NocDetailsModel();
            model.setNocAddressCode(entity.getNocAddressCode());
            model.setAddress(new AddressModel());
            model.getAddress().setCity(entity.getNocAddress());
            model.setRtaOffice(new RTAOfficeModel());
            model.getRtaOffice().setCode(entity.getRtaOfficeId().getCode());
            model.getRtaOffice().setName(entity.getRtaOfficeId().getName());
            models.add(model);
        });
        return models;
    }

    @Override
    @Transactional
    public NocDetailsModel getNocAddressDetails(String code) throws NotFoundException {
       
        NocAddressEntity entity = nocAddressDAO.getNocAddressDetails(code);
        if (ObjectsUtil.isNull(entity)) {
            throw new NotFoundException();
        }
        NocDetailsModel model = new NocDetailsModel();
        model.setNocAddressCode(entity.getNocAddressCode());
        model.setRtaOffice(new RTAOfficeModel());
        model.getRtaOffice().setCode(entity.getRtaOfficeId().getCode());
        model.getRtaOffice().setName(entity.getRtaOfficeId().getName());
        model.setAddress(new AddressModel());
        model.getAddress().setCity(entity.getNocAddress());
        model.getAddress().setDistrictName(entity.getDistrictId().getName());
        model.getAddress().setDistrictCode(entity.getDistrictId().getCode());
        model.getAddress().setStateName(entity.getDistrictId().getStateEntity().getName());
        model.getAddress().setStateCode(entity.getDistrictId().getStateEntity().getCode());
        model.getAddress().setCountryName("India");// entity.getDistrictId().getStateEntity().getCountryEntity().getName());

        return model;
    }
}
