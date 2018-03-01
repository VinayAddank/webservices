package org.rta.core.service.tempreport;

import java.util.Map;

public interface TempReportService {
	public Map<Long, String[]> getDealerApplicationSummaryReport(long from, long to);

	public Map<String, Map<String, Map<String, Integer>>> getRtaOfficeWiseReport(Long from, Long to);

	public void getRTOApplication(long from, long to);

	public void getTransactionReport(long from, long to);

    public void getTransactionStatusReport(long from, long to);
    
    public void getRTORejReport(long from , long to);
}
