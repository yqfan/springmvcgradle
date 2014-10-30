package com.yqfan.springmvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;

import com.yqfan.springmvc.MyController;
import com.yqfan.springmvc.model.Gift;



public class JdbcGiftDao implements GiftDao{
private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void insert(Gift gift) {
		// insert gift object into mysql. gift object only has 'title' and 'description' set.
		// get an id for gift.
		// get a dataurl for gift after the id, and update the item in mysql.
		String sql = "INSERT INTO GIFT " +
				"(TITLE, DESCRIP, DATAURL) VALUES (?, ?, ?)";
		String sqlcnt = "SELECT COUNT(*) FROM GIFT";
		String sqlupdate="UPDATE GIFT SET dataurl = ? WHERE id = ?";
		long id = -1;
		String dataurl = null;
		Connection conn = null;
 
		try {
			// insert gift into mysql
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, gift.getTitle());
			ps.setString(2, gift.getDescription());
			ps.setString(3, gift.getDataUrl());
			ps.executeUpdate();
			ps.close();
			
			// get id of the new inserted item
			ps = conn.prepareStatement(sqlcnt);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				id = rs.getLong(1);
				gift.setId(id);
			}
			System.out.println("insert done, gift id="+gift.getId());
			rs.close();
			ps.close();
			
			// get dataurl for this gift
			dataurl = MyController.getGiftAbsPath(gift);
			System.out.println("dataurl="+dataurl);
			gift.setDataUrl(dataurl);
			ps = conn.prepareStatement(sqlupdate);
			ps.setString(1, dataurl);
			ps.setLong(2, id);
			ps.executeUpdate();
			ps.close();
 
		} catch (SQLException e) {
			throw new RuntimeException(e);
 
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}

	}
	

	@Override
	public Gift findById(long id) {
		String sql = "SELECT * FROM GIFT WHERE ID = ?";
		 
		Connection conn = null;
 
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, id);
			Gift gift = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				gift = new Gift(
					rs.getString("TITLE"), 
					rs.getString("DESCRIP")
				);
				gift.setId(rs.getLong("ID"));
				gift.setDataUrl(rs.getString("DATAURL"));
			}
			rs.close();
			ps.close();
			return gift;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
				conn.close();
				} catch (SQLException e) {}
			}
		}
	}

	@Override
	public Collection<Gift> findByTitle(String title) {
		String sql = "SELECT * FROM GIFT WHERE TITLE = ?";
		 
		Connection conn = null;
		ArrayList<Gift> res = new ArrayList<Gift>();
 
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(2, title);
			Gift gift = null;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				gift = new Gift(
					rs.getString("TITLE"), 
					rs.getString("DESCRIP")
				);
				gift.setId(rs.getLong("ID"));
				gift.setDataUrl(rs.getString("DATAURL"));
				res.add(gift);
			}
			rs.close();
			ps.close();
			return res;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
				conn.close();
				} catch (SQLException e) {}
			}
		}
	}
	
	@Override
	public Collection<Gift> getAll() {
		String sql = "SELECT * FROM GIFT";
		Connection conn = null;
		ArrayList<Gift> res = new ArrayList<Gift>();
 
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			Gift gift = null;
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				gift = new Gift(
					rs.getString("TITLE"), 
					rs.getString("DESCRIP")
				);
				gift.setId(rs.getLong("ID"));
				gift.setDataUrl(rs.getString("DATAURL"));
				res.add(gift);
			}
			rs.close();
			ps.close();
			return res;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
				conn.close();
				} catch (SQLException e) {}
			}
		}
	}
}
