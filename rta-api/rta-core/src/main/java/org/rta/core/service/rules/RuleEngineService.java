package org.rta.core.service.rules;

import java.util.List;

import org.rta.core.model.docs.DocPermissionModel;
import org.rta.core.model.master.DocTypesModel;


public interface RuleEngineService {

    public List<DocTypesModel> getAttachments(DocPermissionModel model);
    
}
