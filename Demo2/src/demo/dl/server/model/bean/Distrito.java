package demo.dl.server.model.bean;

import java.io.Serializable;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@PersistenceCapable(detachable = "true")
public class Distrito implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8795961951766684615L;
	@PrimaryKey
	@Persistent
	@Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private String idDistrito;
	@Persistent
	private String codigo;
	@NotPersistent
	private String operacion;
	@NotPersistent
	private String codeDistrito;
	@Persistent
	private String codeProvincia;
	@Persistent	
	private Provincia beanProvincia;
	@Persistent
	private String codeDepartamento;
	@NotPersistent
	private String descDepartamento;
	@Persistent
	private String codePais;
	@NotPersistent
	private String descPais;
	
	
	public String getCodeDepartamento() {
		return codeDepartamento;
	}
	public void setCodeDepartamento(String codeDepartamento) {
		this.codeDepartamento = codeDepartamento;
	}
	public String getDescDepartamento() {
		return descDepartamento;
	}
	public void setDescDepartamento(String descDepartamento) {
		this.descDepartamento = descDepartamento;
	}
	public String getCodePais() {
		return codePais;
	}
	public void setCodePais(String codePais) {
		this.codePais = codePais;
	}
	public String getDescPais() {
		return descPais;
	}
	public void setDescPais(String descPais) {
		this.descPais = descPais;
	}
	public String getIdDistrito() {
		return idDistrito;
	}
	public void setIdDistrito(String idProvincia) {		
		Key keyProvincia=KeyFactory.stringToKey(idProvincia);
		Key keyDistrito=KeyFactory.createKey(keyProvincia,Distrito.class.getSimpleName(), java.util.UUID.randomUUID().toString());
		this.idDistrito = KeyFactory.keyToString(keyDistrito);		
	}
	public String getOperacion() {
		return operacion;
	}
	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}	
	public String getCodeDistrito() {
		return codeDistrito;
	}
	public void setCodeDistrito(String codeDistrito) {
		this.idDistrito=codeDistrito;
		this.codeDistrito = codeDistrito;
	}
	public String getCodeProvincia() {
		return codeProvincia;
	}
	public void setCodeProvincia(String codeProvincia) {
		this.codeProvincia = codeProvincia;
	}
	public Provincia getBeanProvincia() {
		return beanProvincia;
	}
	public void setBeanProvincia(Provincia beanProvincia) {
		this.beanProvincia = beanProvincia;
	}
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}	
}
