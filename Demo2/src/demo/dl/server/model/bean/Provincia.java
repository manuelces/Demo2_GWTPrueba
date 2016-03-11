package demo.dl.server.model.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@PersistenceCapable(detachable = "true")
public class Provincia implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2560617366920790922L;
	@PrimaryKey
	@Persistent
	@Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private String idProvincia;
	@Persistent
	private String codigo;
	@NotPersistent
	private String operacion;
	@NotPersistent
	private String codeProvincia;
	@Persistent
	private String codeDepartamento;
	@Persistent
	private Departamento beanDepartamento;
	@Persistent
	private String codePais;
	@NotPersistent
	private String descPais;
	@Persistent(mappedBy = "beanProvincia")
	private List<Distrito> listDistrito=new ArrayList<Distrito>();
	
	
	
	public String getDescPais() {
		return descPais;
	}
	public void setDescPais(String descPais) {
		this.descPais = descPais;
	}
	public String getIdProvincia() {
		return idProvincia;
	}
	public void setIdProvincia(String idDepartamento) {		
		Key keyDepartamento=KeyFactory.stringToKey(idDepartamento);
		Key keyProvincia=KeyFactory.createKey(keyDepartamento,Provincia.class.getSimpleName(), java.util.UUID.randomUUID().toString());
		this.idProvincia = KeyFactory.keyToString(keyProvincia);
	}
	public String getOperacion() {
		return operacion;
	}
	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}
	
	public String getCodeProvincia() {
		return codeProvincia;
	}
	public void setCodeProvincia(String codeProvincia) {
		this.idProvincia=codeProvincia;
		this.codeProvincia = codeProvincia;
	}
	public String getCodeDepartamento() {
		return codeDepartamento;
	}
	public void setCodeDepartamento(String codeDepartamento) {
		this.codeDepartamento = codeDepartamento;
	}
	public Departamento getBeanDepartamento() {
		return beanDepartamento;
	}
	public void setBeanDepartamento(Departamento beanDepartamento) {
		this.beanDepartamento = beanDepartamento;
	}
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public String getCodePais() {
		return codePais;
	}
	public void setCodePais(String codePais) {
		this.codePais = codePais;
	}		
	public List<Distrito> getListDistrito() {
		return listDistrito;
	}
	public void setListDistrito(List<Distrito> listDistrito) {
		this.listDistrito = listDistrito;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idProvincia == null) ? 0 : idProvincia.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Provincia other = (Provincia) obj;
		if (idProvincia == null) {
			if (other.idProvincia != null)
				return false;
		} else if (!idProvincia.equals(other.idProvincia))
			return false;
		return true;
	}
	
}
