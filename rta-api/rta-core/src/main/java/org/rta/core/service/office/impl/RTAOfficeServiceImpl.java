/**
 * 
 */
package org.rta.core.service.office.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.rta.core.dao.master.DistrictDAO;
import org.rta.core.dao.master.MandalDAO;
import org.rta.core.dao.master.StateDAO;
import org.rta.core.dao.office.NocAddressDAO;
import org.rta.core.dao.office.RtaOfficeDAO;
import org.rta.core.entity.master.DistrictEntity;
import org.rta.core.entity.master.MandalEntity;
import org.rta.core.entity.office.NocAddressEntity;
import org.rta.core.entity.office.RtaOfficeEntity;
import org.rta.core.helper.office.RTAOfficeHelper;
import org.rta.core.model.office.RTAOfficeModel;
import org.rta.core.service.office.RTAOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shivangi.gupta
 *
 */
@Service("rtaOfficeService")
public class RTAOfficeServiceImpl implements RTAOfficeService {

	@Autowired
	private MandalDAO mandalDAO;

	@Autowired
	private RtaOfficeDAO rtaOfficeDAO;

	@Autowired
	private RTAOfficeHelper rtaOfficeHelper;

	@Autowired
	private NocAddressDAO nocAddressDAO;

	@Autowired
	private DistrictDAO districtDAO;
	
	@Autowired
	private StateDAO stateDAO;

	@Transactional
	public RtaOfficeEntity getRTAOffice4MandalNm(String mandalNm) {

		MandalEntity mandal = mandalDAO.getByMandalName(mandalNm);
		RtaOfficeEntity rtaOfficeEntity = null;
		if (mandal != null && mandal.getRtaOfficeEntity() != null)
			rtaOfficeEntity = mandal.getRtaOfficeEntity();
		return rtaOfficeEntity;
	}

	@Transactional
	@Override
	public List<RTAOfficeModel> getRtaOfficeList(Boolean status) {

		return rtaOfficeHelper.convertEntityListToModelList(rtaOfficeDAO.getRtaOfficeList(status));
	}
	
	@Transactional
    @Override
    public List<RTAOfficeModel> getRtaOfficeListByState(String stateCode,Boolean status, Boolean showUnit) {

	    List<DistrictEntity> stateDistricts = districtDAO.getDistrictsByStateCode(stateCode);
	    List<String> districtCode = stateDistricts.stream().map(dist -> dist.getCode()).collect(Collectors.toList());
        return rtaOfficeHelper.convertEntityListToModelList(rtaOfficeDAO.getRtaOfficeList(districtCode, status, showUnit));
    }

	@Transactional
	@Override
	public RTAOfficeModel getRTAOffice(String code) {
		NocAddressEntity nocAddress = nocAddressDAO.getNocAddressDetailsByRTAOfficeCode(code);
		RTAOfficeModel rtaOfficeModel = rtaOfficeHelper.convertToModel(rtaOfficeDAO.getRtaOfficeDetailsByCode(code));
		if (nocAddress != null) {
			DistrictEntity district = nocAddress.getDistrictId();
			rtaOfficeModel.setAddress(nocAddress.getNocAddress() + ", " + district.getName() + ", "
					+ district.getStateEntity().getName());
		}
		return rtaOfficeModel;
	}

	@Transactional
	@Override
	public List<RTAOfficeModel> getRtaOfficeInfo() {
		List rtaModeloutObj = new ArrayList();
		List<RtaOfficeEntity> rtaOfficeModel=rtaOfficeDAO.getRtaOfficeInfo();
		for (RtaOfficeEntity outObj : rtaOfficeModel) {
			RTAOfficeModel rtaObj = new RTAOfficeModel();
			rtaObj.setCode(outObj.getCode());
			rtaObj.setName(outObj.getName());
			rtaModeloutObj.add(rtaObj);
		}
		return rtaModeloutObj;
	}
}
