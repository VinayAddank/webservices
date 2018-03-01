package org.rta.core.helper.finance;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.rta.core.entity.finance.FinanceDetailsEntity;
import org.rta.core.entity.finance.FinancerMasterEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.finance.FinanceModel;
import org.rta.core.model.finance.FinancerModel;
import org.springframework.stereotype.Component;

@Component
public class FinanceHelper implements BaseConverter<FinanceDetailsEntity, FinanceModel> {

    @Override
    public FinanceModel convertToModel(FinanceDetailsEntity entity) {
        FinanceModel model = new FinanceModel();
        if (entity == null) {
            model.setIsFinance(false);
            return model;
        }
        model.setIsFinance(true);
        model.setCity(entity.getCity());
        model.setDateOfAgreement(entity.getDateOfAgreement());
        model.setDistrict(entity.getDistrictEntity() == null ? "" : entity.getDistrictEntity().getCode());
        model.setFinanceApprovalStatus(entity.getFinanceApprStatus());
        // entity.setFinancerMId(financerMId);
        model.setFinancerMode(entity.getFinancerMode());
        model.setMandal(entity.getMandalEntity() == null ? 0 : entity.getMandalEntity().getCode());
        model.setName(entity.getName());
        model.setState(entity.getStateEntity() == null ? "" : entity.getStateEntity().getCode());
        model.setVehicleRcId(
                entity.getVehicleRcId() == null ? "" : entity.getVehicleRcId().getVehicleRcId().toString());
        model.setDistrcitName(entity.getDistrictEntity()==null?"":entity.getDistrictEntity().getName());
        model.setMandalName(entity.getMandalEntity()==null?"":entity.getMandalEntity().getName());
        model.setStateName(entity.getStateEntity()==null?"":entity.getStateEntity().getName());
        return model;
    }

    @Override
    public FinanceDetailsEntity convertToEntity(FinanceModel model) {
        FinanceDetailsEntity entity = new FinanceDetailsEntity();
        entity.setCity(model.getCity());
        entity.setDateOfAgreement(model.getDateOfAgreement());
        entity.setFinanceApprStatus(model.getFinanceApprovalStatus());
        // entity.setFinancerMId(financerMId);
        entity.setFinancerMode(model.getFinancerMode());

        entity.setName(model.getName());
        
        //commented for compatibility with version
        
        /*VehicleRCEntity vehicle = new VehicleRCEntity();
        try {
            vehicle.setVehicleRcId(Long.valueOf(model.getVehicleRcId()));
        } catch (NumberFormatException e) {
            vehicle.setVehicleRcId(null);
        }
        entity.setVehicleRcId(vehicle);*/
        return entity;
    }

    @Override
    public Collection<FinanceModel> convertToModelList(Collection<FinanceDetailsEntity> source) {
        return source.stream().map(s -> convertToModel(s)).collect(Collectors.toList());
    }

    @Override
    public Collection<FinanceDetailsEntity> convertToEntityList(Collection<FinanceModel> source) {
        //return source.stream().map(s -> convertToEntity(s)).collect(Collectors.toList());
        return null;
    }

    public FinancerModel convertFinancerEntityToFinancerModel(FinancerMasterEntity source) {
        FinancerModel model = new FinancerModel();
        model.setFinancerId(source.getId());
        model.setFinancerName(source.getName());
        return model;
    }

    public List<FinancerModel> convertFinancerEntityToFinancerModelList(Collection<FinancerMasterEntity> source) {
        return source.stream().map(s -> convertFinancerEntityToFinancerModel(s)).collect(Collectors.toList());
    }

    public void addFinanceModelToFinancerDetails(FinanceDetailsEntity entity, FinanceModel model) {

        if (entity == null)
            entity = new FinanceDetailsEntity();
        entity.setCity(model.getCity());
        entity.setDateOfAgreement(model.getDateOfAgreement());
        entity.setFinancerMId(model.getFinancerId());
        entity.setFinanceApprStatus(model.getFinanceApprovalStatus());
        entity.setFinancerMode(model.getFinancerMode());
        entity.setName(model.getName());
    }

    public FinanceModel addfinanceDtlsEntityToFinanceModel(FinanceDetailsEntity entity, FinanceModel model) {
        model.setCity(entity.getCity());
        model.setDateOfAgreement(entity.getDateOfAgreement());
        model.setDistrict(entity.getDistrictEntity() == null ? "" : entity.getDistrictEntity().getCode());
        model.setFinanceApprovalStatus(entity.getFinanceApprStatus());
        // entity.setFinancerMId(financerMId);
        model.setFinancerMode(entity.getFinancerMode());
        model.setMandal(entity.getMandalEntity() == null ? 0 : entity.getMandalEntity().getCode());
        model.setName(entity.getName());
        model.setState(entity.getStateEntity() == null ? "" : entity.getStateEntity().getCode());
        model.setVehicleRcId(
                entity.getVehicleRcId() == null ? "" : entity.getVehicleRcId().getVehicleRcId().toString());
        return model;
    }

    public FinancerMasterEntity convertToFinancerEntity(FinanceModel model) {
        FinancerMasterEntity entity = new FinancerMasterEntity();
        entity.setUserId(model.getUserId());
        return entity;
    }

    // public FinanceDetailsEntity addfinancerModelToFinanceDetailsEntity(FinanceModel model) {
    // FinanceDetailsEntity entity = new FinanceDetailsEntity();
    // entity.setApplication(model.getApplicationId());
    // entity.setFinancerId(model.getFinancerId());
    // entity.setDateOfAgreement(model.getDateOfAgreement());
    // entity.setName(model.getFinancerNm());
    // entity.setStatus(model.getStatus());
    // return entity;
    // }

}
