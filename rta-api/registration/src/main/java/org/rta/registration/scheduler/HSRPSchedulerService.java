package org.rta.registration.scheduler;

public interface HSRPSchedulerService {

	public void postTRDataToHSRP(Long from , Long to);

	public void postPRDataToHSRP(Long from , Long to);
	
	public void updateHSRPDetailTable(Long from , Long to);
}
