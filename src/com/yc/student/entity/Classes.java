package com.yc.student.entity;

public class Classes {
	private int cid;
	private String cname;
	
	/**
	 * @return the cid
	 */
	public int getCid() {
		return cid;
	}
	
	/**
	 * @param cid the cid to set
	 */
	public void setCid(int cid) {
		this.cid = cid;
	}
	
	/**
	 * @return the cname
	 */
	public String getCname() {
		return cname;
	}
	
	/**
	 * @param cname the cname to set
	 */
	public void setCname(String cname) {
		this.cname = cname;
	}
	
	public Classes() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Classes(int cid, String cname) {
		super();
		this.cid = cid;
		this.cname = cname;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return "{\"cid\":\""+cid+"\","+"\"cname\":\""+cname+"\"}";
		
//		return "Classes [cid=" + cid + ", cname=" + cname + "]";
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cid;
		result = prime * result + ((cname == null) ? 0 : cname.hashCode());
		return result;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Classes other = (Classes) obj;
		if (cid != other.cid)
			return false;
		if (cname == null) {
			if (other.cname != null)
				return false;
		} else if (!cname.equals(other.cname))
			return false;
		return true;
	}
	
	
}
