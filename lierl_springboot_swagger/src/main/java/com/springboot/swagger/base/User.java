package com.springboot.swagger.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lierl
 */
@ApiModel(value = "用户模型")
public class User implements Serializable {
    @ApiModelProperty(name="id", value = "用户id", required = true,position = 0)
    private Integer id;
    @ApiModelProperty(name="name", value = "姓名", required = true,position = 1)
    private String name;
    @ApiModelProperty(hidden = true)
    private String password;
    @ApiModelProperty(name="email", value = "邮箱",position =2)
    private String email;
    @ApiModelProperty(name="sex", value = "性别", example = "0: 男, 1：女",position = 3, allowableValues = "0,1")
    private String sex;
    @ApiModelProperty(name="createTime",value = "创建时间",position = 4, allowableValues = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @ApiModelProperty(name="updateTime",value = "更新时间",position = 5, allowableValues = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    @ApiModelProperty(hidden = true)
    private Boolean hasConfirmed;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getHasConfirmed() {
        return hasConfirmed;
    }

    public void setHasConfirmed(Boolean hasConfirmed) {
        this.hasConfirmed = hasConfirmed;
    }
}
