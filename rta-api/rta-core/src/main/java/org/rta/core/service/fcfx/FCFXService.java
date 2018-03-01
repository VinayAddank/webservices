package org.rta.core.service.fcfx;

import org.rta.core.exception.UserNotFoundException;
import org.rta.core.exception.VehicleRcNotFoundException;
import org.rta.core.model.certificate.CFXModel;
import org.rta.core.model.certificate.CFXNoticeModel;
import org.rta.core.service.citizen.ResponseModel;
import org.rta.core.service.fcfx.exception.FitnessExpiredException;
import org.rta.core.service.fcfx.exception.FitnessNotFoundException;

public interface FCFXService {
    public ResponseModel<?> createAppInCitizen(String prNumber, Long userId, String token) throws VehicleRcNotFoundException, FitnessNotFoundException, FitnessExpiredException;

    void saveCFXNoteDetails(CFXModel cfxModel, Long userId) throws UserNotFoundException, VehicleRcNotFoundException;

    CFXNoticeModel getCFXNoteDetails(String prNumber) throws UserNotFoundException, VehicleRcNotFoundException;
}
