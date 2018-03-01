/**
 * 
 */
package org.rta.core.service.master.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.rta.core.dao.master.MandalDAO;
import org.rta.core.dao.office.RtaOfficeDAO;
import org.rta.core.entity.master.MandalEntity;
import org.rta.core.entity.office.RtaOfficeEntity;
import org.rta.core.enums.OfficeType;
import org.rta.core.helper.master.MandalHelper;
import org.rta.core.helper.office.RTAOfficeHelper;
import org.rta.core.model.master.MandalModel;
import org.rta.core.model.office.RTAOfficeModel;
import org.rta.core.service.master.MandalService;
import org.rta.core.utils.StringsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author arun.verma
 *
 */
@Service
public class MandalServiceImpl implements MandalService{

    @Autowired
    MandalDAO mandalDao;
    
    @Autowired
    RtaOfficeDAO rtaOfficeDAO;
    
    @Autowired
    MandalHelper mandalHelper;
    
    @Autowired
    RTAOfficeHelper rTAOfficeHelper;
    
    @Override
    @Transactional
    public List<MandalModel> getAll() {
        List<MandalEntity> mandalEntityList = mandalDao.getAll();
        if(mandalEntityList == null){
            return null;
        }
        return (List<MandalModel>) mandalHelper.convertToModelList(mandalEntityList);
    }

    @Override
    public void save(MandalModel model) {
        MandalEntity entity = new MandalEntity();
        mandalDao.save(entity);
    }

    @Override
    @Transactional
    public MandalModel getMandal(String mandalName, String districtName) {
        MandalModel mandalModel = mandalHelper.convertToModelNoDetails(mandalDao.getMandal(mandalName, districtName));
        return mandalModel;
    }
    
    @Override
    @Transactional
    public MandalModel getByMandalCode(Integer mandalCode) {
        MandalEntity mandalEntity = mandalDao.getByMandalCode(mandalCode);
        return mandalHelper.convertToModel(mandalEntity);
    }
    
    @Override
    @Transactional
    public List<MandalModel> getByDistrictCode(String code) {
        return (List<MandalModel>) mandalHelper.convertToModelListNoDetails(mandalDao.getByDistrictCode(code));
    }

    @Override
    @Transactional
    public RTAOfficeModel getRTAOfficeByMandalCode(Integer code, String registrationCategory) {
        RtaOfficeEntity rtaOffice = null;
        if(!StringsUtil.isNullOrEmpty(registrationCategory) && "T".equalsIgnoreCase(registrationCategory)) {
            rtaOffice = mandalDao.getRTAOfficeByMandalCode(code);
            if(null !=  rtaOffice && !OfficeType.RTA.getLabel().equalsIgnoreCase(rtaOffice.getOfficeType()) && !rtaOffice.getCode().equalsIgnoreCase(rtaOffice.getParentOfficeCode())){
                rtaOffice = rtaOfficeDAO.getRtaOfficeDetailsByCode(rtaOffice.getParentOfficeCode());
            }
        } else {
            rtaOffice = mandalDao.getRTAOfficeByMandalCode(code);
        }
        return rTAOfficeHelper.convertToModel(rtaOffice);
    }

}
