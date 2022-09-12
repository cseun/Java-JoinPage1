package ex02;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MemberDAO {
	private Connection con;
	private PreparedStatement pstmt;
	private Statement stmt;
	private DataSource dataFactory;

	public MemberDAO() {
		try {
			Context ctx = new InitialContext();
			dataFactory = (DataSource)ctx.lookup("java:comp/env/jdbc/mariadb"); // envCtx.lookup("jdbc/mariadb") -> object е╦ют
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	public void getConnection() {
		try {
			con = dataFactory.getConnection();
			stmt = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addMember(MemberVO vo) {
		try {
			con = dataFactory.getConnection();
			String query = "INSERT INTO user VALUES (?,?,?,?)";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPwd());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getEmail());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public List<MemberVO> listMember() {
		List<MemberVO> voList = new ArrayList<MemberVO>();
		try {
			getConnection();
			String query = "SELECT * FROM user";
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				MemberVO vo = new MemberVO();
				vo.setId(rs.getString("id"));
				vo.setPwd(rs.getString("password"));
				vo.setName(rs.getString("name"));
				vo.setEmail(rs.getString("email"));

				voList.add(vo);
			}
			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return voList;

	}
	
	public void deleteMember(String id) {
		try {
			getConnection();
			String query = "DELETE FROM user WHERE id = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
