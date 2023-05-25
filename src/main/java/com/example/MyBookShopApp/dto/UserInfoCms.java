package com.example.MyBookShopApp.dto;


import java.util.Objects;

public class UserInfoCms extends UserInfo implements AopDto{
    private Long id;

    public UserInfoCms(UserInfo userInfoForProfile) {
        super.setBalance(userInfoForProfile.getBalance());
        super.setName(userInfoForProfile.getName());
        super.setEmailAndApprove(userInfoForProfile.getEmailAndApprove());
        super.setPhoneAndApprove(userInfoForProfile.getPhoneAndApprove());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserInfoCms that = (UserInfoCms) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}
