/**
 * 
 */
package org.rta.core.helper.master;

import java.util.Collection;
import java.util.stream.Collectors;

import org.rta.core.entity.master.MandalEntity;
import org.rta.core.entity.office.RtaOfficeEntity;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.master.MandalModel;
import org.rta.core.model.office.RTAOfficeModel;
import org.rta.core.utils.ObjectsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author arun.verma
 *
 */
@Component
public class MandalHelper implements BaseConverter<MandalEntity, MandalModel>{

    @Autowired
    DistrictHelper districtHelper;
    @Autowired
    StateHelper stateHelper;
    
    @Override
    public MandalModel convertToModel(MandalEntity source) {
        if(source == null){
            return null;
        }
        MandalModel mm = new MandalModel();
        mm.setId(source.getMandalId());
        mm.setCode(source.getCode());
        mm.setDistrictModel(districtHelper.convertToModel(source.getDistrictEntity()));
        mm.setName(source.getName());
        mm.setRtaOfficeModel(toRTAOfficeModel(source.getRtaOfficeEntity()));
        mm.setStatus(source.getStatus());
        mm.setCreatedBy(source.getCreatedBy());
        mm.setCreatedOn(source.getCreatedOn());
        return mm;
    }

    @Override
    public Collection<MandalModel> convertToModelList(Collection<MandalEntity> source) {
        return source.stream().map(s->convertToModel(s)).collect(Collectors.toList());
    }

    @Override
    public MandalEntity convertToEntity(MandalModel source) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<MandalEntity> convertToEntityList(Collection<MandalModel> source) {
        // TODO Auto-generated method stub
        return null;
    }

    public RTAOfficeModel toRTAOfficeModel(RtaOfficeEntity entity){
        if(ObjectsUtil.isNull(entity)){
            return null;
        }
        RTAOfficeModel ddl = new RTAOfficeModel();
        ddl.setCode(entity.getCode());
        ddl.setEmail(entity.getEmail());
        ddl.setFax(entity.getFax());
        ddl.setMobile(entity.getMobile());
        ddl.setName(entity.getName());
        ddl.setOfficeType(entity.getOfficeType());
        ddl.setPhone(entity.getPhone());
        ddl.setSeriesMapid(entity.getSeriesMapid());
        ddl.setStatus(entity.getStatus());
        return ddl;
    }
    
    public MandalModel convertToModelNoDetails(MandalEntity source) {
        if(source == null){
            return null;
        }
        MandalModel mm = new MandalModel();
        mm.setId(source.getMandalId());
        mm.setCode(source.getCode());
        mm.setName(source.getName());
        mm.setStatus(source.getStatus());
        mm.setCreatedBy(source.getCreatedBy());
        mm.setCreatedOn(source.getCreatedOn());
        return mm;
    }

    public Collection<MandalModel> convertToModelListNoDetails(Collection<MandalEntity> source) {
        return source.stream().map(s->convertToModelNoDetails(s)).collect(Collectors.toList());
    }
}
