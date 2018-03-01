package org.rta.core.service.master.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.rta.core.dao.master.AddressProofTypeDAO;
import org.rta.core.helper.master.AddressProofTypeHelper;
import org.rta.core.model.master.AddressProofTypeModel;
import org.rta.core.service.master.AddressProofTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *	@Author sohan.maurya created on Jul 12, 2016.
 */
@Service("addressProofTypeService")
public class AddressProofTypeServiceImpl implements AddressProofTypeService {

    @Autowired
    private AddressProofTypeDAO addressProofTypeDAO;

    @Autowired
    private AddressProofTypeHelper addressProofTypeHelper;


    @Transactional
    public void create(AddressProofTypeModel model) {
        addressProofTypeDAO.save(addressProofTypeHelper.convertModelToEntity(model));
    }

    @Transactional
    public List<AddressProofTypeModel> getAll() {

        return (List<AddressProofTypeModel>) addressProofTypeHelper.convertToModelList(addressProofTypeDAO.getAll());
    }

}
