/*
AssemblX: A three step assembly process for up to 25 genes

To express large sets of proteins in yeast or other host organisms, we
have developed a cloning framework which allows the modular cloning of
up to 25 genes into one circular artificial yeast chromosome.
	
AssemblXWeb assists the user with all design and assembly 
steps and therefore greatly reduces the time required to complete complex 
assemblies.
	
Copyright (C) 2016,  gremmels(at)mpimp-golm.mpg.de

AssemblXWeb is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>

Contributors:
gremmels(at)mpimp-golm.mpg.de - initial API and implementation
*/
package mpimp.assemblxweb.db.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

import mpimp.assemblxweb.db.AssemblXWebModel;
import mpimp.assemblxweb.db.OperatorRecord;
import mpimp.assemblxweb.hibernate.HibernateSessionFactory;
import mpimp.assemblxweb.util.AssemblXException;

public class AssemblXWebDBUtil {

	@SuppressWarnings("unchecked")
	public static Boolean readUserData(AssemblXWebModel model) throws AssemblXException {
		Session hibernateSession = null;
		try {
			hibernateSession = HibernateSessionFactory.getSession();
			hibernateSession.beginTransaction();

			SQLQuery query = hibernateSession
					.createSQLQuery("select us.id as operatorId, " + "us.login_name as login, us.password as password,"
							+ " us.first_name as firstName, us.last_name as lastName "
							+ "from user us where us.login_name = :loginName")
					.addScalar("operatorId", IntegerType.INSTANCE).addScalar("login", StringType.INSTANCE)
					.addScalar("password", StringType.INSTANCE).addScalar("firstName", StringType.INSTANCE)
					.addScalar("lastName", StringType.INSTANCE);

			query.setString("loginName", model.getOperator().getLogin());
			query.setResultTransformer(Transformers.aliasToBean(OperatorRecord.class));
			ArrayList<OperatorRecord> operatorRecords = new ArrayList<OperatorRecord>();
			operatorRecords = (ArrayList<OperatorRecord>) query.list();
			hibernateSession.getTransaction().commit();
			if (operatorRecords.size() == 1) {
				model.setOperator(operatorRecords.get(0));
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
//			if (hibernateSession.getTransaction() != null && hibernateSession.getTransaction().isActive()) {
//				hibernateSession.getTransaction().rollback();
//			}
			String message = "Error during reading user data from database. " + e.getMessage();
			logger_.error(message);
			throw new AssemblXException(message, AssemblXWebDBUtil.class);
		} finally {
			HibernateSessionFactory.closeSession();
		}
	}

	public static void enterJ5AccountCredentials(AssemblXWebModel model) throws Exception {
		Session hibernateSession = null;
		try {
			hibernateSession = HibernateSessionFactory.getSession();
			hibernateSession.beginTransaction();

			SQLQuery query = hibernateSession
					.createSQLQuery("insert into user (login_name, password)"
							+ " values (:loginName, :password)");
//			query.setString("firstName", model.getOperator().getFirstName());
//			query.setString("lastName", model.getOperator().getLastName());
			query.setString("loginName", model.getOperator().getLogin());
			query.setString("password", model.getOperator().getPassword());

			if (query.executeUpdate() != 1) {
				if (hibernateSession.getTransaction() != null && hibernateSession.getTransaction().isActive()) {
					hibernateSession.getTransaction().rollback();
				}
				String message = "Insertion of j5 credentials failed!";
				throw new AssemblXException(message, AssemblXWebDBUtil.class);
			}
			hibernateSession.getTransaction().commit();
		} catch (Exception e) {
//			if (hibernateSession.getTransaction() != null && hibernateSession.getTransaction().isActive()) {
//				hibernateSession.getTransaction().rollback();
//			}
			String message = "Error during entering of j5 credentials. " + e.getStackTrace();
			logger_.error(message);
			throw new AssemblXException(message, AssemblXWebDBUtil.class);
		} finally {
		 	HibernateSessionFactory.closeSession();
		}
	}

	@SuppressWarnings("unchecked")
	public static Boolean isSessionValid(AssemblXWebModel model) throws AssemblXException {
		Session hibernateSession = null;
		try {
			hibernateSession = HibernateSessionFactory.getSession();
			hibernateSession.beginTransaction();

			String queryString = "select expire from j5session where user_id = :userId";
			SQLQuery query = hibernateSession.createSQLQuery(queryString);
			query.setInteger("userId", model.getOperator().getOperatorId());

			ArrayList<Timestamp> result = (ArrayList<Timestamp>) query.list();
			hibernateSession.getTransaction().commit();
			Timestamp expireTimestamp;
			if (result.size() == 1) {
				expireTimestamp = result.get(0);
				Calendar currentCalendar = Calendar.getInstance();
				currentCalendar.setTime(new Date());

				int compareResult = expireTimestamp.compareTo(currentCalendar.getTime());

				if (compareResult > 0) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} catch (Exception e) {
//			if (hibernateSession.getTransaction() != null && hibernateSession.getTransaction().isActive()) {
//				hibernateSession.getTransaction().rollback();
//			}
			String message = "Error during j5 session check. " + e.getMessage();
			logger_.error(message);
			throw new AssemblXException(message, AssemblXWebDBUtil.class);
		} finally {
			HibernateSessionFactory.closeSession();
		}
	}

//	@SuppressWarnings("unchecked")
//	public static void readJ5SessionId(AssemblXWebModel model) throws AssemblXException {
//		Session hibernateSession = null;
//		try {
//			hibernateSession = HibernateSessionFactory.getSession();
//			hibernateSession.beginTransaction();
//
//			String queryString = "select session_id from j5session where user_id = :userId";
//
//			SQLQuery query = hibernateSession.createSQLQuery(queryString);
//			query.setInteger("userId", model.getOperator().getOperatorId());
//
//			ArrayList<String> result = (ArrayList<String>) query.list();
//
//			if (result.size() == 1) {
//				model.setJ5SessionId(result.get(0));
//			}
//			hibernateSession.getTransaction().commit();
//		} catch (Exception e) {
////			if (hibernateSession.getTransaction() != null && hibernateSession.getTransaction().isActive()) {
////				hibernateSession.getTransaction().rollback();
////			}
//			String message = "Error during reading j5 session id from database. " + e.getMessage();
//			logger_.error(message);
//			throw new AssemblXException(message, AssemblXWebDBUtil.class);
//		} finally {
//			HibernateSessionFactory.closeSession();
//		}
//	}

	public static void insertOrUpdateJ5Session(AssemblXWebModel model) throws Exception {
		Session hibernateSession = null;
		try {
			hibernateSession = HibernateSessionFactory.getSession();
			hibernateSession.beginTransaction();

			String queryString = "select session_id from j5session where user_id = :userId";

			SQLQuery query = hibernateSession.createSQLQuery(queryString);
			query.setInteger("userId", model.getOperator().getOperatorId());

			ArrayList<String> result = (ArrayList<String>) query.list();
			Boolean sessionIdPresent = false;
			if (result.size() == 1) {
				sessionIdPresent = true;
			}
			hibernateSession.getTransaction().commit();
			// ////////////
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

			Calendar currentCalendar = Calendar.getInstance();
			currentCalendar.add(Calendar.HOUR_OF_DAY, 10);

			String dateString = dateFormat.format(currentCalendar.getTime());

			if (sessionIdPresent == false) {
				// we will have to insert a session id
				try {
					hibernateSession = HibernateSessionFactory.getSession();
					hibernateSession.beginTransaction();
				} catch (Exception e) {
//					if (hibernateSession.getTransaction() != null && hibernateSession.getTransaction().isActive()) {
//						hibernateSession.getTransaction().rollback();
//					}
					String message = "Error during update of j5 session data. " + e.getMessage();
					logger_.error(message);
					throw new AssemblXException(message, AssemblXWebDBUtil.class);
				}

				queryString = "insert into j5session (session_id, expire, user_id)"
						+ "values (:sessionId, :expire, :userId)";

				query = hibernateSession.createSQLQuery(queryString);
				query.setString("sessionId", model.getJ5SessionId());
				query.setString("expire", dateString);
				query.setInteger("userId", model.getOperator().getOperatorId());

				if (query.executeUpdate() != 1) {
//					if (hibernateSession.getTransaction() != null && hibernateSession.getTransaction().isActive()) {
//						hibernateSession.getTransaction().rollback();
//					}
					String message = "Insertion of new j5 session id failed!";
					logger_.error(message);
					throw new AssemblXException(message, AssemblXWebDBUtil.class);
				}
				hibernateSession.getTransaction().commit();
			} else {
				// we will have to update the session id
				try {
					hibernateSession = HibernateSessionFactory.getSession();
					hibernateSession.beginTransaction();
				} catch (Exception e) {
//					if (hibernateSession.getTransaction() != null && hibernateSession.getTransaction().isActive()) {
//						hibernateSession.getTransaction().rollback();
//					}
					String message = "Error during update of j5 session data. " + e.getMessage();
					logger_.error(message);
					throw new AssemblXException(message, AssemblXWebDBUtil.class);
				} 

				queryString = "update j5session set session_id = :sessionId, expire = :expire "
						+ "where user_id = :userId";
				query = hibernateSession.createSQLQuery(queryString);
				query.setString("sessionId", model.getJ5SessionId());
				query.setString("expire", dateString);
				query.setInteger("userId", model.getOperator().getOperatorId());

				if (query.executeUpdate() != 1) {
//					if (hibernateSession.getTransaction() != null && hibernateSession.getTransaction().isActive()) {
//						hibernateSession.getTransaction().rollback();
//					}
					String message = "Insertion of new j5 session id failed!";
					logger_.error(message);
					throw new AssemblXException(message, AssemblXWebDBUtil.class);
				}
				hibernateSession.getTransaction().commit();
			}
		} catch (Exception e) {
//			if (hibernateSession.getTransaction() != null && hibernateSession.getTransaction().isActive()) {
//				hibernateSession.getTransaction().rollback();
//			}
			String message = "Error during update of j5 session data. " + e.getMessage();
			logger_.error(message);
			throw new AssemblXException(message, AssemblXWebDBUtil.class);
		} finally {
			HibernateSessionFactory.closeSession();
		}

	}

	private static final Logger logger_ = LogManager.getLogger();
}
