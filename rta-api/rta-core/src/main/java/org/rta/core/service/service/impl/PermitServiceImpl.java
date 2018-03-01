/**
 * 
 */
package org.rta.core.service.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.rta.core.dao.master.PermitConditionsDAO;
import org.rta.core.dao.master.PermitGoodsDAO;
import org.rta.core.dao.master.PermitRouteDAO;
import org.rta.core.dao.master.PermitTypeDAO;
import org.rta.core.dao.master.PrimaryTempPermitMappingDAO;
import org.rta.core.dao.permit.PermitHeaderDAO;
import org.rta.core.entity.master.PermitConditionsEntity;
import org.rta.core.entity.master.PermitGoodsMasterEntity;
import org.rta.core.entity.master.PermitRouteConditionsMasterEntity;
import org.rta.core.entity.master.PermitTypeEntity;
import org.rta.core.entity.master.PrimaryTempPermitMappingEntity;
import org.rta.core.entity.permit.PermitHeaderEntity;
import org.rta.core.enums.PermitDetailsType;
import org.rta.core.exception.NotFoundException;
import org.rta.core.model.master.PermitTypeModel;
import org.rta.core.model.permit.PermitCodeDescModel;
import org.rta.core.model.permit.PermitTempPermitModel;
import org.rta.core.service.service.PermitService;
import org.rta.core.utils.ObjectsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author arun.verma
 *
 */

@Service
public class PermitServiceImpl implements PermitService {

	private static final Logger log = Logger.getLogger(PermitServiceImpl.class);
    
    @Autowired
    private PermitGoodsDAO permitGoodsDAO;

    @Autowired
    private PermitRouteDAO permitRouteDAO;

    @Autowired
    private PermitConditionsDAO permitConditionsDAO;
    
    @Autowired
    private PermitHeaderDAO permitHeaderDAO;
    
    @Autowired
    private PermitTypeDAO permitTypeDAO;
    
    @Autowired
    private PrimaryTempPermitMappingDAO primaryTempPermitMappingDAO;

    @Override
    @Transactional
    public List<PermitCodeDescModel> getRouteGoodsConditionsForPrimaryPermit(PermitDetailsType permitDetailsType, String cov, String permitType) {
        List<PermitCodeDescModel> listModel = new ArrayList<>();
        if(permitDetailsType.equals(PermitDetailsType.GOODS)){
            List<PermitGoodsMasterEntity> list = permitGoodsDAO.getPermitGoods(cov, permitType);
            for (PermitGoodsMasterEntity entity : list) {
                PermitCodeDescModel mdl = new PermitCodeDescModel();
                mdl.setCode(entity.getCode());
                mdl.setDesc(entity.getPerGoodsDesc());
                listModel.add(mdl);
            }
            return listModel;
        } else if(permitDetailsType.equals(PermitDetailsType.ROUTE)){
            List<PermitRouteConditionsMasterEntity> list = permitRouteDAO.getPermitRoute(cov, permitType);
            for (PermitRouteConditionsMasterEntity entity : list) {
                PermitCodeDescModel mdl = new PermitCodeDescModel();
                mdl.setCode(entity.getCode());
                mdl.setDesc(entity.getPerRouteDesc());
                listModel.add(mdl);
            }
        } else if(permitDetailsType.equals(PermitDetailsType.CONDITION)){
            List<PermitConditionsEntity> list = permitConditionsDAO.getPermitConditions(cov, permitType);
            for (PermitConditionsEntity entity : list) {
                PermitCodeDescModel mdl = new PermitCodeDescModel();
                mdl.setCode(entity.getCode());
                mdl.setDesc(entity.getPermitCondDesc());
                listModel.add(mdl);
            }
        }
        return listModel;
    }
    
