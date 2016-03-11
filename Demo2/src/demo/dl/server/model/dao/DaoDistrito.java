package demo.dl.server.model.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.apphosting.api.ApiProxy.UnknownException;

import demo.dl.server.model.bean.Distrito;
import demo.dl.shared.BeanParametro;

public class DaoDistrito {
	private PersistenceManager pm;

	public DaoDistrito(PersistenceManager pm) {
		this.pm = pm;
	}

	public boolean mantenimiento(BeanParametro parametro)
			throws UnknownException {
		Querys query = new Querys(this.pm);
		return query.mantenimiento(parametro);
	}

	public Object getBean(String id) throws UnknownException {
		Querys query = new Querys(this.pm);
		return query.getBean(Distrito.class, id);
	}

	@SuppressWarnings("unchecked")
	public Collection<Distrito> getListarBean() throws UnknownException {
		Querys query = new Querys(this.pm);
		Collection<Distrito> lista = (Collection<Distrito>) query
				.getListaBean(Distrito.class);
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Distrito> getListarBean(String codeProvincia) throws UnknownException {
		Query query = pm.newQuery(Distrito.class);	
		query.setFilter("codeProvincia == paramCodeProvincia");		
		query.setOrdering("descripcion asc");
		query.declareParameters("String paramCodeProvincia");		
		try{
		List<Distrito> lista=new ArrayList<Distrito>();
		lista.addAll((List<Distrito>)query.execute(codeProvincia));
		return lista;
		}catch(Exception ex){			
			throw new UnknownException(ex.getMessage());
		}finally{
			query.closeAll();
		}
	}
}
