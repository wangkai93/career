package cn.edu.zzia.career.service;

import java.text.DateFormat;
import java.text.ParseException;
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

import cn.edu.zzia.career.ResObj.RecruitResObj;
import cn.edu.zzia.career.dao.IRecruitDao;
import cn.edu.zzia.career.pojo.CmRecruit;
import cn.edu.zzia.career.tools.DateUtil;
import cn.edu.zzia.career.tools.OutputData;
import cn.edu.zzia.career.tools.PageBean;

/**
 * Created by w on 2016/10/31.
 */
@Service("recruitService")
public class RecruitService {

	@Autowired
	private IRecruitDao recruitDao;

	public List<CmRecruit> findRecruitByState(Integer state) {

		if (null != state) {

			String whereHql = " and o.rstate = ? ";
			Object[] params = { state };
			return recruitDao.findObjectsByConditionWithNoPage(whereHql, params);
		}
		return null;
	}

	/**
	 * 更新招聘信息
	 * 
	 * @param recruit
	 */
	public void updateRecruit(CmRecruit recruit) {

		recruitDao.update(recruit);
	}

	/**
	 * 查询所有的招聘信息
	 * 
	 * @return
	 */
	public List<CmRecruit> findAllRecruit() {
		return recruitDao.findObjectsByConditionWithNoPage();
	}

	/**
	 * 增加一个招聘信息
	 * 
	 * @param recruit
	 * @throws Exception 
	 */
	public void addRecruit(CmRecruit recruit) throws Exception {
		if (null != recruit) {

			recruit.setRstart(new DateUtil().SysDate());
			recruitDao.save(recruit);
		}
	}

	/**
	 * 根据招聘id查找招聘信息
	 * 
	 * @param recuritId
	 * @return
	 */
	public CmRecruit findRecruitById(Integer recuritId) {

		if (null != recuritId) {
			return recruitDao.findObjectById(recuritId);
		}
		return null;
	}

	/**
	 * 根据类型查招聘信息
	 * 
	 * @param type
	 * @return
	 */
	public List<CmRecruit> findRecruitByType(Integer type) {

		if (null != type) {
			String whereHql = " and o.rtype = ? and rstate = ? ";
			Object[] params = { type, 0 };
			return recruitDao.findObjectsByConditionWithNoPage(whereHql, params);
		} else {
			return recruitDao.findObjectsByConditionWithNoPage();
		}
	}

	/*******************************************************************************************/

	@Autowired
	private HibernateTemplate hibernateTemplate;
	@Autowired
	private JobService jobService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private AreaService areaService;

	// 增加招聘信息——ly
	public boolean addRecruit(int cid, int aid, int jid, int rsalary, Boolean rsex, int rnum, String rend, String rinfo)
			throws ParseException {
		CmRecruit recruit = new CmRecruit();
		recruit.setCmCompanyByCid(companyService.findCompByCid(cid));
		recruit.setCmAreaByAid(areaService.findByAid(aid));
		recruit.setCmJobByJid(jobService.findByJid(jid));
		recruit.setRsex(rsex);
		/*
		 * if(rsex!=null){ recruit.setRsex(rsex); }
		 */
		recruit.setRsalary(rsalary);
		recruit.setRnum(rnum);
		recruit.setRinfo(rinfo);
		recruit.setRstate(0);
		try {
			recruit.setRstart(new DateUtil().SysDate());// 发布时间为当前系统时间
			recruit.setRend(DateUtil.StringtoUtilDate(rend));
			hibernateTemplate.save(recruit);
		} catch (Exception e) {
			System.out.println("添加招聘信息出错");
		}
		return true;
	}

	// 删除招聘信息——ly
	public boolean delRecruit(int rid) {
		CmRecruit recruit = this.findByRid(rid);
		if (recruit != null) {
			recruit.setRstate(1);
			hibernateTemplate.saveOrUpdate(recruit);
			return true;
		}
		return false;
	}

