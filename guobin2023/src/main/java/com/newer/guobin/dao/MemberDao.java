package com.newer.guobin.dao;

import com.newer.guobin.entity.Member;

public interface MemberDao {

    // 添加会员
    public void add(Member member);

    // 根据手机号查询会员信息（唯一）
    public Member findByTelephone(String telephone);


}
