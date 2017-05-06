package cn.edu.zzia.career.control.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.zzia.career.pojo.CmCompany;
import cn.edu.zzia.career.pojo.Message;
import cn.edu.zzia.career.service.CompanyService;

@Controller
@RequestMapping("/api/company")
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	@ModelAttribute
	public void loadCompanyInfo(@RequestParam(value = "companyId", required = false) Integer companyId,
			Map<String, Object> map) {

		if (null != companyId) {
			CmCompany company = companyService.findCompanyById(companyId);
			if (null != company) {
				map.put("company", company);
			}
		}
	}

	/**
	 * 提供查询招聘信息的接口
	 * 
	 * @param type
	 *            type=1 与学校合作的企业 type=0 非与学校合作的企业 type=null 所有企业信息
	 * @return
	 */
	@RequestMapping(value = "/getCompanys", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public List<CmCompany> getCompanyInfoByType(@RequestParam(value = "type", required = false) Integer type) {

		if (null != type)
			return companyService.findCompanyByType(type);
		else
			return companyService.findAllCompany();
	}

	/**
	 * 根据公司的状态查询公司信息
	 * 
	 * @param state
	 *            0：正常 ；1删除；2未审核；3审核未通过
	 * @return
	 */
	@RequestMapping(value = "/getCompanyByState", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public List<CmCompany> getCompanyByState(@RequestParam("state") Integer state) {
		return companyService.findCompanyByState(state);
	}

	/**
	 * 根据公司id查询公司信息，并返回json数据
	 * 
	 * @param companyId
	 * @return
	 */
	@RequestMapping(value = "/getOneCompany", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public CmCompany findCompanyById(@RequestParam("companyId") Integer companyId) {

		return companyService.findCompanyById(companyId);
	}

	/**
	 * 更新企业信息
	 * 
	 * @param company
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public Message updateCompany(@ModelAttribute("company") CmCompany company) {

		Message msg = null;
		try {
			companyService.updateCompany(company);
			msg = new Message(200, "update company success");
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
			msg = new Message(207, "update company failure");
			return msg;
		}
	}

	/**
	 * 审核公司信息
	 * 
	 * @param company
	 * @return
	 */
	@RequestMapping(value = "/verifyCompany", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public Message verifyCompanyInfo(@ModelAttribute("company") CmCompany company) {

		Message msg = null;
		try {
			companyService.updateCompany(company);
			msg = new Message(200, "verify company success");
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
			msg = new Message(210, "verify company failure");
			return msg;
		}
	}
}
