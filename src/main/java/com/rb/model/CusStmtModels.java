package com.rb.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "records")
@XmlAccessorType(XmlAccessType.FIELD)
public class CusStmtModels implements Serializable {

	private static final long serialVersionUID = -7197669804556801305L;
	@XmlElement(name = "record")
	private List<CusStmtModel> records;

	public List<CusStmtModel> getRecords() {
		return records;
	}

	public void setRecords(List<CusStmtModel> records) {
		this.records = records;
	}

}
