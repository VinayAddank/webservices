package org.rta.core.dao.license.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.license.LicenseHolderDtlsDAO;
import org.rta.core.entity.licence.LicenseHolderDtlsEntity;
import org.rta.core.model.license.ForgetLicenseModel;
import org.springframework.stereotype.Repository;

@Repository
public class LicenseHolderDtlsDAOImpl extends  BaseDAO<LicenseHolderDtlsEntity> implements LicenseHolderDtlsDAO{

	public LicenseHolderDtlsDAOImpl() {
		super(LicenseHolderDtlsEntity.class);
	}

	@Override
	public LicenseHolderDtlsEntity getLicenseHolderDtls(String aadharNo) {
		Criteria criteria=getSession().createCriteria(getPersistentClass())
				.add(Restrictions.eq("aadhaarNo", aadharNo));
		return (LicenseHolderDtlsEntity) criteria.uniqueResult();
	}

	@Override
	public LicenseHolderDtlsEntity getLicenseHolderDtlsByPassportNumber(String passportNumber) {
		Criteria criteria=getSession().createCriteria(getPersistentClass())
				.add(Restrictions.eq("licenceIdentityId.passportNumber", passportNumber));
		return (LicenseHolderDtlsEntity) criteria.uniqueResult();
	}
	
	@Override
	public LicenseHolderDtlsEntity getLicenseHolderDtls(ForgetLicenseModel forgetLicenseModel) throws ParseException  {
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatterWithoutTime = new SimpleDateFormat("yyyy-MM-dd");
		Date dob = sdf.parse(formatterWithoutTime.format(forgetLicenseModel.getDob()));
		Criteria criteria=getSession().createCriteria(getPersistentClass(),"licenseHolderDtlsEntity")
				.add(Restrictions.eq("aadhaarNo", forgetLicenseModel.getAadharNumber())).add(Restrictions.eq("dateOfBirth",dob));
		if(criteria.list().size()>=2){
			
		criteria.createAlias("licenseHolderDtlsEntity.rtaOfficeId", "rtaOfficeId");
		
		if(forgetLicenseModel.getFirstName()!=null){
			criteria.add(Restrictions.eq("firstName",forgetLicenseModel.getFirstName().trim()));
			
		}
		if(forgetLicenseModel.getLastName()!=null){
			criteria.add(Restrictions.eq("lastName",forgetLicenseModel.getLastName().trim()));
			
		}
		if(forgetLicenseModel.getFatherName()!=null){
			criteria.add(Restrictions.eq("guardianName",forgetLicenseModel.getFatherName().trim()));
			
		}
		if(forgetLicenseModel.getRtaOffice()!=null){
			criteria.add(Restrictions.eq("rtaOfficeId.code",forgetLicenseModel.getRtaOffice().trim()));
			
		}
		}
		
		return (LicenseHolderDtlsEntity) criteria.uniqueResult();
	}
	
	@Override
	public LicenseHolderDtlsEntity getLicenseHolderDtlsByHolderId(Long holderId) {
		Criteria criteria=getSession().createCriteria(getPersistentClass())
				.add(Restrictions.eq("licenceHolderId", holderId));
		return (LicenseHolderDtlsEntity) criteria.uniqueResult();
	}

}
