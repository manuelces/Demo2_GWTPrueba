package demo.dl.server.model.logic;

import java.util.Collection;

import javax.jdo.PersistenceManager;

import com.google.apphosting.api.ApiProxy.UnknownException;

import demo.dl.server.model.bean.Distrito;
import demo.dl.server.model.dao.DaoDistrito;
import demo.dl.shared.BeanParametro;

public class LogicDistrito {
	private PersistenceManager pm;

	public LogicDistrito(PersistenceManager pm) {
		this.pm = pm;
	}

	public boolean mantenimiento(BeanParametro parametro)
			throws UnknownException {
		DaoDistrito dao = new DaoDistrito(this.pm);
		return dao.mantenimiento(parametro);
	}

	public Object getBean(String id) throws UnknownException {
		DaoDistrito dao = new DaoDistrito(this.pm);
		return dao.getBean(id);
	}
	
	public Collection<Distrito> getListarBean() throws UnknownException {
		DaoDistrito dao = new DaoDistrito(this.pm);
		Collection<Distrito> lista = dao.getListarBean();
		return lista;
	}
	
	public Collection<Distrito> getListarBean(String codePais) throws UnknownException {
		DaoDistrito dao = new DaoDistrito(this.pm);
		Collection<Distrito> lista = dao.getListarBean(codePais);
		return lista;
	}
}
