package day19_DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBClass {
	private String url;
	private String id;
	private String pwd;
	private Connection con;
	public DBClass() {
		try {
			// 자바에서 오라클에 연결할 수 있게끔 도와주는 라이브러리를 등록
			Class.forName("oracle.jdbc.driver.OracleDriver");
			url = "jdbc:oracle:thin:@localhost:1521:xe";
			id = "C##DBTEST";
			pwd = "jsp";
			con = DriverManager.getConnection(url, id ,pwd);
			System.out.println(con);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
		1. 드라이브 로드 (오라클 기능 사용)
		2. 연결된 객체를 얻어온다
		3. 연결된 객체를 이용해서 명령어(쿼리문)을 전송할 수 있는 전송 객체를 얻어온다
		4. 전송 객체를 이용해서 DB에 전송후 결과 얻어온다
		5. 얻어온 결과는 int 또는 ResultRest(쿼리문이 SELECT문 일때만)로 받는다
	*/
	public ArrayList<StudentDTO> getUsers() {
		ArrayList<StudentDTO> list = new ArrayList<StudentDTO>();
		String sql = "SELECT * FROM newst";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				StudentDTO dto = new StudentDTO();
				dto.setStNum(rs.getString(1));
				dto.setName(rs.getString("name"));
				dto.setAge(rs.getInt(3));
				list.add(dto);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public int saveData(String stNum, String name, int age) {
		String sql = "insert into newst values('"+stNum+"', '"+name+"', "+age+")";
		PreparedStatement ps;
		int result = 0;
		try {
			ps = con.prepareStatement(sql);
			result = ps.executeUpdate(); // 성공시 1을 반환, 실패시 0반환
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		return result;

	}
	
	public int saveData02(String stNum, String name, int age) {
		String sql = "insert into newst values(?, ?, ?)";
		PreparedStatement ps;
		int result = 0;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, stNum);
			ps.setString(2, name);
			ps.setInt(3, age);
			result = ps.executeUpdate(); // 성공시 1을 반환, 실패시 0 또는 catch문 이동
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		return result;
	}
	
	public int delete(String userNum) {
		int result = 0;
		String sql = "DELETE FROM newst WHERE id = '"+userNum+"'";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			result = ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public int modify(String stNum1, String name1, int age1) {
		int result = 0;
		String sql = "update newst set name=?, age=? WHERE id=?";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, name1);
			ps.setInt(2, age1);
			ps.setString(3, stNum1);
			result = ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
