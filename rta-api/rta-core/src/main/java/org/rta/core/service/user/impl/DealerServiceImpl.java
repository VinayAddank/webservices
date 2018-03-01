package org.rta.core.service.user.impl;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.rta.core.dao.master.DistrictDAO;
import org.rta.core.dao.master.MandalDAO;
import org.rta.core.dao.master.StateDAO;
import org.rta.core.dao.user.AddressDAO;
import org.rta.core.dao.user.DealerDAO;
import org.rta.core.entity.master.DistrictEntity;
import org.rta.core.entity.master.MandalEntity;
import org.rta.core.entity.master.StateEntity;
import org.rta.core.entity.office.RtaOfficeEntity;
import org.rta.core.entity.user.AddressEntity;
import org.rta.core.entity.user.DealerEntity;
import org.rta.core.entity.user.UserEntity;
import org.rta.core.enums.UserType;
import org.rta.core.helper.user.DealerHelper;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.user.DealerModel;
import org.rta.core.service.user.DealerService;
import org.rta.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

@Service
public class DealerServiceImpl implements DealerService {

	private static final Logger logger = Logger.getLogger(DealerServiceImpl.class);
	
	@Autowired
	private DealerDAO dealerDAO;

	@Autowired
	private AddressDAO addressDao;

	@Autowired
	private StateDAO stateDao;

	@Autowired
	private DistrictDAO distDao;
	
	@Autowired
	private MandalDAO mandalDAO;

	@Autowired
	private DealerHelper dealerHelper;

	@Override
	@Transactional
	public DealerModel findByUserName(String userName) {
		DealerEntity dealerEntity = dealerDAO.findByUserName(userName);
        DealerModel dm = dealerHelper.convertToModel(dealerEntity);
        if (null != dm) {
            dm.setShowRoomName(dealerEntity.getName());
            dm.setFax(dealerEntity.getFax());
            dm.setParentId(dealerEntity.getParentId());
            AddressEntity address = addressDao.findByUserIdAndType(dealerEntity.getUser().getUserId(), "T");
            if (null != address) {
                dm.setDoorNo(address.getDoorNo());
                dm.setStreet(address.getStreet());
                dm.setCity(address.getCity());
                StateEntity state = stateDao.getEntity(StateEntity.class, address.getState());
                dm.setState(state != null ? state.getName() : "");
                DistrictEntity dist = distDao.getEntity(DistrictEntity.class, address.getDistrict());
                dm.setDistrict(dist != null ? dist.getName() : "");
                MandalEntity me = address.getMandal();
                if (me != null) {
                    dm.setMandalId(me.getCode());
                    dm.setMandalName(me.getName());
                    RtaOfficeEntity roe;
                    dm.setRtaOffice((roe = me.getRtaOfficeEntity()) != null ? roe.getName() : "");
                }
            }
            return dm;
        }
        return null;
	}

	@Override
	@Transactional
	public DealerModel findByUserId(Long id) {
		DealerEntity dealerEntity = dealerDAO.findByUserId(id);
		DealerModel dm = dealerHelper.convertToModel(dealerEntity);
		if (null != dm) {
			dm.setShowRoomName(dealerEntity.getName());
			dm.setFax(dealerEntity.getFax());
			dm.setParentId(dealerEntity.getParentId());
			AddressEntity address = addressDao.findByUserIdAndType(id, "T");
			if (null != address) {
				dm.setDoorNo(address.getDoorNo());
				dm.setStreet(address.getStreet());
				dm.setCity(address.getCity());
				StateEntity state = stateDao.getEntity(StateEntity.class, address.getState());
				dm.setState(state != null ? state.getName() : "");
				DistrictEntity dist = distDao.getEntity(DistrictEntity.class, address.getDistrict());
				dm.setDistrict(dist != null ? dist.getName() : "");
				MandalEntity me = address.getMandal();
				if (me != null) {
					dm.setMandalId(me.getCode());
					dm.setMandalName(me.getName());
					RtaOfficeEntity roe;
					dm.setRtaOffice((roe = me.getRtaOfficeEntity()) != null ? roe.getName() : "");
				}
			}
			return dm;
		}
		return null;
	}

	@Override
	@Transactional
	public DealerModel findByUserIdForPay(Long id) {
		DealerEntity dealerEntity = dealerDAO.findByUserId(id);
		return dealerHelper.convertToModel(dealerEntity);
	}

	@Override
    @Transactional
    public DealerModel findDealerByUserId(Long id) {
        DealerEntity dealerEntity = dealerDAO.findByUserId(id);
        return dealerHelper.convertToModel(dealerEntity);
    }
	
	@Transactional
	@Override
	public SaveUpdateResponse saveOrUpdate(DealerModel model, String createrUserName) throws DataIntegrityViolationException,InvalidDataAccessApiUsageException{
		
		DealerEntity dealerEntity = dealerHelper.convertToEntity(model);
	
		UserEntity userEntity= dealerHelper.getUserEntity(model);
		userEntity.setCreatedBy(createrUserName);
		userEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
		userEntity.setModifiedBy(createrUserName);
		userEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
		dealerEntity.setUser(userEntity);
		dealerDAO.saveOrUpdate(dealerEntity);
		 
		AddressEntity addressEntity= getAddressEntity(model, createrUserName);
		addressEntity.setUserId(dealerEntity.getUser().getUserId());
		addressDao.saveOrUpdate(addressEntity);
		
		return new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, "Registration Successfully Completed", model.getUserName());
	}
	
	private AddressEntity getAddressEntity(DealerModel model, String userName){
		
		AddressEntity addressEntity = new AddressEntity();
		addressEntity.setType("T");
		addressEntity.setStatus(true);
		addressEntity.setCreatedBy(userName);
		addressEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
		addressEntity.setDoorNo(model.getDoorNo());
		addressEntity.setStreet(model.getStreet());
		addressEntity.setUserType(UserType.getUserType(model.getUserType()));
		addressEntity.setCity(model.getCity());
		addressEntity.setCountry(Long.valueOf(1)); // India always, will change in future        
		addressEntity.setState(stateDao.getStateByCode(model.getStateCode()).getStateId());
		addressEntity.setDistrict(distDao.getDistrictByCode(model.getDistrictCode()).getDistrictId());
		addressEntity.setMandal(mandalDAO.getByMandalCode(model.getMandalId()));
		addressEntity.setPinCode(model.getPincode());
		addressEntity.setModifiedBy(userName);
		addressEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
		return addressEntity;
	}
}