	// 编辑招聘信息——ly
	public boolean updateRecruit(int rid, int cid, int aid, int jid, int rsalary, Boolean rsex, int rnum, String rend,
			String rinfo) {
		CmRecruit recruit = this.findByRid(rid);
		if (recruit != null) {
			recruit.setCmJobByJid(jobService.findByJid(jid));
			recruit.setCmAreaByAid(areaService.findByAid(aid));
			recruit.setCmCompanyByCid(companyService.findCompByCid(cid));
			recruit.setRsalary(rsalary);
			recruit.setRsex(rsex);
			/*
			 * if(rsex!=null){ recruit.setRsex(rsex); }else{
			 * recruit.setRsex(null); }
			 */
			recruit.setRnum(rnum);
			try {
				recruit.setRend(DateUtil.StringtoUtilDate(rend));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			recruit.setRinfo(rinfo);
			hibernateTemplate.saveOrUpdate(recruit);
			return true;
		}
		return false;
	}

	// 按企业名称模糊查询招聘信息——ly
	public List<RecruitResObj> findByCname(String cname) {
		String hsql = "select new cn.edu.zzia.career.ResObj.RecruitResObj(r.rid,r.rsex,r.rsalary,r.rstart,r.rend,r.rnum,r.rinfo,r.rstate,a.aid,a.aprovince,a.acity,j.jid,j.jname,c.cid,c.cname,c.chr,c.cphone,c.cemail) "
				+ "from CmRecruit r " + "inner join r.cmAreaByAid a " + "inner join r.cmJobByJid j "
				+ "inner join r.cmCompanyByCid c " + "where r.rstate = 0 and c.cname like ? order by r.rstart desc ";
		List<RecruitResObj> data = (List<RecruitResObj>) hibernateTemplate.find(hsql, "%" + cname + "%");
		if (data.size() > 0) {
			return data;
		}
		System.out.println("未查到相关数据！");
		return null;
	}

	// 按发布时间段查询招聘信息——ly
	public List<RecruitResObj> FindByDate(String startDate, String endDate) {
		System.out.println("startDate----" + startDate);
		System.out.println("endDate----" + endDate);
		String hsql = "select new cn.edu.zzia.career.ResObj.RecruitResObj(r.rid,r.rsex,r.rsalary,r.rstart,r.rend,r.rnum,r.rinfo,r.rstate,a.aid,a.aprovince,a.acity,j.jid,j.jname,c.cid,c.cname,c.chr,c.cphone,c.cemail) "
				+ "from CmRecruit r " + "inner join r.cmAreaByAid a " + "inner join r.cmJobByJid j "
				+ "inner join r.cmCompanyByCid c "
				+ "where r.rstate = 0 and TO_DAYS(r.rstart)>=TO_DAYS(?) and TO_DAYS(r.rstart)<=TO_DAYS(?) order by r.rstart desc ";
		Object[] value = { startDate, endDate };
		List<RecruitResObj> data = (List<RecruitResObj>) hibernateTemplate.find(hsql, value);
		if (data.size() > 0) {
			return data;
		}
		System.out.println("未查到相关数据！");
		return null;
	}

	// 按cid查询招聘信息——ly
	public List<RecruitResObj> findByCid(int cid) {
		String hsql = "select new cn.edu.zzia.career.ResObj.RecruitResObj(r.rid,r.rsex,r.rsalary,r.rstart,r.rend,r.rnum,r.rinfo,r.rstate,a.aid,a.aprovince,a.acity,j.jid,j.jname,c.cid,c.cname,c.chr,c.cphone,c.cemail) "
				+ "from CmRecruit r " + "inner join r.cmAreaByAid a " + "inner join r.cmJobByJid j "
				+ "inner join r.cmCompanyByCid c " + "where r.rstate = 0 and c.cid = ? order by r.rstart desc ";
		List<RecruitResObj> data = (List<RecruitResObj>) hibernateTemplate.find(hsql, cid);
		if (data.size() > 0) {
			return data;
		}
		System.out.println("未查到相关数据！");
		return null;
	}

	// 按rid查询招聘表——ly
	public CmRecruit findByRid(int rid) {
		String hsql = "from CmRecruit r where r.rid = ?";
		List<CmRecruit> data = (List<CmRecruit>) hibernateTemplate.find(hsql, rid);
		if (data.size() > 0) {
			return data.get(0);
		}
		System.out.println("未查到相关数据！");
		return null;
	}

	// 按rid查询招聘及面试信息——ly
	public RecruitResObj findByRid2(int rid) {
		String hsql = "select new cn.edu.zzia.career.ResObj.RecruitResObj(r.rid,r.rsex,r.rsalary,r.rstart,r.rend,r.rnum,r.rinfo,r.rstate,a.aid,a.aprovince,a.acity,j.jid,j.jname,c.cid,c.cname,c.chr,c.cphone,c.cemail) "
				+ "from CmRecruit r " + "inner join r.cmAreaByAid a " + "inner join r.cmJobByJid j "
				+ "inner join r.cmCompanyByCid c " + "where r.rstate = 0 and r.rid = ? order by r.rstart desc ";
		List<RecruitResObj> data = (List<RecruitResObj>) hibernateTemplate.find(hsql, rid);
		if (data.size() > 0) {
			return data.get(0);
		}
		System.out.println("未查到相关数据！");
		return null;
	}

	// 查询所有招聘信息——ly
	public List<RecruitResObj> findAll(final PageBean pageBean) {
		final String hsql = "select new cn.edu.zzia.career.ResObj.RecruitResObj(r.rid,r.rsex,r.rsalary,r.rstart,r.rend,r.rnum,r.rinfo,r.rstate,a.aid,a.aprovince,a.acity,j.jid,j.jname,c.cid,c.cname,c.chr,c.cphone,c.cemail) "
				+ "from CmRecruit r " + "inner join r.cmAreaByAid a " + "inner join r.cmJobByJid j "
				+ "inner join r.cmCompanyByCid c " + "where r.rstate = 0 order by r.rstart desc ";
		final List<RecruitResObj> data = (List<RecruitResObj>) hibernateTemplate.execute(new HibernateCallback<List>() {
			@Override
			public List doInHibernate(Session session) throws HibernateException {
				List data2 = session.createQuery(hsql).setFirstResult(pageBean.getStart())
						.setMaxResults(pageBean.getPageSize()).list();
				// System.out.println("所有招聘信息数量： "+data.size());
				// System.out.println("发布时间： "+data.get(0).getRstart());
				if (data2.size() > 0) {
					return data2;
				}
				System.out.println("未查到相关数据！");
				return null;
			}
		});
		return data;
	}

	// 查询所有招聘信息数量——ly
	public int findAllCount() {
		String hsql = "select count(*) from CmRecruit r where r.rstate = 0 ";
		List<?> data = hibernateTemplate.find(hsql);
		if (data.get(0) != null) {
			return Integer.parseInt(data.get(0).toString());
		}
		return 0;
	}

	// 查询该公司下该岗位的招聘信息——ly
	public List<RecruitResObj> findByCidAndJid(int cid, int jid) {
		String hsql = "select new cn.edu.zzia.career.ResObj.RecruitResObj(r.rid,r.rsex,r.rsalary,r.rstart,r.rend,r.rnum,r.rinfo,r.rstate,a.aid,a.aprovince,a.acity,j.jid,j.jname,c.cid,c.cname,c.chr,c.cphone,c.cemail) "
				+ "from CmRecruit r " + "inner join r.cmAreaByAid a " + "inner join r.cmJobByJid j "
				+ "inner join r.cmCompanyByCid c "
				+ "where c.cid = ? and j.jid = ? and r.rstate = 0 order by r.rstart desc ";
		Object[] value = { cid, jid };
		List<RecruitResObj> data = (List<RecruitResObj>) hibernateTemplate.find(hsql, value);
		if (data.size() > 0) {
			return data;
		}
		System.out.println("未查到相关数据！");
		return null;
	}

	// 查询近七天招聘发布数量——ly
	public int findCountByWeek() {
		String hsql = "select count(*) from CmRecruit r where r.rstate = 0 and (TO_DAYS( NOW( ) ) - TO_DAYS(r.rstart) <= 7)";
		List<?> data = hibernateTemplate.find(hsql);
		if (data.get(0) != null) {
			return Integer.parseInt(data.get(0).toString());
		}
		return 0;
	}

	// 查询近七天发布招聘的公司数量——ly
	public int findComCountByWeek() {
		String hsql = "select count(distinct r.cmCompanyByCid.cid) from CmRecruit r where r.rstate = 0 and (TO_DAYS( NOW( ) ) - TO_DAYS(r.rstart) <= 7)";
		List<?> data = hibernateTemplate.find(hsql);
		if (data.get(0) != null) {
			return Integer.parseInt(data.get(0).toString());
		}
		return 0;
	}

	// 查询近七天招聘信息——ly
	public List<RecruitResObj> findByWeek() {
		String hsql = "select new cn.edu.zzia.career.ResObj.RecruitResObj(r.rid,r.rsex,r.rsalary,r.rstart,r.rend,r.rnum,r.rinfo,r.rstate,a.aid,a.aprovince,a.acity,j.jid,j.jname,c.cid,c.cname,c.chr,c.cphone,c.cemail) "
				+ "from CmRecruit r " + "inner join r.cmAreaByAid a " + "inner join r.cmJobByJid j "
				+ "inner join r.cmCompanyByCid c "
				+ "where r.rstate = 0 and (TO_DAYS( NOW( ) ) - TO_DAYS(r.rstart) <= 7) order by r.rstart desc ";
		List<RecruitResObj> data = (List<RecruitResObj>) hibernateTemplate.find(hsql);
		if (data.size() > 0) {
			return data;
		}
		System.out.println("未查到相关数据！");
		return null;
	}

	/* 导出招聘信息 */
	public String outputRecruit() {
		String hsql = "select new cn.edu.zzia.career.ResObj.RecruitResObj(r.rid,r.rsex,r.rsalary,r.rstart,r.rend,r.rnum,r.rinfo,r.rstate,a.aid,a.aprovince,a.acity,j.jid,j.jname,c.cid,c.cname,c.chr,c.cphone,c.cemail) "
				+ "from CmRecruit r " + "inner join r.cmAreaByAid a " + "inner join r.cmJobByJid j "
				+ "inner join r.cmCompanyByCid c " + "where r.rstate = 0 order by r.rstart desc ";
		List<RecruitResObj> data = (List<RecruitResObj>) hibernateTemplate.find(hsql);
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("公司信息表");
		HSSFRow row1 = sheet.createRow(0);
		HSSFCell cell = row1.createCell(0);
		row1.setHeight((short) 20);
		cell.setCellValue("公司信息");
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 12));
		HSSFRow row2 = sheet.createRow(1);
		row2.createCell(0).setCellValue("rid");
		row2.createCell(1).setCellValue("岗位名称");
		row2.createCell(2).setCellValue("公司名称");
		row2.createCell(3).setCellValue("省份");
		row2.createCell(4).setCellValue("城市");
		row2.createCell(5).setCellValue("HR姓名");
		row2.createCell(6).setCellValue("电话");
		row2.createCell(7).setCellValue("邮箱");
		row2.createCell(8).setCellValue("性别要求");
		row2.createCell(9).setCellValue("月薪");
		row2.createCell(10).setCellValue("发布时间");
		row2.createCell(11).setCellValue("截至时间");
		int rownum = 2;
		// 在sheet里创建数据
		for (RecruitResObj es : data) {
			HSSFRow row = sheet.createRow(rownum);
			row.createCell(0).setCellValue(es.getRid());
			row.createCell(1).setCellValue(es.getJname());
			row.createCell(2).setCellValue(es.getCname());
			row.createCell(3).setCellValue(es.getAprovince());
			row.createCell(4).setCellValue(es.getAcity());
			row.createCell(5).setCellValue(es.getChr());
			row.createCell(6).setCellValue(es.getCphone());
			row.createCell(7).setCellValue(es.getCemail());
			if (es.getRsex() == null) {
				row.createCell(8).setCellValue("不限");
			} else {
				if (es.getRsex()) {
					row.createCell(8).setCellValue("女");
				} else {
					row.createCell(8).setCellValue("男");
				}
			}
			row.createCell(9).setCellValue(es.getRsalary());
			row.createCell(10).setCellValue(es.getRstart());
			row.createCell(11).setCellValue(es.getRend());
			rownum++;
		}
		OutputData od = new OutputData();
		String file = od.fileNameConvert(wb, "公司信息");
		return file;
	}

}
