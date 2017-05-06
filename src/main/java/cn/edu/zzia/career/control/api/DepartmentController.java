package cn.edu.zzia.career.control.api;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.edu.zzia.career.pojo.Message;
import cn.edu.zzia.career.pojo.CmStudent;
import cn.edu.zzia.career.pojo.CmUnemp;
import cn.edu.zzia.career.pojo.Department;
import cn.edu.zzia.career.service.DepartmentService;

@Controller
@RequestMapping("/api/department")
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;

	/**
	 * 用于在更新时从数据库查询专业
	 * 
	 * @param departmentId
	 * @param map
	 */
	@ModelAttribute
	public void loadDepartment(@RequestParam(value = "departmentId", required = false) Integer departmentId,
			Map<String, Object> map) {

		if (null != departmentId) {
			Department note = departmentService.findById(departmentId);
			if (null != note) {
				map.put("department", note);
			}
		}
	}
	
	@RequestMapping(value = "/getUnemp",method = RequestMethod.GET,produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public List<CmStudent> getUmempByDepartmentId(@RequestParam("departmentId")Integer departmentId){
		
		return departmentService.getUnempByDepartmentId(departmentId);
	}

	/**
	 * 新增一条专业，并返回新增的消息
	 * 
	 * @param department
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public Message save(Department department) {
		Message msg = null;
		try {
			departmentService.save(department);
			msg = new Message(200, "add department success");
			return msg;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			msg = new Message(214, "add department failure");
			return msg;
		}

	}

	/**
	 * 更新专业的内容，并返回更新的消息
	 * 
	 * @param department
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public Message update(@ModelAttribute("department") Department department) {
		Message msg = null;
		try {
			departmentService.update(department);
			msg = new Message(200, "update department success");
			return msg;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			msg = new Message(215, "update department failure");
			return msg;
		}
	}

	/**
	 * 根据专业id删除一个专业信息，并返回删除的消息
	 * 
	 * @param departmentId
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public Message deleteById(@RequestParam(value = "departmentId") Integer departmentId) {

		Message msg = null;
		try {
			departmentService.deleteById(departmentId);
			msg = new Message(200, "delete department success");
			return msg;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			msg = new Message(216, "delete department failure");
			return msg;
		}

	}

	/**
	 * 根据专业id查询一个专业信息，并返回json数据
	 * 
	 * @param departmentId
	 * @return
	 */
	@RequestMapping(value = "/findOne", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public Department findById(@RequestParam(value = "departmentId") Integer departmentId) {

		return departmentService.findById(departmentId);
	}

	/**
	 * 根据学生id查询该学生所有的专业信息，并返回json数据
	 * 
	 * @param studentId
	 * @return
	 */
	@RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public List<Department> findAllDepartment() {

		return departmentService.findAllDepartment();
	}
	
	
	@RequestMapping(value = "/inputDepartment")
    public String inputDepartment(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, ModelMap model){
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
        msg = departmentService.uploadDepartment(path+File.separator+fileName);
        model.addAttribute("file", msg);
        System.out.println(msg);
        return "/system/admin/inputData";
    }
	

}