    @Override
    @Transactional
    public PermitTempPermitModel getTempPermits(String prNumber) throws NotFoundException{
        PermitHeaderEntity permitHeaderEntity = permitHeaderDAO.getPrimaryPermitActiveByPr(prNumber);
        if(ObjectsUtil.isNull(permitHeaderEntity)){
            log.error("No Permit Details found for pr :" + prNumber);
            throw new NotFoundException("No Permit Details found !!!");
        }
        PermitTypeEntity permitTypeEntity =  permitTypeDAO.getPermitTypeByCode(permitHeaderEntity.getPermitType());
        PermitTempPermitModel permitDtlMdl = new PermitTempPermitModel();
        PermitTypeModel permit  = new PermitTypeModel();
        permit.setCode(permitTypeEntity.getCode());
        permit.setName(permitTypeEntity.getName());
        permitDtlMdl.setPermitType(permit);
        
        List<PrimaryTempPermitMappingEntity> mappingList = primaryTempPermitMappingDAO.getList(permitTypeEntity.getCode());
        List<String> tempPermitCodelist = new ArrayList<>();
        for(PrimaryTempPermitMappingEntity l : mappingList){
            tempPermitCodelist.add(l.getTemporaryPermitCode());
        }
        List<PermitTypeEntity> tempPermitList = null;
        if(!ObjectsUtil.isNullOrEmpty(tempPermitCodelist)){
            tempPermitList = permitTypeDAO.getPermitTypeByCodes(tempPermitCodelist, true);
            List<PermitTypeModel> tempPermitModelList = new ArrayList<>();
            for(PermitTypeEntity entity : tempPermitList){
                PermitTypeModel temp  = new PermitTypeModel();
                temp.setCode(entity.getCode());
                temp.setName(entity.getName());
                tempPermitModelList.add(temp);
            }
            permitDtlMdl.setTemporaryPermits(tempPermitModelList);
        }
        return permitDtlMdl;
    }
    
    @Override
    @Transactional
    public Map<String, Object> getPukkaTempPermit(String prNumber){
        Map<String, Object> map = new HashMap<>();
        List<PermitHeaderEntity> permitHeaderEntityList = permitHeaderDAO.getPermitActiveByPr(prNumber);
        for(PermitHeaderEntity headerEntity : permitHeaderEntityList){
            PermitTypeEntity permitTypeEntity =  permitTypeDAO.getPermitTypeByCode(headerEntity.getPermitType());
            PermitTypeModel permitType = new PermitTypeModel();
            if(!ObjectsUtil.isNull(permitTypeEntity)){
                permitType.setName(permitTypeEntity.getName());
                permitType.setCode(permitTypeEntity.getCode());
            }
            if(headerEntity.getIsTempPermit()){
                map.put("temporaryPermit", permitType);
            } else {
                map.put("pukkaPermit", permitType);
            }
        }
        return map;
    }
    
    @Override
    @Transactional
    public List<PermitCodeDescModel> getRouteGoodsConditionsForTemporaryPermit(PermitDetailsType permitDetailsType, String primaryPermitType, String temporaryPermitType) {
        List<PermitCodeDescModel> listModel = new ArrayList<>();
        if(permitDetailsType.equals(PermitDetailsType.GOODS)){
            List<PermitGoodsMasterEntity> list = permitGoodsDAO.getTempPermitGoods(primaryPermitType, temporaryPermitType);
            for (PermitGoodsMasterEntity entity : list) {
                PermitCodeDescModel mdl = new PermitCodeDescModel();
                mdl.setCode(entity.getCode());
                mdl.setDesc(entity.getPerGoodsDesc());
                listModel.add(mdl);
            }
            return listModel;
        } else if(permitDetailsType.equals(PermitDetailsType.ROUTE)){
            List<PermitRouteConditionsMasterEntity> list = permitRouteDAO.getTempPermitRoute(primaryPermitType, temporaryPermitType);
            for (PermitRouteConditionsMasterEntity entity : list) {
                PermitCodeDescModel mdl = new PermitCodeDescModel();
                mdl.setCode(entity.getCode());
                mdl.setDesc(entity.getPerRouteDesc());
                listModel.add(mdl);
            }
            return listModel;
        } else if(permitDetailsType.equals(PermitDetailsType.CONDITION)){
            List<PermitConditionsEntity> list = permitConditionsDAO.getTempPermitConditions(primaryPermitType, temporaryPermitType);
            for (PermitConditionsEntity entity : list) {
                PermitCodeDescModel mdl = new PermitCodeDescModel();
                mdl.setCode(entity.getCode());
                mdl.setDesc(entity.getPermitCondDesc());
                listModel.add(mdl);
            }
            return listModel;
        }
        return null;
    }
}
