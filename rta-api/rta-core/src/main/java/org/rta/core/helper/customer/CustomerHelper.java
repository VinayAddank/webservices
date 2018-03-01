/**
 * 
 */
package org.rta.core.helper.customer;

import java.util.Collection;
import java.util.stream.Collectors;

import org.rta.core.entity.applicant.AadharEntity;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.AadharModel;
import org.springframework.stereotype.Component;

/**
 * @author arun.verma
 *
 */
@Component
public class CustomerHelper implements BaseConverter<AadharEntity, AadharModel> {

	@Override
	public AadharModel convertToModel(AadharEntity entity) {
		AadharModel model = new AadharModel();
		model.setKSA_KUA_Txn(entity.getKSA_KUA_Txn());
		model.setAuth_date(entity.getAuth_date());
		model.setAuth_status(entity.getAuth_status());
		model.setAuth_transaction_code(entity.getAuth_transaction_code());
		model.setCo(entity.getCo());
		model.setDistrict(entity.getDistrict());
		model.setDistrict_name(entity.getDistrict_name());
		model.setDob(entity.getDob());
		model.setGender(entity.getGender());
		model.setHouse(entity.getHouse());
		model.setLc(entity.getLc());
		model.setMandal(entity.getMandal());
		model.setMandal_name(entity.getMandal_name());
		model.setName(entity.getName());
		model.setPincode(entity.getPincode());
		model.setPo(entity.getPo());
		model.setStatecode(entity.getStatecode());
		model.setStreet(entity.getStreet());
		model.setSubdist(entity.getSubdist());
		model.setUid(Long.valueOf(entity.getUid()));
		model.setVillage(entity.getVillage());
		model.setVillage_name(entity.getVillage_name());
		model.setVtc(entity.getVtc());
		;

		return model;
	}

	@Override
	public AadharEntity convertToEntity(AadharModel model) {
		AadharEntity entity = new AadharEntity();
		entity.setKSA_KUA_Txn(model.getKSA_KUA_Txn());
		entity.setAuth_date(model.getAuth_date());
		entity.setAuth_status(model.getAuth_status());
		entity.setAuth_transaction_code(model.getAuth_transaction_code());
		entity.setCo(model.getCo());
		entity.setDistrict(model.getDistrict());
		entity.setDistrict_name(model.getDistrict_name());
		entity.setDob(model.getDob());
		entity.setGender(model.getGender());
		entity.setHouse(model.getHouse());
		entity.setLc(model.getLc());
		entity.setMandal(model.getMandal());
		entity.setMandal_name(model.getMandal_name());
		entity.setName(model.getName());
		entity.setPincode(model.getPincode());
		entity.setPo(model.getPo());
		entity.setStatecode(model.getStatecode());
		entity.setStreet(model.getStreet());
		entity.setSubdist(model.getSubdist());
		entity.setUid(Long.valueOf(model.getUid()));
		entity.setVillage(model.getVillage());
		entity.setVillage_name(model.getVillage_name());
		entity.setVtc(model.getVtc());

		return entity;
	}

	@Override
	public Collection<AadharModel> convertToModelList(Collection<AadharEntity> source) {
		if (source == null) {
			return null;
		}
		return source.stream().map(s -> convertToModel(s)).collect(Collectors.toList());
	}

	@Override
	public Collection<AadharEntity> convertToEntityList(Collection<AadharModel> source) {
		if (source == null) {
			return null;
		}
		return source.stream().map(s -> convertToEntity(s)).collect(Collectors.toList());
	}


}
