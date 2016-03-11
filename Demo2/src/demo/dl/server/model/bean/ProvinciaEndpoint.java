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

@Api(name = "provinciaendpoint", namespace = @ApiNamespace(ownerDomain = "dl.demo", ownerName = "dl.demo", packagePath = "server.model.bean"))
public class ProvinciaEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listProvincia")
	public CollectionResponse<Provincia> listProvincia(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		PersistenceManager mgr = null;
		Cursor cursor = null;
		List<Provincia> execute = null;

		try {
			mgr = getPersistenceManager();
			Query query = mgr.newQuery(Provincia.class);
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				HashMap<String, Object> extensionMap = new HashMap<String, Object>();
				extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
				query.setExtensions(extensionMap);
			}

			if (limit != null) {
				query.setRange(0, limit);
			}

			execute = (List<Provincia>) query.execute();
			cursor = JDOCursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Provincia obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Provincia> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getProvincia")
	public Provincia getProvincia(@Named("id") String id) {
		PersistenceManager mgr = getPersistenceManager();
		Provincia provincia = null;
		try {
			provincia = mgr.getObjectById(Provincia.class, id);
		} finally {
			mgr.close();
		}
		return provincia;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param provincia the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertProvincia")
	public Provincia insertProvincia(Provincia provincia) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			if (containsProvincia(provincia)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.makePersistent(provincia);
		} finally {
			mgr.close();
		}
		return provincia;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param provincia the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateProvincia")
	public Provincia updateProvincia(Provincia provincia) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			if (!containsProvincia(provincia)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.makePersistent(provincia);
		} finally {
			mgr.close();
		}
		return provincia;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeProvincia")
	public void removeProvincia(@Named("id") String id) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			Provincia provincia = mgr.getObjectById(Provincia.class, id);
			mgr.deletePersistent(provincia);
		} finally {
			mgr.close();
		}
	}

	private boolean containsProvincia(Provincia provincia) {
		PersistenceManager mgr = getPersistenceManager();
		boolean contains = true;
		try {
			mgr.getObjectById(Provincia.class, provincia.getIdProvincia());
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
