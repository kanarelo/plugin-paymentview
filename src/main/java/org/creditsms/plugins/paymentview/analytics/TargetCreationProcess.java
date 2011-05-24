package org.creditsms.plugins.paymentview.analytics;



import java.util.Date;
import java.util.List;

import org.creditsms.plugins.paymentview.data.domain.Account;
import org.creditsms.plugins.paymentview.data.domain.Client;
import org.creditsms.plugins.paymentview.data.domain.ServiceItem;
import org.creditsms.plugins.paymentview.data.domain.Target;
import org.creditsms.plugins.paymentview.data.repository.AccountDao;
import org.creditsms.plugins.paymentview.data.repository.TargetDao;

public abstract class TargetCreationProcess {
	
	
	//> DAOs
	TargetDao targetDao;
    AccountDao accountDao;
	
	//> FIELDS
	protected Client client;
	protected Date targetStartDate;
	protected Date targetEndDate;
	protected ServiceItem serviceItem;
	protected Account account;
	protected Target target;
	protected List<Account> inactiveAccounts;
	protected List<Account> totalListAccounts;

	public TargetDao getTargetDao() {
		return targetDao;
	}

	public void setTargetDao(TargetDao targetDao) {
		this.targetDao = targetDao;
	}

	public AccountDao getAccountDao() {
		return accountDao;
	}

	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	public TargetCreationProcess() {
		// TODO Auto-generated constructor stub
	};
	
	public abstract void createTarget();
	
	/*public abstract void createAccount();*/
	
	
	
	//ACCESSORS
	public Client getClient(){
		return client;
	}
	
	public void setClient(Client client) {
		this.client = client;
	}
	
	public Date getTargetStartDate() {
		return targetStartDate;
	}

	public void setTargetStartDate(Date targetStartDate) {
		this.targetStartDate = targetStartDate;
	}

	public Date getTargetEndDate() {
		return targetEndDate;
	}

	public void setTargetEndDate(Date targetEndDate) {
		this.targetEndDate = targetEndDate;
	}

	public ServiceItem getServiceItem() {
		return serviceItem;
	}

	public void setServiceItem(ServiceItem serviceItem) {
		this.serviceItem = serviceItem;
	}
	
	public Target getTarget() {
		return target;
	}

	public void setTarget(Target target) {
		this.target = target;
	}
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	public List<Account> getInactiveAccounts() {
		return inactiveAccounts;
	}

	public void setInactiveAccounts(List<Account> inactiveAccounts) {
		this.inactiveAccounts = inactiveAccounts;
	}

	public List<Account> getTotalListAccounts() {
		return totalListAccounts;
	}

	public void setTotalListAccounts(List<Account> totalListAccounts) {
		this.totalListAccounts = totalListAccounts;
	}
}
