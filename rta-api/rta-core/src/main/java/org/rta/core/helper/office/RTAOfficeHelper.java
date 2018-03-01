/**
 * 
 */
package org.rta.core.helper.office;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.rta.core.entity.office.RtaOfficeEntity;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.office.RTAOfficeModel;
import org.rta.core.utils.ObjectsUtil;
import org.springframework.stereotype.Component;

/**
 * @author arun.verma
 *
 */
@Component
public class RTAOfficeHelper implements BaseConverter<RtaOfficeEntity, RTAOfficeModel> {

    @Override
    public RTAOfficeModel convertToModel(RtaOfficeEntity source) {
        if (ObjectsUtil.isNull(source)) {
            return null;
        }
        RTAOfficeModel mdl = new RTAOfficeModel();
        mdl.setCode(source.getCode());
        mdl.setEmail(source.getEmail());
        mdl.setFax(source.getFax());
        mdl.setMobile(source.getMobile());
        mdl.setName(source.getName());
        mdl.setOfficeType(source.getOfficeType());
        mdl.setSeriesMapid(source.getSeriesMapid());
        return mdl;
    }

    @Override
    public RtaOfficeEntity convertToEntity(RTAOfficeModel source) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<RTAOfficeModel> convertToModelList(Collection<RtaOfficeEntity> source) {
        if (ObjectsUtil.isNull(source)) {
            return null;
        }
        return source.stream().map(s -> convertToModel(s)).collect(Collectors.toList());
    }

    @Override
    public Collection<RtaOfficeEntity> convertToEntityList(Collection<RTAOfficeModel> source) {
        // TODO Auto-generated method stub
        return null;
    }


    public List<RTAOfficeModel> convertEntityListToModelList(List<RtaOfficeEntity> source) {
        
        List<RTAOfficeModel> models = new ArrayList<RTAOfficeModel>();
        RTAOfficeModel model = null;
        for (RtaOfficeEntity entity : source) {
            model = new RTAOfficeModel();
            model.setCode(entity.getCode());
            model.setName(entity.getName());
            models.add(model);
        }
        return models;
    }

}
