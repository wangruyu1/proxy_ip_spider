package com.boot.proxyip.entity;

public class ProxyIp {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column proxy_ip.id
     *
     * @mbg.generated Fri Sep 01 16:56:17 CST 2017
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column proxy_ip.ip
     *
     * @mbg.generated Fri Sep 01 16:56:17 CST 2017
     */
    private String ip;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column proxy_ip.port
     *
     * @mbg.generated Fri Sep 01 16:56:17 CST 2017
     */
    private Integer port;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column proxy_ip.address
     *
     * @mbg.generated Fri Sep 01 16:56:17 CST 2017
     */
    private String address;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column proxy_ip.anonymous
     *
     * @mbg.generated Fri Sep 01 16:56:17 CST 2017
     */
    private String anonymous;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column proxy_ip.type
     *
     * @mbg.generated Fri Sep 01 16:56:17 CST 2017
     */
    private String type;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column proxy_ip.survival_time
     *
     * @mbg.generated Fri Sep 01 16:56:17 CST 2017
     */
    private String survivalTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column proxy_ip.speed
     *
     * @mbg.generated Fri Sep 01 16:56:17 CST 2017
     */
    private String speed;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column proxy_ip.country
     *
     * @mbg.generated Fri Sep 01 16:56:17 CST 2017
     */
    private String country;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column proxy_ip.bk_field1
     *
     * @mbg.generated Fri Sep 01 16:56:17 CST 2017
     */
    private String bkField1;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column proxy_ip.check_time
     *
     * @mbg.generated Fri Sep 01 16:56:17 CST 2017
     */
    private String checkTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column proxy_ip.valid
     *
     * @mbg.generated Fri Sep 01 16:56:17 CST 2017
     */
    private Boolean valid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column proxy_ip.bk_field2
     *
     * @mbg.generated Fri Sep 01 16:56:17 CST 2017
     */
    private String bkField2;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column proxy_ip.src
     *
     * @mbg.generated Fri Sep 01 16:56:17 CST 2017
     */
    private String src;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column proxy_ip.id
     *
     * @return the value of proxy_ip.id
     *
     * @mbg.generated Fri Sep 01 16:56:17 CST 2017
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column proxy_ip.id
     *
     * @param id the value for proxy_ip.id
     *
     * @mbg.generated Fri Sep 01 16:56:17 CST 2017
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column proxy_ip.ip
     *
     * @return the value of proxy_ip.ip
     *
     * @mbg.generated Fri Sep 01 16:56:17 CST 2017
     */
    public String getIp() {
        return ip;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column proxy_ip.ip
     *
     * @param ip the value for proxy_ip.ip
     *
     * @mbg.generated Fri Sep 01 16:56:17 CST 2017
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column proxy_ip.port
     *
     * @return the value of proxy_ip.port
     *
     * @mbg.generated Fri Sep 01 16:56:17 CST 2017
     */
    public Integer getPort() {
        return port;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column proxy_ip.port
     *
     * @param port the value for proxy_ip.port
     *
     * @mbg.generated Fri Sep 01 16:56:17 CST 2017
     */
    public void setPort(Integer port) {
        this.port = port;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column proxy_ip.address
     *
     * @return the value of proxy_ip.address
     *
     * @mbg.generated Fri Sep 01 16:56:17 CST 2017
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column proxy_ip.address
     *
     * @param address the value for proxy_ip.address
     *
     * @mbg.generated Fri Sep 01 16:56:17 CST 2017
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column proxy_ip.anonymous
     *
     * @return the value of proxy_ip.anonymous
     *
     * @mbg.generated Fri Sep 01 16:56:17 CST 2017
     */
    public String getAnonymous() {
        return anonymous;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column proxy_ip.anonymous
     *
     * @param anonymous the value for proxy_ip.anonymous
     *
     * @mbg.generated Fri Sep 01 16:56:17 CST 2017
     */
    public void setAnonymous(String anonymous) {
        this.anonymous = anonymous == null ? null : anonymous.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column proxy_ip.type
     *
     * @return the value of proxy_ip.type
     *
     * @mbg.generated Fri Sep 01 16:56:17 CST 2017
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column proxy_ip.type
     *
     * @param type the value for proxy_ip.type
     *
     * @mbg.generated Fri Sep 01 16:56:17 CST 2017
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column proxy_ip.survival_time
     *
     * @return the value of proxy_ip.survival_time
     *
     * @mbg.generated Fri Sep 01 16:56:17 CST 2017
     */
    public String getSurvivalTime() {
        return survivalTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column proxy_ip.survival_time
     *
     * @param survivalTime the value for proxy_ip.survival_time
     *
     * @mbg.generated Fri Sep 01 16:56:17 CST 2017
     */
    public void setSurvivalTime(String survivalTime) {
        this.survivalTime = survivalTime == null ? null : survivalTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column proxy_ip.speed
     *
     * @return the value of proxy_ip.speed
     *
     * @mbg.generated Fri Sep 01 16:56:17 CST 2017
     */
    public String getSpeed() {
        return speed;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column proxy_ip.speed
     *
     * @param speed the value for proxy_ip.speed
     *
     * @mbg.generated Fri Sep 01 16:56:17 CST 2017
     */
    public void setSpeed(String speed) {
        this.speed = speed == null ? null : speed.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column proxy_ip.country
     *
     * @return the value of proxy_ip.country
     *
     * @mbg.generated Fri Sep 01 16:56:17 CST 2017
     */
    public String getCountry() {
        return country;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column proxy_ip.country
     *
     * @param country the value for proxy_ip.country
     *
     * @mbg.generated Fri Sep 01 16:56:17 CST 2017
     */
    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column proxy_ip.bk_field1
     *
     * @return the value of proxy_ip.bk_field1
     *
     * @mbg.generated Fri Sep 01 16:56:17 CST 2017
     */
    public String getBkField1() {
        return bkField1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column proxy_ip.bk_field1
     *
     * @param bkField1 the value for proxy_ip.bk_field1
     *
     * @mbg.generated Fri Sep 01 16:56:17 CST 2017
     */
    public void setBkField1(String bkField1) {
        this.bkField1 = bkField1 == null ? null : bkField1.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column proxy_ip.check_time
     *
     * @return the value of proxy_ip.check_time
     *
     * @mbg.generated Fri Sep 01 16:56:17 CST 2017
     */
    public String getCheckTime() {
        return checkTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column proxy_ip.check_time
     *
     * @param checkTime the value for proxy_ip.check_time
     *
     * @mbg.generated Fri Sep 01 16:56:17 CST 2017
     */
    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime == null ? null : checkTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column proxy_ip.valid
     *
     * @return the value of proxy_ip.valid
     *
     * @mbg.generated Fri Sep 01 16:56:17 CST 2017
     */
    public Boolean getValid() {
        return valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column proxy_ip.valid
     *
     * @param valid the value for proxy_ip.valid
     *
     * @mbg.generated Fri Sep 01 16:56:17 CST 2017
     */
    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column proxy_ip.bk_field2
     *
     * @return the value of proxy_ip.bk_field2
     *
     * @mbg.generated Fri Sep 01 16:56:17 CST 2017
     */
    public String getBkField2() {
        return bkField2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column proxy_ip.bk_field2
     *
     * @param bkField2 the value for proxy_ip.bk_field2
     *
     * @mbg.generated Fri Sep 01 16:56:17 CST 2017
     */
    public void setBkField2(String bkField2) {
        this.bkField2 = bkField2 == null ? null : bkField2.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column proxy_ip.src
     *
     * @return the value of proxy_ip.src
     *
     * @mbg.generated Fri Sep 01 16:56:17 CST 2017
     */
    public String getSrc() {
        return src;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column proxy_ip.src
     *
     * @param src the value for proxy_ip.src
     *
     * @mbg.generated Fri Sep 01 16:56:17 CST 2017
     */
    public void setSrc(String src) {
        this.src = src == null ? null : src.trim();
    }
}