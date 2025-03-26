package org.example;

public class Member {
    private int id = 1;
    private String name;
    private String loginId;
    private String loginPw;
    private String regDate;
    public Member(int id, String name, String loginId, String loginPw, String regDate) {
        this.id = id;
        this.name = name;
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.regDate = regDate;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLoginId() {
        return loginId;
    }
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
    public String getLoginPw() {
        return loginPw;
    }
    public void setLoginPw(String loginPw) {
        this.loginPw = loginPw;
    }
    public String getRegDate() {
        return regDate;
    }
    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }
}
