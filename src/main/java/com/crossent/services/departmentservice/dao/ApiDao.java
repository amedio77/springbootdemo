package com.crossent.services.departmentservice.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ApiDao {
	protected static final String NAMESPACE = "com.crossent.services.departmentservice.api.";

	@Autowired
	private SqlSession sqlSession;


	public String selectName(){
		return sqlSession.selectList(NAMESPACE + "selectName").toString();
	}

	public String selectNameWhere(Map<String,String> requstMap){
		return sqlSession.selectOne(NAMESPACE + "selectNameWhere", requstMap);
	}

	public int insertName(Map<String,String> requstMap){
		return sqlSession.insert(NAMESPACE + "insertName", requstMap);
	}

	public int updateName(Map<String,String> requstMap){
		return sqlSession.update(NAMESPACE + "updateName", requstMap);
	}

	public int deleteName(Map<String,String> requstMap){
		return sqlSession.delete(NAMESPACE + "deleteName", requstMap);
	}
}