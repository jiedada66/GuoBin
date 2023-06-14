package com.newer.guobin.dao;

import com.newer.guobin.entity.Member;

import java.util.List;
import java.util.Map;

public interface MemberDao {

    // 添加会员
    void add(Member member);

    // 根据手机号查询会员信息（唯一）
    Member findByTelephone(String telephone);


    Integer findMemberCountBeforeDate(String date);

    List<Map<String, Object>> findSetmealCount();

    int getTodayNewMember(String date);

    int getTotalMember();

    int getThisWeekAndMonthNewMember(String date);


}
