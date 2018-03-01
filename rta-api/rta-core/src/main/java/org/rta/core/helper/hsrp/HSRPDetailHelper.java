package org.rta.core.helper.hsrp;

import java.util.Collection;
import java.util.stream.Collectors;

import org.rta.core.entity.hsrp.HSRPDetailEntity;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.hsrp.HSRPDetailModel;
import org.rta.core.utils.DateUtil;
import org.springframework.stereotype.Component;

@Component
public class HSRPDetailHelper implements BaseConverter<HSRPDetailEntity, HSRPDetailModel> {

	@Override
	public HSRPDetailModel convertToModel(HSRPDetailEntity source) {
		HSRPDetailModel hsrpDetailModel = new HSRPDetailModel();
		hsrpDetailModel.setVehicleRcId(source.getVehicleRcId().getVehicleRcId());
		hsrpDetailModel.setAffixationCenterCode(source.getAffixationCenterCode());
		hsrpDetailModel.setAuthRefNo(source.getAuthRefNo());
		hsrpDetailModel.setCreatedOn(source.getCreatedOn());
		return hsrpDetailModel;
	}

	@Override
	public HSRPDetailEntity convertToEntity(HSRPDetailModel source) {
		HSRPDetailEntity hsrpDetailEntity = new HSRPDetailEntity();
		hsrpDetailEntity.setTransactionNo(source.getTransactionNo());
		hsrpDetailEntity.setTransactionStatus(source.getTransactionStatus());
		hsrpDetailEntity.setPaymentRecieveDate(DateUtil.dateFormater(source.getPaymentReceivedDate()));
		hsrpDetailEntity.setOrderNo(source.getOrderNo());
		hsrpDetailEntity.setOrderDate(DateUtil.dateFormater(source.getOrderDate()));
		hsrpDetailEntity.setAuthRefNo(source.getAuthRefNo());
		return hsrpDetailEntity;
	}

	@Override
	public Collection<HSRPDetailModel> convertToModelList(Collection<HSRPDetailEntity> source) {
		if (source == null) {
			return null;
		}
		return source.stream().map(s -> convertToModel(s)).collect(Collectors.toList());
	}

	@Override
	public Collection<HSRPDetailEntity> convertToEntityList(Collection<HSRPDetailModel> source) {
		// TODO Auto-generated method stub
		return null;
	}

}
