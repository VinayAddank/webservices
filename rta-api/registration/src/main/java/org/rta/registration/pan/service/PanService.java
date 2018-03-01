package org.rta.registration.pan.service;

import java.io.FileNotFoundException;
import java.security.KeyStoreException;

import org.rta.registration.pan.model.PanDetailsModel;

public interface PanService {
	
	public PanDetailsModel getPanDetails(String PanNumber)throws KeyStoreException, FileNotFoundException, Exception;

}
