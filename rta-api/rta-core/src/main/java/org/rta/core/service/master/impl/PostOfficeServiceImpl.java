/**
 * 
 */
package org.rta.core.service.master.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.rta.core.dao.master.PostOfficeDAO;
import org.rta.core.entity.master.PostOfficeEntity;
import org.rta.core.helper.master.PostOfficeHelper;
import org.rta.core.model.master.PostOfficeModel;
import org.rta.core.service.master.PostOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author arun.verma
 *
 */
@Service
public class PostOfficeServiceImpl implements PostOfficeService {

    @Autowired
    PostOfficeDAO postOfficeDAO;

    @Autowired
    PostOfficeHelper postOfficeHelper;

    @Override
    @Transactional
    public List<PostOfficeModel> getAll() {
        boolean status = true;
        List<PostOfficeEntity> postofficeEntityList = postOfficeDAO.getAll(status);
        return (List<PostOfficeModel>) postOfficeHelper.convertToModelList(postofficeEntityList);
    }

    @Override
    @Transactional
    public PostOfficeModel getByPinCode(String pinCode) {
        PostOfficeEntity postofficeEntity = postOfficeDAO.getByPinCode(pinCode);
        return postOfficeHelper.convertToModel(postofficeEntity);
    }

    @Override
    @Transactional
    public PostOfficeModel getByPostOfficeName(String postOfficeName) {
        PostOfficeEntity postofficeEntity = postOfficeDAO.getByPostOfficeName(postOfficeName);
        return postOfficeHelper.convertToModel(postofficeEntity);
    }

}
