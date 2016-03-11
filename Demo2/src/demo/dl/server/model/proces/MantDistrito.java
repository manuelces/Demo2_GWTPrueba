package demo.dl.server.model.proces;

import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

import com.google.apphosting.api.ApiProxy.UnknownException;

import demo.dl.server.model.bean.Departamento;
import demo.dl.server.model.bean.Distrito;
import demo.dl.server.model.bean.Provincia;
import demo.dl.server.model.dao.PMF;
import demo.dl.server.model.logic.LogicDistrito;
import demo.dl.server.model.logic.LogicProvincia;
import demo.dl.shared.BeanParametro;

public class MantDistrito {
	private static final Logger LOG = Logger.getLogger(MantDistrito.class
			.getName());

	public static Boolean insertarDistrito(Distrito bean)
			throws UnknownException {
		if (bean.getOperacion().equalsIgnoreCase("I")
				&& bean.getIdDistrito() != null) {
			PersistenceManager pm = null;
			Transaction tx = null;
			try {
				pm = PMF.getPMF().getPersistenceManager();
				tx = pm.currentTransaction();
				tx.begin();
				Boolean resultado =insertarDistrito(bean,pm);				
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
	
	public static Boolean insertarDistrito(Distrito bean,PersistenceManager pm)throws UnknownException{	
		LogicProvincia logicProvincia=new LogicProvincia(pm);
		Provincia beanProvincia=(Provincia)logicProvincia.getBean(bean.getCodeProvincia());		
		bean.setBeanProvincia(beanProvincia);	
		beanProvincia.getListDistrito().add(bean);
		BeanParametro parametro = new BeanParametro();
		parametro.setBean(bean);
		parametro.setTipoOperacion(bean.getOperacion());
		LogicDistrito logic = new LogicDistrito(pm);
		Boolean resultado = logic.mantenimiento(parametro);
		return resultado;
	}


	public static Boolean actualizarDistrito(Distrito bean)
			throws UnknownException {
		if (bean.getOperacion().equalsIgnoreCase("A")
				&& bean.getIdDistrito() != null) {
			PersistenceManager pm = null;
			Transaction tx = null;
			Boolean resultado=false;
			try {
				pm = PMF.getPMF().getPersistenceManager();
				tx = pm.currentTransaction();
				tx.begin();							
				BeanParametro parametro = new BeanParametro();				
				LogicDistrito logic = new LogicDistrito(pm);
				Distrito beanAux=(Distrito)logic.getBean(bean.getIdDistrito());
				if(beanAux.getBeanProvincia().getIdProvincia().equalsIgnoreCase(bean.getBeanProvincia().getIdProvincia())){
					beanAux.setCodigo(bean.getCodigo());					
					parametro.setBean(beanAux);
					parametro.setTipoOperacion(bean.getOperacion());
					resultado = logic.mantenimiento(parametro);
				}else{					
					bean.setOperacion("E");
					resultado = eliminarDistrito(bean,pm);
					if(resultado){
						bean.setIdDistrito(bean.getBeanProvincia().getIdProvincia());
						bean.setOperacion("I");
						resultado = insertarDistrito(bean,pm);
					}
				}
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

	public static Boolean eliminarDistrito(Distrito bean)
			throws UnknownException {
		if (bean.getOperacion().equalsIgnoreCase("E")
				&& bean.getIdDistrito() != null) {
			PersistenceManager pm = null;
			Transaction tx = null;
			try {
				pm = PMF.getPMF().getPersistenceManager();
				tx = pm.currentTransaction();
				tx.begin();
				Boolean resultado = eliminarDistrito(bean,pm);								
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
	
	public static Boolean eliminarDistrito(Distrito bean,PersistenceManager pm)throws UnknownException{
		BeanParametro parametro = new BeanParametro();
		parametro.setBean(bean);
		parametro.setId(bean.getIdDistrito());
		parametro.setTipoOperacion(bean.getOperacion());
		LogicDistrito logic = new LogicDistrito(pm);
		Boolean resultado = logic.mantenimiento(parametro);
		return resultado;
	}
	

	public static List<Distrito> listarDistrito() throws UnknownException {

		PersistenceManager pm = null;
		Transaction tx = null;
		try {
			pm = PMF.getPMF().getPersistenceManager();
			PMF.getPMF().getFetchGroup(Distrito.class, "DistritoGroup").addMember("beanProvincia");
			PMF.getPMF().getFetchGroup(Provincia.class, "ProvinciaGroup").addMembers("beanDepartamento");
			PMF.getPMF().getFetchGroup(Departamento.class, "DepartamentoGroup").addMember("beanPais");
			pm.getFetchPlan().addGroup("DistritoGroup");
			pm.getFetchPlan().addGroup("ProvinciaGroup");
			pm.getFetchPlan().addGroup("DepartamentoGroup");
			pm.getFetchPlan().setMaxFetchDepth(-1);
			tx = pm.currentTransaction();
			tx.begin();
			pm.setDetachAllOnCommit(true);
			LogicDistrito logic = new LogicDistrito(pm);
			List<Distrito> resultado = (List<Distrito>) pm
					.detachCopyAll(logic.getListarBean());			
			tx.commit();
			pm.close();
			return resultado;
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

	}

	public static List<Distrito> listarDistrito(String codeProvincia)
			throws UnknownException {

		PersistenceManager pm = null;
		Transaction tx = null;
		try {
			pm = PMF.getPMF().getPersistenceManager();
			PMF.getPMF().getFetchGroup(Distrito.class, "DistritoGroup")
					.addMember("beanProvincia");
			pm.getFetchPlan().addGroup("DistritoGroup");
			pm.getFetchPlan().setMaxFetchDepth(1);
			tx = pm.currentTransaction();
			tx.begin();
			pm.setDetachAllOnCommit(true);
			LogicDistrito logic = new LogicDistrito(pm);
			List<Distrito> resultado = (List<Distrito>) pm
					.detachCopyAll(logic.getListarBean(codeProvincia));
			tx.commit();
			pm.close();
			return resultado;
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

	}
}


