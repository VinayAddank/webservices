/**
 * 
 */
package org.rta.core.helper.insurance;

import java.util.Calendar;
import java.util.Collection;
import java.util.stream.Collectors;
import org.rta.core.entity.insurance.InsuranceDetailsEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.enums.Status;
import org.rta.core.helper.BaseConverter;
import org.rta.core.helper.master.InsuranceTypeHelper;
import org.rta.core.model.insurance.IibResponseModel;
import org.rta.core.model.insurance.InsuranceDetailsModel;
import org.rta.core.utils.ObjectsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author arun.verma
 *
 */
@Component
public class InsuranceDetailsHelper implements BaseConverter<InsuranceDetailsEntity, InsuranceDetailsModel> {

    @Autowired
    InsuranceTypeHelper insuranceTypeHelper;

    @Override
    public InsuranceDetailsModel convertToModel(InsuranceDetailsEntity source) {
        if (source == null) {
            return null;
        }
        InsuranceDetailsModel model = new InsuranceDetailsModel();
        model.setInsuranceDtlId(source.getInsuranceDtlId());
        model.setMode(source.getMode());
        model.setProvider(source.getProvider());
        model.setPolicyNo(source.getPolicyNo());
        model.setStartDate(source.getStartDate());
        model.setEndDate(source.getEndDate());
        model.setTenure(source.getTenure());
        model.setStatus(source.getStatus());
        model.setVehicleRcId(String.valueOf(source.getVehicleRcEntity().getVehicleRcId()));
        model.setInsuranceTypeCode(source.getInsuranceTypeEntity().getCode());
        model.setInsuranceTypeName(source.getInsuranceTypeEntity().getName());
        model.setCreatedBy(source.getCreatedBy());
        model.setCreatedOn(source.getCreatedOn());
        model.setModifiedBy(source.getModifiedBy());
        model.setModifiedOn(source.getModifiedOn());
        return model;
    }

    @Override
    public InsuranceDetailsEntity convertToEntity(InsuranceDetailsModel source) {
        //not in use
        return null;
    }
    public InsuranceDetailsEntity convertToEntity(IibResponseModel source, InsuranceDetailsEntity entity){
    	 if (source.getResult() == null) {
             return null;
         }
         if(ObjectsUtil.isNull(entity)){
             entity = new InsuranceDetailsEntity();
             entity.setInsuranceDtlId(Long.parseLong(source.getResult().getPolicyDetailsId()));//.getInsuranceDtlId()
         }
         entity.setMode("Online");//source.getMode()
         entity.setProvider(source.getResult().getInsurerName());
         entity.setPolicyNo(source.getResult().getPolicyNo());
         entity.setStartDate(Long.parseLong(source.getResult().getPolicyStartdate())/1000);
         entity.setEndDate(Long.parseLong(source.getResult().getPolicyEndDate())/1000);
         Calendar cal= Calendar.getInstance();
 		cal.setTimeInMillis(Long.parseLong(source.getResult().getPolicyStartdate()));
 		int startDate=cal.get(Calendar.YEAR);
 		cal.setTimeInMillis(Long.parseLong(source.getResult().getPolicyEndDate()));
 		int endDate=cal.get(Calendar.YEAR);
 		int tenure=endDate-startDate;     
         entity.setTenure(tenure);
         entity.setStatus(Integer.valueOf(source.getResult().getPolicyStatus()));
         entity.setInsuranceTypeEntity(null);
         entity.setVehicleDetailsEntity(null);
         return entity;
    }
    public InsuranceDetailsEntity convertToEntity(InsuranceDetailsModel source, InsuranceDetailsEntity entity) {
        if (source == null) {
            return null;
        }
        if(ObjectsUtil.isNull(entity)){
            entity = new InsuranceDetailsEntity();
            entity.setInsuranceDtlId(source.getInsuranceDtlId());
        }
        entity.setMode(source.getMode());
        entity.setProvider(source.getProvider());
        entity.setPolicyNo(source.getPolicyNo());
        entity.setStartDate(source.getStartDate());
        entity.setEndDate(source.getEndDate());
        entity.setTenure(source.getTenure());
        entity.setStatus(source.getStatus());
        entity.setInsuranceTypeEntity(null);
        entity.setVehicleDetailsEntity(null);
        return entity;
    }
    
    @Override
    public Collection<InsuranceDetailsModel> convertToModelList(Collection<InsuranceDetailsEntity> source) {
        return source.stream().map(s -> convertToModel(s)).collect(Collectors.toList());
    }

    @Override
    public Collection<InsuranceDetailsEntity> convertToEntityList(Collection<InsuranceDetailsModel> source) {
        // TODO Auto-generated method stub
        return null;
    }
    public InsuranceDetailsModel convertIibResModelToInsuranceDetailsModel(IibResponseModel iibResponseModel){
		InsuranceDetailsModel insuranceDetailsModel=null;
		if(iibResponseModel.getResult()!=null){
		insuranceDetailsModel=new InsuranceDetailsModel();
		insuranceDetailsModel.setPolicyNo(iibResponseModel.getResult().getPolicyNo());
		insuranceDetailsModel.setProvider(iibResponseModel.getResult().getInsurerName());
		insuranceDetailsModel.setStartDate(Long.parseLong(iibResponseModel.getResult().getPolicyStartdate())/1000);
		insuranceDetailsModel.setEndDate(Long.parseLong(iibResponseModel.getResult().getPolicyEndDate())/1000);
		insuranceDetailsModel.setMode("Online");
		insuranceDetailsModel.setInsuranceTypeName("OTHER");
		insuranceDetailsModel.setInsuranceTypeCode("OTHER");
		insuranceDetailsModel.setStatus(Status.IIB_VERIFIED.getValue());
		Calendar cal= Calendar.getInstance();
		cal.setTimeInMillis(Long.parseLong(iibResponseModel.getResult().getPolicyStartdate()));
		int startDate=cal.get(Calendar.YEAR);
		cal.setTimeInMillis(Long.parseLong(iibResponseModel.getResult().getPolicyEndDate()));
		int endDate=cal.get(Calendar.YEAR);
		int tenure=endDate-startDate;
		 insuranceDetailsModel.setTenure(tenure);
		 insuranceDetailsModel.setInsuranceDtlId(Long.parseLong(iibResponseModel.getResult().getPolicyDetailsId()));
		}
			return insuranceDetailsModel;
		
	}
}
