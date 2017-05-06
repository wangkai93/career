package cn.edu.zzia.career.control;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.zzia.career.ResObj.ResComList;
import cn.edu.zzia.career.ResObj.ResCompanyObj;
import cn.edu.zzia.career.pojo.CmArea;
import cn.edu.zzia.career.pojo.CmCompany;
import cn.edu.zzia.career.pojo.CmJob;
import cn.edu.zzia.career.service.AreaService;
import cn.edu.zzia.career.service.CompanyService;
import cn.edu.zzia.career.service.JobService;
import cn.edu.zzia.career.tools.DateUtil;
import cn.edu.zzia.career.tools.PageBean;

/**
 * Created by Administrator on 2016/10/23.
 */
@Controller
@RequestMapping("/company")
public class CompanyCtrl {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private JobService jobService;
    //张小丽：添加公司
    @RequestMapping(value = "/addCompany",method = RequestMethod.POST)
    public ModelAndView addCompany(CmCompany  cmCompany,@RequestParam("ddlCity")Integer city)throws  Exception{
        ModelAndView mv = new ModelAndView();
        CmArea cmArea=new CmArea();
        cmArea.setAid(city);
        cmCompany.setCmAreaByAid(cmArea);
        cmCompany.setCtime(new DateUtil().SysDate());
        boolean flag=  companyService.addCompany(cmCompany);
        if (flag){
            mv.setViewName("redirect:/area/selectAllArea2");
        }
        return mv;
    }
    //张小丽：根据公司ID查询，即查找需要修改的公司信息
    @RequestMapping(value = "/findCompByCid",method = RequestMethod.GET)
    public String findCompByCid(int cid, ModelMap  modelMap){
        CmCompany cmCompany=companyService.findCompByCid(cid);
        List<CmArea> data=areaService.findAllArea();
        CmArea cmArea=areaService.findAreaByCid(cid);
        cmCompany.setCmAreaByAid(cmArea);
        List<CmArea>data2=areaService.findAreaByAApro(cmArea.getAprovince());
        modelMap.addAttribute("allAreaList",data);
        modelMap.put("findCompByCid",cmCompany);
        modelMap.put("findCityByApro",data2);
        return  "/system/company/CompUpdate";
    }
    //张小丽：根据公司ID查询，即查找需要修改的公司信息
    @RequestMapping(value = "/findCompByCid2",method = RequestMethod.GET)
    public String findCompByCid2(int cid, ModelMap  modelMap){
        CmCompany cmCompany=companyService.findCompByCid(cid);
        List<CmArea> data=areaService.findAllArea();
        CmArea cmArea=areaService.findAreaByCid(cid);
        cmCompany.setCmAreaByAid(cmArea);
        List<CmArea>data2=areaService.findAreaByAApro(cmArea.getAprovince());
        modelMap.addAttribute("allAreaList",data);
        modelMap.put("findCompByCid",cmCompany);
        modelMap.put("findCityByApro",data2);
        modelMap.addAttribute("state","10001");
        modelMap.addAttribute("info","修改成功！");
        modelMap.addAttribute("allAreaList",data);
        modelMap.put("findCompByCid",cmCompany);
        return  "/system/company/CompUpdate";
    }
    //张小丽：修改公司信息
    @RequestMapping(value = "/updateCompany",method = RequestMethod.POST)
    public ModelAndView updateCompany(int cid,String cname,String chr,String cphone,String cemail,String aprovince,
                                      String cinfo,String cmark,String caddress,Integer city,ModelMap modelMap) throws Exception{
        ModelAndView mv=new ModelAndView();
        boolean flag=companyService.updateCompany(cid, cname, chr, cphone, cemail, cinfo, cmark, caddress, city);
        if (flag){
            // "/system/company/CompUpdate"
            mv.setViewName("redirect:/company/findCompByCid2?cid="+cid);
        }
        return mv;
    }
    /**
     * 查询显示所有企业信息
     * @return
     */
    @RequestMapping(value = "/findAllCompany")
    public String findAllCompany(ModelMap modelMap, @RequestParam("page") String page){
        List<ResComList> companyList = new ArrayList<>();
        //每页显示的条数
        int pageSize = 5;
        //处理分页类
        PageBean pageBean = new PageBean(Integer.parseInt(page),pageSize);
        List<CmCompany> company = companyService.FindALLCompany(pageBean);
        for(CmCompany com : company){
            ResComList rcl = new ResComList();
            rcl.setCstate(com.getCstate());
            rcl.setChr(com.getChr());
            rcl.setCid(com.getCid());
            rcl.setCname(com.getCname());
            rcl.setCphone(com.getCphone());
            //按Cid查询该公司有多少招聘岗位
            rcl.setCmJobs(jobService.findJobByCid(com.getCid()));
            //计算该公司在岗学生人数
            rcl.setStuCount(companyService.StuCountByCid(com.getCid()));
            companyList.add(rcl);
        }
        //计算公司总数
        int total = companyService.CompanyCount();
        String pageCode = this.genPagation(total, Integer.parseInt(page), pageSize);
        modelMap.put("pageCode",pageCode);
        modelMap.addAttribute("companylist",companyList);
        return "system/company/selectAllCom";
    }
    /**
     * 查询该公司该岗位下的学生信息
     * @param cid
     * @param jid
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/findStuInfoByJname")
    public String findStuInfoByJname(String cid,String jid,ModelMap modelMap){
        List<ResCompanyObj> dataList = companyService.findStuInfoByJname(Integer.parseInt(cid),Integer.parseInt(jid));
        modelMap.addAttribute("dataList",dataList);
        return "/system/company/StudentInfo";
    }
    /**
     * 查询该公司下的所有在岗学生信息
     * @param cid
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/forCompStuInfo")
    public String findCompStuInfo(String cid,ModelMap modelMap){
        List<ResCompanyObj> dataList = companyService.findCompStuInfo(Integer.parseInt(cid));
        modelMap.addAttribute("dataList",dataList);
        return "/system/company/CompStuInfo";
    }
    /**
     * 按公司ID 查询该公司信息
     * @param modelMap
     * @param cid
     * @return
     */
    @RequestMapping(value = "/findByCompCid")
    public String SelectByCid(ModelMap modelMap,String cid){
        System.out.println(cid);
        List<CmCompany> dateList = companyService.findByCompCid(Integer.parseInt(cid));
        for(CmCompany comp : dateList) {
            //按Cid查询该公司有多少招聘岗位
            List<CmJob> jobList = jobService.findJobByCid(comp.getCid());
            //计算该公司的在岗学生人数
            int stuCount = companyService.StuCountByCid(comp.getCid());
            modelMap.addAttribute("jobList",jobList);
            modelMap.put("stuCount",stuCount);
        }
        modelMap.addAttribute("dateList",dateList);
        return  "/system/company/CompInfo";
    }
    /**
     * 按条件搜索相关企业
     * @param searchtext,searchType
     * @return
     */
    @RequestMapping(value = "/findByComp" )
    @ResponseBody
    public ModelAndView findByName(ModelMap modelMap, String searchtext, String searchType) throws Exception{
        ModelAndView mv = new ModelAndView();
        System.out.println(searchType);
        System.out.println(searchtext);
        if(searchType.equals("cname")) {
            List<CmCompany> listdata = companyService.FindByCName(searchtext);
            for(CmCompany comp : listdata){
                List<CmJob> jobList = jobService.findJobByCid(comp.getCid());
                modelMap.addAttribute("jobList",jobList);
                int stuCount = companyService.StuCountByCid(comp.getCid());
                modelMap.put("stuCount",stuCount);
            }
            modelMap.addAttribute("listdata", listdata);
        }else if(searchType.equals("chr")){
            List<CmCompany> listdata = companyService.FindByCHr(searchtext);
            for(CmCompany comp : listdata){
                List<CmJob> jobList = jobService.findJobByCid(comp.getCid());
                modelMap.addAttribute("jobList",jobList);
                int stuCount = companyService.StuCountByCid(comp.getCid());
                modelMap.put("stuCount",stuCount);
            }
            modelMap.addAttribute("listdata", listdata);
        }
        mv.setViewName("system/company/CompSearch");
        return mv;
    }
    /**
     * 按条件搜索相关在岗学生名单
     * @param searchtext,searchType
     * @return
     */
    @RequestMapping(value = "/findByType" )
    @ResponseBody
    public ModelAndView findByType(ModelMap modelMap, String searchtext, String searchType) throws Exception{
        ModelAndView mv = new ModelAndView();
        System.out.println(searchType);
        System.out.println(searchtext);
        if(searchType.equals("sname")) {
            List<ResCompanyObj> listdata = companyService.findCompStuInfoBySname(searchtext);
            modelMap.addAttribute("dataList", listdata);
        }else if(searchType.equals("sgrade")){
            List<ResCompanyObj> listdata = companyService.findCompStuInfoBySgrade(Integer.parseInt(searchtext));
            modelMap.addAttribute("dataList", listdata);
        }else if(searchType.equals("jname")){
            List<ResCompanyObj> listdata = companyService.findCompStuInfoByJname(searchtext);
            modelMap.addAttribute("dataList", listdata);
        }
        mv.setViewName("system/company/CompStuInfo");
        return mv;
    }
    /**
     * 删除该条企业信息记录
     * @param cid
     * @return
     */
    @RequestMapping( value = "/delCompany")
    @ResponseBody
    public ModelAndView DelCompany(@RequestParam("cid") String cid){
        System.out.println(cid);
        ModelAndView mv = new ModelAndView();
        boolean isSucc = companyService.DelCompany(Integer.parseInt(cid));
        if(isSucc) {
            mv.setViewName("redirect:/company/findAllCompany?page=1");
            return mv;
        }
        return null;
    }

