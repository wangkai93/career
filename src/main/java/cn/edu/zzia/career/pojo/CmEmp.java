package cn.edu.zzia.career.pojo;

import java.sql.Date;
import java.sql.Date;


/**
 * Created by LENOVO on 2016/10/20.
 */
@SuppressWarnings("serial")
public class CmEmp implements java.io.Serializable{
    private Integer eid;
    private Date etime;
    private Integer esalary;
    private String einfo;
    private Integer estate;
    private Boolean ewq;
    private Date eleave;
    private String ereason;
    private String ejobName;
    private CmStudent cmStudentBySid;
    private CmJob cmJobByJid;
    private CmUser cmUserByUid;

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public Date getEtime() {
        return etime;
    }

    public void setEtime(Date etime) {
        this.etime = etime;
    }

    public Integer getEsalary() {
        return esalary;
    }

    public void setEsalary(Integer esalary) {
        this.esalary = esalary;
    }

    public String getEinfo() {
        return einfo;
    }

    public void setEinfo(String einfo) {
        this.einfo = einfo;
    }

    public Integer getEstate() {
        return estate;
    }

    public void setEstate(Integer estate) {
        this.estate = estate;
    }

    public Boolean getEwq() {
        return ewq;
    }

    public void setEwq(Boolean ewq) {
        this.ewq = ewq;
    }

    public Date getEleave() {
        return eleave;
    }

    public void setEleave(Date eleave) {
        this.eleave = eleave;
    }

    public String getEreason() {
        return ereason;
    }

    public void setEreason(String ereason) {
        this.ereason = ereason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CmEmp cmEmp = (CmEmp) o;

        if (eid != null ? !eid.equals(cmEmp.eid) : cmEmp.eid != null) return false;
        if (etime != null ? !etime.equals(cmEmp.etime) : cmEmp.etime != null) return false;
        if (esalary != null ? !esalary.equals(cmEmp.esalary) : cmEmp.esalary != null) return false;
        if (einfo != null ? !einfo.equals(cmEmp.einfo) : cmEmp.einfo != null) return false;
        if (estate != null ? !estate.equals(cmEmp.estate) : cmEmp.estate != null) return false;
        if (ewq != null ? !ewq.equals(cmEmp.ewq) : cmEmp.ewq != null) return false;
        if (eleave != null ? !eleave.equals(cmEmp.eleave) : cmEmp.eleave != null) return false;
        if (ereason != null ? !ereason.equals(cmEmp.ereason) : cmEmp.ereason != null) return false;

        return true;
    }

    public CmEmp() {
    }

    public CmEmp(Integer eid, Integer esalary, String einfo, Integer estate, Boolean ewq, String ereason) {
        this.eid = eid;
        this.esalary = esalary;
        this.einfo = einfo;
        this.estate = estate;
        this.ewq = ewq;
        this.ereason = ereason;
    }

    public CmEmp(Integer eid, Date etime, Integer esalary, String einfo, Integer estate, Boolean ewq, String ereason) {
        this.eid = eid;
        this.etime = etime;
        this.esalary = esalary;
        this.einfo = einfo;
        this.estate = estate;
        this.ewq = ewq;
        this.ereason = ereason;
    }

    public CmEmp(Integer eid, Date etime, Integer esalary, String einfo, Integer estate, Boolean ewq, Date eleave, String ereason) {
        this.eid = eid;
        this.etime = etime;
        this.esalary = esalary;
        this.einfo = einfo;
        this.estate = estate;
        this.ewq = ewq;
        this.eleave = eleave;
        this.ereason = ereason;
    }

    public CmEmp(Integer esalary, Date etime, String einfo, Boolean ewq, CmStudent cmStudentBySid, CmJob cmJobByJid, CmUser cmUserByUid) {
        this.esalary = esalary;
        this.etime = etime;
        this.einfo = einfo;
        this.ewq = ewq;
        this.cmStudentBySid = cmStudentBySid;
        this.cmJobByJid = cmJobByJid;
        this.cmUserByUid = cmUserByUid;
    }
    public CmEmp(Integer esalary, Date etime,String  ereason, String einfo, Boolean ewq, CmStudent cmStudentBySid, CmJob cmJobByJid, CmUser cmUserByUid) {
        this.esalary = esalary;
        this.etime = etime;
        this.ereason=ereason;
        this.einfo = einfo;
        this.ewq = ewq;
        this.cmStudentBySid = cmStudentBySid;
        this.cmJobByJid = cmJobByJid;
        this.cmUserByUid = cmUserByUid;
    }

    public CmEmp(CmUser cmUserByUid, CmJob cmJobByJid, CmStudent cmStudentBySid, Date etime, String einfo, Integer esalary, Date eleave, Boolean ewq) {
        this.cmUserByUid = cmUserByUid;
        this.cmJobByJid = cmJobByJid;
        this.cmStudentBySid = cmStudentBySid;
        this.etime = etime;
        this.einfo = einfo;
        this.esalary = esalary;
        this.eleave = eleave;
        this.ewq = ewq;
    }

    @Override
    public int hashCode() {
        int result = eid != null ? eid.hashCode() : 0;
        result = 31 * result + (etime != null ? etime.hashCode() : 0);
        result = 31 * result + (esalary != null ? esalary.hashCode() : 0);
        result = 31 * result + (einfo != null ? einfo.hashCode() : 0);
        result = 31 * result + (estate != null ? estate.hashCode() : 0);
        result = 31 * result + (ewq != null ? ewq.hashCode() : 0);
        result = 31 * result + (eleave != null ? eleave.hashCode() : 0);
        result = 31 * result + (ereason != null ? ereason.hashCode() : 0);
        return result;
    }

    public CmStudent getCmStudentBySid() {
        return cmStudentBySid;
    }

    public void setCmStudentBySid(CmStudent cmStudentBySid) {
        this.cmStudentBySid = cmStudentBySid;
    }

    public CmJob getCmJobByJid() {
        return cmJobByJid;
    }

    public void setCmJobByJid(CmJob cmJobByJid) {
        this.cmJobByJid = cmJobByJid;
    }

    public CmUser getCmUserByUid() {
        return cmUserByUid;
    }

    public void setCmUserByUid(CmUser cmUserByUid) {
        this.cmUserByUid = cmUserByUid;
    }

	public String getEjobName() {
		return ejobName;
	}

	public void setEjobName(String ejobName) {
		this.ejobName = ejobName;
	}
	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());
	}
}
