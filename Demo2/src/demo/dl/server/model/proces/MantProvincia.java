package demo.dl.server.model.proces;

import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

import com.google.apphosting.api.ApiProxy.UnknownException;

import demo.dl.server.model.bean.Departamento;
import demo.dl.server.model.bean.Provincia;
import demo.dl.server.model.dao.PMF;
import demo.dl.server.model.logic.LogicDepartamento;
import demo.dl.server.model.logic.LogicProvincia;
import demo.dl.shared.BeanParametro;

public class MantProvincia {
	private static final Logger LOG = Logger.getLogger(MantProvincia.class
			.getName());

	public static Boolean insertarProvincia(Provincia bean)
			throws UnknownException {
		if (bean.getOperacion().equalsIgnoreCase("I")
				&& bean.getIdProvincia() != null) {
			PersistenceManager pm = null;
			Transaction tx = null;
			try {
				pm = PMF.getPMF().getPersistenceManager();
				tx = pm.currentTransaction();
				tx.begin();
				Boolean resultado=insertarProvincia(bean,pm);				 
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
	
	public static Boolean insertarProvincia(Provincia bean,PersistenceManager pm)throws UnknownException{
		
		LogicDepartamento logicDepartamento=new LogicDepartamento(pm);
		Departamento beanDepartamento=(Departamento)logicDepartamento.getBean(bean.getCodeDepartamento());	
		bean.setBeanDepartamento(beanDepartamento);	
		beanDepartamento.getListProvincia().add(bean);
		BeanParametro parametro = new BeanParametro();
		parametro.setBean(bean);
		parametro.setTipoOperacion(bean.getOperacion());
		LogicProvincia logic = new LogicProvincia(pm);
		Boolean resultado = logic.mantenimiento(parametro);
		return resultado;
	}


	public static Boolean actualizarProvincia(Provincia bean)
			throws UnknownException {
		if (bean.getOperacion().equalsIgnoreCase("A")
				&& bean.getIdProvincia() != null) {
			PersistenceManager pm = null;
			Transaction tx = null;
			Boolean resultado=false;
			try {
				pm = PMF.getPMF().getPersistenceManager();
				tx = pm.currentTransaction();
				tx.begin();			
				BeanParametro parametro = new BeanParametro();				
				LogicProvincia logic = new LogicProvincia(pm);
				Provincia beanAux=(Provincia)logic.getBean(bean.getIdProvincia());
				if(beanAux.getBeanDepartamento().getIdDepartamento().equalsIgnoreCase(bean.getBeanDepartamento().getIdDepartamento())){					
					beanAux.setCodigo(bean.getCodigo());					
					parametro.setBean(beanAux);
					parametro.setTipoOperacion(bean.getOperacion());
					resultado = logic.mantenimiento(parametro);
				}else{					
					bean.setOperacion("E");
					resultado = eliminarProvincia(bean,pm);
					if(resultado){
						bean.setIdProvincia(bean.getBeanDepartamento().getIdDepartamento());
						bean.setOperacion("I");
						resultado = insertarProvincia(bean,pm);
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

	public static Boolean eliminarProvincia(Provincia bean)
			throws UnknownException {
		if (bean.getOperacion().equalsIgnoreCase("E")
				&& bean.getIdProvincia() != null) {
			PersistenceManager pm = null;
			Transaction tx = null;
			try {
				pm = PMF.getPMF().getPersistenceManager();
				tx = pm.currentTransaction();
				tx.begin();
				Boolean resultado =eliminarProvincia(bean,pm);				 
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
	
	public static Boolean eliminarProvincia(Provincia bean,PersistenceManager pm)throws UnknownException{
		BeanParametro parametro = new BeanParametro();
		parametro.setBean(bean);
		parametro.setId(bean.getIdProvincia());
		parametro.setTipoOperacion(bean.getOperacion());
		LogicProvincia logic = new LogicProvincia(pm);
		Boolean resultado = logic.mantenimiento(parametro);
		return resultado;
	}
	

	public static List<Provincia> listarProvincia() throws UnknownException {

		PersistenceManager pm = null;
		Transaction tx = null;
		try {
			pm = PMF.getPMF().getPersistenceManager();
			PMF.getPMF().getFetchGroup(Provincia.class, "ProvinciaGroup").addMembers("beanDepartamento");
			PMF.getPMF().getFetchGroup(Departamento.class, "DepartamentoGroup").addMember("beanPais");
			pm.getFetchPlan().addGroup("DepartamentoGroup");
			pm.getFetchPlan().addGroup("ProvinciaGroup");						
			pm.getFetchPlan().setMaxFetchDepth(2);
			tx = pm.currentTransaction();
			tx.begin();
			pm.setDetachAllOnCommit(true);
			LogicProvincia logic = new LogicProvincia(pm);
			List<Provincia> resultado = (List<Provincia>) pm
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

	public static List<Provincia> listarProvincia(String codeDepartamento)
			throws UnknownException {

		PersistenceManager pm = null;
		Transaction tx = null;
		try {
			pm = PMF.getPMF().getPersistenceManager();
			PMF.getPMF().getFetchGroup(Provincia.class, "ProvinciaGroup").addMember("beanDepartamento");		
			pm.getFetchPlan().addGroup("ProvinciaGroup");
			pm.getFetchPlan().setMaxFetchDepth(1);
			tx = pm.currentTransaction();
			tx.begin();
			pm.setDetachAllOnCommit(true);
			LogicProvincia logic = new LogicProvincia(pm);
			List<Provincia> resultado = (List<Provincia>) pm
					.detachCopyAll(logic.getListarBean(codeDepartamento));			
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

