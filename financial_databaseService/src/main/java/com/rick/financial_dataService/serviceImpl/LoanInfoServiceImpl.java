package com.rick.financial_dataService.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.rick.financial_api.common.constant.LoanConstant;
import com.rick.financial_api.common.util.CommonUtil;
import com.rick.financial_api.domain.LoanInfo;
import com.rick.financial_api.service.LoanInfoService;
import com.rick.financial_dataService.mapper.LoanInfoMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ASUS
 * @description 针对表【b_loan_info(产品信息表)】的数据库操作Service实现
 * @createDate 2023-09-19 16:40:47
 */
@DubboService(interfaceClass = LoanInfoService.class, version = "1.0")
@Service
@Transactional
public class LoanInfoServiceImpl extends ServiceImpl<LoanInfoMapper, LoanInfo>
        implements LoanInfoService {

    @Resource
    private LoanInfoMapper loanInfoMapper;

    /**
     * 查询平均利率
     *
     * @return
     */
    @Override
    public BigDecimal getAverageRate() {
        return loanInfoMapper.getAverageRate();
    }


    /**
     * 普通查询，根据id查产品
     */
    @Override
    public LoanInfo queryById(Integer Id) {
        return loanInfoMapper.selectById(Id);
    }

    /**
     * 查询投标信息和投资信息
     */


    /**
     * 简单的分页查询，只需要传入当前页数和page size
     *
     * @param page Page<LoanInfo> page = new Page<>(1, 10);
     * @param <E>
     * @return 方法返回的类型E必须是实现了IPage<LoanInfo>接口的类型，LoanInfo或者子类
     */
    @Override
    public <E extends IPage<LoanInfo>> E page(E page) {
        return super.page(page);
    }

    /**
     * 包含条件的分页查询
     *
     * @param page
     * @param queryWrapper
     * @param <E>
     * @return
     */
    @Override
    public <E extends IPage<LoanInfo>> E page(E page, Wrapper<LoanInfo> queryWrapper) {
        return super.page(page, queryWrapper);
    }


    /**
     * 分页查询按照类型
     */
    @Override
    public List<LoanInfo> queryByTypeLimit(Integer pType, Integer pageNo, Integer pageSize) {

        if (pType >= 0 || pType < 3) {
            // 创建分页对象，并设置分页信息
            Page<LoanInfo> page = new Page<>(CommonUtil.PageNo(pageNo), CommonUtil.PageSize(pageSize));
            QueryWrapper<LoanInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("product_type", pType);

            Page<LoanInfo> resultPage = loanInfoMapper.selectPage(page, queryWrapper);
//            System.out.println("current--->>>>>>>>>>>>>>"+resultPage.getCurrent());
            // 从分页对象中获取查询结果
            List<LoanInfo> resultList = resultPage.getRecords(); // 获取查询结果列表
            return resultList;

        }
        return null;
    }


    /**
     * 首页数据展示方法
     */
    @Override
    public List<LoanInfo> multipleLoan() {

        List<LoanInfo> rookie = queryByTypeLimit(LoanConstant.PRODUCT_TYPE_ROOKIE, 1, 1);
        List<LoanInfo> preference = queryByTypeLimit(LoanConstant.PRODUCT_TYPE_PREFERENCE, 1, 3);
        List<LoanInfo> loads = queryByTypeLimit(LoanConstant.PRODUCT_TYPE_LOAD, 1, 3);
        List<LoanInfo> allLoanInfo = new ArrayList<>();

        // 将三个列表合并成一个
        allLoanInfo.addAll(rookie);
        allLoanInfo.addAll(preference);
        allLoanInfo.addAll(loads);

        return allLoanInfo;
    }

    //查询类型的总记录数
    @Override
    public long queryRecordByType(Integer pType) {
        long counts = 0;
        if (pType >= 0 || pType < 3) {
            QueryWrapper<LoanInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("product_type", pType);
            counts = loanInfoMapper.selectCount(queryWrapper);
            return counts;
        }
        return counts;
    }
}




