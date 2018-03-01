package org.rta.registration.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.rta.core.service.tempreport.TempReportService;
import org.rta.registration.scheduler.RTAScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TempReportController {
	private static final Logger log = Logger.getLogger(TempReportController.class);
	@Autowired
	TempReportService tempService;
	
	@Autowired
	RTAScheduler rtaScheduler;

	@Value("${rta.report.destination}")
	private String reportPath;

	public static final int RTA_OFFICE_WISE_RPT = 1;
	public static final int DEALER_APP_SUMMARY_RPT = 2;
	public static final int RTO_APP_SUMMARY_RPT = 3;

	@RequestMapping(value = "/registrationreport", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> report(@RequestHeader("reporttype") int reportType, @RequestHeader("from") Long from,
			@RequestHeader("to") Long to) {
		Map<Long, String[]> response = tempService.getDealerApplicationSummaryReport(from, to);
		return ResponseEntity.ok(response);

	}

	@RequestMapping(value = "/rtaofficewisereport")
	public ResponseEntity<?> reportRTAWise(@RequestParam(name = "from", required = true) Long from,
			@RequestParam(name = "to", required = true) Long to) {
		Map<String, Map<String, Map<String, Integer>>> data = tempService.getRtaOfficeWiseReport(from, to);
			return ResponseEntity.ok(data);
	}


	@RequestMapping(value = "/rtoapplicationreport", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public void getRtoApplicationReport(HttpServletRequest request, @RequestParam(name = "from", required = false) Long from,
			@RequestParam(name = "to", required = false) Long to) {
			log.debug("::::::::getRtoApplicationReport::::::::::::");
			tempService.getRTOApplication(from, to);
	}
	
	@RequestMapping(value = "/sendreports", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public void sendReports() {
		rtaScheduler.reportScheduler();
	}
	
	@RequestMapping(value = "/transactionreport", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public void getTransactionReport(HttpServletRequest request, @RequestParam(name = "from", required = false) Long from,
			@RequestParam(name = "to", required = false) Long to) {
		log.debug(":::::getTransactionReport::::::::");
		tempService.getTransactionReport(from, to);
	}

    @RequestMapping(value = "/transactionstatusreport", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public void getTransactionStatusReport(HttpServletRequest request,
            @RequestParam(name = "from", required = false) Long from,
            @RequestParam(name = "to", required = false) Long to) {
        log.debug(":::::getTransactionStatusReport::::::::");
        tempService.getTransactionStatusReport(from, to);
    }
    
    @RequestMapping(value = "/rtoapprejectionreport", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public void getRTORejReport(HttpServletRequest request, @RequestParam(name = "from", required = false) Long from,
            @RequestParam(name = "to", required = false) Long to) {
        log.debug(":::::getTransactionStatusReport::::::::");
        tempService.getRTORejReport(from, to);
    }
   

	
}
