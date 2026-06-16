package com.book.books.domain;

public class User {
    private Integer uid;

    private String uname;

    private String password;

    private String gender;

    private String phone;

    private String email;

    private String adress;

    // 新增角色字段，区分管理员admin/普通用户user
    private String role;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname == null ? null : uname.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress == null ? null : adress.trim();
    }

    // role 对应的get/set方法（必须加，否则Controller拿不到角色）
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }

    @Override
    public String toString() {
        // 新增role打印，方便控制台日志排查
        return "User [uid=" + uid + ", uname=" + uname + ", password="
                + password + ", gender=" + gender + ", phone=" + phone
                + ", email=" + email + ", adress=" + adress + ", role=" + role + "]";
    }

}