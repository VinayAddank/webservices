/**
 * 
 */
package org.rta.registration.service.coverfox.helper;

import org.rta.core.enums.Status;
import org.rta.core.model.insurance.CfxTxnDtlModel;
import org.rta.coverfox.model.CustomerCfx;
import org.rta.coverfox.model.InsAddOn;
import org.rta.coverfox.model.PaymentCfxModel;
import org.rta.coverfox.model.VehicleCfx;

/**
 * @author arun.verma
 *
 */
public class CoverfoxHelper {

    public static VehicleCfx getVehicleDetails() {
        VehicleCfx vehicle = new VehicleCfx();
        vehicle.setChassisNo("757847584");
        vehicle.setEngineNo("785784758");
        vehicle.setIsCNGOrLPGFitted(0);
        vehicle.setIsCompanyCar("0");
        vehicle.setIsFinanced("0");
        vehicle.setManufacturingDate("01-08-2015");
        vehicle.setNewPolicyStartDate("25-08-2016");
        vehicle.setPastPolicyExpiryDate("24-08-2016");
        vehicle.setRegistrationDate("01-08-2015");
        vehicle.setRegistrationNumber(new String[] {"MH", "01", "0", "0"});
        return vehicle;
    }

    public static CustomerCfx getCustomerDetails() {
        CustomerCfx cust = new CustomerCfx();
        cust.setAddCityCode("MUMBAI");
        cust.setAddConsolidatedAddress("21, B Park, M G Road");
        cust.setAddDistrict("");
        cust.setAddPinCode("400007");
        cust.setAddSame("1");
        cust.setAddStateCode("MH");
        cust.setCompName("");
        cust.setCompPan("");
        cust.setCustDOB("12-09-1988");
        cust.setCustEmail("akv@gmail.com");
        cust.setCustGender("Male");
        cust.setCustMarritalStatus("Unmarried");
        cust.setCustName("Arun Kumar Verma");
        cust.setCustOccupation("IT");
        cust.setCustPan("");
        cust.setCustPhone("7838118205");
        cust.setIsCompanyCar("0");
        cust.setNomineeAge("56");
        cust.setNomineeName("Ram Murat Verma");
        cust.setNomineeRelationship("Father");
        return cust;
    }

    public static InsAddOn getAddOn() {
        InsAddOn addOn = new InsAddOn();
        addOn.setAddonIs247RoadsideAssistance(0);
        addOn.setAddonIsAntiTheftFitted(0);
        addOn.setAddonIsDepreciationWaiver(0);
        addOn.setAddonIsDriveThroughProtected(0);
        addOn.setAddonIsEngineProtector(0);
        addOn.setAddonIsInvoiceCover(0);
        addOn.setAddonIsKeyReplacement(0);
        addOn.setAddonIsNcbProtection(0);
        addOn.setExtraIsAntiTheftFitted(0);
        addOn.setExtraIsLegalLiability(0);
        addOn.setExtraIsMemberOfAutoAssociation(0);
        addOn.setExtraPaPassenger(0);
        addOn.setIdvElectrical(0);
        addOn.setIdvNonElectrical(0);
        addOn.setIsNCBCertificate(0);
        addOn.setVoluntaryDeductible(0);
        return addOn;
    }
    
    public static PaymentCfxModel getPaymentData(CfxTxnDtlModel cfxTxnDtlModel){
        PaymentCfxModel paymentCfxModel = new PaymentCfxModel();
        paymentCfxModel.setAmount(String.valueOf(cfxTxnDtlModel.getAmount()));
        paymentCfxModel.setDateTime(cfxTxnDtlModel.getDateTime());
        paymentCfxModel.setPaymentId(cfxTxnDtlModel.getCfxPaymentId());
        paymentCfxModel.setPaymentToken(cfxTxnDtlModel.getPgPaymentToken());
        paymentCfxModel.setStatus(Status.getStatus(cfxTxnDtlModel.getPaymentStatus()).getLabel());
        paymentCfxModel.setTransactionId(cfxTxnDtlModel.getCfxTxnId());
        return paymentCfxModel;
    }
}
