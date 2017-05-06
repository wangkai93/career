package cn.edu.zzia.career.control;

import cn.edu.zzia.career.pojo.CmDirection;
import cn.edu.zzia.career.pojo.CmJob;
import cn.edu.zzia.career.pojo.CmStudent;
import cn.edu.zzia.career.pojo.CmUnemp;
import cn.edu.zzia.career.service.DirectionService;
import cn.edu.zzia.career.service.JobService;
import cn.edu.zzia.career.service.StudentService;
import cn.edu.zzia.career.service.UnempService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/11/4.
 */
@Controller
@RequestMapping("/direction")
public class DirectionCtrl {
    @Autowired
    private DirectionService directionService;
    @Autowired
    private JobService jobService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private UnempService unempService;
    //zxl：查询所有动向
    @RequestMapping(value = "/selectAllDirection",method = RequestMethod.GET)
    public String selectAllDirection(ModelMap modelMap){
        List<CmDirection> data=directionService.findAllDirection();
        List<CmJob> data1=jobService.findAllJob();
        modelMap.put("allDirection",data);
        modelMap.put("allJob",data1); 
        return  "/system/not-employed/NotEmpAdd";
    }
    //zxl：查询所有动向
    @RequestMapping(value = "/selectAllDirection2",method = RequestMethod.GET)
    public String selectAllDirection2(int sid,ModelMap modelMap){
        CmStudent cmStudent=studentService.findStuBySid(sid);
        List<CmDirection> data=directionService.findAllDirection();
        List<CmJob> data1=jobService.findAllJob();
        CmUnemp cmUnemp=unempService.findUnEmpBySid(sid);
        CmJob cmJob=jobService.findJobByUeid(cmUnemp.getUeid());
        CmDirection cmDirection=directionService.findDirByUeid(cmUnemp.getUeid());
        modelMap.put("allDirection",data);
        modelMap.put("allJob",data1);
        modelMap.put("cmStudent",cmStudent);
        modelMap.put("cmUnemp",cmUnemp);
        modelMap.put("cmJob",cmJob);
        modelMap.put("cmDirection",cmDirection);
        return  "/system/not-employed/NotEmpUpdate";
    }
    //zxl：查询所有动向
    @RequestMapping(value = "/selectAllDirection3",method = RequestMethod.GET)
    public String selectAllDirection3(ModelMap modelMap){
        List<CmDirection> data=directionService.findAllDirection();
        List<CmJob> data1=jobService.findAllJob();
        modelMap.addAttribute("state","10001");
        modelMap.addAttribute("info","添加成功！");
        modelMap.put("allDirection",data);
        modelMap.put("allJob",data1);
        return  "/system/not-employed/NotEmpAdd";
    }
    //zxl：查询所有动向
    @RequestMapping(value = "/selectAllDirection4",method = RequestMethod.GET)
    public String selectAllDirection4(int sid,ModelMap modelMap){
        CmStudent cmStudent=studentService.findStuBySid(sid);
        List<CmDirection> data=directionService.findAllDirection();
        List<CmJob> data1=jobService.findAllJob();
        CmUnemp cmUnemp=unempService.findUnEmpBySid(sid);
        CmJob cmJob=jobService.findJobByUeid(cmUnemp.getUeid());
        CmDirection cmDirection=directionService.findDirByUeid(cmUnemp.getUeid());
        modelMap.put("allDirection",data);
        modelMap.put("allJob",data1);
        modelMap.put("cmStudent",cmStudent);
        modelMap.put("cmUnemp",cmUnemp);
        modelMap.put("cmJob",cmJob);
        modelMap.put("cmDirection",cmDirection);
        modelMap.addAttribute("state","10001");
        modelMap.addAttribute("info","修改成功！");
        return  "/system/not-employed/NotEmpUpdate";
    }

    /**
     * 显示所有未就业学生的准备方向
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/findAllDirection")
    public ModelAndView FindAllDirection(ModelMap modelMap){
        ModelAndView mv = new ModelAndView();
        List<CmDirection> DirList = directionService.FindAllDirection();
        System.out.println(DirList);
        modelMap.addAttribute("DirList",DirList);
        mv.setViewName("system/admin/fangxiang");
        return mv;
    }

    /**
     * 添加未就业学生的准备方向
     * @param direction
     * @return
     */
    @RequestMapping(value = "/addDirection")
    @ResponseBody
    public ModelAndView AddDirection(String direction){
        ModelAndView mv = new ModelAndView();
        System.out.println(direction);
        CmDirection cmDirection = new CmDirection(direction);
        boolean isSucc = directionService.addDirection(cmDirection);
        if(isSucc) {
            mv.setViewName("redirect:/direction/findAllDirection");
            return mv;
        }
        return null;
    }

    /**
     *删除没有学生选择的方向
     * @param did
     * @return
     */
    @RequestMapping(value = "/delDiretion")
    @ResponseBody
    public ModelAndView DelDirection(@RequestParam("did") String did){
        ModelAndView mv = new ModelAndView();
        System.out.println(did);
        boolean isSucc = directionService.DelCmDirection(Integer.parseInt(did));
        if(isSucc) {
            mv.setViewName("redirect:/direction/findAllDirection");
            return mv;
        }
        return null;
    }
    
    @RequestMapping(value = "/inputDirection")
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
        msg = directionService.uploadDirection(path+File.separator+fileName);
        model.addAttribute("file", msg);
        System.out.println(msg);
        return "/system/admin/inputData";
    }
}
