package cn.edu.zzia.career.service;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;

import cn.edu.zzia.career.pojo.CmDirection;
import cn.edu.zzia.career.tools.HssfExcelHelper;
import cn.edu.zzia.career.tools.StringUtils;

/**
 * Created by Administrator on 2016/11/4.
 */
@Service("directionService")
public class DirectionService {
    @Autowired
    private HibernateTemplate hibernateTemplate;
    //zxl：查询所有动向
    public List<CmDirection> findAllDirection(){
        String hsql="from CmDirection d";
        List<CmDirection>data=(List<CmDirection>) hibernateTemplate.find(hsql);
        return  data;

    }
    //zxl:查询就业生的就业动向
    public CmDirection findDirByUeid(int ueid){
        String hsql="select  new cn.edu.zzia.career.pojo.CmDirection(d.did, d.dname,d.dstate)  from  CmUnemp un inner  join  un.cmDirectionByDid d where un.ueid=? and d.dstate=0";
        List<CmDirection>data=(List<CmDirection>) hibernateTemplate.find(hsql,ueid);
        return  data.get(0);
    }
    /**
     * 显示所有未就业学生的准备方向
     * @return
     */
    public List<CmDirection> FindAllDirection(){
        String hsql = "select new cn.edu.zzia.career.pojo.CmDirection(dir.did,dir.dname,dir.dstate) from CmDirection dir where dir.dstate = 0";
        List<CmDirection> data = (List<CmDirection>) hibernateTemplate.find(hsql);
        CmDirection cmDirection = (CmDirection)data.get(0);
        System.out.println(cmDirection.getDname());
        return data;
    }

    /**
     *添加未就业学生的准备方向
     * @param cmDirection
     * @return
     */
    public boolean addDirection(CmDirection cmDirection){
        hibernateTemplate.save(cmDirection);
        return true;
    }
    /**
     * 删除没有学生选择的方向
     * @param did
     * @return
     */
    public boolean DelCmDirection(Integer did){
        System.out.println(did);
        String hsql = "update CmDirection dir set dir.dstate=1 where dir.did = ?";
        hibernateTemplate.bulkUpdate(hsql,did);
        return true;
    }
    
    /**
     * 
     * @param string
     * @return
     */
	public String uploadDirection(String path) {
		
		if (StringUtils.isNotBlank(path)) {
			try {
				HssfExcelHelper xssfHelper = HssfExcelHelper.getInstance(path);
				String[] productTitle = { "did", "dname", "dstate" };
				List<CmDirection> dir = xssfHelper.readExcel(CmDirection.class, productTitle, 0, true);
				Session session = hibernateTemplate.getSessionFactory().openSession();
				for (CmDirection cmDirection : dir) {
					session.save(cmDirection);
				}
				session.close();
				return "导入成功！";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "导入失败！";
			}
		}
		return "导入失败！";
		
	}
}