    /**
     * 分页处理
     * @param totalNum 总页数
     * @param currentPage 当前页
     * @param pageSize 一页显示几条
     * @return
     */
    private String genPagation(int totalNum, int currentPage, int pageSize){
        int totalPage = totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
        StringBuffer pageCode = new StringBuffer();
        pageCode.append("<li><a href='/company/findAllCompany?page=1'>首页</a></li>");
        if(currentPage==1) {
            pageCode.append("<li class='disabled'><a href='#'>上一页</a></li>");
        }else {
            pageCode.append("<li><a href='/company/findAllCompany?page="+(currentPage-1)+"'>上一页</a></li>");
        }
        for(int i=currentPage-2;i<=currentPage+2;i++) {
            if(i<1||i>totalPage) {
                continue;
            }
            if(i==currentPage) {
                pageCode.append("<li class='active'><a href='#'>"+i+"</a></li>");
            } else {
                pageCode.append("<li><a href='/company/findAllCompany?page="+i+"'>"+i+"</a></li>");
            }
        }
        if(currentPage==totalPage) {
            pageCode.append("<li class='disabled'><a href='#'>下一页</a></li>");
        } else {
            pageCode.append("<li><a href='/company/findAllCompany?page="+(currentPage+1)+"'>下一页</a></li>");
        }
        pageCode.append("<li><a href='/company/findAllCompany?page="+totalPage+"'>尾页</a></li>");
        return pageCode.toString();
    }

