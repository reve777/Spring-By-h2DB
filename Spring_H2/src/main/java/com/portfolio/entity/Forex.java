package com.portfolio.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Forex {
    @Id
    @Column
    private String code;
    
    @Column
    private String description;
    
    @Column
    private String rate;
    
    @Column
    private Double rate_float;
    
    @Column
    private String updateTime;
    
    public Forex() {
    }

    public Forex(String code, String description, String rate, Double rate_float, String updateTime) {
        this.code = code;
        this.description = description;
        this.rate = rate;
        this.rate_float = rate_float;
        this.updateTime = updateTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public Double getRate_float() {
        return rate_float;
    }

    public void setRate_float(Double rate_float) {
        this.rate_float = rate_float;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Forex [code=" + code + ", description=" + description + ", rate=" + rate
                + ", rate_float=" + rate_float + ", updateTime=" + updateTime + "]";
    }
}