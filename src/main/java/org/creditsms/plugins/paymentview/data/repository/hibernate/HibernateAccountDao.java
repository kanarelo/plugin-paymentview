package org.creditsms.plugins.paymentview.data.repository.hibernate;
import java.util.List;

import net.frontlinesms.data.DuplicateKeyException;
import net.frontlinesms.data.repository.hibernate.BaseHibernateDao;

import org.creditsms.plugins.paymentview.data.domain.Account;
import org.creditsms.plugins.paymentview.data.repository.AccountDao;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;


public class HibernateAccountDao extends BaseHibernateDao<Account>  implements AccountDao{

	protected HibernateAccountDao() {
		super(Account.class);
	}
	
	public List<Account> getAllAcounts() {
		return super.getAll();
	}

	public List<Account> getAccountsByClientId(long id) {
		DetachedCriteria criteria = super.getCriterion();
		DetachedCriteria clientCriteria = criteria.createCriteria("client");
		clientCriteria.add(Restrictions.eq("id", id));
		return super.getList(criteria);
	}

	public Account getAccountById(long id) {
		DetachedCriteria criteria = super.getCriterion();
		criteria.add(Restrictions.eq("id", id));
		return super.getUnique(criteria);
	}

	public Account getAccountByAccountNumber(long accNumber) {
		DetachedCriteria criteria = super.getCriterion();
		criteria.add(Restrictions.eq("accNumber", accNumber));
		return super.getUnique(criteria);
	}

	public void deleteAccount(Account account) {
		super.delete(account);
	}

	public void saveAccount(Account account) throws DuplicateKeyException {
		super.save(account);
	}

	public void updateAccount(Account account) throws DuplicateKeyException {
		super.update(account);
	}

}
