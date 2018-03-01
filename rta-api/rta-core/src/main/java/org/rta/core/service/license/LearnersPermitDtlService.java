package org.rta.core.service.license;

import java.util.List;

import org.rta.core.exception.NotFoundException;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.license.LearnersPermitDtlModel;

public interface LearnersPermitDtlService {
	
	public List<LearnersPermitDtlModel> getLearnersPermitDtl(Long pk) throws NotFoundException;
	
	public SaveUpdateResponse saveLearnersPermitDetail( List<LearnersPermitDtlModel> models, String aadharNumber, String userName);

	public SaveUpdateResponse updateLearnersPermitDetail(List<LearnersPermitDtlModel> models, String aadharNumber,	String userName);

}
