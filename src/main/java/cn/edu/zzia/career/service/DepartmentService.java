package cn.edu.zzia.career.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.zzia.career.dao.IDepartmentDao;
import cn.edu.zzia.career.dao.IStudentDao;
import cn.edu.zzia.career.pojo.CmStudent;
import cn.edu.zzia.career.pojo.CmUnemp;
import cn.edu.zzia.career.pojo.Department;
import cn.edu.zzia.career.tools.HssfExcelHelper;
import cn.edu.zzia.career.tools.StringUtils;

@Service
public class DepartmentService {

	@Autowired
	private IDepartmentDao departmentDao;
	
	@Autowired
	private IStudentDao studentDao;

	/**
	 * 增加一个专业 信息
	 * 
	 * @param department
	 */
	public void save(Department department) {

		if (null != department) {

			departmentDao.save(department);
		}
	}

	/**
	 * 更新专业信息
	 * 
	 * @param department
	 */
	public void update(Department department) {
		if (null != department) {
			departmentDao.update(department);
		}
	}

	/**
	 * 根据专业的id删除一个专业
	 * 
	 * @param noteId
	 */
	public void deleteById(Integer departmentId) {
		if (null != departmentId) {
			departmentDao.deleteByIds(departmentId);
		}
	}

	/**
	 * 根据专业的id查找一个专业
	 * 
	 * @param noteId
	 * @return
	 */
	public Department findById(Integer departmentId) {

		if (null != departmentId)
			return departmentDao.findObjectById(departmentId);

		return null;

	}

	/**
	 * 查找该学生所有的专业
	 * 
	 * @param studentId
	 * @return
	 */
	public List<Department> findAllDepartment() {

		return departmentDao.findObjectsByConditionWithNoPage();
	}

	public String uploadDepartment(String path) {
		if (StringUtils.isNotBlank(path)) {
			try {
				HssfExcelHelper xssfHelper = HssfExcelHelper.getInstance(path);
				String[] productTitle = { "did", "depName","description" };
				List<Department> departments = xssfHelper.readExcel(Department.class, productTitle, 0, true);
//				Session session = hibernateTemplate.getSessionFactory().openSession();
				departmentDao.saveDepartments(departments);
				return "导入成功！";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "导入失败！";
			}
		}
		return "导入失败！";
	}

	public List<CmStudent> getUnempByDepartmentId(Integer departmentId) {
		
		String whereHql = " and o.department.did = ? and o.sstate = ? ";
		Object[] params = {departmentId,4};
		return studentDao.findObjectsByConditionWithNoPage(whereHql, params);
	}
}
