package com.wu.library.repositories.admin;

import com.wu.library.models.Book;
import com.wu.library.models.Borrow;
import com.wu.library.models.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BorrowRepository {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    public int borrow(String _bookId){

        String sql = "UPDATE tbl_book SET title= false where id= :bookId";
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("bookId", _bookId);
        return jdbcTemplate.update(sql,param);
    }


    public int insert(String _approverId, String _approver , String _bookId, String _memberId){
        String sql ="INSERT INTO tbl_borrow (book_id, member_id, approver, approver_id) VALUES(:bookId, :memberId, :approver, :approverId)";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("bookId", _bookId);
        parameterSource.addValue("memberId", _memberId);
        parameterSource.addValue("approver", _approver);
        parameterSource.addValue("approverId",_approverId);
        return jdbcTemplate.update(sql,parameterSource);
    }

    public List<Borrow> getAll()
    {
        String sql = String.format("select br.id, br.approver, br.borrow_date,me.id, me.major,me.year,me.section,me.carrer, me.name, me.sex, me.contact,bo.id, bo.title from tbl_borrow br\n" +
                "\tinner JOIN tbl_book bo on br.book_id = bo.id\n" +
                "\tinner join tbl_member me on br.member_id = me.id \n" +
                "Where bo.status =FALSE");

       return jdbcTemplate.query(sql, rs->{
           List<Borrow> borrows = new ArrayList<>();
           while (rs.next())
           {
               borrows.add(rsToBorrow(rs));
           }
           return borrows;
       });
    }

    public int delete(String _id){
        String sql = "DELETE from tbl_borrow WHERE id =:id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id",_id);
        return jdbcTemplate.update(sql,params);
    }

    private Borrow rsToBorrow(ResultSet _rs)
        throws SQLException
    {
        Borrow borrow = new Borrow();
        borrow.setId(_rs.getLong("id"));
        borrow.setApprover(_rs.getString("approver"));
        borrow.setBorrow_date(_rs.getDate("borrow_date"));
        borrow.setBook(new Book(_rs.getString("title"),_rs.getString("id")));
        borrow.setMember(new Member(_rs.getString("id"),_rs.getString("name"),_rs.getString("sex"),_rs.getString("major"),_rs.getInt("year"),_rs.getString("section"),_rs.getString("contact"),_rs.getString("carrer")));
        return borrow;
    }
}
