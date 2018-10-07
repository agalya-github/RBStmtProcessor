package com.rb.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "record")
@XmlAccessorType(XmlAccessType.FIELD)
public class CusStmtModel implements Serializable {

	private static final long serialVersionUID = -7197669804556801305L;
	@XmlAttribute(name = "reference")
	private Integer reference;
	@XmlElement(name = "accountNumber")
	private String accountNumber;
	@XmlElement(name = "description")
	private String description;
	@XmlElement(name = "startBalance")
	private double startBalance;
	@XmlElement(name = "mutation")
	private double mutation;
	@XmlElement(name = "endBalance")
	private double endBalance;
	private String remarks;

	public Integer getReference() {
		return reference;
	}

	public void setReference(Integer reference) {
		this.reference = reference;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getStartBalance() {
		return startBalance;
	}

	public void setStartBalance(double startBalance) {
		this.startBalance = startBalance;
	}

	public double getMutation() {
		return mutation;
	}

	public void setMutation(double mutation) {
		this.mutation = mutation;
	}

	public double getEndBalance() {
		return endBalance;
	}

	public void setEndBalance(double endBalance) {
		this.endBalance = endBalance;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String toString() {
		return "Reference: " + reference + ", " + "AccountNumber: "
				+ accountNumber + ", " + "Description: " + description + ", "
				+ "StartBalance: " + startBalance + ", " + "Mutation: "
				+ mutation + ", " + "EndBalance: " + endBalance + ", "
				+ "Remarks: " + remarks;
	}
}
