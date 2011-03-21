package org.eayrad.api.persistence;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtil {

	private static SessionFactory sessionFactory;
	private static ThreadLocal<Session> session;
	private static String configurationFile;

	static {
		try {
			session = new ThreadLocal<Session>();
			AnnotationConfiguration configuration = new AnnotationConfiguration();
			if (configurationFile != null)
				configuration.configure(configurationFile);
			else
				configuration.configure();
			sessionFactory = configuration.buildSessionFactory();
		}
		catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erro ao conectar-se com o banco", "Erro ao conectar-se com o banco"));
		}
	}

	public static void inicialize() {
		try {
			sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Session getSession() {
		Session s = null;
		try {
			if (sessionFactory == null) {
				inicialize();
				closeSession();
			}
			s = session.get();
			if (s == null) {
				s = sessionFactory.openSession();
				session.set(s);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	public static void closeSession() {
		try {
			Session s = session.get();
			session.set(null);
			if (s != null && s.isOpen())
				s.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
