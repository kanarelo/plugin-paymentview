package net.frontlinesms.payment;

import java.math.BigDecimal;

import net.frontlinesms.messaging.sms.SmsService;

import org.creditsms.plugins.paymentview.data.domain.Account;
import org.creditsms.plugins.paymentview.data.repository.AccountDao;
import org.creditsms.plugins.paymentview.data.repository.ClientDao;
import org.creditsms.plugins.paymentview.data.repository.IncomingPaymentDao;
import org.smslib.CService;

public interface PaymentService {
	void setCService(CService cService);
	CService getCService();
	
	void setSmsService(SmsService smsService);
	SmsService getSmsService();
	
	void setClientDao(ClientDao clientDao);
	void setIncomingPaymentDao(IncomingPaymentDao incomingPaymentDao);
	void setAccountDao(AccountDao accountDao);
	
	void makePayment(Account account, BigDecimal amount) throws PaymentServiceException;
	void checkBalance() throws PaymentServiceException;
}
