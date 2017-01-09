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
package mpimp.assemblxweb.hibernate;

import mpimp.assemblxweb.util.AssemblXWebProperties;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Configures and provides access to Hibernate sessions, tied to the current
 * thread of execution. Follows the Thread Local Session pattern, see
 * {@link http://hibernate.org/42.html }.
 */
public class HibernateSessionFactory {

	/**
	 * Location of hibernate.cfg.xml file. Location should be on the classpath
	 * as Hibernate uses #resourceAsStream style lookup for its configuration
	 * file. The default classpath location of the hibernate config file is in
	 * the default package. Use #setConfigFile() to update the location of the
	 * configuration file for the current session.
	 */
	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
	private static org.hibernate.SessionFactory sessionFactory;

	private static Configuration configuration = new Configuration();
	private static ServiceRegistry serviceRegistry;

	static {
		try {
			setConfigurationProperties();
			serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
					.buildServiceRegistry();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		} catch (Exception e) {
			System.err.println("%%%% Error Creating SessionFactory %%%%");
			e.printStackTrace();
		}
	}

	private HibernateSessionFactory() {
	}

	/**
	 * Returns the ThreadLocal Session instance. Lazy initialize the
	 * <code>SessionFactory</code> if needed.
	 *
	 * @return Session
	 * @throws HibernateException
	 */
	public static Session getSession() throws Exception {
		Session session = (Session) threadLocal.get();

		if (session == null || !session.isOpen()) {
			if (sessionFactory == null) {
				rebuildSessionFactory();
			}
			session = (sessionFactory != null) ? sessionFactory.openSession() : null;
			threadLocal.set(session);
		}

		return session;
	}

	/**
	 * Rebuild hibernate session factory
	 *
	 */
	public static void rebuildSessionFactory() throws Exception {
		setConfigurationProperties();
		configuration.configure();
		serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
				.buildServiceRegistry();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}

	/**
	 * Close the single hibernate session instance.
	 *
	 * @throws HibernateException
	 */
	public static void closeSession() throws HibernateException {
		Session session = (Session) threadLocal.get();
		threadLocal.set(null);

		if (session != null) {
			session.close();
		}
	}

	/**
	 * return session factory
	 *
	 */
	public static org.hibernate.SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	private static void setConfigurationProperties() throws Exception {
		configuration.setProperty("hibernate.connection.username",
				AssemblXWebProperties.getInstance().getProperty("hibernateConnectionUsername"));
		configuration.setProperty("hibernate.connection.url", createConnectionUrl());
		configuration.setProperty("hibernate.dialect",
				AssemblXWebProperties.getInstance().getProperty("hibernateDialect"));
		configuration.setProperty("hibernate.myeclipse.connection.profile",
				AssemblXWebProperties.getInstance().getProperty("hibernateConnectionProfile"));
		configuration.setProperty("hibernate.connection.password",
				AssemblXWebProperties.getInstance().getProperty("hibernateConnectionPassword"));
		configuration.setProperty("hibernate.connection.driver_class",
				AssemblXWebProperties.getInstance().getProperty("hibernateConnectionDriverClass"));
		configuration.setProperty("hibernate.c3p0.max_size",
				AssemblXWebProperties.getInstance().getProperty("hibernateC3POMaxSize"));
		configuration.setProperty("hibernate.c3p0.min_size",
				AssemblXWebProperties.getInstance().getProperty("hibernateC3POMinSize"));
		configuration.setProperty("hibernate.c3p0.timeout",
				AssemblXWebProperties.getInstance().getProperty("hibernateC3POTimeout"));
		configuration.setProperty("hibernate.c3p0.max_statements",
				AssemblXWebProperties.getInstance().getProperty("hibernateC3POMaxStatements"));
		configuration.setProperty("hibernate.current_session_context_class",
				AssemblXWebProperties.getInstance().getProperty("hibernateCurrentSessionContextClass"));
		configuration.setProperty("hibernate.c3p0.validate",
				AssemblXWebProperties.getInstance().getProperty("hibernateC3POValidate"));
		configuration.setProperty("connection.provider_class",
				AssemblXWebProperties.getInstance().getProperty("connectionProviderClass"));
		configuration.setProperty("hibernate.c3p0.preferredTestQuery",
				AssemblXWebProperties.getInstance().getProperty("hibernateC3POPreferredTestQuery"));
		configuration.setProperty("hibernate.c3p0.testConnectionOnCheckin",
				AssemblXWebProperties.getInstance().getProperty("hibernateC3POTestConnectionOnCheckin"));
		configuration.setProperty("hibernate.show_sql",
				AssemblXWebProperties.getInstance().getProperty("hibernateShowSQL"));

	}

	private static String createConnectionUrl() {
		String connectionUrl = "jdbc:mysql://";
		connectionUrl += AssemblXWebProperties.getInstance().getProperty("databaseServerName");
		connectionUrl += "/";
		connectionUrl += AssemblXWebProperties.getInstance().getProperty("databaseName");
		connectionUrl += "?useSSL=false&autoReconnect=true";
		return connectionUrl;
	}

	/**
	 * return hibernate configuration
	 *
	 */
	public static Configuration getConfiguration() {
		return configuration;
	}

}