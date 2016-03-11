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
public class Departamento implements Serializable{

	private static final long serialVersionUID = 2029238048901900816L;
	@PrimaryKey
	@Persistent
	@Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private String idDepartamento;
	@Persistent
	private String codigo;
	@Persistent
	private Pais beanPais;
	@Persistent
	private String codePais;
	
	@NotPersistent
	private String operacion;
	@NotPersistent
	private String codeDepartamento;
	@Persistent(mappedBy = "beanDepartamento")
	private List<Provincia> listProvincia=new ArrayList<Provincia>();	
	
	public String getIdDepartamento() {
		return idDepartamento;
	}
	public void setIdDepartamento(String idpais) {		
		Key keyPais=KeyFactory.stringToKey(idpais);
		Key keyDepartamento=KeyFactory.createKey(keyPais,Departamento.class.getSimpleName(), java.util.UUID.randomUUID().toString());
		this.idDepartamento = KeyFactory.keyToString(keyDepartamento);
		
	}
	public String getOperacion() {
		return operacion;
	}
	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}	
	public String getCodeDepartamento() {
		return codeDepartamento;
	}
	public void setCodeDepartamento(String codeDepartamento) {
		this.idDepartamento=codeDepartamento;
		this.codeDepartamento = codeDepartamento;
	}
	public Pais getBeanPais() {
		return beanPais;
	}
	public void setBeanPais(Pais beanPais) {
		this.beanPais = beanPais;
	}
	public String getCodePais() {
		return codePais;
	}
	public void setCodePais(String codePais) {
		this.codePais = codePais;
	}
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public List<Provincia> getListProvincia() {
		return listProvincia;
	}
	public void setListProvincia(List<Provincia> listProvincia) {
		this.listProvincia = listProvincia;
	}	
	
}
