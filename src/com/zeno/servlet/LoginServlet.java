package com.zeno.servlet;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zeno.bean.User;
import com.zeno.service.UserService;
import com.zeno.tools.VerificationCode;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserService service = new UserService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取请求的方法
		String method = request.getParameter("method");

		if ("GetVCode".equalsIgnoreCase(method)) {
			getVCode(request, response);
		}

		if ("LoginOut".equals(method)) { // 退出系统
			loginOut(request, response);
		} else if ("toAdminView".equalsIgnoreCase(method)) { // 到管理员界面
			request.getRequestDispatcher("/WEB-INF/view/admin/admin.jsp").forward(request, response);
		} else if ("toStudentView".equals(method)) { // 到学生界面
			request.getRequestDispatcher("/WEB-INF/view/student/student.jsp").forward(request, response);
		} else if ("toAdminPersonalView".equals(method)) { // 到系统管理员系统设置界面
			request.getRequestDispatcher("/WEB-INF/view/admin/adminPersonal.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");

		if ("Login".equals(method)) {
			login(request, response);
		}
	}

	/**
	 * 验证用户登录
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 输入用户输入的账户
		String account = request.getParameter("account");
		// 获取用户输入的密码
		String password = request.getParameter("password");
		// 获取用户输入的验证码
		String vcode = request.getParameter("vcode");
		// 获取登录类型
		int type = Integer.parseInt(request.getParameter("type"));

		// 返回信息
		String msg = "";

		// 获取session中的验证码
		String sVcode = (String) request.getSession().getAttribute("vcode");
		if (!sVcode.equalsIgnoreCase(vcode)) { // 判断验证码
			msg = "vcodeError";
		} else { // 判断用户名
			// 封装账户和密码
			User user = new User();
			user.setAccount(account);
			user.setPassword(password);
			user.setType(Integer.parseInt(request.getParameter("type")));

			// 查询用户是否存在
			User loginUser = service.getAdmin(user);
			if (loginUser == null) { // 如果不存在用户
				msg = "loginError";
			} else { // 正确
				if (User.TYPE_ADMIN == type) {
					msg = "admin";
				} else if (User.TYPE_STUDENT == type) {
					msg = "student";
				}
				// 将该用户名保存到session中
				request.getSession().setAttribute("user", loginUser);
			}
		}
		// 返回登录信息
		response.getWriter().write(msg);
	}

	/**
	 * 获取验证码
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void getVCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 创建验证码生成器对象
		VerificationCode verificationCode = new VerificationCode();
		// 生成验证码
		String vcode = verificationCode.generatorVCode();
		// 将验证码保存在session域中，以判断验证码是否正确
		request.getSession().setAttribute("vcode", vcode);
		// 生成验证码图片
		BufferedImage vImg = verificationCode.generatorRotateVCodeImage(vcode, true);
		// 输出图像
		ImageIO.write(vImg, "gif", response.getOutputStream());
	}

	/**
	 * 退出系统
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void loginOut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 退出系统时清除系统登录的用户
		request.getSession().removeAttribute("user");
		String contextPath = request.getContextPath();
		// 转发到登录界面
		response.sendRedirect(contextPath + "/index.jsp");
	}

}
