package org.rta.core.service.master.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.rta.core.dao.master.impl.DesignationDAOImpl;
import org.rta.core.helper.master.DesignationHelper;
import org.rta.core.model.master.DesignationModel;
import org.rta.core.service.master.DesignationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DesignationServiceImpl implements DesignationService {

    @Autowired
    private DesignationDAOImpl designationDAO;

    @Autowired
    private DesignationHelper designationHelper;

    @Override
    @Transactional
    public List<DesignationModel> getAll() {
        return (List<DesignationModel>) designationHelper.convertToModelList(designationDAO.getAll());
    }
}