    //查询近七天发布招聘的公司——ly
    @RequestMapping(value = "/findComByWeek" )
    public ModelAndView findComByWeek(ModelMap modelMap){
        List<ResComList> listdata = new ArrayList<>();
        ModelAndView mv = new ModelAndView();
        List<CmCompany> company = companyService.findComByWeek();
        for(CmCompany com : company) {
            ResComList rcl = new ResComList();
            rcl.setCstate(com.getCstate());
            rcl.setChr(com.getChr());
            rcl.setCid(com.getCid());
            rcl.setCname(com.getCname());
            rcl.setCphone(com.getCphone());
            //按Cid查询该公司有多少招聘岗位
            rcl.setCmJobs(jobService.findJobByCid(com.getCid()));
            //计算该公司在岗学生人数
            rcl.setStuCount(companyService.StuCountByCid(com.getCid()));
            listdata.add(rcl);
        }
        modelMap.addAttribute("listdata",listdata);
        mv.setViewName("system/company/CompSearch1");
        return mv;
    }

    /*TianYu 公司数据导入*/
    @RequestMapping(value = "/inputCompany")
    public String inputCompany(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, ModelMap model){
        String path = request.getSession().getServletContext().getRealPath("upload");
        String msg;
        String fileName = file.getOriginalFilename();
        System.out.println("File------------"+path+"\\"+fileName);
        File targetFile = new File(path, fileName);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            msg = "文件上传失败！";
        }
        msg = companyService.uploadCompany(path+File.separator+fileName);
        model.addAttribute("file", msg);
        System.out.println(msg);
        return "/system/admin/inputData";
    }

    /*TianYu 导出公司数据*/
    @RequestMapping(value = "/outputCompany")
    public ResponseEntity<byte[]> DownloadCom(HttpServletRequest httpServletRequest) throws IOException {
        File file = new File(companyService.outputCompany());
        HttpHeaders httpHeaders = new HttpHeaders();
        String fileName = file.getName();
        httpHeaders.setContentDispositionFormData("attachment",java.net.URLEncoder.encode(fileName,"ISO-8859-1"));
        httpHeaders.setContentType(MediaType.parseMediaType("application/xls"));
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),httpHeaders, HttpStatus.CREATED);
    }

    /*TianYu 导出公司学生数据*/
    @RequestMapping(value = "/outputComStu")
    public ResponseEntity<byte[]> DownloadStu(@RequestParam("cid") String cid,HttpServletRequest httpServletRequest) throws IOException {
        File file = new File(companyService.outputComStu(Integer.parseInt(cid)));
        HttpHeaders httpHeaders = new HttpHeaders();
        String fileName = file.getName();
        httpHeaders.setContentDispositionFormData("attachment",java.net.URLEncoder.encode(fileName,"ISO-8859-1"));
        httpHeaders.setContentType(MediaType.parseMediaType("application/xls"));
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),httpHeaders, HttpStatus.CREATED);
    }
}
