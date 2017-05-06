package cn.edu.zzia.career.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;

import cn.edu.zzia.career.ResObj.InterResObj;
import cn.edu.zzia.career.dao.ICompanyDao;
import cn.edu.zzia.career.dao.IInterDao;
import cn.edu.zzia.career.dao.IJobDao;
import cn.edu.zzia.career.dao.IRecruitDao;
import cn.edu.zzia.career.pojo.CmInter;
import cn.edu.zzia.career.pojo.CmRecruit;
import cn.edu.zzia.career.tools.DateUtil;
import cn.edu.zzia.career.tools.OutputData;
import cn.edu.zzia.career.tools.PageBean;

/**
 * Created by w on 2016/10/26.
 */
@Service("interService")
public class InterService {

	@Autowired
	private IInterDao interDao;

	@Autowired
	private IRecruitDao recruitDao;
	
	/**
	 * 根据学生的id查询该学生所有的面试
	 * @param studentId
	 * @return
	 */
	public List<CmInter> findAllInterByStudentId(Integer studentId) {
		if(null != studentId){
			String whereHql = " and o.cmStudentBySid.sid = ? ";
			Object[] params = {studentId};
			return interDao.findObjectsByConditionWithNoPage(whereHql, params);
			
		}
		return null;
	}

	/**
	 * 更新面试信息
	 * 
	 * @param inter
	 */
	public void updateInter(CmInter inter) {

		if (null != inter) {

			interDao.update(inter);
		}
	}

	/**
	 * 根据企业的id，先查询该企业所有的招聘信息，然后再循环查询和招聘相关的面试
	 * 
	 * @param companyId
	 * @return
	 */
	public List<CmInter> findInterByCompanyId(Integer companyId) {

		if (null != companyId) {

			String whereHql = " and o.cmCompanyByCid.cid = ? ";
			Object[] params = { companyId };

			List<CmInter> cmInters = new ArrayList<>();

			List<CmRecruit> recruits = recruitDao.findObjectsByConditionWithNoPage(whereHql, params);

			for (int i = 0; null != recruits && i < recruits.size(); i++) {

				CmRecruit recruit = recruits.get(i);

				whereHql = " and o.cmRecruitByRid.rid = ? ";
				Object[] params2 = { recruit.getRid() };

				List<CmInter> cmList = interDao.findObjectsByConditionWithNoPage(whereHql, params2);
				cmInters.addAll(cmList);
			}
			return cmInters;
		}

		return null;
	}

	/**
	 * 保存一个面试信息
	 * 
	 * @param inter
	 */
	public void saveInter(CmInter inter) {

		if (null != inter) {
			interDao.save(inter);
		}
	}

	/********************************************************************************************/
	@Autowired
	private HibernateTemplate hibernateTemplate;
	@Autowired
	private AreaService areaService;
	@Autowired
	private RecruitService recruitService;
	@Autowired
	private StudentService studentService;

	// 增加面试学生——ly
	public boolean addInter(int sid, int rid, int aid, String iaddress, String itype, String itime)
			throws ParseException {
		CmInter inter = new CmInter();
		inter.setCmStudentBySid(studentService.findBySid(sid));
		inter.setCmRecruitByRid(recruitService.findByRid(rid));
		inter.setCmAreaByAid(areaService.findByAid(aid));
		inter.setIaddress(iaddress);
		inter.setItype(itype);
		inter.setIsuccess(0);
		inter.setIstate(0);
		try {
			inter.setItime( DateUtil.StringtoUtilDate(itime));
			hibernateTemplate.save(inter);
			return true;
		} catch (Exception e) {
			System.out.println("添加面试学生出错");
		}
		return false;
	}

