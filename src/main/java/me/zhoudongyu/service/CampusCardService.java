package me.zhoudongyu.service;

import me.zhoudongyu.entity.CampusCard;

public interface CampusCardService {

    /**
     * 获得校园卡信息
     *
     * @param studentNo 学号
     */
    CampusCard getCampusCardInfo(String studentNo);
}
