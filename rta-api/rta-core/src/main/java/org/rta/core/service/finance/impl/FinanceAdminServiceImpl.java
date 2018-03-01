package org.rta.core.service.finance.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.rta.core.dao.finance.FinanceBranchDAO;
import org.rta.core.dao.finance.FinanceBranchEmployeeDAO;
import org.rta.core.dao.finance.FinanceYardDAO;
import org.rta.core.dao.master.DistrictDAO;
import org.rta.core.dao.master.MandalDAO;
import org.rta.core.dao.master.StateDAO;
import org.rta.core.dao.user.UserDAO;
import org.rta.core.entity.finance.FinanceBranchEmployeeEntity;
import org.rta.core.entity.finance.FinanceBranchEntity;
import org.rta.core.entity.finance.FinanceYardEntity;
import org.rta.core.entity.master.DistrictEntity;
import org.rta.core.entity.master.MandalEntity;
import org.rta.core.entity.master.StateEntity;
import org.rta.core.entity.user.UserEntity;
import org.rta.core.enums.UserType;
import org.rta.core.model.finance.FinanceBranchModel;
import org.rta.core.model.finance.FinanceYardModel;
import org.rta.core.model.user.AddressModel;
import org.rta.core.service.finance.FinanceAdminService;
import org.rta.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinanceAdminServiceImpl implements FinanceAdminService {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private FinanceBranchDAO branchDAO;

	@Autowired
	private FinanceYardDAO yardDAO;

	@Autowired
	private FinanceBranchEmployeeDAO financeBranchEmpDAO;

	@Autowired
	private MandalDAO mandalDAO;

	@Autowired
	private DistrictDAO districtDAO;

	@Autowired
	private StateDAO stateDAO;

	@Transactional
	public String saveUpdateBranch(FinanceBranchModel model) {
		if (model == null)
			throw new IllegalArgumentException("No data found!!!");
		FinanceBranchEntity branch = null;
		String msg = "";

		if (model.getBranchId() != null && model.getBranchId() >= 0) {
			branch = branchDAO.getEntity(FinanceBranchEntity.class, model.getBranchId());
			if (branch == null)
				throw new IllegalArgumentException("Invalid Branch Id!!!");
			if (!branch.getHeadFinancer().getUserId().equals(model.getHeadFinancerId()))
				throw new IllegalArgumentException("Financier is unAuthorised!!!");
			msg = "updated successfully";
		} else
			msg = "Saved successfully";
		if (branch == null)
			branch = new FinanceBranchEntity();
		UserEntity userEntity = userDAO.findByUserId(model.getHeadFinancerId());
		if (userEntity == null || userEntity.getUserType() != UserType.ROLE_ONLINE_FINANCER)
			throw new IllegalArgumentException("Invalid Head financierId!!!");

		branch.setActiveStatus(model.getActiveStatus());
		if (model.getBranchAddress() == null)
			throw new IllegalArgumentException("Address must be specified!!");
		AddressModel addModel = model.getBranchAddress();
		branch.setBranchAddress((addModel.getDoorNo() != null ? addModel.getStreet() : "") + " "
				+ (addModel.getStreet() != null ? addModel.getStreet() : ""));
		branch.setCity(addModel.getCity());
		branch.setCreatedBy(userEntity.getUserName());
		branch.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
		MandalEntity mandal = mandalDAO.getEntity(MandalEntity.class, addModel.getMandal());
		if (mandal == null)
			throw new IllegalArgumentException("Mandal not found!!");
		DistrictEntity district = districtDAO.getEntity(DistrictEntity.class, addModel.getDistrict());
		if (district == null)
			throw new IllegalArgumentException("district not found!!");
		StateEntity state = stateDAO.getEntity(StateEntity.class, addModel.getState());
		if (state == null)
			throw new IllegalArgumentException("state not found!!");

		branch.setDistrict(district);
		branch.setHeadFinancer(userEntity);
		branch.setMandal(mandal);
		branch.setModifiedBy(userEntity.getUserName());
		branch.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
		branch.setName(model.getBranchName());
		branch.setState(state);
		branchDAO.saveOrUpdate(branch);
		return msg;
	}

	@Transactional
	public List<FinanceBranchModel> getBranchList(Long headUserId, boolean considerStatus, Integer activeStatus) {
		UserEntity user = userDAO.findByUserId(headUserId);
		if (user == null)
			throw new IllegalArgumentException("User not found!!");

		List<FinanceBranchEntity> branches = branchDAO.getAllBranchesForHead(headUserId, considerStatus, activeStatus);
		if (branches == null || branches.isEmpty())
			throw new IllegalArgumentException("No branches created!!!");

		List<FinanceBranchModel> models = new ArrayList<FinanceBranchModel>();
		FinanceBranchModel financeBranchModel = null;
		AddressModel addModel = null;

		for (FinanceBranchEntity branch : branches) {
			financeBranchModel = new FinanceBranchModel();
			financeBranchModel.setActiveStatus(branch.getActiveStatus());
			addModel = new AddressModel();
			addModel.setCity(branch.getCity());
			addModel.setDistrict(branch.getDistrict().getDistrictId());
			addModel.setDistrictName(branch.getDistrict().getName());
			addModel.setState(branch.getState().getStateId());
			addModel.setStateName(branch.getState().getName());
			addModel.setMandal(branch.getMandal().getMandalId());
			addModel.setMandalName(branch.getMandal().getName());
			addModel.setStreet(branch.getBranchAddress() != null ? branch.getBranchAddress() : "");
			financeBranchModel.setBranchAddress(addModel);
			financeBranchModel.setHeadFinancerId(branch.getHeadFinancer().getUserId());
			financeBranchModel.setBranchName(branch.getName());
			financeBranchModel.setBranchId(branch.getId());
			models.add(financeBranchModel);
		}
		return models;
	}

	@Transactional
	public String saveUpdateYard(FinanceYardModel model) {
		if (model == null)
			throw new IllegalArgumentException("No data found!!!");
		FinanceYardEntity yard = null;
		String msg = "";

		if (model.getYardId() != null && model.getYardId() >= 0) {
			yard = yardDAO.getEntity(FinanceYardEntity.class, model.getYardId());
			if (yard == null)
				throw new IllegalArgumentException("Invalid Yard Id!!!");
			if (!yard.getHeadFinancer().getUserId().equals(model.getHeadFinancerId()))
				throw new IllegalArgumentException("Financier is unAuthorised!!!");
			msg = "updated successfully";
		} else
			msg = "Saved successfully";
		if (yard == null)
			yard = new FinanceYardEntity();
		UserEntity userEntity = userDAO.findByUserId(model.getHeadFinancerId());
		if (userEntity == null || userEntity.getUserType() != UserType.ROLE_ONLINE_FINANCER)
			throw new IllegalArgumentException("Invalid Head financierId!!!");

		yard.setActiveStatus(model.getActiveStatus());
		yard.setYardArea(model.getYardArea());
		if (model.getYardAddress() == null)
			throw new IllegalArgumentException("Address must be specified!!");
		AddressModel addModel = model.getYardAddress();
		yard.setYardAddress(addModel.getStreet() != null ? addModel.getStreet() : "");
		yard.setCity(addModel.getCity());
		yard.setCreatedBy(userEntity.getUserName());
		yard.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
		MandalEntity mandal = mandalDAO.getEntity(MandalEntity.class, addModel.getMandal());
		if (mandal == null)
			throw new IllegalArgumentException("Mandal not found!!");
		DistrictEntity district = districtDAO.getEntity(DistrictEntity.class, addModel.getDistrict());
		if (district == null)
			throw new IllegalArgumentException("district not found!!");
		StateEntity state = stateDAO.getEntity(StateEntity.class, addModel.getState());
		if (state == null)
			throw new IllegalArgumentException("state not found!!");

		yard.setDistrict(district);
		yard.setHeadFinancer(userEntity);
		yard.setMandal(mandal);
		yard.setModifiedBy(userEntity.getUserName());
		yard.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
		yard.setName(model.getYardName());
		yard.setState(state);
		yardDAO.saveOrUpdate(yard);
		return msg;
	}

	@Transactional
	public List<FinanceYardModel> getYardList(Long headUserId, boolean considerStatus, Integer activeStatus) {
		UserEntity user = userDAO.findByUserId(headUserId);
		if (user == null)
			throw new IllegalArgumentException("User not found!!");

		List<FinanceYardEntity> yardes = yardDAO.getAllYardsForHead(headUserId, considerStatus, activeStatus);
		if (yardes == null || yardes.isEmpty())
			throw new IllegalArgumentException("No yards created!!!");

		List<FinanceYardModel> models = new ArrayList<FinanceYardModel>();
		FinanceYardModel financeYardModel = null;
		AddressModel addModel = null;

		for (FinanceYardEntity yard : yardes) {
			financeYardModel = new FinanceYardModel();
			financeYardModel.setActiveStatus(yard.getActiveStatus());
			addModel = new AddressModel();
			addModel.setCity(yard.getCity());
			addModel.setDistrict(yard.getDistrict().getDistrictId());
			addModel.setDistrictName(yard.getDistrict().getName());
			addModel.setState(yard.getState().getStateId());
			addModel.setStateName(yard.getState().getName());
			addModel.setMandal(yard.getMandal().getMandalId());
			addModel.setMandalName(yard.getMandal().getName());
			addModel.setStreet(yard.getYardAddress() != null ? yard.getYardAddress() : "");
			financeYardModel.setYardAddress(addModel);
			financeYardModel.setHeadFinancerId(yard.getHeadFinancer().getUserId());
			financeYardModel.setYardName(yard.getName());
			financeYardModel.setYardId(yard.getFinanceYardId());
			models.add(financeYardModel);
		}
		return models;
	}

}
