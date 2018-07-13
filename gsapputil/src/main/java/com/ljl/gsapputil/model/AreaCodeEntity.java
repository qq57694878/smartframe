package com.ljl.gsapputil.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "area_code")
public class AreaCodeEntity {
    private int areaId;
    private String areaCode;
    private String province;
    private String city;
    private String country;
    private String phonePrefix;
    private String zip;
    private String areaLevel;
    private String areaStatus;

    @Id
    @Column(name = "area_id", nullable = false)
    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    @Basic
    @Column(name = "area_code", nullable = true, length = 6)
    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    @Basic
    @Column(name = "province", nullable = true, length = 30)
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Basic
    @Column(name = "city", nullable = true, length = 30)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "country", nullable = true, length = 30)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Basic
    @Column(name = "phone_prefix", nullable = true, length = 4)
    public String getPhonePrefix() {
        return phonePrefix;
    }

    public void setPhonePrefix(String phonePrefix) {
        this.phonePrefix = phonePrefix;
    }

    @Basic
    @Column(name = "zip", nullable = true, length = 6)
    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Basic
    @Column(name = "area_level", nullable = true)
    public String getAreaLevel() {
        return areaLevel;
    }

    public void setAreaLevel(String areaLevel) {
        this.areaLevel = areaLevel;
    }

    @Basic
    @Column(name = "area_status", nullable = true)
    public String getAreaStatus() {
        return areaStatus;
    }

    public void setAreaStatus(String areaStatus) {
        this.areaStatus = areaStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AreaCodeEntity that = (AreaCodeEntity) o;

        if (areaId != that.areaId) return false;
        if (areaCode != null ? !areaCode.equals(that.areaCode) : that.areaCode != null) return false;
        if (province != null ? !province.equals(that.province) : that.province != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        if (phonePrefix != null ? !phonePrefix.equals(that.phonePrefix) : that.phonePrefix != null) return false;
        if (zip != null ? !zip.equals(that.zip) : that.zip != null) return false;
        if (areaLevel != null ? !areaLevel.equals(that.areaLevel) : that.areaLevel != null) return false;
        if (areaStatus != null ? !areaStatus.equals(that.areaStatus) : that.areaStatus != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = areaId;
        result = 31 * result + (areaCode != null ? areaCode.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (phonePrefix != null ? phonePrefix.hashCode() : 0);
        result = 31 * result + (zip != null ? zip.hashCode() : 0);
        result = 31 * result + (areaLevel != null ? areaLevel.hashCode() : 0);
        result = 31 * result + (areaStatus != null ? areaStatus.hashCode() : 0);
        return result;
    }
}
