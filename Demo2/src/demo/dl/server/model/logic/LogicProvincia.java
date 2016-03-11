package demo.dl.server.model.logic;

import java.util.Collection;

import javax.jdo.PersistenceManager;

import com.google.apphosting.api.ApiProxy.UnknownException;

import demo.dl.server.model.bean.Provincia;
import demo.dl.server.model.dao.DaoProvincia;
import demo.dl.shared.BeanParametro;

public class LogicProvincia {
	private PersistenceManager pm;

	public LogicProvincia(PersistenceManager pm) {
		this.pm = pm;
	}

	public boolean mantenimiento(BeanParametro parametro)
			throws UnknownException {
		DaoProvincia dao = new DaoProvincia(this.pm);
		return dao.mantenimiento(parametro);
	}

	public Object getBean(String id) throws UnknownException {
		DaoProvincia dao = new DaoProvincia(this.pm);
		return dao.getBean(id);
	}
	
	public Collection<Provincia> getListarBean() throws UnknownException {
		DaoProvincia dao = new DaoProvincia(this.pm);
		Collection<Provincia> lista = dao.getListarBean();
		return lista;
	}
	
	public Collection<Provincia> getListarBean(String codePais) throws UnknownException {
		DaoProvincia dao = new DaoProvincia(this.pm);
		Collection<Provincia> lista = dao.getListarBean(codePais);
		return lista;
	}
}

