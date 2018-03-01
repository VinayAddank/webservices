package org.rta.core.service.user;

import org.rta.core.model.user.AddressModel;

public interface AddressService {

    public AddressModel findByUserId(Long id);
}
