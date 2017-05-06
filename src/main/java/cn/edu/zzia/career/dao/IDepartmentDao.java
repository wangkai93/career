package cn.edu.zzia.career.dao;

import java.util.List;

import cn.edu.zzia.career.base.ICommonDao;
import cn.edu.zzia.career.pojo.Department;

public interface IDepartmentDao extends ICommonDao<Department>{

	void saveDepartments(List<Department> departments);

}
