package com.example.admin.ajs.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by VEER7 on 6/20/2017.
 */

public class LoginUserModel implements Serializable {

    @SerializedName("DataId")
    @Expose
    private int dataId;
    @SerializedName("ClientId")
    @Expose
    private int clientId;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("CompanyName")
    @Expose
    private String companyName;
    @SerializedName("Address1")
    @Expose
    private String address1;
    @SerializedName("Address2")
    @Expose
    private String address2;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("POBox")
    @Expose
    private String pOBox;
    @SerializedName("State")
    @Expose
    private String state;
    @SerializedName("Country")
    @Expose
    private String country;
    @SerializedName("MobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("EmailId")
    @Expose
    private String emailId;
    @SerializedName("PhoneNo")
    @Expose
    private String phoneNo;
    @SerializedName("Extention")
    @Expose
    private String extention;
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("IsActive")
    @Expose
    private int isActive;
    @SerializedName("Remarks")
    @Expose
    private String remarks;
    @SerializedName("LastLoginTime")
    @Expose
    private String lastLoginTime;
    @SerializedName("LastFileDownloadTime")
    @Expose
    private String lastFileDownloadTime;
    @SerializedName("CreatedOn")
    @Expose
    private String createdOn;
    @SerializedName("EndDate")
    @Expose
    private String endDate;
    @SerializedName("BalanceTender")
    @Expose
    private String balanceTender;


    private final static long serialVersionUID = 7295017410248278913L;

    public int getDataId() {
        return dataId;
    }

    public void setDataId(int dataId) {
        this.dataId = dataId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPOBox() {
        return pOBox;
    }

    public void setPOBox(String pOBox) {
        this.pOBox = pOBox;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getExtention() {
        return extention;
    }

    public void setExtention(String extention) {
        this.extention = extention;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastFileDownloadTime() {
        return lastFileDownloadTime;
    }

    public void setLastFileDownloadTime(String lastFileDownloadTime) {
        this.lastFileDownloadTime = lastFileDownloadTime;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getBalanceTender() {
        return balanceTender;
    }

    public void setBalanceTender(String balanceTender) {
        this.balanceTender = balanceTender;
    }
}
