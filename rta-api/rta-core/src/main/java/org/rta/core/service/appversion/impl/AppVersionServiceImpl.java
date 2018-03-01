/**
 * 
 */
package org.rta.core.service.appversion.impl;

import javax.transaction.Transactional;

import org.rta.core.dao.appversion.AppVersionDAO;
import org.rta.core.entity.appversion.AppVersionEntity;
import org.rta.core.enums.Status;
import org.rta.core.model.appversion.AppVersionModel;
import org.rta.core.service.appversion.AppVersionService;
import org.rta.core.utils.ObjectsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author arun.verma
 *
 */

@Service
public class AppVersionServiceImpl implements AppVersionService{

    @Autowired
    AppVersionDAO appVersionDAO;
    
    @Override
    @Transactional
    public AppVersionModel getAppVersion(Integer major, Integer minor, Integer revision) {
        AppVersionEntity entity = appVersionDAO.getAppVersion(major, minor, revision);
        if(ObjectsUtil.isNull(entity)){
            throw new IllegalArgumentException("Invalid version requested !!!");
        }
        AppVersionModel model = new AppVersionModel();
        model.setAppName(entity.getAppName());
        model.setAppUrl(entity.getAppUrl());
        model.setMessage(entity.getMessage());
        model.setStatus(Status.getStatus(entity.getStatus()));
        /*
         * 8 => "OPEN"
         * 6 => "EXPIRED"
         * 23 => "WARNING"
         */
        model.setVerMajor(entity.getVerMajor());
        model.setVerMinor(entity.getVerMinor());
        model.setVerRevision(entity.getVerRevision());
        return model;
    }

}
