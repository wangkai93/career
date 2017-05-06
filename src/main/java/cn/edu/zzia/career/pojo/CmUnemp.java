package cn.edu.zzia.career.pojo;

import java.sql.Date;

/**
 * Created by LENOVO on 2016/10/20.
 */
@SuppressWarnings("serial")
public class CmUnemp implements java.io.Serializable {
	private Integer ueid;
	private Integer uesalary;
	private Date uetime;
	private String ueschool;
	private String uemajor;
	private Integer uesuccess;
	private Integer uestate;
	private String ujobName;
	private CmStudent cmStudentBySid;
	private CmDirection cmDirectionByDid;
	private CmJob cmJobByJid;

	public Integer getUeid() {
		return ueid;
	}

	public void setUeid(Integer ueid) {
		this.ueid = ueid;
	}

	public Integer getUesalary() {
		return uesalary;
	}

	public void setUesalary(Integer uesalary) {
		this.uesalary = uesalary;
	}

	public Date getUetime() {
		return uetime;
	}

	public void setUetime(Date uetime) {
		this.uetime = uetime;
	}

	public String getUeschool() {
		return ueschool;
	}

	public void setUeschool(String ueschool) {
		this.ueschool = ueschool;
	}

	public String getUemajor() {
		return uemajor;
	}

	public String getUjobName() {
		return ujobName;
	}

	public void setUjobName(String ujobName) {
		this.ujobName = ujobName;
	}

	public void setUemajor(String uemajor) {
		this.uemajor = uemajor;
	}

	public Integer getUesuccess() {
		return uesuccess;
	}

	public void setUesuccess(Integer uesuccess) {
		this.uesuccess = uesuccess;
	}

	public Integer getUestate() {
		return uestate;
	}

	public void setUestate(Integer uestate) {
		this.uestate = uestate;
	}

	public CmUnemp() {
	}

	public CmUnemp(Integer ueid) {
		this.ueid = ueid;
	}

	public CmUnemp(Integer ueid, Integer uesalary, Date uetime, String ueschool, String uemajor, Integer uesuccess,
			Integer uestate, CmStudent cmStudentBySid, CmDirection cmDirectionByDid, CmJob cmJobByJid) {
		this.ueid = ueid;
		this.uesalary = uesalary;
		this.uetime = uetime;
		this.ueschool = ueschool;
		this.uemajor = uemajor;
		this.uesuccess = uesuccess;
		this.uestate = uestate;
		this.cmStudentBySid = cmStudentBySid;
		this.cmDirectionByDid = cmDirectionByDid;
		this.cmJobByJid = cmJobByJid;
	}

	public CmUnemp(CmStudent cmStudentBySid, CmDirection cmDirectionByDid, CmJob cmJobByJid, Integer uesalary,
			Date uetime, String ueschool, String uemajor, Integer uesuccess) {
		this.uesalary = uesalary;
		this.uetime = uetime;
		this.ueschool = ueschool;
		this.uemajor = uemajor;
		this.uesuccess = uesuccess;
		this.cmStudentBySid = cmStudentBySid;
		this.cmDirectionByDid = cmDirectionByDid;
		this.cmJobByJid = cmJobByJid;
	}

	public CmUnemp(CmStudent cmStudentBySid, CmDirection cmDirectionByDid, CmJob cmJobByJid, Integer uesalary,
			Date uetime) {
		this.uesalary = uesalary;
		this.uetime = uetime;
		this.cmStudentBySid = cmStudentBySid;
		this.cmDirectionByDid = cmDirectionByDid;
		this.cmJobByJid = cmJobByJid;
	}

	public CmUnemp(CmStudent cmStudentBySid, CmDirection cmDirectionByDid, String ueschool, String uemajor,
			Integer uesuccess) {
		this.ueschool = ueschool;
		this.uemajor = uemajor;
		this.uesuccess = uesuccess;
		this.cmStudentBySid = cmStudentBySid;
		this.cmDirectionByDid = cmDirectionByDid;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		CmUnemp cmUnemp = (CmUnemp) o;

		if (ueid != null ? !ueid.equals(cmUnemp.ueid) : cmUnemp.ueid != null)
			return false;
		if (uesalary != null ? !uesalary.equals(cmUnemp.uesalary) : cmUnemp.uesalary != null)
			return false;
		if (uetime != null ? !uetime.equals(cmUnemp.uetime) : cmUnemp.uetime != null)
			return false;
		if (ueschool != null ? !ueschool.equals(cmUnemp.ueschool) : cmUnemp.ueschool != null)
			return false;
		if (uemajor != null ? !uemajor.equals(cmUnemp.uemajor) : cmUnemp.uemajor != null)
			return false;
		if (uesuccess != null ? !uesuccess.equals(cmUnemp.uesuccess) : cmUnemp.uesuccess != null)
			return false;
		if (uestate != null ? !uestate.equals(cmUnemp.uestate) : cmUnemp.uestate != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = ueid != null ? ueid.hashCode() : 0;
		result = 31 * result + (uesalary != null ? uesalary.hashCode() : 0);
		result = 31 * result + (uetime != null ? uetime.hashCode() : 0);
		result = 31 * result + (ueschool != null ? ueschool.hashCode() : 0);
		result = 31 * result + (uemajor != null ? uemajor.hashCode() : 0);
		result = 31 * result + (uesuccess != null ? uesuccess.hashCode() : 0);
		result = 31 * result + (uestate != null ? uestate.hashCode() : 0);
		return result;
	}

	public CmStudent getCmStudentBySid() {
		return cmStudentBySid;
	}

	public void setCmStudentBySid(CmStudent cmStudentBySid) {
		this.cmStudentBySid = cmStudentBySid;
	}

	public CmDirection getCmDirectionByDid() {
		return cmDirectionByDid;
	}

	public void setCmDirectionByDid(CmDirection cmDirectionByDid) {
		this.cmDirectionByDid = cmDirectionByDid;
	}

	public CmJob getCmJobByJid() {
		return cmJobByJid;
	}

	public void setCmJobByJid(CmJob cmJobByJid) {
		this.cmJobByJid = cmJobByJid;
	}
}
