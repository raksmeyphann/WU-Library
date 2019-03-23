package com.wu.library.repositories.admin;

import com.wu.library.models.Member;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MemberRepository {

    NamedParameterJdbcTemplate jdbcTemplate;


    private Member rsToMember(ResultSet _rs) throws SQLException{
        Member member = new Member();
        member.setId(_rs.getString("id"));
        member.setName(_rs.getString("name"));
        member.setContact(_rs.getString("contact"));
        member.setCarrer(_rs.getString("carrer"));
        member.setSection(_rs.getString("sex"));
        member.setYear(_rs.getInt("year"));
        member.setMajor(_rs.getString("major"));
        return member;

    }

    public List<Member> getAll()
    {
        String sql = String.format("select * from tbl_member");
        return jdbcTemplate.query(sql, rs-> {
            List<Member> memberList = new ArrayList<>();
            while (rs.next()){
                memberList.add(rsToMember(rs));
            }
            return memberList;

        });
    }

    public int insert(Member _member){
        String sql = String.format("INSERT INTO tbl_member (id, carrer, contact, major, name, section,sex,status,year) VALUES (:id, :career, :contact, :major, :name, : section, :sex, :status, :year)");
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", _member.getId());
        params.addValue("career", _member.getCarrer());
        params.addValue("contact", _member.getContact());
        params.addValue("major", _member.getMajor());
        params.addValue("name", _member.getName());
        params.addValue("section", _member.getSection());
        params.addValue("sex", _member.getSex());
        params.addValue("status",true);
        params.addValue("year",_member.getYear());
        return jdbcTemplate.update(sql,params);
    }

    public int update(String _id, Member _member){
        String sql = String.format("UPDATE tbl_member SET id =:id, carrer =:career, contact =:contact, major =:major, name =:name, section =:section, sex =:sex, year =:year WHERE id =:id");
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", _id);
        parameterSource.addValue("career", _member.getCarrer());
        parameterSource.addValue("contact", _member.getContact());
        parameterSource.addValue("major", _member.getMajor());
        parameterSource.addValue("name", _member.getName());
        parameterSource.addValue("section", _member.getSection());
        parameterSource.addValue("sex", _member.getSex());
        parameterSource.addValue("year", _member.getYear());
        return jdbcTemplate.update(sql,parameterSource);

    }
}
