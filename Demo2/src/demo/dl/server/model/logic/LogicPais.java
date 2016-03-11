package demo.dl.server.model.logic;

import java.util.Collection;

import javax.jdo.PersistenceManager;

import com.google.apphosting.api.ApiProxy.UnknownException;

import demo.dl.server.model.bean.Pais;
import demo.dl.server.model.dao.DaoPais;
import demo.dl.shared.BeanParametro;

public class LogicPais {
	private PersistenceManager pm;

	public LogicPais(PersistenceManager pm) {
		this.pm = pm;
	}

	public boolean mantenimiento(BeanParametro parametro)
			throws UnknownException {
		DaoPais dao = new DaoPais(this.pm);
		return dao.mantenimiento(parametro);
	}

	public Object getBean(String id) throws UnknownException {
		DaoPais dao = new DaoPais(this.pm);
		return dao.getBean(id);
	}		

	public Collection<Pais> getListarBean() throws UnknownException {
		DaoPais dao = new DaoPais(this.pm);
		Collection<Pais> lista = dao.getListarBean();
		return lista;
	}
}
