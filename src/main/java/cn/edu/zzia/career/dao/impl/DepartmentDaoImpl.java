package cn.edu.zzia.career.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import cn.edu.zzia.career.base.CommonDaoImpl;
import cn.edu.zzia.career.dao.IDepartmentDao;
import cn.edu.zzia.career.pojo.Department;

@Repository
public class DepartmentDaoImpl extends CommonDaoImpl<Department> implements IDepartmentDao {

	@Override
	public void saveDepartments(List<Department> departments) {

		Session session = this.getSessionFactory().openSession();
		for (Department department : departments) {
			session.save(department);
		}
		session.close();

	}

}
