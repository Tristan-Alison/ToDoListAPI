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

			// on ferme tout
			preparedStatement.close();
			connexion.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void supprimer(String id) {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		System.out.println("suppr");

		try {
			connexion = daoFactory.getConnection();
			System.out.println("DELETE FROM task where id=" + id);
			preparedStatement = connexion.prepareStatement("DELETE FROM task where id=" + id);

			preparedStatement.executeUpdate();

			// on ferme tout
			preparedStatement.close();
			connexion.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void modifier(String nom, String descr, String id) {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = daoFactory.getConnection();

			System.out.println("UPDATE task SET nom=" + nom + ", description " + descr + " where id=" + id);
			preparedStatement = connexion
					.prepareStatement("UPDATE task SET nom='" + nom + "', description='" + descr + "' where id=" + id);

			preparedStatement.executeUpdate();

			// on ferme tout
			preparedStatement.close();
			connexion.close();

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
			// on ferme tout
				resultat.close();
				connexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tasks;
	}

	public void transaction() throws SQLException {
		
		boolean transactionOk = false;
		Connection connexion = null;

		try {
			connexion = daoFactory.getConnection();
			connexion.setAutoCommit(false);
			//Ajout user
			String requeteAjoutUser = "insert into user (nom, prenom)  values (?, ?)";
			System.out.println("try primaire");
			try (PreparedStatement pstmt = connexion.prepareStatement(requeteAjoutUser)) {
				pstmt.setString(1, "Tri2");
				pstmt.setString(2, "Ali2");

				System.out.println("try secondaire1 ");
				pstmt.executeUpdate();
			}

			// Ajout log a un user
			String requeteAjoutLog = "insert into log (identifiant, motDePasse,id_user) values (?, ?, ?);";

			try (PreparedStatement pstmt = connexion.prepareStatement(requeteAjoutLog)) {
				pstmt.setString(1, "user1");
				pstmt.setString(2, "user2");
				pstmt.setInt(3, 2);
				System.out.println("try secondaire2 ");
				pstmt.executeUpdate();
			}
			transactionOk = true;
			
		}catch (SQLException e) {
			e.printStackTrace();
			
		}finally {
			if (transactionOk) {
				System.out.println("commit");
				connexion.commit();
			} else {
				connexion.rollback();
				System.out.println("rollback");
			}
		}
	}
}
