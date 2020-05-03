package me.zhoudongyu.service;

import me.zhoudongyu.entity.CampusPhone;

import java.util.List;

public interface CampusPhoneService {

    /**
     * 获得校园电话信息
     */
    List<CampusPhone> getCampusPhoneInfo();
}
