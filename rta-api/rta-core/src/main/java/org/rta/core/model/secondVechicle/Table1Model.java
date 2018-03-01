package org.rta.core.model.secondVechicle;

import java.util.List;


public class Table1Model {

	private String APPLICANT_FNAME;
	private String APPLIACNT_LNAME;
	private String PG_NAME;
	private String DISP_NAME;
	private String REGN_NO;
	private String ADDRESS1;
	private String ADDRESS2;
	private String ADDRESS3;
	private String FLAG;
	List<Table1Model> Table1;


	public List<Table1Model> getTable1() {
		return Table1;
	}

	public void setTable1(List<Table1Model> table1) {
		Table1 = table1;
	}


	public String getAPPLICANT_FNAME() {
		return APPLICANT_FNAME;
	}

	public void setAPPLICANT_FNAME(String aPPLICANT_FNAME) {
		APPLICANT_FNAME = aPPLICANT_FNAME;
	}


	public String getAPPLIACNT_LNAME() {
		return APPLIACNT_LNAME;
	}

	public void setAPPLIACNT_LNAME(String aPPLIACNT_LNAME) {
		APPLIACNT_LNAME = aPPLIACNT_LNAME;
	}


	public String getPG_NAME() {
		return PG_NAME;
	}

	public void setPG_NAME(String pG_NAME) {
		PG_NAME = pG_NAME;
	}


	public String getDISP_NAME() {
		return DISP_NAME;
	}

	public void setDISP_NAME(String dISP_NAME) {
		DISP_NAME = dISP_NAME;
	}


	public String getREGN_NO() {
		return REGN_NO;
	}

	public void setREGN_NO(String rEGN_NO) {
		REGN_NO = rEGN_NO;
	}


	public String getADDRESS1() {
		return ADDRESS1;
	}

	public void setADDRESS1(String aDDRESS1) {
		ADDRESS1 = aDDRESS1;
	}


	public String getADDRESS2() {
		return ADDRESS2;
	}

	public void setADDRESS2(String aDDRESS2) {
		ADDRESS2 = aDDRESS2;
	}


	public String getADDRESS3() {
		return ADDRESS3;
	}

	public void setADDRESS3(String aDDRESS3) {
		ADDRESS3 = aDDRESS3;
	}


	public String getFLAG() {
		return FLAG;
	}

	public void setFLAG(String fLAG) {
		FLAG = fLAG;
	}



}
