package org.rta.core.service.master;

import java.util.List;

import org.rta.core.model.master.AddressProofTypeModel;

/**
 *	@Author sohan.maurya created on Jul 12, 2016.
 */
public interface AddressProofTypeService {

    public void create(AddressProofTypeModel model);

    public List<AddressProofTypeModel> getAll();

}