	// 批量增加面试学生——ly
	public boolean addInters(String sid, int rid, int aid, String iaddress, String itype, String itime)
			throws ParseException {
		Session session = hibernateTemplate.getSessionFactory().openSession();
		String spl[] = sid.split(",");
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < spl.length; i++) {
			list.add(spl[i]);
		}
		System.out.println("sid的list集合----" + list);
		try {
			for (int j = 0; j < list.size(); j++) {
				CmInter inter = new CmInter();
				inter.setCmStudentBySid(studentService.findBySid(Integer.parseInt(list.get(j))));
				inter.setCmRecruitByRid(recruitService.findByRid(rid));
				inter.setCmAreaByAid(areaService.findByAid(aid));
				inter.setIaddress(iaddress);
				inter.setItype(itype);
				inter.setItime( DateUtil.StringtoUtilDate(itime));
				inter.setIsuccess(0);
				inter.setIstate(0);
				session.save(inter);
			}
			session.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// 删除面试学生——ly
	public boolean delInter(int iid) {
		CmInter inter = this.findByIid(iid);
		if (inter != null) {
			inter.setIstate(1);
			hibernateTemplate.saveOrUpdate(inter);
			return true;
		}
		return false;
	}

	// 编辑面试学生——ly
	public boolean updateInter(int iid, int isuccess, String isuccleave) {
		CmInter inter = this.findByIid(iid);
		if (inter != null) {
			inter.setIsuccess(isuccess);
			if (isuccleave != null) {
				inter.setIsuccleave(isuccleave);
			}
			hibernateTemplate.saveOrUpdate(inter);
			return true;
		}
		return false;
	}

	// 按不同条件查询该招聘信息下的面试学生——ly
	public List<InterResObj> query(int rid, int type, String searchtext) {
		List<InterResObj> data = new ArrayList<>();
		String hsql = "select new cn.edu.zzia.career.ResObj.InterResObj(i.iid,i.iaddress,i.itype,i.itime,i.isuccess,r.rid,a.aid,a.aprovince,a.acity,s.sid,s.sname,s.ssex,s.sbirth,s.spro,s.sgrade,s.sclass,s.sphone) "
				+ "from CmInter i " + "inner join i.cmRecruitByRid r " + "inner join i.cmAreaByAid a "
				+ "inner join i.cmStudentBySid s " + "where i.istate = 0 and i.cmRecruitByRid.rid = ? ";
		if (type == 0) {
			hsql = hsql + "and s.sname like ? order by i.itime desc";
			Object[] value = { rid, "%" + searchtext + "%" };
			data = (List<InterResObj>) hibernateTemplate.find(hsql, value);
		} else if (type == 1) {
			hsql = hsql + "and s.spro like ? order by i.itime desc";
			Object[] value = { rid, "%" + searchtext + "%" };
			data = (List<InterResObj>) hibernateTemplate.find(hsql, value);
		} else {
			int sgrade = Integer.parseInt(searchtext);
			hsql = hsql + "and s.sgrade = ? order by i.itime desc";
			Object[] value = { rid, sgrade };
			data = (List<InterResObj>) hibernateTemplate.find(hsql, value);
		}
		if (data.size() > 0) {
			return data;
		}
		System.out.println("未查到相关数据！");
		return null;
	}

	// 按发布时间段查询面试信息——ly
	public List<InterResObj> FindByDate(int rid, String startDate, String endDate) {
		System.out.println("startDate----" + startDate);
		System.out.println("endDate----" + endDate);
		String hsql = "select new cn.edu.zzia.career.ResObj.InterResObj(i.iid,i.iaddress,i.itype,i.itime,i.isuccess,r.rid,a.aid,a.aprovince,a.acity,s.sid,s.sname,s.ssex,s.sbirth,s.spro,s.sgrade,s.sclass,s.sphone) "
				+ "from CmInter i " + "inner join i.cmRecruitByRid r " + "inner join i.cmAreaByAid a "
				+ "inner join i.cmStudentBySid s "
				+ "where i.istate = 0 and i.cmRecruitByRid.rid = ? and (TO_DAYS(i.itime)>=TO_DAYS(?) and TO_DAYS(i.itime)<=TO_DAYS(?)) order by i.itime desc ";
		Object[] value = { rid, startDate, endDate };
		List<InterResObj> data = (List<InterResObj>) hibernateTemplate.find(hsql, value);
		if (data.size() > 0) {
			return data;
		}
		System.out.println("未查到相关数据！");
		return null;
	}

	// 按iid查询面试学生——ly
	public CmInter findByIid(int iid) {
		String hsql = "from CmInter i where i.iid = ?";
		List<CmInter> data = (List<CmInter>) hibernateTemplate.find(hsql, iid);
		if (data.size() > 0) {
			return data.get(0);
		}
		System.out.println("未查到相关数据！");
		return null;
	}

	// 按iid查询面试学生——ly
	public InterResObj findResObjByIid(int iid) {
		String hsql = "select new cn.edu.zzia.career.ResObj.InterResObj(i.iid,i.iaddress,i.itype,i.itime,i.isuccess,r.rid,a.aid,a.aprovince,a.acity,s.sid,s.sname,s.ssex,s.sbirth,s.spro,s.sgrade,s.sclass,s.sphone) "
				+ "from CmInter i " + "inner join i.cmRecruitByRid r " + "inner join i.cmAreaByAid a "
				+ "inner join i.cmStudentBySid s " + "where i.istate = 0 and i.iid = ?";
		List<InterResObj> data = (List<InterResObj>) hibernateTemplate.find(hsql, iid);
		if (data.size() > 0) {
			return data.get(0);
		}
		System.out.println("未查到相关数据！");
		return null;
	}

	// 查询学生的面试记录——ly
	public List<InterResObj> findInterBySid(int sid) {
		String hsql = "select new cn.edu.zzia.career.ResObj.InterResObj(i.iid,i.iaddress,i.itype,i.itime,i.isuccess,r.rid,c.cid,c.cname,j.jid,j.jname,a.aid,a.aprovince,a.acity,s.sid,s.sname,s.sno) "
				+ "from CmInter i " + "inner join i.cmRecruitByRid r " + "inner join r.cmCompanyByCid c "
				+ "inner join r.cmJobByJid j " + "inner join i.cmAreaByAid a " + "inner join i.cmStudentBySid s "
				+ "where i.istate = 0 and s.sid = ? ";
		List<InterResObj> data = (List<InterResObj>) hibernateTemplate.find(hsql, sid);
		if (data.size() > 0) {
			return data;
		}
		System.out.println("未查到相关数据！");
		return null;
	}

	// 查询面试人数——ly
	public int findByRidCount(int rid) {
		String hsql = "select count(*) from CmInter i where i.istate = 0 and i.cmRecruitByRid.rid = ?";
		List<?> data = hibernateTemplate.find(hsql, rid);
		System.out.println("面试报名人数：" + Integer.parseInt(data.get(0).toString()));
		return Integer.parseInt(data.get(0).toString());
	}

	// 查询面试次数——ly
	public int findBySidTimes(int sid) {
		String hsql = "select count(*) from CmInter i where i.istate = 0 and i.cmStudentBySid.sid = ?";
		List<?> data = hibernateTemplate.find(hsql, sid);
		System.out.println("面试次数：" + Integer.parseInt(data.get(0).toString()));
		return Integer.parseInt(data.get(0).toString());
	}

	// 查询面试人员信息——ly
	public List<InterResObj> findByRid(int rid) {
		String hsql = "select new cn.edu.zzia.career.ResObj.InterResObj(i.iid,i.iaddress,i.itype,i.itime,i.isuccess,r.rid,a.aid,a.aprovince,a.acity,s.sid,s.sname,s.ssex,s.sbirth,s.spro,s.sgrade,s.sclass,s.sphone) "
				+ "from CmInter i " + "inner join i.cmRecruitByRid r " + "inner join i.cmAreaByAid a "
				+ "inner join i.cmStudentBySid s " + "where i.istate = 0 and r.rid = ? order by i.itime desc ";
		List<InterResObj> data = (List<InterResObj>) hibernateTemplate.find(hsql, rid);
		if (data.size() > 0) {
			return data;
		}
		System.out.println("未查到相关数据！");
		return null;
	}

	// 查询所有的面试记录——ly
	public List<InterResObj> findAll(final PageBean pageBean) {
		final String hsql = "select new cn.edu.zzia.career.ResObj.InterResObj(i.iid,i.iaddress,i.itype,i.itime,i.isuccess,r.rid,c.cid,c.cname,j.jid,j.jname,a.aid,a.aprovince,a.acity,s.sid,s.sname,s.sno) "
				+ "from CmInter i " + "inner join i.cmRecruitByRid r " + "inner join r.cmCompanyByCid c "
				+ "inner join r.cmJobByJid j " + "inner join i.cmAreaByAid a " + "inner join i.cmStudentBySid s "
				+ "where i.istate = 0 order by i.itime desc ";
		final List<InterResObj> data = (List<InterResObj>) hibernateTemplate.execute(new HibernateCallback<List>() {
			@Override
			public List doInHibernate(Session session) throws HibernateException {
				List data2 = session.createQuery(hsql).setFirstResult(pageBean.getStart())
						.setMaxResults(pageBean.getPageSize()).list();
				if (data2.size() > 0) {
					return data2;
				}
				System.out.println("未查到相关数据！");
				return null;
			}
		});
		return data;
	}

	// 查询所有面试记录数量——ly
	public int findAllCount() {
		String hsql = "select count(*) from CmInter i where i.istate = 0 ";
		List<?> data = hibernateTemplate.find(hsql);
		if (data.get(0) != null) {
			return Integer.parseInt(data.get(0).toString());
		}
		return 0;
	}

	// 按不同条件查询面试学生——ly
	public List<InterResObj> query2(int type, String searchtext) {
		String hsql = "select new cn.edu.zzia.career.ResObj.InterResObj(i.iid,i.iaddress,i.itype,i.itime,i.isuccess,r.rid,c.cid,c.cname,j.jid,j.jname,a.aid,a.aprovince,a.acity,s.sid,s.sname,s.sno) "
				+ "from CmInter i " + "inner join i.cmRecruitByRid r " + "inner join r.cmCompanyByCid c "
				+ "inner join r.cmJobByJid j " + "inner join i.cmAreaByAid a " + "inner join i.cmStudentBySid s "
				+ "where i.istate = 0 ";
		List<InterResObj> data = new ArrayList<InterResObj>();
		if (type == 0) {
			hsql = hsql + "and s.sname like ?  order by i.itime desc ";
			data = (List<InterResObj>) hibernateTemplate.find(hsql, "%" + searchtext + "%");
		} else if (type == 1) {
			hsql = hsql + "and s.sno = ?  order by i.itime desc ";
			data = (List<InterResObj>) hibernateTemplate.find(hsql, searchtext);
		} else if (type == 2) {
			hsql = hsql + "and c.cname like ?  order by i.itime desc ";
			data = (List<InterResObj>) hibernateTemplate.find(hsql, "%" + searchtext + "%");
		}
		if (data.size() > 0) {
			return data;
		}
		System.out.println("未查到相关数据！");
		return null;
	}

	// 按发布时间段查询面试信息——ly
	public List<InterResObj> FindByDate2(String startDate, String endDate) {
		System.out.println("startDate----" + startDate);
		System.out.println("endDate----" + endDate);
		String hsql = "select new cn.edu.zzia.career.ResObj.InterResObj(i.iid,i.iaddress,i.itype,i.itime,i.isuccess,r.rid,c.cid,c.cname,j.jid,j.jname,a.aid,a.aprovince,a.acity,s.sid,s.sname,s.sno) "
				+ "from CmInter i " + "inner join i.cmRecruitByRid r " + "inner join r.cmCompanyByCid c "
				+ "inner join r.cmJobByJid j " + "inner join i.cmAreaByAid a "
				+ "inner join i.cmStudentBySid s where i.istate = 0 and (TO_DAYS(i.itime)>=TO_DAYS(?) and TO_DAYS(i.itime)<=TO_DAYS(?)) order by i.itime desc ";
		Object[] value = { startDate, endDate };
		List<InterResObj> data = (List<InterResObj>) hibernateTemplate.find(hsql, value);
		if (data.size() > 0) {
			return data;
		}
		System.out.println("未查到相关数据！");
		return null;
	}

	// 查询当天面试学生数量——ly
	public int findCountByDay() {
		String hsql = "select count(*) from CmInter i where i.istate = 0 and TO_DAYS(i.itime) = TO_DAYS(NOW()) order by i.itime desc ";
		List<?> data = hibernateTemplate.find(hsql);
		if (data.get(0) != null) {
			return Integer.parseInt(data.get(0).toString());
		}
		return 0;
	}

	// 查询当天面试学生——ly
	public List<InterResObj> findByDay() {
		String hsql = "select new cn.edu.zzia.career.ResObj.InterResObj(i.iid,i.iaddress,i.itype,i.itime,i.isuccess,r.rid,c.cid,c.cname,j.jid,j.jname,a.aid,a.aprovince,a.acity,s.sid,s.sname,s.sno) "
				+ "from CmInter i " + "inner join i.cmRecruitByRid r " + "inner join r.cmCompanyByCid c "
				+ "inner join r.cmJobByJid j " + "inner join i.cmAreaByAid a "
				+ "inner join i.cmStudentBySid s where i.istate = 0 and TO_DAYS(i.itime) = TO_DAYS(NOW()) order by i.itime desc ";
		List<InterResObj> data = (List<InterResObj>) hibernateTemplate.find(hsql);
		if (data.size() > 0) {
			return data;
		}
		System.out.println("未查到相关数据！");
		return null;
	}

	/* TianYu 导出全部面试学生 */
	public String outputInter() {
		String hsql = "select new cn.edu.zzia.career.ResObj.InterResObj(i.iid,i.iaddress,i.itype,i.itime,i.isuccess,r.rid,c.cid,c.cname,j.jid,j.jname,a.aid,a.aprovince,a.acity,s.sid,s.sname,s.ssex,s.sno,s.spro,s.sgrade,s.sclass,s.sphone) "
				+ "from CmInter i " + "inner join i.cmRecruitByRid r " + "inner join r.cmCompanyByCid c "
				+ "inner join r.cmJobByJid j " + "inner join i.cmAreaByAid a " + "inner join i.cmStudentBySid s "
				+ "where i.istate = 0 ";
		List<InterResObj> data = (List<InterResObj>) hibernateTemplate.find(hsql);
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("面试信息表");
		HSSFRow row1 = sheet.createRow(0);
		HSSFCell cell = row1.createCell(0);
		row1.setHeight((short) 20);
		cell.setCellValue("面试信息");
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 12));
		HSSFRow row2 = sheet.createRow(1);
		row2.createCell(0).setCellValue("姓名");
		row2.createCell(1).setCellValue("性别");
		row2.createCell(2).setCellValue("专业");
		row2.createCell(3).setCellValue("年级");
		row2.createCell(4).setCellValue("班级");
		row2.createCell(5).setCellValue("学号");
		row2.createCell(6).setCellValue("电话");
		row2.createCell(7).setCellValue("面试企业");
		row2.createCell(8).setCellValue("面试岗位");
		row2.createCell(9).setCellValue("面试时间");
		row2.createCell(10).setCellValue("面试城市");
		row2.createCell(11).setCellValue("面试地点");
		row2.createCell(12).setCellValue("面试方式");
		row2.createCell(13).setCellValue("面试状态");
		int rownum = 2;
		// 在sheet里创建数据
		for (InterResObj es : data) {
			HSSFRow row = sheet.createRow(rownum);
			row.createCell(0).setCellValue(es.getSname());
			if (es.getSsex() == null) {
				row.createCell(1).setCellValue("性别不限");
			} else {
				if (es.getSsex()) {
					row.createCell(1).setCellValue("女");
				} else {
					row.createCell(1).setCellValue("男");
				}
			}
			row.createCell(2).setCellValue(es.getSpro());
			row.createCell(3).setCellValue(es.getSgrade());
			row.createCell(4).setCellValue(es.getSclass());
			row.createCell(5).setCellValue(es.getSno());
			row.createCell(6).setCellValue(es.getSphone());
			row.createCell(7).setCellValue(es.getCname());
			row.createCell(8).setCellValue(es.getJname());
			row.createCell(9).setCellValue(es.getItime());
			row.createCell(10).setCellValue(es.getAprovince() + es.getAcity());
			row.createCell(11).setCellValue(es.getIaddress());
			row.createCell(12).setCellValue(es.getItype());
			if (es.getIsuccess() == 0) {
				row.createCell(13).setCellValue("暂无");
			} else if (es.getIsuccess() == 1) {
				row.createCell(13).setCellValue("成功");
			} else if (es.getIsuccess() == 2) {
				row.createCell(13).setCellValue("失败");
			}
			rownum++;
		}
		OutputData od = new OutputData();
		String file = od.fileNameConvert(wb, "面试信息");
		return file;
	}

}
