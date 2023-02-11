package com.my.product.control;
//우리가 예전에 했던 디스패처서블릿..
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.my.control.Controller;

@WebServlet("/product/*")
public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void service(HttpServletRequest request, 
						   HttpServletResponse response) throws ServletException, IOException {
		String contextPath = request.getContextPath();
		String servletPath = request.getServletPath();
		String uri = request.getRequestURI();
		String url = request.getRequestURL().toString();
		
		System.out.println("contextPath=" + contextPath);
		System.out.println("servletPath=" + servletPath);
		System.out.println("uri=" + uri); //uri=/myback/product/list, uri=/myback/product/info
		System.out.println("url=" + url);
		
		//바뀐내용: 각각메서드를 저장하는게 아니고 메서드를 담고있는 클래스를 추가하는 것
		//즉 요청할때마다 호출되는 
		
		String []arr = uri.split("/");
		//uri를 구성하는 서브 path 찾기 => "list"
		//arr.length-1: 마지막값
		String subPath = arr[arr.length-1];
		
		//JVM에서 쓸 수 있는 환경 정보
		String envFileName = "product.properties";
		//getRealPath: 톰캣이 배포된 실제 서버경로를 찾아내야함
		envFileName = getServletContext().getRealPath(envFileName);
		Properties env = new Properties();
		//.load의 요구되는 인자는 inputstream
		env.load(new FileInputStream(envFileName));
		//className: properties에value역할 (com.my.control.ListController)
		//subPath: properties에 key역할
		String className = env.getProperty(subPath);
		
		//리플렉션을 활용한 런타임 다이나믹 로드
		try {
			//JVM 메모리에 올림
			Class clazz = Class.forName(className);
			//객체 생성(요청할때마다 새로운 객체 생김)
			Object obj = clazz.getDeclaredConstructor().newInstance();
			Controller controller = (Controller)obj;
			String result = controller.execute(request, response);
			//예: result값:{"list":[{"prodNo":"C0001","prodName":"아메리카노","prodPrice":1000,"prodDetail":null,"prodMfDt":null} ...주르륵
			System.out.println("result값:" + result);
			response.getWriter().print(result);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
