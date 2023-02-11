package com.my.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;


@WebServlet("/attach/*")
public class AttachServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		String []arr = uri.split("/");
		String subPath = arr[arr.length-1];
		if("upload".equals(subPath)) {
			upload(request, response);
		}else if("download".equals(subPath)) {
			download(request, response);
		}
	}
	
	private void download(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		
		String opt = request.getParameter("opt");
		
		String fileName = request.getParameter("no");
		String saveDirectory = "D:\\255\\attach";
		String subDirectory = fileName.substring(0, 1);
		File dir = new File(saveDirectory, subDirectory);
		File[] files = dir.listFiles();
		for(File f: files) {
			if(f.getName().split("_")[1].equals(fileName)) {
				//찾은 경우
				//응답헤더설정: 응답형식 응답 길이, 응답의형태(바로응답, 파일로저장)
				String contentType = Files.probeContentType(f.toPath()); //파일의 형식
				response.setHeader("Content-type", contentType);//응답형식(무슨파일이든지)
//				response.setHeader("Content-Type", "image/jpg");//응답형식(이미지로만 지정해서 한거)
				
				response.setHeader("Content-Length", ""+f.length()); //응답크기
				//아래의 두줄은 둘중하나만 쓰는 것.
				if("attachment".equals(opt)) { //다운로드
					response.setHeader("content-disposition", "attachment;filename=" + f.getName());//다운로드
				}else { //바로응답
					response.setHeader("content-disposition", "inline;filename=" + f.getName());//바로응답
				}
				FileInputStream fis = new FileInputStream(f);
				OutputStream os = response.getOutputStream();
				int readValue = -1;
				while((readValue = fis.read()) != -1) {
					os.write(readValue);
				}
			}
		}
		//찾지 못한 경우
	}
	private void upload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
//		InputStream is = request.getInputStream();
//		int readValue = -1;
//		while((readValue = is.read()) != -1) {
//			System.out.print((char)readValue);
//		}

		// 첨부파일이 저장될 폴더 (원본과 복제본 분리)
		String saveDirectory = "D:\\255\\attach";
		String tempDirectory = "D:\\255\\attachtemp";

		int maxPostSize = 100 * 1024;
		String encoding = "UTF-8";
		
		//파일첨부
				//MultipartRequest mr = new MultipartRequest(request, saveDirectory, maxPostSize, encoding);
				MultipartRequest mr = new MultipartRequest(request, tempDirectory, maxPostSize, encoding);

				
				//File file = mr.getFile("f");
				File[] files = new File(tempDirectory).listFiles();
				for(File file: files) {
					String fileName = file.getName();
					long fileLength = file.length();
					System.out.println("fileName=" + fileName + ", fileLength=" + fileLength);
			
					//폴더생성
					String subDirectory = fileName.substring(0, 1);//C G
					File dir = new File(saveDirectory, subDirectory);//saveDirectory, subDirectory);
					if(!dir.exists()) {
						dir.mkdir();
					}	
					//파일 옮기기
					File origin = new File(tempDirectory, fileName);//원본
					
					File copy = new File(dir, UUID.randomUUID() + "_" + fileName); //복사본
					
					//1)원본읽기
					FileInputStream fis = null;
					fis = new FileInputStream(origin);
					
					//2)복사본붙여넣기
					FileOutputStream fos = null;
					fos = new FileOutputStream(copy);
					
					int readValue = -1;
					while((readValue = fis.read()) != -1) {
						fos.write(readValue);
					}
					
					fos.close();
					fis.close();
					
					//3)원본삭제
					origin.delete();
				}
				
				
				String t = mr.getParameter("t");

			}
		}