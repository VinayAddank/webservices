package org.rta.core.service.master;

import java.util.List;

import org.rta.core.model.master.DocTypesModel;

public interface DocTypesService {

    public abstract List<DocTypesModel> getAll();

    public List<Long> getMandatoryDocTypesIds();

}
