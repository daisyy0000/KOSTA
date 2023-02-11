//01.26.목
package com.my.cart.control;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.my.control.Controller;

@WebServlet("/cart/*")
public class CartController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Properties env;
	protected void service(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		String contextPath = request.getContextPath();
		String servletPath = request.getServletPath();
		String uri = request.getRequestURI();
		String url = request.getRequestURL().toString();
		String []arr = uri.split("/");
		String subPath = arr[arr.length-1];
		String envFileName = "cart.properties";
		envFileName = getServletContext().getRealPath(envFileName);
		Properties env = new Properties();
		env.load(new FileInputStream(envFileName));
		String className = env.getProperty(subPath);
		try {
			Class clazz = Class.forName(className);
			Object obj = clazz.getDeclaredConstructor().newInstance();
			Controller controller = (Controller)obj;
			String result = controller.execute(request, response);
			response.getWriter().print(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
