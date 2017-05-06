package cn.edu.zzia.career.pojo;

import java.util.Date;

@SuppressWarnings("serial")
public class NoteBook implements java.io.Serializable {

	private Integer nid;
	private CmStudent student;
	private String content;
	private Date createAt;
	private String title;
	private Date updateAt;

	public Integer getNid() {
		return nid;
	}

	public void setNid(Integer nid) {
		this.nid = nid;
	}

	public CmStudent getStudent() {
		return student;
	}

	public void setStudent(CmStudent student) {
		this.student = student;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
