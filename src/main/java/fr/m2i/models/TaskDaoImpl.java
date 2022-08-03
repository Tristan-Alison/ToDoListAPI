package fr.m2i.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.m2i.Db.DaoFactory;

public class TaskDaoImpl implements TaskDao {
	private DaoFactory daoFactory;

	public TaskDaoImpl(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void ajouter(Task task) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		System.out.println("ajout");
		System.out.println(task);
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = connexion.prepareStatement("INSERT INTO task(nom, description) VALUES(?, ?);");
			preparedStatement.setString(1, task.get_nom());
			preparedStatement.setString(2, task.get_description());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void supprimer (String id) {
	
			Connection connexion = null;
			PreparedStatement preparedStatement = null;
			System.out.println("suppr");

			try {
				connexion = daoFactory.getConnection();
				System.out.println("DELETE FROM task where id=" + id);
				preparedStatement = connexion.prepareStatement("DELETE FROM task where id=" + id);
			
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	public void modifier (String nom,String descr, String id) {
		
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
	

		try {
			connexion = daoFactory.getConnection();
			
			System.out.println("UPDATE task SET nom="+nom+", description " +descr+ " where id=" + id);
			preparedStatement = connexion.prepareStatement("UPDATE task SET nom='"+nom+"', description='" +descr+ "' where id=" + id);
		
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
}

	
	
	
	
	@Override
	public List<Task> lister() {
		List<Task> tasks = new ArrayList<Task>();
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null;

		try {
			connexion = daoFactory.getConnection();
			statement = connexion.createStatement();
			resultat = statement.executeQuery("SELECT nom, description, id FROM task;");

			while (resultat.next()) {
				String nom = resultat.getString("nom");
				String description = resultat.getString("description");
				Integer id = resultat.getInt("id");

				Task task = new Task();
				task.set_nom(nom);
				task.set_description(description);
				task.set_id(id);
				// actor.setFirst_name(nom);
				// actor.setLast_name(prenom);

				tasks.add(task);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tasks;
	}
}
