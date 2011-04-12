package org.creditsms.plugins.paymentview.data.repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.frontlinesms.data.DuplicateKeyException;

import org.creditsms.plugins.paymentview.data.domain.IncomingPayment;

/**
 * @author Roy
 * */
public interface IncomingPaymentDao {
	/**
	 * get and return an IncomingPayment with a specific id 
	 * */
	public IncomingPayment getIncomingPaymentById(long incomingPaymentId);
	
	/**
	 * returns all the incomingPayments in the system
	 * */
	public List<IncomingPayment> getAllIncomingPayments();
	

	/**
	 * returns all the incomingPayments in the system
	 * 
	 * @param startingIndex
	 * @param limit
	 * @return
	 */
	public List<IncomingPayment> getAllIncomingPayments(int startingIndex, int limit);
	
	/**
	 * returns IncomingPayment(s) within a given date range
	 * */
	public List<IncomingPayment> getIncomingPaymentsByDateRange(Calendar startDate, Calendar endDate);
	
	/**
	 * returns IncomingPayment(s) by time range
	 * */
	public List<IncomingPayment> getIncomingPaymentsByTimeRange(Date startTime, Date endtime);
	
	/**
	 * returns IncomingPayment(s) by client and by date
	 * */
	public List<IncomingPayment> getIncomingPaymentsByClientIdByDateRange(long clientId, Calendar startDate, Calendar endDate);
	
	/**
	 * returns IncomingPayment(s) by clientId
	 * */
	public List<IncomingPayment> getIncomingPaymentByClientId(long clientId);
	
	/**
	 * returns IncomingPayment(s) by accountId
	 * */
	public List<IncomingPayment> getIncomingPaymentsByAccountNumber(long accountId);
	
	/**
	 * returns IncomingPayment(s) by accountId by date ranges
	 * */
	public List<IncomingPayment> getIncomingPaymentsByAccountNumberByDateRange(long accountId, Calendar startDate, Calendar endDate);
	
	/**
	 * returns IncomingPayment(s) by accountId by time ranges
	 * */
	public List<IncomingPayment> getIncomingPaymentsByAccountNumberByTimeRange(long accountId, Date startDate, Date endDate);

	/**
	 * returns IncomingPayment(s) by payer 
	 * */
	public List<IncomingPayment> getIncomingPaymentsByPayer(String payer);

	/**
	 * returns IncomingPayment(s) by phone number
	 * */
	public List<IncomingPayment> getIncomingPaymentsByPhoneNo(String phoneNo);

	/**
	 * saves an IncomingPayment payment to the system
	 * */
	public void saveIncomingPayment(IncomingPayment incomingPayment) throws DuplicateKeyException;

	/**
	 * updates an IncomingPayment payment to the system
	 * */
	public void updateIncomingPayment(IncomingPayment incomingPayment) throws DuplicateKeyException;
	
	/**
	 * removes an IncomingPayment payment to the system
	 * */
	public void deleteIncomingPayment(IncomingPayment incomingPayment);
}