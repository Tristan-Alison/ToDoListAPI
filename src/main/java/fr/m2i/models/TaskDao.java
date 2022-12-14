package fr.m2i.models;

import java.sql.SQLException;
import java.util.List;

public interface TaskDao {
	void ajouter( Task task );
    List<Task> lister();
    void supprimer(String id);
    void modifier(String nom,String descr, String id);
    void transaction() throws SQLException;
}
