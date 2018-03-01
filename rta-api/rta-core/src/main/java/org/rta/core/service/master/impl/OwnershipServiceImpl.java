package org.rta.core.service.master.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.rta.core.dao.master.OwnershipDAO;
import org.rta.core.helper.master.OwnershipHelper;
import org.rta.core.model.master.OwnershipModel;
import org.rta.core.service.master.OwnershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OwnershipServiceImpl implements OwnershipService {

    @Autowired
    protected OwnershipDAO ownerDao;

    @Autowired
    public OwnershipHelper ownershipHelper;

    @Override
    @Transactional
    public List<OwnershipModel> getAll() {
        return (List<OwnershipModel>) ownershipHelper.convertToModelList(ownerDao.getAll());
    }



}
