package org.rta.core.service.finance;

import java.util.List;

import org.rta.core.model.finance.FinanceBranchModel;
import org.rta.core.model.finance.FinanceYardModel;

public interface FinanceAdminService {
	public String saveUpdateBranch(FinanceBranchModel model);
	public List<FinanceBranchModel> getBranchList(Long headUserId,boolean considerStatus, Integer activeStatus) ;
	public String saveUpdateYard(FinanceYardModel model);
	public List<FinanceYardModel> getYardList(Long headUserId,boolean considerStatus, Integer activeStatus) ;
}
