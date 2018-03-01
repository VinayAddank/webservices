package org.rta.core.service.master;

import java.util.List;

import org.rta.core.model.master.PermitTypeModel;

public interface PermitTypeService {

    public List<PermitTypeModel> getPermitType(String vehicleClassDescCode);

  ///  public PermitTypeVehicleClassModel getPermitTypeVehicleClass(String trNumber);

    public List<PermitTypeModel> getPermitType();

}
