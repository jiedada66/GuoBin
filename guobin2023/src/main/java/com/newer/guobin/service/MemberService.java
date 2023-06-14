package com.newer.guobin.service;

import java.util.List;

public interface MemberService {

    List<Integer> findMemberCountByMonth(List<String> monthsList);

}
