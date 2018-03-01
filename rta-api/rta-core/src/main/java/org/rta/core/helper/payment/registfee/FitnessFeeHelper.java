package org.rta.core.helper.payment.registfee;

import java.util.Collection;

import org.rta.core.entity.payment.registfee.FitnessFeeDetailsEntity;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.payment.registfee.FitnessFeeModel;
import org.rta.core.utils.NumberParser;
import org.springframework.stereotype.Component;

@Component
public class FitnessFeeHelper implements BaseConverter<FitnessFeeDetailsEntity, FitnessFeeModel> {

    @Override
    public FitnessFeeModel convertToModel(FitnessFeeDetailsEntity source) {
        FitnessFeeModel fitnessFeeModel = new FitnessFeeModel();
        fitnessFeeModel.setFitnessFee(NumberParser.numberFormat(source.getFitnessFee()));
        fitnessFeeModel.setFitnessService(NumberParser.numberFormat(source.getFitnessService()));
        fitnessFeeModel.setTotalFitnessFee(NumberParser.numberFormat(source.getTotalFitnessFee()));
        return fitnessFeeModel;
    }

    @Override
    public FitnessFeeDetailsEntity convertToEntity(FitnessFeeModel source) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<FitnessFeeModel> convertToModelList(Collection<FitnessFeeDetailsEntity> source) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<FitnessFeeDetailsEntity> convertToEntityList(Collection<FitnessFeeModel> source) {
        // TODO Auto-generated method stub
        return null;
    }

}
