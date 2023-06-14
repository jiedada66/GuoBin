package com.newer.guobin.service.impl;

import com.newer.guobin.dao.MemberDao;
import com.newer.guobin.service.MemberService;
import com.newer.guobin.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberDao memberDao;

    // 根据月份统计会员数量
    public List<Integer> findMemberCountByMonth(List<String> monthsList) {
        List<Integer> memeberCountList = new ArrayList<Integer>();
        if(monthsList!=null && monthsList.size()>0){
            for (String months : monthsList) {
                //String regTime = months+"-31";
                // 获取指定月份的最后一天
                String regTime =  DateUtils.getLastDayOfMonth(months);
                //  迭代过去12个月，每个月注册会员的数量，根据注册日期查询
                Integer memeberCount = memberDao.findMemberCountBeforeDate(regTime);
                memeberCountList.add(memeberCount);
            }
        }
        return memeberCountList;
    }

}
