package demo.dl.server.model.proces;

import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

import com.google.apphosting.api.ApiProxy.UnknownException;

import demo.dl.server.model.bean.Pais;
import demo.dl.server.model.dao.PMF;
import demo.dl.server.model.logic.LogicPais;
import demo.dl.shared.BeanParametro;

public class MantPais {
	private static final Logger LOG = Logger.getLogger(MantPais.class
			.getName());

	public static Boolean insertarPais(Pais bean) throws UnknownException {
		if (bean.getOperacion().equalsIgnoreCase("I")
				&& bean.getIdPais() != null) {
			PersistenceManager pm = null;
			Transaction tx = null;
			try {
				pm = PMF.getPMF().getPersistenceManager();
				tx = pm.currentTransaction();
				tx.begin();			
				BeanParametro parametro = new BeanParametro();
				parametro.setBean(bean);
				parametro.setTipoOperacion(bean.getOperacion());
				LogicPais logic = new LogicPais(pm);
				Boolean resultado = logic.mantenimiento(parametro);
				if (resultado) {
					tx.commit();
					pm.close();
					return true;
				} else {
					tx.rollback();
					pm.close();
					return false;
				}
			} catch (Exception ex) {
				LOG.warning(ex.getMessage());
				LOG.info(ex.getLocalizedMessage());
				throw new UnknownException(ex.getMessage());
			} finally {
				if (!pm.isClosed()) {
					if (tx.isActive()) {
						tx.rollback();
					}
					pm.close();
				}
			}
		} else {
			throw new UnknownException("Verifique Catalogo de Servicio");
		}
	}

	public static Boolean actualizarPais(Pais bean)
			throws UnknownException {
		if (bean.getOperacion().equalsIgnoreCase("A")
				&& bean.getIdPais() != null) {
			PersistenceManager pm = null;
			Transaction tx = null;
			try {
				pm = PMF.getPMF().getPersistenceManager();
				tx = pm.currentTransaction();
				tx.begin();				
				BeanParametro parametro = new BeanParametro();
				LogicPais logic = new LogicPais(pm);
				parametro.setBean(bean);
				parametro.setTipoOperacion(bean.getOperacion());
				Boolean resultado = logic.mantenimiento(parametro);
				if (resultado) {
					tx.commit();
					pm.close();
					return true;
				} else {
					tx.rollback();
					pm.close();
					return false;
				}
			} catch (Exception ex) {
				LOG.warning(ex.getMessage());
				LOG.info(ex.getLocalizedMessage());
				throw new UnknownException(ex.getMessage());
			} finally {
				if (!pm.isClosed()) {
					if (tx.isActive()) {
						tx.rollback();
					}
					pm.close();
				}
			}
		} else {
			throw new UnknownException("Verifique Catalogo de Servicio");
		}
	}

	public static Boolean eliminarPais(Pais bean) throws UnknownException {
		if (bean.getOperacion().equalsIgnoreCase("E")
				&& bean.getIdPais() != null) {
			PersistenceManager pm = null;
			Transaction tx = null;
			try {
				pm = PMF.getPMF().getPersistenceManager();
				tx = pm.currentTransaction();
				tx.begin();
				BeanParametro parametro = new BeanParametro();
				parametro.setBean(bean);
				parametro.setId(bean.getIdPais());
				parametro.setTipoOperacion(bean.getOperacion());
				LogicPais logic = new LogicPais(pm);
				Boolean resultado = logic.mantenimiento(parametro);
				if (resultado) {
					tx.commit();
					pm.close();
					return true;
				} else {
					tx.rollback();
					pm.close();
					return false;
				}
			} catch (Exception ex) {
				LOG.warning(ex.getMessage());
				LOG.info(ex.getLocalizedMessage());
				throw new UnknownException(ex.getMessage());
			} finally {
				if (!pm.isClosed()) {
					if (tx.isActive()) {
						tx.rollback();
					}
					pm.close();
				}
			}
		} else {
			throw new UnknownException("Verifique Catalogo de Servicio");
		}
	}

	public static List<Pais> listarPais() throws UnknownException {

		PersistenceManager pm = null;
		try {
			pm = PMF.getPMF().getPersistenceManager();
			LogicPais logic = new LogicPais(pm);
			List<Pais> resultado = (List<Pais>) logic.getListarBean();
			pm.close();
			return resultado;
		} catch (Exception ex) {
			LOG.warning(ex.getMessage());
			LOG.info(ex.getLocalizedMessage());
			throw new UnknownException(ex.getMessage());
		} finally {
			if (!pm.isClosed()) {
				pm.close();
			}
		}

	}
}
