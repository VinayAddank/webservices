package org.rta.registration.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.rta.core.model.finance.FinanceBranchModel;
import org.rta.core.model.finance.FinanceYardModel;
import org.rta.core.service.finance.FinanceAdminService;
import org.rta.security.utills.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FinanceAdminController {
	
	private static final Logger log = Logger.getLogger(FinanceAdminController.class);
	
	@Autowired
	FinanceAdminService adminSerice;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil; 
	
	@Autowired
	HttpServletRequest request;

	@Value("${jwt.header}")
	private String tokenHeader;
	
	final static int ACTIVE_STATUS=1;
	final static int INACTIVE_STATUS=2;
	
	@RequestMapping(path = "finance/admin/branch", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity saveUpdateBranch(@RequestBody FinanceBranchModel model){
		String response=adminSerice.saveUpdateBranch(model);
		Map<String,String> respMap=new HashMap<>(1);
		respMap.put("response", response);
		return ResponseEntity.ok(respMap);
		
	}
	
	
	@RequestMapping(path = "finance/admin/branches", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity getBraches(){
		String token = request.getHeader(tokenHeader);
		Long userId = jwtTokenUtil.getUserIdFromToken(token);
		List<FinanceBranchModel> response=adminSerice.getBranchList(userId, true, ACTIVE_STATUS);
		return ResponseEntity.ok(response);
	}
	
	
	@RequestMapping(path = "finance/admin/yard", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity saveUpdateYard(@RequestBody FinanceYardModel model){
		String response=adminSerice.saveUpdateYard(model);
		Map<String,String> respMap=new HashMap<>(1);
		respMap.put("response", response);
		return ResponseEntity.ok(respMap);
		
	}
	
	
	@RequestMapping(path = "finance/admin/yards", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getYards(){
		String token = request.getHeader(tokenHeader);
		Long userId = jwtTokenUtil.getUserIdFromToken(token);
		List<FinanceYardModel> response=adminSerice.getYardList(userId, true, ACTIVE_STATUS);
		return ResponseEntity.ok(response);
	}
	

}
