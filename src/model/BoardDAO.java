package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardDAO {
	Connection con;
	PreparedStatement ptmt;
	ResultSet rs;
	String sql;
	
	public BoardDAO() {
		// TODO Auto-generated constructor stub
		
		try {
			//¡÷ºÆ
			Context init = new InitialContext();
			DataSource ds = (DataSource)init.lookup("java:comp/env/oooo");
			
			con = ds.getConnection();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<BoardVO> list(int start,int end){
		ArrayList<BoardVO> res = new ArrayList<>();
		
		sql = "select * from (select rownum rnum, tt.* from (select * from nsmvcboard order by gid desc, seq) tt) where rnum >= ? and rnum <=?";
		try {
			ptmt = con.prepareStatement(sql);
			ptmt.setInt(1, start);
			ptmt.setInt(2, end);
			
			rs = ptmt.executeQuery();
			
			while(rs.next()) {
				BoardVO vo = new BoardVO();
				vo.setGid(rs.getInt("gid"));
				vo.setSeq(rs.getInt("seq"));
				vo.setLev(rs.getInt("lev"));
				vo.setId(rs.getInt("id"));
				vo.setTitle(rs.getString("title"));
				vo.setPname(rs.getString("pname"));
				vo.setReg_date(rs.getTimestamp("reg_date"));
				res.add(vo);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		return res;
	} 
	
	
	public void close() {
		if(rs!=null) try {rs.close();} catch(SQLException e) {}
		if(ptmt!=null) try {ptmt.close();} catch(SQLException e) {}
		if(con!=null) try {con.close();} catch(SQLException e) {}		
	}
}
