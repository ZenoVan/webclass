package com.zeno.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zeno.bean.Student;
import com.zeno.bean.User;
import com.zeno.service.StudentService;

/**
 * Servlet implementation class StudentServlet
 */
public class StudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private StudentService service = new StudentService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取请求的方法
		String method = request.getParameter("method");
		if ("toExamStudentView".equalsIgnoreCase(method)) { // 转发到学生列表页
			request.getRequestDispatcher("/WEB-INF/view/student/examStudentList.jsp").forward(request, response);
		} else if ("toStudentClassmateView".equalsIgnoreCase(method)) { // 转发到学生列表页
			request.getRequestDispatcher("/WEB-INF/view/student/studentClassmate.jsp").forward(request, response);
		} else if ("toStudentListView".equalsIgnoreCase(method)) { // 转发到学生列表页
			toPersonal(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * 转到个人信息页，加载个人信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void toPersonal(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		Student student = service.getStudent(user.getAccount());
		request.getSession().setAttribute("student", student);
		request.getRequestDispatcher("/WEB-INF/view/student/studentList.jsp").forward(request, response);
	}

}
