package org.rta.registration.scheduler.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.rta.core.dao.hsrp.HSRPDetailDAO;
import org.rta.core.dao.vehicle.VehicleDAO;
import org.rta.core.entity.hsrp.HSRPDetailEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.model.hsrp.HSRPDetailModel;
import org.rta.core.model.hsrp.HSRPRTADetailModel;
import org.rta.core.service.hsrp.HSRPDetailService;
import org.rta.registration.scheduler.HSRPSchedulerService;
import org.rta.registration.service.hsrp.HSRPRestApiCall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class HSRPSchedulerServiceImpl implements HSRPSchedulerService {
	private static final Logger log = Logger.getLogger(HSRPSchedulerServiceImpl.class);
	@Autowired
	private HSRPDetailService hsrpDetailService;
	@Value("${hsrp.securitykey}")
	String securitykey;
	@Autowired
	private HSRPRestApiCall hsrpRestApiCall;
	@Value("${hsrp.post.tr.records.url}")
	String POST_TR_HSRP_URL;
	@Value("${hsrp.contenttype}")
	String contenttype;
	@Value("${hsrp.post.pr.records.url}")
	String POST_PR_HSRP_URL;
	@Autowired
	private VehicleDAO vehicleDAO;
	@Autowired
	private HSRPDetailDAO hsrpDetailDAO;
	
	public HSRPSchedulerServiceImpl() {
	}

	public void postTRDataToHSRP(Long from , Long to) {
		log.debug("::::::::postTRDataToHSRP:::::::::");
		List<HSRPDetailModel> hsrpDetailList = hsrpDetailService.getAllOpenStatusData(from,to);
		if (hsrpDetailList == null)
			log.info(":::::::::NO RECORDS TR FOUNDS::::::::::");
		else if (hsrpDetailList.size() == 0)
			log.info(":::::::::NO RECORDS TR FOUNDS::::::::::");
		for (HSRPDetailModel hsrpDetailModel : hsrpDetailList) {
			try {
				HSRPRTADetailModel hsrpRTADetailModel = hsrpDetailService.getTRData(hsrpDetailModel);
				ResponseEntity<String> responseEntity = hsrpRestApiCall.callAPIPost(POST_TR_HSRP_URL,
						hsrpRTADetailModel, contenttype);
				if (responseEntity.getStatusCode() == HttpStatus.OK) {
					log.debug("::::HSRP::TR::response::::::" + responseEntity.getBody());
					String response = responseEntity.getBody();
					ObjectMapper objectMapper = new ObjectMapper();
					JsonNode node = objectMapper.readValue(response, JsonNode.class);
					log.debug("::::HSRP TR NODE::::::" + node);
					JsonNode object = node.get(0);
					int status = object.get("Status").asInt();
					String message = object.get("Message").textValue();
					hsrpDetailService.updateHSRPTRStatus(hsrpDetailModel.getVehicleRcId(), status, message);
				}
			} catch (Exception e1) {
				log.error("::::::::::TR Internal Server Error from HSRP:::::::::::" + e1.getMessage());
				log.debug(e1);
			}
		}

	}

	public void postPRDataToHSRP(Long from , Long to) {
		log.debug("::::::::postPRDataToHSRP:::::::::");
		List<HSRPDetailModel> hsrpDetailList = hsrpDetailService.getAllTRPostData(from,to);
		if (hsrpDetailList == null)
			log.info(":::::::::NO RECORDS PR FOUNDS::::::::::");
		else if (hsrpDetailList.size() == 0)
			log.info(":::::::::NO RECORDS PR FOUNDS::::::::::");
		for (HSRPDetailModel hsrpDetailModel : hsrpDetailList) {
			try {
				HSRPRTADetailModel hsrpRTADetailModel = hsrpDetailService.getPRData(hsrpDetailModel);
				ResponseEntity<String> responseEntity = hsrpRestApiCall.callAPIPost(POST_PR_HSRP_URL,
						hsrpRTADetailModel, contenttype);
				if (responseEntity.getStatusCode() == HttpStatus.OK) {
					log.debug("::::HSRP::PR:::response::::::" + responseEntity.getBody());
					String response = responseEntity.getBody();
					ObjectMapper objectMapper = new ObjectMapper();
					JsonNode node = objectMapper.readValue(response, JsonNode.class);
					log.debug("::::HSRP PR NODE::::::" + node);
					JsonNode object = node.get(0);
					int status = object.get("Status").asInt();
					String message = object.get("Message").textValue();
					hsrpDetailService.updateHSRPPRStatus(hsrpDetailModel.getVehicleRcId(), status, message);
				}
			} catch (Exception e1) {
				log.error("::::::::::PR Internal Server Error from HSRP:::::::::::" + e1.getMessage());
				log.debug(e1);
			}
		}

	}

	@Override
	@Transactional
	public void updateHSRPDetailTable(Long from, Long to) {
		log.info("::::::::updateHSRPDetailTable:::::::::");
		List<VehicleRCEntity> vehicleRCList = vehicleDAO.getByTRApprovedNdFromToDate(from, to);
		if (vehicleRCList == null)
			log.info(":::::::::NO RECORDS FOUNDS::::updateHSRPDetailTable::::::");
		else if (vehicleRCList.size() == 0)
			log.info(":::::::::NO RECORDS FOUNDS:::::updateHSRPDetailTable:::::");
		for (VehicleRCEntity vehicleRCEntity : vehicleRCList) {
			HSRPDetailEntity hsrpDetailEntity = hsrpDetailDAO.getByVehicleRcId(vehicleRCEntity.getVehicleRcId());
			if(hsrpDetailEntity != null)
				continue;
			try {
				hsrpDetailService.saveHSRPDetail(vehicleRCEntity.getVehicleRcId(), vehicleRCEntity.getModifiedBy());
			} catch (Exception e) {
				log.error("Exception updateHSRPDetailTable : " + e.getMessage());
				log.debug(e);
				continue;
			}
		}
		
	}

}
