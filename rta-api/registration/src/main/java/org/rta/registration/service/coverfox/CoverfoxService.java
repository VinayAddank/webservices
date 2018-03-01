/**
 * 
 */
package org.rta.registration.service.coverfox;

import org.rta.core.model.insurance.CfxTxnDtlModel;

/**
 * @author arun.verma
 *
 */
public interface CoverfoxService {
    
    public String getModels() throws RuntimeException;
    
    public String getModelVariants() throws RuntimeException;
    
    /**
     * getQuotes
     * 
     * @return
     * @throws RuntimeException
     */
    public String getQuotes() throws RuntimeException;

    /**
     * refreshQuote
     * 
     * @param quoteId
     * @param insurerSlug
     * @return
     * @throws RuntimeException
     */
    public String refreshQuote(String quoteId, String insurerSlug) throws RuntimeException;

    /**
     * confirmQuote
     * 
     * @param quoteId
     * @param insurerSlug
     * @return
     * @throws RuntimeException
     */
    public String confirmQuote(String quoteId, String insurerSlug) throws RuntimeException;
    
    /**
     * getStates
     * 
     * @param insurerSlug
     * @return
     * @throws RuntimeException
     */
    public String getStates(String insurerSlug) throws RuntimeException;
    
    /**
     * getCities
     * 
     * @param insurerSlug
     * @return
     * @throws RuntimeException
     */
    public String getCities(String insurerSlug) throws RuntimeException;
    
    /**
     * getLoanProviders
     * 
     * @param insurerSlug
     * @return
     * @throws RuntimeException
     */
    public String getLoanProviders(String insurerSlug) throws RuntimeException;

    /**
     * buy insurance
     * 
     * @param insurerSlug
     * @param transactionId
     * @return
     * @throws RuntimeException
     */
    public String buy(String insurerSlug, String transactionId) throws RuntimeException;

    /**
     * retrieveConfirmedQuote
     * 
     * @param transactionId
     * @return
     * @throws RuntimeException
     */
    public String retrieveConfirmedQuote(String transactionId) throws RuntimeException;

    /**
     * retrieveProposalForm
     * 
     * @param transactionId
     * @return
     * @throws RuntimeException
     */
    public String retrieveProposalForm(String transactionId) throws RuntimeException;

    /**
     * retrieveStatusDetails
     * 
     * @param transactionId
     * @return
     * @throws RuntimeException
     */
    public String retrieveStatusDetails(String transactionId) throws RuntimeException;
    
    /**
     * prePayment
     * 
     * @param transactionId
     * @return
     * @throws RuntimeException
     */
    public String prePayment(String transactionId) throws RuntimeException;
    
    /**
     * postPayment
     * 
     * @param CfxTxnDtlModel cfxTxnDtlMode
     * @return
     * @throws RuntimeException
     */
    public String postPayment(CfxTxnDtlModel cfxTxnDtlModel) throws RuntimeException;
    
    /**
     * get policy details
     * 
     * @param transactionId
     * @return
     * @throws RuntimeException
     */
    public String policyAPI(String transactionId) throws RuntimeException;

}
