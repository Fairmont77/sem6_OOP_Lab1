package db;

import java.sql.SQLException;
import java.util.List;

/**
 * Generic Data Access Object (DAO) interface definition.
 * This interface defines standard operations to be performed on model objects.
 *
 * @param <T> the type parameter which will be replaced by the actual type of the model class.
 */
public interface Dao<T> {

    /**
     * Retrieves an entity by its ID.
     *
     * @param id the ID of the entity.
     * @return the entity found or null if no entity found with given ID.
     */
    T get(int id);

    /**
     * Retrieves all entities of type T.
     *
     * @return a list of entities of type T.
     */
    List<T> getAll();

    /**
     * Adds a new entity to the database.
     *
     * @param t the entity to be added.
     * @throws SQLException if there is an issue performing the operation.
     */
    void add(T t) throws SQLException;

    /**
     * Updates an existing entity in the database.
     *
     * @param id the ID of the entity to update.
     * @param t  the entity with updated values.
     */
    void update(int id, T t);

    /**
     * Deletes an entity from the database by its ID.
     *
     * @param id the ID of the entity to delete.
     */
    void delete(int id);
}
