package cn.edu.zzia.career.control;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.zzia.career.ResObj.EmpIncrease;
import cn.edu.zzia.career.pojo.CmUser;
import cn.edu.zzia.career.service.EmpService;
import cn.edu.zzia.career.service.InterService;
import cn.edu.zzia.career.service.RecruitService;
import cn.edu.zzia.career.service.UnempService;
import cn.edu.zzia.career.service.UserService;
import cn.edu.zzia.career.tools.MD5;


/**
 * Created by TianYu on 2016/10/19.
 * User控制层
 */
@Controller
@RequestMapping("/user")
public class UserCtrl {
    @Autowired
    private UserService userService;
    @Autowired
    private EmpService empService;
    @Autowired
    private UnempService unempService;
    @Autowired
    private InterService interService;
    @Autowired
    private RecruitService recruitService;
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index(){
        return "/login";
    }
    //zxl：添加用户
    @RequestMapping(value = "/register",method =RequestMethod.GET )
    @ResponseBody
    public Map<String,String> register(String uname, String urname,String upwd, String uemail,String uphone,int urank){
        CmUser myUsers = new CmUser(uname,urname,upwd,uemail,uphone,urank);
        userService.addUser(myUsers);
        Map<String,String> data = new HashMap<String,String>();
        data.put("state","10001");
        data.put("info","插入成功!");
        return data;
    }
    //zxl：登陆
    @RequestMapping(value = "/login",method =RequestMethod.POST)
    public String  login(String uname, String upwd, ModelMap model,HttpServletRequest request) throws Exception {
        CmUser cmUser=userService.findlogin(uname,upwd);
        if (cmUser!=null){
            //统计当前已就业生数量
            int empCount = empService.EmpCount2();
            System.out.println(empCount);
            request.getSession().setAttribute("empCount",empCount);
            //统计当前未就业生数量
            int unempCount = unempService.UnEmpCount2();
            System.out.println(unempCount);
            request.getSession().setAttribute("unempCount",unempCount);
            //近一个月就业学生数量
            //获取系统当前时间
            Date day = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = df.format(day);
            //获取系统一个月之前时间
            Calendar c = Calendar.getInstance();
            c.add(Calendar.MONTH, -1);
            String currentDate2 = df.format(c.getTime());
            System.out.println(currentDate2);
            System.out.println(currentDate);
            int EmpCountByMonth = empService.EmpCount3(currentDate2,currentDate);
            request.getSession().setAttribute("EmpCountByMonth",EmpCountByMonth);
            request.getSession().setAttribute("cmUser",cmUser);
            //zxl：近一个月准备就业学生数
            int unempmonth=unempService.findSumNotEmpMonth();
            request.getSession().setAttribute("unempmonth",unempmonth);
            /*TianYu 就业增量统计*/
            List<EmpIncrease> ls =empService.Increase();
            for (EmpIncrease ei : ls){
                System.out.println(ei.getBeformonth()+ei.getData()+ei.getThismonth());
            }
            request.getSession().setAttribute("empIncrease",ls);
            //今天参加面试的学生数量——ly
            int dayInter = interService.findCountByDay();
            request.getSession().setAttribute("dayInter",dayInter);
            //近一周企业发布的招聘信息数——ly
            int weekRecruit = recruitService.findCountByWeek();
            request.getSession().setAttribute("weekRecruit",weekRecruit);
            //近一周发布招聘信息的企业数——ly
            int weekRecruitCom = recruitService.findComCountByWeek();
            request.getSession().setAttribute("weekRecruitCom",weekRecruitCom);
            return "/index";
        }else{
            try{
                model.addAttribute("info","用户名或密码错误！");
                return "/login";
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return "/login";
    }
    //zxl：查询所有用户
    @RequestMapping(value = "/selectAllUser",method =RequestMethod.GET)
    public  String selectAllUser(ModelMap modelMap){
        List<CmUser>data=userService.findAllUsers();
        modelMap.addAttribute("allUsers",data);
        return "/system/admin/Admins";
    }
    //zxl：添加用户
    @RequestMapping(value = "/addUser",method =RequestMethod.POST)
    public ModelAndView addUser(CmUser user)throws  Exception{
        ModelAndView mv=new ModelAndView();
        user.setUpwd(new MD5().getMD5String(user.getUpwd()));
        boolean  flag=userService.addUser(user);
        if (flag){
            mv.setViewName("redirect:/user/selectAllUser");
        }
        return  mv;
    }
    //zxl：修改密码
    @RequestMapping(value = "/updateUpwd",method =RequestMethod.POST)
    public  String updateUpwd(int uid, String oldpwd,String newpwd,ModelMap modelMap){
        boolean flag=userService.updateUpwd(uid,oldpwd,newpwd);
        if (flag){
            modelMap.addAttribute("info","修改成功！");
        }else{
            modelMap.addAttribute("info","修改失败！原始密码错误！");
        }
        return "/system/admin/adminInfo";
    }
    //zxl：查询该用户名是否存在
    @RequestMapping(value = "/selectUser",method =RequestMethod.GET)
    @ResponseBody
    public  String selectUser(@RequestParam(value = "key", required = true) String key){
        CmUser cmUser=userService.findUser(key);
        if (cmUser!=null){
            return "notnull";
        }
        return  "null";
    }
    //zxl：修改用户个人信息
    @RequestMapping(value = "/updateUser",method = RequestMethod.GET)
    public ModelAndView updateUser(int uid1,String urname,String uphone,String uemail) throws Exception{
        ModelAndView mv=new ModelAndView();
        String urname0=new String(urname.getBytes("iso-8859-1"),"utf-8");
        boolean  flag=userService.updateUser(uid1,urname0,uphone,uemail);
        if (flag){
            mv.setViewName("redirect:/user/findUserByUid?uid="+uid1);
        }
        return  mv;
    }
    //zxl：根据根据id查询学生
    @RequestMapping(value = "/findUserByUid",method = RequestMethod.GET)
    public  String findUserByUid(int uid,HttpServletRequest request,ModelMap modelMap){
        CmUser cmUser =userService.findUserByUid(uid);
        request.getSession().setAttribute("cmUser",cmUser);
        modelMap.addAttribute("info","修改成功！");
        return "/system/admin/adminInfo";
    }
    //zxl：修改用户权限
    @RequestMapping(value = "/updateUrank",method = RequestMethod.GET)
    public  ModelAndView updateUrank(int uid1,int urank1){
        ModelAndView mv =new ModelAndView();
        boolean flag=userService.updateUrank(uid1,urank1);
        if (flag){
            mv.setViewName("redirect:/user/selectAllUser");
        }
        return mv;
    }
    //zxl:退出
    @RequestMapping(value = "/quit",method = RequestMethod.GET)
    public String quit(HttpServletRequest request) throws  Exception{
        HttpSession session=request.getSession();
        session.setAttribute("cmUser",null);
        return  "/login";
    }




}
