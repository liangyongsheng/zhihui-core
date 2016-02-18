package com.zhihui.core.hibernate;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.zhihui.core.context.MyContext;

public class MyTransaction {
	private static Map<Long, MyTransaction> mpTransactions = new HashMap<Long, MyTransaction>();
	private HibernateTemplate hibernateTemplate;
	private Session session;
	private Transaction transaction;
	private int transactionCount;
	private static int TRYCNT = 30;

	public static Session getCurrentSession() {
		long threadId = Thread.currentThread().getId();
		MyTransaction myTransaction = mpTransactions.get(threadId);
		if (myTransaction == null)
			return null;
		else
			return myTransaction.session;
	}

	private MyTransaction() {
	}

	public static void beginTransaction() throws HibernateException {
		long threadId = Thread.currentThread().getId();
		MyTransaction myTransaction = mpTransactions.get(threadId);
		if (myTransaction == null)
			myTransaction = new MyTransaction();

		HibernateTemplate hibernateTemplate = myTransaction.hibernateTemplate;
		Session session = myTransaction.session;
		Transaction transaction = myTransaction.transaction;

		// hibernateTemplate
		if (hibernateTemplate == null) {
			try {
				hibernateTemplate = (HibernateTemplate) MyContext.getRootApplicationContext().getBean("hibernateTemplate");
			} catch (Throwable e) {
				throw new HibernateException(e);
			}
			if (hibernateTemplate == null)
				throw new HibernateException("hibernateTemplate is null.");
		}

		// session, transaction
		int i = 0;
		while (transaction == null && i < MyTransaction.TRYCNT) {
			try {
				if (session != null)
					transaction = session.beginTransaction();
				else {
					session = hibernateTemplate.getSessionFactory().openSession();
					transaction = session.beginTransaction();
				}
			} catch (Throwable e) {
			}

			if (transaction == null) {
				try {
					Thread.currentThread().wait(6 * 1000);
				} catch (Throwable ec) {
				}
			}
			i++;
		}

		if (transaction != null) {
			myTransaction = new MyTransaction();
			myTransaction.hibernateTemplate = hibernateTemplate;
			myTransaction.session = session;
			myTransaction.transaction = transaction;
			myTransaction.transactionCount++;
			mpTransactions.put(threadId, myTransaction);
		} else
			throw new HibernateException("can not open a transaction.");
	}

	/**
	 * N-nested transactions, may have some wrongs <br/>
	 * warinning:in one function, you can not use twice;
	 * 
	 * @throws HibernateException
	 */
	public static void endTransaction() throws HibernateException {
		long threadId = Thread.currentThread().getId();
		MyTransaction myTransaction = mpTransactions.get(threadId);
		if (myTransaction == null)
			return;

		myTransaction.transactionCount--;
		if (myTransaction.transactionCount == 0) {
			try {
				if (myTransaction.transaction != null) {
					myTransaction.transaction.commit();
					try {
						if (myTransaction.session != null)
							myTransaction.session.close();
					} catch (Throwable e) {
					}
					myTransaction.session = null;
					myTransaction.transaction = null;
					mpTransactions.remove(threadId);
				}
			} catch (Throwable e) {
				rollbackTransaction();
				throw new HibernateException(e);
			}
		} else if (myTransaction.transactionCount < 0) {
			myTransaction.transactionCount = 0;
			throw new HibernateException("already have commited or rollbacked.");
		}
	}

	public static void rollbackTransaction() {
		long threadId = Thread.currentThread().getId();
		MyTransaction myTransaction = mpTransactions.get(threadId);
		if (myTransaction == null)
			return;

		myTransaction.transactionCount = 0;
		try {
			if (myTransaction.transaction != null) {
				myTransaction.transaction.rollback();
				try {
					if (myTransaction.session != null)
						myTransaction.session.close();
				} catch (Throwable e) {
				}
				myTransaction.session = null;
				myTransaction.transaction = null;
				mpTransactions.remove(threadId);
			}
		} catch (Throwable e) {
		}
	}

	public static void setTimeoutOnTransaction(int arg0) {
		long threadId = Thread.currentThread().getId();
		MyTransaction myTransaction = mpTransactions.get(threadId);
		if (myTransaction == null)
			return;

		if (myTransaction.transaction != null)
			myTransaction.transaction.setTimeout(arg0);
	}
}
