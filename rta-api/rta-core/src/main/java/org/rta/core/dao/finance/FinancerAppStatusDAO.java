package org.rta.core.dao.finance;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.finance.FinancerAppStatusEntity;
import org.rta.core.entity.user.UserEntity;
import org.rta.core.enums.ServiceType;
import org.rta.core.enums.Status;

public interface FinancerAppStatusDAO extends GenericDAO<FinancerAppStatusEntity> {
	public FinancerAppStatusEntity getFinancerAppFromToken(String tokenId, UserEntity user);

	public List<FinancerAppStatusEntity> getFinancerAppList(UserEntity user, Status status, Integer perPageRecords,
			Integer pageNumber);
	
	public List<FinancerAppStatusEntity> getFinancerAppListMultiStatus(UserEntity user, List<Integer> statuses, Integer perPageRecords,
			Integer pageNumber);

	public List<FinancerAppStatusEntity> getFinancerAppList4Token(String token);

	public FinancerAppStatusEntity getFinancerApp4Token(String token,Long financerId);

	public List<FinancerAppStatusEntity> getAppsForStatus(Long vehicleRc);

	public Long getCountFinancerAppListMultiStatus(UserEntity user, List<Integer> statuses);

    List<FinancerAppStatusEntity> getFinancerAppListMultiStatusNew(UserEntity user, List<Integer> statuses,
            Integer perPageRecords, Integer pageNumber, Long rtaOfficeId, ServiceType serviceType);

    Integer getCountFinancerAppListMultiStatusNew(UserEntity user, List<Integer> statuses,
            Long rtaOfficeId, ServiceType serviceType);

    public void setAppStatus(String token, Status status);

    List<FinancerAppStatusEntity> getAppStatusForTokens(String tokenId);

}
