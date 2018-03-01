package org.rta.core.service.master.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.rta.core.dao.master.CountryDAO;
import org.rta.core.helper.master.CountryHelper;
import org.rta.core.model.master.CountryModel;
import org.rta.core.service.master.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    public CountryDAO countryDAO;

    @Autowired
    public CountryHelper countryHelper;

    @Override
    @Transactional
    public List<CountryModel> getAll() {
        return (List<CountryModel>) countryHelper.convertToModelList(countryDAO.getAll());
    }
}
