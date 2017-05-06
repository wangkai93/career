package cn.edu.zzia.career.control;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.zzia.career.pojo.CmJob;
import cn.edu.zzia.career.service.JobService;

/**
 * Created by Administrator on 2016/10/31.
 */
@Controller
@RequestMapping("/job")
public class JobCtrl {
    @Autowired
    private JobService jobService;

    /**
     * 查询显示所有的岗位信息
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/findAllJob")
    public ModelAndView findAllJob(ModelMap modelMap){
        ModelAndView mv = new ModelAndView();
        List<CmJob> dataList = jobService.findAllJobInfo();
        for(CmJob job : dataList){
            System.out.println(job.getJtype());
            //按岗位类型jtype查询该类型下所有的岗位标签
            List<CmJob> jobList = jobService.findJobByJtype(job.getJtype());
            modelMap.addAttribute("jobList",jobList);
        }
        System.out.println(dataList);
        modelMap.put("dataList",dataList);
        mv.setViewName("system/admin/gangweiInfo");
        return mv;
    }
    @RequestMapping(value = "/getJobs",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public List<CmJob> getAllJob(){
    	List<CmJob> dataList = jobService.findAllJobInfo();
    	
    	return dataList;
    }

    /**
     * 添加岗位标签
     * @param jname
     * @param jtype
     * @param jinfo
     * @return
     */
    @RequestMapping(value = "/addJob")
    @ResponseBody
    public ModelAndView addJob(String jname, Boolean jtype, String jinfo){
        ModelAndView mv = new ModelAndView();
        System.out.println(jname);
        System.out.println(jtype);
        CmJob cmJob = new CmJob(jname,jtype,jinfo);
        boolean isSucc = jobService.addJob(cmJob);
        if(isSucc) {
            mv.setViewName("redirect:/job/findAllJob");
            return mv;
        }
        return null;
    }
    
    @RequestMapping(value = "/inputJob")
    public String inputArea(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, ModelMap model){
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
        msg = jobService.uploadJob(path+File.separator+fileName);
        model.addAttribute("file", msg);
        System.out.println(msg);
        return "/system/admin/inputData";
    }
}
