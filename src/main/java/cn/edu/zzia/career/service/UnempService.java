package cn.edu.zzia.career.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;

import cn.edu.zzia.career.ResObj.ResUnempObj;
import cn.edu.zzia.career.dao.IUnEmpDao;
import cn.edu.zzia.career.pojo.CmDirection;
import cn.edu.zzia.career.pojo.CmJob;
import cn.edu.zzia.career.pojo.CmStudent;
import cn.edu.zzia.career.pojo.CmUnemp;
import cn.edu.zzia.career.tools.DateUtil;
import cn.edu.zzia.career.tools.InputData;
import cn.edu.zzia.career.tools.OutputData;
import cn.edu.zzia.career.tools.PageBean;

/**
 * Created by w on 2016/11/10.
 */
@Service("unempService")
public class UnempService {

	@Autowired
	private IUnEmpDao unEmpDao;

	/**
	 * 查询所有未就业的学生
	 * 
	 * @return
	 */
	public List<CmUnemp> findAllUnemp() {
		return unEmpDao.findObjectsByConditionWithNoPage();
	}
	
	/**
	 * 根据学生id删除该学生的未就业信息
	 * @param studentId
	 */
	public void deleleteUnEmp(Integer studentId) {
		
		if(null != studentId){
			String whereHql = "and o.cmStudentBySid.sid = ? ";
			Object[] params = {studentId};
			List<CmUnemp> list = unEmpDao.findObjectsByConditionWithNoPage(whereHql, params);
			if(null != list && list.size() > 0){
				unEmpDao.deleteAllObjects(list);
			}
		}
	}

	/*************************************************************************************************/
	@Autowired
	private HibernateTemplate hibernateTemplate;
	@Autowired
	private JobService jobService;
	@Autowired
	private StudentService studentService;

