package org.rta.core.service.user.impl;

import javax.transaction.Transactional;

import org.rta.core.dao.user.AddressDAO;
import org.rta.core.entity.user.AddressEntity;
import org.rta.core.helper.user.AddressHelper;
import org.rta.core.model.user.AddressModel;
import org.rta.core.service.user.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressDAO addressDao;

    @Autowired
    private AddressHelper addressHelper;

    @Override
    @Transactional
    public AddressModel findByUserId(Long id) {
        AddressEntity address = addressDao.findByUserIdAndType(id, "T");
        AddressModel am = addressHelper.convertToModel(address);
        return am;
    }

}
