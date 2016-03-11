package demo.dl.server.model.bean;

import demo.dl.server.model.dao.PMF;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JDOCursorHelper;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

@Api(name = "paisendpoint", namespace = @ApiNamespace(ownerDomain = "dl.demo", ownerName = "dl.demo", packagePath = "server.model.bean"))
public class PaisEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listPais")
	public CollectionResponse<Pais> listPais(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		PersistenceManager mgr = null;
		Cursor cursor = null;
		List<Pais> execute = null;

		try {
			mgr = getPersistenceManager();
			Query query = mgr.newQuery(Pais.class);
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				HashMap<String, Object> extensionMap = new HashMap<String, Object>();
				extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
				query.setExtensions(extensionMap);
			}

			if (limit != null) {
				query.setRange(0, limit);
			}

			execute = (List<Pais>) query.execute();
			cursor = JDOCursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Pais obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Pais> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getPais")
	public Pais getPais(@Named("id") String id) {
		PersistenceManager mgr = getPersistenceManager();
		Pais pais = null;
		try {
			pais = mgr.getObjectById(Pais.class, id);
		} finally {
			mgr.close();
		}
		return pais;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param pais the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertPais")
	public Pais insertPais(Pais pais) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			if (containsPais(pais)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.makePersistent(pais);
		} finally {
			mgr.close();
		}
		return pais;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param pais the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updatePais")
	public Pais updatePais(Pais pais) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			if (!containsPais(pais)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.makePersistent(pais);
		} finally {
			mgr.close();
		}
		return pais;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removePais")
	public void removePais(@Named("id") String id) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			Pais pais = mgr.getObjectById(Pais.class, id);
			mgr.deletePersistent(pais);
		} finally {
			mgr.close();
		}
	}

	private boolean containsPais(Pais pais) {
		PersistenceManager mgr = getPersistenceManager();
		boolean contains = true;
		try {
			mgr.getObjectById(Pais.class, pais.getIdPais());
		} catch (javax.jdo.JDOObjectNotFoundException ex) {
			contains = false;
		} finally {
			mgr.close();
		}
		return contains;
	}

	private static PersistenceManager getPersistenceManager() {
		return PMF.getPMF().getPersistenceManager();
	}

}
