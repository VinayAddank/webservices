/**
 * 
 */
package org.rta.core.service.rsc;

import org.rta.core.service.citizen.ResponseModel;

/**
 * @author arun.verma
 *
 */
public interface RSCService {
    public ResponseModel<?> createAppInCitizen(String prNumber, Long userId, String token);
}