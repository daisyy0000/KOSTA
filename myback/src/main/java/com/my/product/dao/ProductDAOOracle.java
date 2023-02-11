package com.my.product.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.my.exception.FindException;
import com.my.product.vo.Product;

public class ProductDAOOracle implements ProductDAO {

	@Override
	public List<Product> selectAll(int startRow, int endRow) throws FindException {
		// 1. JDBC 드라이버 로드(ojbc11.jar)
		// 클래스이름으로 찾아서 JVM메모리 위로 올리는
		// 2. DB연결
		Connection conn = null;
		// 3. SQL 구문 송신
		PreparedStatement pstmt = null;
		// 4. 송신 결과 수신
		ResultSet rs = null;

		ArrayList<Product> list = new ArrayList<>();
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			// 접속할IP주소: 포트번호: 서비스아이디(xe)
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "test";
			String password = "test";
			// sql구문 테스트필수: 오라클db에서 먼저 테스트하기
			// 끝에 "안에 ;콜론은 안된다 지우기!
			//서브쿼리가 2개인 이유: 로우넘을 위한 서브쿼리, 오더바이를 위한 서브퀴리. 그래서 이 두개조건을 만족하는 Select문~ 
			String selectAllPageSQL = "SELECT *\r\n" + "FROM (\r\n" + "  SELECT rownum rn, a.*\r\n"
					+ "  FROM (SELECT * FROM product ORDER BY prod_no) a\r\n" + ") WHERE rn BETWEEN ? and ?";
			// mybatis에서는 ?처리를 #{startRow} #{endRow}

			conn = DriverManager.getConnection(url, user, password);
			pstmt = conn.prepareStatement(selectAllPageSQL);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery(); // mybatis: session.selectList()

			// 5. 수신내용 활용
			while (rs.next()) { // 커서를 다음행으로 보낸다
				// ""안에는 대소문자 구별 안함

				String prodNo = rs.getString("prod_no");
				String prodName = rs.getString("Prod_Name");
				int proprice = rs.getInt("prod_price");
				// 아래의 구문을while문 밖으로 올리면 list에는 제일 마지막꺼만 들어가게됨
				Product p = new Product();
				p.setProdNo(prodNo);
				p.setProdName(prodName);
				p.setProdPrice(proprice);
				// list에 담아서 추가
				list.add(p);
			}
			return list;
		} catch (ClassNotFoundException e) {
			// 실제 오류 내용은 여기서(콘솔에서, 개발자들이)
			e.printStackTrace();
			// 메서드를 호출한 곳으로 예외 떠넘겨서 단순히 메시지만 알려주기!
			throw new FindException("JDBC드라이버 로드 실패");
		} catch (SQLException e) {
			e.printStackTrace();
			// 예외 상세메시지 설정
			throw new FindException(e.getMessage());
		} finally { // 메서드가 빠져나오기직전에 꼭 finally는 꼭 실행이 된다
					// ex) throw전에 꼭 finally를 실행!거쳐감
			// 6. DB연결 닫기. 닫지않으면 메모리 누수s..어느날갑자기 메모리연결이제안돼~라는메시지 나온다
			// close는 반드시 해야하며, 순서는 연(open) 순서의 거꾸로
			// 3개를 같이 한곳으로 묶으면 안된다. 묵었는데 rs.close에서 문제가 나면 나머지 밑에줄은 작동되지 않으니
			// 닫히지 않음..! 그래서 각각해주는게 안전하다

			// 널포인트익셉션 방지..
			if (rs != null) { // mybatis:if(session != null) session.close();
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}
	// 자바애플리케이션에서는 실행할때는 lib파일을 빌드패쓰해서 써야됨! 그래야지 효과적용
	// 웹프로젝트 실행할때는 lib ㅠ폴더

	@Override
	public int totalCnt() throws FindException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "test";
			String password = "test";
			conn = DriverManager.getConnection(url, user, password);
			String selectCountSQL = "SELECT COUNT(*)\r\n" + "FROM product";
			pstmt = conn.prepareStatement(selectCountSQL);
			rs = pstmt.executeQuery();
			rs.next(); // 무조건 결과행을 1개를 받기때문에 if..할필요가 없다
			int totalCnt = rs.getInt(1);
			return totalCnt;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new FindException("JDBC드라이버로드 실패");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			// 6. DB연결닫기
			if (rs != null) { // if(session != null)session.close();
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}

	}

	public static void main(String[] args) {
		ProductDAOOracle dao = new ProductDAOOracle();
		int startRow = 4;
		int endRow = 6;
		try {
			List<Product> list = dao.selectAll(startRow, endRow);
			System.out.println(list);
		} catch (FindException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Product selectByProdNo(String prodNo) throws FindException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName("oracle.jdbc.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "test";
			String password = "test";
			conn = DriverManager.getConnection(url, user, password);
			String selectByProdNoSQL = "select * from product where prod_no=?";
			pstmt = conn.prepareStatement(selectByProdNoSQL);
			pstmt.setString(1, prodNo);
			// 최소 0개 최대 1개
			rs = pstmt.executeQuery();

			if (rs.next()) {
				String prodName = rs.getString("Prod_Name");
				int proprice = rs.getInt("prod_price");
				String prodDetail = rs.getString("prod_detail");
				Date prodMfDt = rs.getDate("prod_Mf_Dt");
				
				Product p = new Product();
				p.setProdNo(prodNo);
				p.setProdName(prodName);
				p.setProdPrice(proprice);
				p.setProdDetail(prodDetail);
				p.setProdMfDt(prodMfDt);
				return p;
			} else { // 상품번호 잘못 기입
				throw new FindException(prodNo + "상품이 없습니다");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new FindException("JDBC드라이버 로드 실패");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			if (rs != null) { // if(session != null) session.close();
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}
}

//
////테스트용
//	public static void main(String[] args) {
//		ProductDAOOracle dao = new ProductDAOOracle();
//		
//		int startRow = 1;
//		int endRow = 3;
//		
//		try {
//			System.out.println(dao.selectAll(startRow, endRow));
//		} catch (FindException e) {
//			e.printStackTrace();
//		}
//	}