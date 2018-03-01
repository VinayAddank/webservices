package org.rta.core.service.master.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.rta.core.dao.master.DocTypesDAO;
import org.rta.core.helper.master.DocTypesHelper;
import org.rta.core.model.master.DocTypesModel;
import org.rta.core.service.master.DocTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("docTypesService")
public class DocTypesServiceImpl implements DocTypesService {

    @Autowired
    private DocTypesDAO docTypesDAO;

    @Autowired
    private DocTypesHelper docTypesHelper;

    @Override
    @Transactional
    public List<DocTypesModel> getAll() {
        return (List<DocTypesModel>) docTypesHelper.convertToModelList(docTypesDAO.getAll());
    }

    @Override
    @Transactional
    public List<Long> getMandatoryDocTypesIds() {

        return docTypesDAO.getMandatoryDocTypesIds();
    }


}
