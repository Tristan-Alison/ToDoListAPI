package fr.m2i.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.m2i.Db.DaoFactory;
import fr.m2i.models.ActorDaoImpl;
import fr.m2i.models.Task;
import fr.m2i.models.TaskDaoImpl;




/**
 * Servlet implementation class toDoList
 */
@WebServlet("/list")
public class toDoList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String PAGE="/WEB-INF/pages/toDoList.jsp";
	Task task = new Task();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public toDoList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		


			
		request.setAttribute("tasks",DaoFactory.getInstance().getTaskDao().lister()); 
	
		this.getServletContext().getRequestDispatcher(PAGE).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String requete = request.getParameter("req");
		System.out.println(requete);
		
		if (requete.equalsIgnoreCase("add")) {
			Task task = new Task();
			task.set_description(request.getParameter("desc"));
			task.set_nom(request.getParameter("nom"));
			DaoFactory.getInstance().getTaskDao().ajouter(task);

			System.out.println("1");
		}
		if (requete.equalsIgnoreCase("del")) {
			String id =   request.getParameter("id"); 
			DaoFactory.getInstance().getTaskDao().supprimer(id);

		}
		if (requete.equalsIgnoreCase("mod")) {
			 
			DaoFactory.getInstance().getTaskDao().modifier((request.getParameter("nom")),request.getParameter("desc"), request.getParameter("id"));
		}
		
		if (requete.equalsIgnoreCase("transac")) {
			 
			try {
				DaoFactory.getInstance().getTaskDao().transaction();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		if (requete.equalsIgnoreCase("list")) {
//			//request.setAttribute("exemple",DaoFactory.getInstance().getTaskDao().lister());
//			List<Task> tasks = DaoFactory.getInstance().getTaskDao().lister();
//			System.out.println(tasks);
//			
//			request.setAttribute("tasks", tasks);
//		}
		//this.getServletContext().getRequestDispatcher(PAGE).forward(request, response);
		doGet(request,response);
	}

}
