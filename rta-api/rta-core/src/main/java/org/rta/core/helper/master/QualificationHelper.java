package org.rta.core.helper.master;

import java.util.Collection;
import java.util.stream.Collectors;

import org.rta.core.entity.master.QualificationEntity;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.master.QualificationModel;
import org.springframework.stereotype.Component;

@Component
public class QualificationHelper implements BaseConverter<QualificationEntity, QualificationModel> {

    @Override
    public Collection<QualificationModel> convertToModelList(Collection<QualificationEntity> source) {
        return source.stream().map(s -> convertToModel(s)).collect(Collectors.toList());
    }

    @Override
    public QualificationModel convertToModel(QualificationEntity source) {
        QualificationModel qm = new QualificationModel();
        qm.setId(source.getQualificationId());
        qm.setCode(source.getCode());
        qm.setName(source.getName());
        qm.setStatus(source.getStatus());
        qm.setCreatedBy(source.getCreatedBy());
        qm.setCreatedOn(source.getCreatedOn());
        return qm;
    }

    @Override
    public QualificationEntity convertToEntity(QualificationModel source) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<QualificationEntity> convertToEntityList(Collection<QualificationModel> source) {
        // TODO Auto-generated method stub
        return null;
    }

}
