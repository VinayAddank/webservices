package org.rta.core.service.master.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.rta.core.dao.master.TaxTypeDAO;
import org.rta.core.helper.master.TaxTypHelper;
import org.rta.core.model.master.TaxTypeModel;
import org.rta.core.service.master.TaxTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaxTypeServiceImpl implements TaxTypeService {
    @Autowired
    public TaxTypeDAO taxTypDao;

    @Autowired
    public TaxTypHelper taxTypHelper;

    @Override
    @Transactional
    public List<TaxTypeModel> getAll() {
        return (List<TaxTypeModel>) taxTypHelper.convertToModelList(taxTypDao.getAll());
    }

    @Override
    public TaxTypeModel getByCode(String code) {
        return taxTypHelper.convertToModel(taxTypDao.getByCode(code));
    }

}
