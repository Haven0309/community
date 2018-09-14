package com.yuchai.community.providerproject.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author Haven
 * @since 2018-09-14
 */
public class Project extends Model<Project> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 项目类型
     */
    private String projectType;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 需求
     */
    private String demand;

    /**
     * 项目描述
     */
    private String projectDesc;

    /**
     * 奖金
     */
    private Integer money;

    /**
     * 要求完成时间
     */
    private LocalDateTime expirationDate;

    /**
     * 当前步骤
     */
    private String currentStep;

    /**
     * 项目状态
     */
    private String status;

    /**
     * 发布人
     */
    private Integer publisherId;

    /**
     * 创建时间
     */
    private LocalDate createdDate;

    /**
     * 参与团队
     */
    @TableField(exist = false)
    private List<Teams> teams;

    /**
     * 参与人数
     */
    @TableField(exist = false)
    private List<User> users;

    public List<Teams> getTeams() {
        return teams;
    }

    public void setTeams(List<Teams> teams) {
        this.teams = teams;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDemand() {
        return demand;
    }

    public void setDemand(String demand) {
        this.demand = demand;
    }

    public String getProjectDesc() {
        return projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(String currentStep) {
        this.currentStep = currentStep;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Integer publisherId) {
        this.publisherId = publisherId;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", projectType='" + projectType + '\'' +
                ", projectName='" + projectName + '\'' +
                ", demand='" + demand + '\'' +
                ", projectDesc='" + projectDesc + '\'' +
                ", money=" + money +
                ", expirationDate=" + expirationDate +
                ", currentStep='" + currentStep + '\'' +
                ", status='" + status + '\'' +
                ", publisherId=" + publisherId +
                ", createdDate=" + createdDate +
                ", teams=" + teams +
                ", users=" + users +
                '}';
    }
}
