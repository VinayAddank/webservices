/**
 * 
 */
package org.rta.core.helper.master;

import java.util.Collection;
import java.util.stream.Collectors;

import org.rta.core.entity.master.PostOfficeEntity;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.master.PostOfficeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author arun.verma
 *
 */
@Component
public class PostOfficeHelper implements BaseConverter<PostOfficeEntity, PostOfficeModel>{

    @Autowired
    MandalHelper mandalHelper;
    
    @Override
    public PostOfficeModel convertToModel(PostOfficeEntity source) {
        if(source == null){
            return null;
        }
        PostOfficeModel po = new PostOfficeModel();
        po.setId(source.getPostOfficeId());
        po.setPinCode(source.getPinCode());
        po.setName(source.getName());
        po.setStatus(source.getStatus());
        po.setCreatedBy(source.getCreatedBy());
        po.setCreatedOn(source.getCreatedOn());
        return po;
    }

    @Override
    public Collection<PostOfficeModel> convertToModelList(Collection<PostOfficeEntity> source) {
        if(source == null){
            return null;
        }
        return source.stream().map(s->convertToModel(s)).collect(Collectors.toList());
    }

    @Override
    public PostOfficeEntity convertToEntity(PostOfficeModel source) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<PostOfficeEntity> convertToEntityList(Collection<PostOfficeModel> source) {
        // TODO Auto-generated method stub
        return null;
    }

}
