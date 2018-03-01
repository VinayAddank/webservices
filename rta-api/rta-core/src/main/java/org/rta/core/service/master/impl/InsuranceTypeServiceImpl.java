package org.rta.core.service.master.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.rta.core.dao.master.InsuranceTypeDAO;
import org.rta.core.helper.master.InsuranceTypeHelper;
import org.rta.core.model.master.InsuranceTypeModel;
import org.rta.core.service.master.InsuranceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author sohan.maurya created on Jul 12, 2016.
 */
@Service("insuranceTypeService")
public class InsuranceTypeServiceImpl implements InsuranceTypeService {

    @Autowired
    private InsuranceTypeDAO insuranceTypeDao;

    @Autowired
    private InsuranceTypeHelper insuranceTypeHelper;

    @Transactional
    public void create(InsuranceTypeModel model) {
        insuranceTypeDao.save(insuranceTypeHelper.convertToEntity(model));
    }

    @Transactional
    public List<InsuranceTypeModel> getAll() {
        return (List<InsuranceTypeModel>) insuranceTypeHelper.convertToModelList(insuranceTypeDao.getAll());
    }

    @Override
    public InsuranceTypeModel getByCode(String code) {
        return insuranceTypeHelper.convertToModel(insuranceTypeDao.getByCode(code));
    }


}
