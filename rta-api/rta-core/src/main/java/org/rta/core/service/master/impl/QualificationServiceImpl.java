package org.rta.core.service.master.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.rta.core.dao.master.impl.QualificationDAOImpl;
import org.rta.core.helper.master.QualificationHelper;
import org.rta.core.model.master.QualificationModel;
import org.rta.core.service.master.QualificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QualificationServiceImpl implements QualificationService {

    @Autowired
    private QualificationDAOImpl qualificationDAO;

    @Autowired
    private QualificationHelper qualificationHelper;

    @Override
    @Transactional
    public List<QualificationModel> getAll() {
        return (List<QualificationModel>) qualificationHelper.convertToModelList(qualificationDAO.getAll(true));
    }

}
