package com.rick.financial_dataService.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rick.financial_api.domain.BidInfo;
import com.rick.financial_api.service.BidInfoService;
import com.rick.financial_dataService.mapper.BidInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
* @author ASUS
* @description 针对表【b_bid_info(投资记录表)】的数据库操作Service实现
* @createDate 2023-09-19 16:40:47
*/
//@DubboService
@Service
@Transactional
public class BidInfoServiceImpl extends ServiceImpl<BidInfoMapper, BidInfo>
    implements BidInfoService {

    @Resource
    private BidInfoMapper bidInfoMapper;

    /**
     *查询总消费金额
     */
    @Override
    public BigDecimal getSumBidMoney() {
        return bidInfoMapper.getSumBidMoney();
    }

}




