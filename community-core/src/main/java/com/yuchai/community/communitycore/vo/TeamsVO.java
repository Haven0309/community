package com.yuchai.community.communitycore.vo;


import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author Haven
 * @since 2018-09-14
 */
public class TeamsVO {


    private Integer id;

    /**
     * 团队名称
     */
    private String teamName;

    /**
     * 团队描述
     */
    private String teamDesc;

    /**
     * 队长
     */
    private String teamLeader;

    private LocalDate createTime;

    private String createBy;

    private List<UserVO> Users;

    public List<UserVO> getUsers() {
        return Users;
    }

    public void setUsers(List<UserVO> users) {
        Users = users;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamDesc() {
        return teamDesc;
    }

    public void setTeamDesc(String teamDesc) {
        this.teamDesc = teamDesc;
    }

    public String getTeamLeader() {
        return teamLeader;
    }

    public void setTeamLeader(String teamLeader) {
        this.teamLeader = teamLeader;
    }

    public LocalDate getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @Override
    public String toString() {
        return "TeamsVO{" +
                "id=" + id +
                ", teamName='" + teamName + '\'' +
                ", teamDesc='" + teamDesc + '\'' +
                ", teamLeader='" + teamLeader + '\'' +
                ", createTime=" + createTime +
                ", createBy='" + createBy + '\'' +
                ", Users=" + Users +
                '}';
    }
}
