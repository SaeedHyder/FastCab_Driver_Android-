package com.app.fastcabdriver.global;

import java.io.File;

/**
 * Created by saeedhyder on 7/17/2017.
 */

public class SignUpFormConstant {

    String fullname;
    String dob;
    String mobileNo;
    String homeAddress;
    String LicenseNo;
    File ProfileImage;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getLicenseNo() {
        return LicenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        LicenseNo = licenseNo;
    }

    public File getProfileImage() {
        return ProfileImage;
    }

    public void setProfileImage(File profileImage) {
        ProfileImage = profileImage;
    }
}
