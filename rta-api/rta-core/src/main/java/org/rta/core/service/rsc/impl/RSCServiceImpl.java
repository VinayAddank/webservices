/**
 * 
 */
package org.rta.core.service.rsc.impl;

import org.apache.log4j.Logger;
import org.rta.core.dao.user.UserDAO;
import org.rta.core.entity.user.UserEntity;
import org.rta.core.enums.ServiceType;
import org.rta.core.enums.citizen.KeyType;
import org.rta.core.model.citizen.CitizenServiceResponseModel;
import org.rta.core.service.citizen.CitizenService;
import org.rta.core.service.citizen.ResponseModel;
import org.rta.core.service.citizen.model.AuthenticationModel;
import org.rta.core.service.citizen.model.CitizenTokenModel;
import org.rta.core.service.rsc.RSCService;
import org.rta.core.utils.ObjectsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

/**
 * @author arun.verma
 *
 */
@Service
public class RSCServiceImpl implements RSCService{

	private static final Logger log = Logger.getLogger(RSCServiceImpl.class);
    
    @Autowired
    private CitizenService citizenService;
    
    @Autowired
    private UserDAO userDAO;
    
    @Override
    @Transactional
    public ResponseModel<?> createAppInCitizen(String prNumber, Long userId, String token) {
        AuthenticationModel authenticationModel = new AuthenticationModel();
        authenticationModel.setPrNumber(prNumber);
        authenticationModel.setUid_num(userDAO.findByUserId(userId).getAadharNumber());
        authenticationModel.setKeyType(KeyType.PR);
        UserEntity userEntity = userDAO.getEntity(UserEntity.class, userId);
        authenticationModel.setAadhaarNumber(userEntity.getAadharNumber());
        CitizenServiceResponseModel<ResponseModel<CitizenTokenModel>> application = null;
        try {
            application = citizenService.login(authenticationModel, ServiceType.REGISTRATION_SUS_CANCELLATION, token);
            return application.getResponseBody();
        } catch (HttpClientErrorException e) {
            log.error("unable to create Registration - Suspension/Cancellation application");
            if (!ObjectsUtil.isNull(application)) {
                return application.getResponseBody();
            } else {
                throw e;
            }
        }
    }
}