	// 编辑非考研学生期望——ly
	public boolean updateFkyExpectation(int sid, int jid, int uesalary, String uetime) {
		CmUnemp unemp = this.findBySid(sid);
		if (unemp != null) {
			unemp.setCmJobByJid(jobService.findByJid(jid));
			unemp.setUesalary(uesalary);
			try {
				unemp.setUetime(DateUtil.StringtoUtilDate(uetime));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			hibernateTemplate.saveOrUpdate(unemp);
			return true;
		}
		return false;
	}

	// 编辑考研学生期望——ly
	public boolean updateKyExpectation(int sid, String ueschool, String uemajor, int uesuccess) {
		CmUnemp unemp = this.findBySid(sid);
		if (unemp != null) {
			unemp.setUeschool(ueschool);
			unemp.setUemajor(uemajor);
			unemp.setUesuccess(uesuccess);
			hibernateTemplate.saveOrUpdate(unemp);
			return true;
		}
		return false;
	}

	// 按sid查询未就业生——ly
	public CmUnemp findBySid(int sid) {
		String hsql = "from CmUnemp u where u.cmStudentBySid.sid = ?";
		List<CmUnemp> data = (List<CmUnemp>) hibernateTemplate.find(hsql, sid);
		if (data.size() > 0) {
			return data.get(0);
		}
		System.out.println("未查到相关数据！");
		return null;
	}

	// zxl：根据id查询未就业学生是否存在
	public CmUnemp findBySid1(int sid) {
		String hsql = "select new cn.edu.zzia.career.pojo.CmUnemp(un.ueid) from CmUnemp un where un.cmStudentBySid.sid=? and un.uestate=0";
		List<?> data = hibernateTemplate.find(hsql, sid);
		if (data.size() > 0) {
			CmUnemp cmUnemp = (CmUnemp) data.get(0);
			return cmUnemp;
		}
		return null;
	}

	// zxl:根据学号查询未就业生是否存在
	public CmUnemp findBySno(String sno) {
		String hsql = "select new cn.edu.zzia.career.pojo.CmUnemp(un.ueid) from CmUnemp un where un.cmStudentBySid.sno=? and un.uestate=0";
		List<?> data = hibernateTemplate.find(hsql, sno);
		if (data.size() > 0) {
			CmUnemp cmUnemp = (CmUnemp) data.get(0);
			return cmUnemp;
		}
		return null;
	}

	// zxl：添加未就业生
	public boolean addUnEmp(CmUnemp cmUnemp) {
		hibernateTemplate.saveOrUpdate(cmUnemp);
		return true;
	}

	// zxl：添加未就业生,该生基本信息不存在
	public boolean addUnEmp2(CmStudent cmStudent, CmUnemp cmUnemp) {
		hibernateTemplate.save(cmStudent);
		hibernateTemplate.save(cmUnemp);
		return true;
	}

	// zxl：根据id查询为就业学生信息
	public CmUnemp findUnEmpBySid(int sid) {
		String hsql = "select un from CmUnemp un where  un.cmStudentBySid.sid=? and un.uestate!=1";
		List<CmUnemp> data = (List<CmUnemp>) hibernateTemplate.find(hsql, sid);
		return data.get(0);
	}

	// zxl：修改未就业生信息,非考研保研人员
	public boolean updateUnEmp(int sid, int did, int jid, int uesalary, Date date) {
		String hsql = "update CmUnemp un set un.cmDirectionByDid.did=?,un.cmJobByJid.jid=?,un.uesalary=?,un.uetime=?,un.ueschool=null,un.uemajor=null ,un.uesuccess=null where un.cmStudentBySid.sid=?";
		hibernateTemplate.bulkUpdate(hsql, did, jid, uesalary, date, sid);
		return true;
	}

	// zxl：修改未就业生信息，考研保研人员
	public boolean updateUnEmp2(int sid, int did, String ueschool, String uemajor, int uesuccess) {
		String hsql = "update CmUnemp un set un.cmDirectionByDid.did=?,un.cmJobByJid.jid=0 ,un.uesalary=null ,un.uetime=null ,un.ueschool=?,un.uemajor=? ,un.uesuccess=? where un.cmStudentBySid.sid=?";
		hibernateTemplate.bulkUpdate(hsql, did, ueschool, uemajor, uesuccess, sid);
		return true;
	}

	// zxl：计算准备就业的有多少人
	public int findSumNotEmp(int did) {
		String hsql = "select  count(*) from CmUnemp un where un.cmDirectionByDid.did=? and un.uestate=0";
		List<Long> data = (List<Long>) hibernateTemplate.find(hsql, did);
		return new Integer(String.valueOf(data.get(0)));
	}

	// zxl：删除未就业生信息
	public boolean delUnEmp(int sid) {
		String hsql = "update CmUnemp un set un.uestate=1 where un.cmStudentBySid.sid=? ";
		hibernateTemplate.bulkUpdate(hsql, sid);
		return true;
	}

	// zxl：获取近一个月期望就业学生的数量
	public int findSumNotEmpMonth() throws Exception {
		String hsql = "select  count(*) from CmUnemp un where un.cmDirectionByDid.did=0 and un.uestate=0 and TO_DAYS(un.uetime)>=TO_DAYS(?) and TO_DAYS(un.uetime)<=TO_DAYS(?)";
		// String beginDate=new Date()
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		String str = dft.format(new java.util.Date());
		List<Long> data = (List<Long>) hibernateTemplate.find(hsql, str, new DateUtil().getStringDate());
		return new Integer(String.valueOf(data.get(0)));
	}

	// zxl：近一个月期望出去实习同学的详细信息
	public List<ResUnempObj> findAllUnempMonth() throws Exception {
		String hsql = "select new cn.edu.zzia.career.ResObj.ResUnempObj(un.ueid,s.sid,j.jid,d.did,un.uesalary,un.uetime,un.ueschool,un.uemajor,un.uesuccess,un.uestate,j.jname,s.sname,s.ssex,s.spro,s.sgrade,s.sclass,d.dname) "
				+ "from CmUnemp un " + "inner join un.cmStudentBySid s " + "inner join un.cmJobByJid j "
				+ "inner join un.cmDirectionByDid d "
				+ "where d.did=0 and un.uestate=0 and TO_DAYS(un.uetime)>=TO_DAYS(?) and TO_DAYS(un.uetime)<=TO_DAYS(?)";
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		String str = dft.format(new java.util.Date());
		List<ResUnempObj> data = (List<ResUnempObj>) hibernateTemplate.find(hsql, str,
				new DateUtil().getStringDate());
		if (data.size() > 0) {
			return data;
		}
		return null;
	}

	// zxl：计算未就业生数量
	public int findAllNotEmpCount() {
		String hsql = "select  count(*) from CmUnemp un inner join un.cmStudentBySid s where un.uestate=0 and s.sgrade=?";
		// int sgrade=studentService.findSgrage();
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		if (month >= 9) {
			year = year - 3;
		} else if (month < 9) {
			year = year - 4;
		}
		List<Long> data = (List<Long>) hibernateTemplate.find(hsql, year);
		if (data.size() > 0) {
			return new Integer(String.valueOf(data.get(0)));
		}
		return 0;
	}

	/**
	 * 查询所有未就业学生信息
	 * 
	 * @return
	 */
	public List<ResUnempObj> FindAllUnemp() {
		String hsql = "select new cn.edu.zzia.career.ResObj.ResUnempObj(unemp.ueid,stu.sid,job.jid,dir.did,unemp.uesalary,unemp.uetime,unemp.ueschool,unemp.uemajor,unemp.uesuccess,unemp.uestate,job.jname,stu.sname,stu.ssex,stu.spro,stu.sgrade,stu.sclass,dir.dname) "
				+ "from CmUnemp unemp " + "inner join unemp.cmStudentBySid stu " + "inner join unemp.cmJobByJid job "
				+ "inner join unemp.cmDirectionByDid dir " + "where unemp.uestate = 0";
		List<ResUnempObj> data = (List<ResUnempObj>) hibernateTemplate.find(hsql);
		System.out.println(data.size());
		return data;
	}

	/**
	 * 查询其他意向未就业学生
	 * 
	 * @return
	 */
	public List<ResUnempObj> AllOthers() {
		String hsql = "select new cn.edu.zzia.career.ResObj.ResUnempObj(unemp.ueid,stu.sid,job.jid,dir.did,unemp.uesalary,unemp.uetime,unemp.ueschool,unemp.uemajor,unemp.uesuccess,unemp.uestate,job.jname,stu.sname,stu.ssex,stu.spro,stu.sgrade,stu.sclass,dir.dname) "
				+ "from CmUnemp unemp " + "inner join unemp.cmStudentBySid stu " + "inner join unemp.cmJobByJid job "
				+ "inner join unemp.cmDirectionByDid dir " + "where unemp.uestate = 0 and dir.did != 0";
		List<ResUnempObj> data = (List<ResUnempObj>) hibernateTemplate.find(hsql);
		System.out.println(data.size());
		return data;
	}

	/**
	 * 查询所有准备就业学生
	 * 
	 * @return
	 */
	public List<ResUnempObj> AllDirectionEmp(int did) {
		String hsql = "select new cn.edu.zzia.career.ResObj.ResUnempObj(unemp.ueid,stu.sid,job.jid,dir.did,unemp.uesalary,unemp.uetime,unemp.ueschool,unemp.uemajor,unemp.uesuccess,unemp.uestate,job.jname,stu.sname,stu.ssex,stu.spro,stu.sgrade,stu.sclass,dir.dname) "
				+ "from CmUnemp unemp " + "inner join unemp.cmStudentBySid stu " + "inner join unemp.cmJobByJid job "
				+ "inner join unemp.cmDirectionByDid dir " + "where unemp.uestate = 0 and dir.did=?";
		List<ResUnempObj> data = (List<ResUnempObj>) hibernateTemplate.find(hsql, did);
		System.out.println(data.size());
		return data;
	}

	/**
	 * 分页查询所有未就业学生信息
	 * 
	 * @return
	 */
	public List<ResUnempObj> findAllUnempPage(final PageBean pageBean) {
		final String hsql = "select new cn.edu.zzia.career.ResObj.ResUnempObj(unemp.ueid,stu.sid,job.jid,dir.did,unemp.uesalary,unemp.uetime,unemp.ueschool,unemp.uemajor,unemp.uesuccess,unemp.uestate,job.jname,stu.sname,stu.ssex,stu.spro,stu.sgrade,stu.sclass,dir.dname) "
				+ "from CmUnemp unemp " + "inner join unemp.cmStudentBySid stu " + "inner join unemp.cmJobByJid job "
				+ "inner join unemp.cmDirectionByDid dir " + "where unemp.uestate = 0";
		final List<ResUnempObj> data = hibernateTemplate.execute(new HibernateCallback<List<ResUnempObj>>() {
			@Override
			public List<ResUnempObj> doInHibernate(Session session) throws HibernateException {
				List<ResUnempObj> list2 = session.createQuery(hsql).setFirstResult(pageBean.getStart())
						.setMaxResults(pageBean.getPageSize()).list();
				return list2;
			}
		});
		System.out.println(data.size());
		return data;
	}

	/**
	 * 查询该年级下该班级下所有未就业学生信息
	 * 
	 * @return
	 */
	public List<ResUnempObj> findUnempBySclass(int sgrade, int sclass) {
		String hsql = "select new cn.edu.zzia.career.ResObj.ResUnempObj(unemp.ueid,stu.sid,job.jid,dir.did,unemp.uesalary,unemp.uetime,unemp.ueschool,unemp.uemajor,unemp.uesuccess,unemp.uestate,job.jname,stu.sname,stu.ssex,stu.spro,stu.sgrade,stu.sclass,dir.dname) "
				+ "from CmUnemp unemp " + "inner join unemp.cmStudentBySid stu " + "inner join unemp.cmJobByJid job "
				+ "inner join unemp.cmDirectionByDid dir "
				+ "where stu.sgrade=? and stu.sclass=? and unemp.uestate = 0";
		Object[] value = { sgrade, sclass };
		List<ResUnempObj> data = (List<ResUnempObj>) hibernateTemplate.find(hsql, value);
		System.out.println(data.size());
		return data;
	}

	/**
	 * 统计未就业生数量
	 * 
	 * @return
	 */
	public int UnEmpCount() {
		String hsql = "select count(*) from CmUnemp unemp where unemp.uestate = 0 ";
		List<?> total = hibernateTemplate.find(hsql);
		System.out.println(Integer.parseInt(total.get(0).toString()));
		return Integer.parseInt(total.get(0).toString());
	}

	/**
	 * 统计未就业生数量
	 * 
	 * @return
	 */
	public int UnEmpCount2() {
		String hsql = "select count(*) from CmUnemp unemp where unemp.uestate = 0 order by unemp.uetime desc ";
		List<?> total = hibernateTemplate.find(hsql);
		System.out.println(Integer.parseInt(total.get(0).toString()));
		return Integer.parseInt(total.get(0).toString());
	}

	/**
	 * 按学生动向查询未就业学生信息
	 * 
	 * @return
	 */
	public List<ResUnempObj> FindByDname(String dname) {
		System.out.println(dname);
		String hsql = "select new cn.edu.zzia.career.ResObj.ResUnempObj(unemp.ueid,stu.sid,job.jid,dir.did,unemp.uesalary,unemp.uetime,unemp.ueschool,unemp.uemajor,unemp.uesuccess,unemp.uestate,job.jname,stu.sname,stu.ssex,stu.spro,stu.sgrade,stu.sclass,dir.dname) "
				+ "from CmUnemp unemp " + "inner join unemp.cmStudentBySid stu inner join unemp.cmJobByJid job "
				+ "inner join unemp.cmDirectionByDid dir " + "where dir.dname like ? and unemp.uestate = 0";
		List<ResUnempObj> data = (List<ResUnempObj>) hibernateTemplate.find(hsql, "%" + dname + "%");
		System.out.println(data);
		return data;
	}

	/**
	 * 按姓名查询未就业学生信息
	 * 
	 * @return
	 */
	public List<ResUnempObj> FindBySname(String sname) {
		System.out.println(sname);
		String hsql = "select new cn.edu.zzia.career.ResObj.ResUnempObj(unemp.ueid,stu.sid,job.jid,dir.did,unemp.uesalary,unemp.uetime,unemp.ueschool,unemp.uemajor,unemp.uesuccess,unemp.uestate,job.jname,stu.sname,stu.ssex,stu.spro,stu.sgrade,stu.sclass,dir.dname) "
				+ "from CmUnemp unemp "
				+ "inner join unemp.cmStudentBySid stu inner join unemp.cmJobByJid job inner join unemp.cmDirectionByDid dir "
				+ "where stu.sname like ? and unemp.uestate = 0";
		List<ResUnempObj> data = (List<ResUnempObj>) hibernateTemplate.find(hsql, "%" + sname + "%");
		return data;
	}

	/**
	 * 按年级查询已就业学生信息
	 * 
	 * @return
	 */
	public List<ResUnempObj> FindBySgrade(int sgrade) {
		System.out.println(sgrade);
		String hsql = "select new cn.edu.zzia.career.ResObj.ResUnempObj(unemp.ueid,stu.sid,job.jid,dir.did,unemp.uesalary,unemp.uetime,unemp.ueschool,unemp.uemajor,unemp.uesuccess,unemp.uestate,job.jname,stu.sname,stu.ssex,stu.spro,stu.sgrade,stu.sclass,dir.dname) "
				+ "from CmUnemp unemp "
				+ "inner join unemp.cmStudentBySid stu inner join unemp.cmJobByJid job inner join unemp.cmDirectionByDid dir "
				+ "where stu.sgrade = ? and unemp.uestate = 0";
		List<ResUnempObj> data = (List<ResUnempObj>) hibernateTemplate.find(hsql, sgrade);
		return data;
	}

	/**
	 * 删除未就业学生信息
	 * 
	 * @param ueid
	 * @return
	 */
	public boolean DelUnEmp(Integer ueid) {
		System.out.println(ueid);
		String hsql = "update CmUnemp unemp set unemp.uestate=1 where unemp.ueid = ?";
		hibernateTemplate.bulkUpdate(hsql, ueid);
		return true;
	}

	/* TianYu 上传excel */
	public String uploadUnemp(String path) throws Exception {
		Session session = hibernateTemplate.getSessionFactory().openSession();
		try {
			List<CmUnemp> ls = this.inputUnemp(path);
			for (CmUnemp cc : ls) {
				session.save(cc);
			}
			session.close();
			return "导入成功！";
		} catch (IOException e) {
			return "数据格式错误！";
		} catch (Exception e) {
			e.printStackTrace();
			return "数据格式错误！";
		}
	}

	public List<CmUnemp> inputUnemp(String path) throws Exception {
		List<CmUnemp> temp = new ArrayList();
		FileInputStream fileIn = new FileInputStream(path);
		Workbook wb0 = new HSSFWorkbook(fileIn);
		Sheet sht0 = wb0.getSheetAt(0);
		for (Row r : sht0) {
			// 如果当前行的行号（从0开始）未达到2（第三行）则从新循环
			if (r.getRowNum() < 1) {
				continue;
			}
			CmUnemp cu = new CmUnemp();
			String hql = "from CmStudent cs where cs.sname = ? ";
			cu.setCmStudentBySid((CmStudent) hibernateTemplate.find(hql, r.getCell(0).getStringCellValue()).get(0));

			String dir = "from CmDirection cd where cd.dname = ?";
			cu.setCmDirectionByDid((CmDirection) hibernateTemplate.find(dir, r.getCell(1).getStringCellValue()).get(0));

			String job = "from CmJob cj where cj.jname = ? ";
			cu.setCmJobByJid((CmJob) hibernateTemplate.find(job, r.getCell(2).getStringCellValue()).get(0));

			cu.setUesalary((int) r.getCell(3).getNumericCellValue());
			cu.setUetime(new java.sql.Date(r.getCell(4).getDateCellValue().getTime()));
			cu.setUeschool(r.getCell(5).getStringCellValue());
			cu.setUemajor(r.getCell(6).getStringCellValue());
			if (r.getCell(7).getStringCellValue().equals("暂无")) {
				cu.setUesuccess(0);
			} else if (r.getCell(7).getStringCellValue().equals("成功")) {
				cu.setUesuccess(1);
			} else if (r.getCell(7).getStringCellValue().equals("失败")) {
				cu.setUesuccess(2);
			}
			cu.setUestate(0);
			temp.add(cu);
		}
		fileIn.close();
		return temp;
	}

	/* TianYu 下载excel */
	public String outUnemp() {
		String hsql = "select new cn.edu.zzia.career.ResObj.ResUnempObj(unemp.ueid,stu.sid,job.jid,dir.did,unemp.uesalary,unemp.uetime,unemp.ueschool,unemp.uemajor,unemp.uesuccess,unemp.uestate,job.jname,stu.sname,stu.ssex,stu.spro,stu.sgrade,stu.sclass,dir.dname) "
				+ "from CmUnemp unemp " + "inner join unemp.cmStudentBySid stu " + "inner join unemp.cmJobByJid job "
				+ "inner join unemp.cmDirectionByDid dir " + "where unemp.uestate = 0";
		List<ResUnempObj> data = (List<ResUnempObj>) hibernateTemplate.find(hsql);
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("未就业生信息表");
		HSSFRow row1 = sheet.createRow(0);
		row1.setHeight((short) 20);
		HSSFCell cell = row1.createCell(0);
		cell.setCellValue("未就业生信息");
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 14));
		HSSFRow row2 = sheet.createRow(1);
		row2.createCell(0).setCellValue("ueid");
		row2.createCell(1).setCellValue("姓名");
		row2.createCell(2).setCellValue("性别");
		row2.createCell(3).setCellValue("专业");
		row2.createCell(4).setCellValue("年级");
		row2.createCell(5).setCellValue("班级");
		row2.createCell(6).setCellValue("动向");
		row2.createCell(7).setCellValue("期望岗位");
		row2.createCell(8).setCellValue("期望月薪");
		row2.createCell(9).setCellValue("期望就业时间");
		row2.createCell(10).setCellValue("考研学校");
		row2.createCell(11).setCellValue("考研专业");
		row2.createCell(12).setCellValue("动向状态");
		int rownum = 2;
		// 在sheet里创建数据
		for (ResUnempObj es : data) {
			HSSFRow row = sheet.createRow(rownum);
			row.createCell(0).setCellValue(es.getUeid());
			row.createCell(1).setCellValue(es.getSname());
			if (es.getSsex()) {
				row.createCell(2).setCellValue("女");
			} else {
				row.createCell(2).setCellValue("男");
			}
			row.createCell(3).setCellValue(es.getSpro());
			row.createCell(4).setCellValue(es.getSgrade());
			row.createCell(5).setCellValue(es.getSclass());
			row.createCell(6).setCellValue(es.getDname());
			row.createCell(7).setCellValue(es.getJname());
			row.createCell(8).setCellValue(es.getUesalary());
			if (es.getUetime() != null) {
				row.createCell(9).setCellValue(es.getUetime());
			} else {
				row.createCell(9).setCellValue("无");
			}
			if (es.getUeschool() != null) {
				row.createCell(10).setCellValue(es.getUeschool());
			} else {
				row.createCell(10).setCellValue("无");
			}
			if (es.getUemajor() != null) {
				row.createCell(11).setCellValue(es.getUemajor());
			} else {
				row.createCell(11).setCellValue("无");
			}
			if (es.getUesuccess() == null) {
				row.createCell(12).setCellValue("暂无");
			}
			if (es.getUesuccess() == 0) {
				row.createCell(12).setCellValue("暂无");
			} else if (es.getUesuccess() == 1) {
				row.createCell(12).setCellValue("成功");
			} else if (es.getUesuccess() == 2) {
				row.createCell(12).setCellValue("失败");
			}
			rownum++;
		}
		OutputData od = new OutputData();
		String file = od.fileNameConvert(wb, "未就业生信息");
		return file;
	}
}
