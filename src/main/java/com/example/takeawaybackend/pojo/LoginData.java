package com.example.takeawaybackend.pojo;

public class LoginData {
    private Integer id;
    private String idValue;
    private Integer pageNum;
    private Integer pid;
    private String username;
    private String password;
    private String email;
    private String code;
    private String ensurePwd;
    private String nickname;
    private String oldValue;
    private String gender;
    private String profile;
    private String birth;
    public LoginData() {
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public LoginData(String username, String password, String email, String code, String ensurePwd, String nickname, String oldValue, String gender) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.code = code;
        this.ensurePwd = ensurePwd;
        this.nickname = nickname;
        this.oldValue = oldValue;
        this.gender = gender;
    }

    public String getIdValue() {
        return idValue;
    }

    public void setIdValue(String idValue) {
        this.idValue = idValue;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEnsurePwd() {
        return ensurePwd;
    }

    public void setEnsurePwd(String ensurePwd) {
        this.ensurePwd = ensurePwd;
    }
}
