package org.rta.core.service.finance;

import java.util.List;

import org.rta.core.enums.Status;
import org.rta.core.enums.UserType;
import org.rta.core.exception.NotFoundException;
import org.rta.core.exception.UserNotFoundException;
import org.rta.core.exception.VehicleRcNotFoundException;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.finance.FreshRcModel;
import org.rta.core.model.finance.ShowCauseDataModel;
import org.rta.core.model.finance.ShowcaseInfoRequestModel;
import org.rta.core.model.finance.ShowcaseNoticeInfoModel;
import org.rta.core.service.citizen.ResponseModel;
import org.rta.core.service.citizen.model.CitizenTokenModel;

public interface FinanceFreshRcService {
	public ResponseModel<CitizenTokenModel> saveFinanceFreshRCDtl(String token, FreshRcModel freshRcModel) throws VehicleRcNotFoundException, UserNotFoundException, NotFoundException;
	public FreshRcModel getFinanceFreshRCDtlByFinancerId(Long vehicleRc,Long financerId) throws VehicleRcNotFoundException, UserNotFoundException;
	public String updateFreshRcStep(int step,Long vehicleRc);
	public List<FreshRcModel> freshRcAppListForApproverType(Long userType, Status status) ;
	public FreshRcModel freshRcApproveReject(String prOrTr,Long userId, Status status) ;
    ShowcaseNoticeInfoModel getShowcaseNoticeInfoModel(ShowcaseInfoRequestModel m) throws VehicleRcNotFoundException, UserNotFoundException;
    FreshRcModel getFinanceFreshRCDtl(Long vehicleRc, String financerAadharNumber) throws VehicleRcNotFoundException, UserNotFoundException;
    public FreshRcModel getFinanceFreshRCDtlByRc(Long vehicleRc) throws VehicleRcNotFoundException;
	public SaveUpdateResponse saveFinanceShowCauseDetails(String applicationNumber, FreshRcModel freshRcModel) ;
	FreshRcModel getFinanceFreshRCDtlByAppNumber(String appNumber);
	public List<FreshRcModel> getOpenedFreshRc();
}
