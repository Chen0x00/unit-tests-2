package com.rick.financial_api.common.constant;

/**
 * 常量类
 */
public class LoanConstant {

    /**新手标通常是为初次投资者或借款人提供的一种较为简单且风险相对较低的投资或借款标的。
     * 这种类型的标的通常具有较低的利率和较短的期限，旨在吸引那些刚开始投资或借款的人。*/
    public static final int PRODUCT_TYPE_ROOKIE=0;
    /**季度月度年度，优选标是一种相对于普通标的更受欢迎或更具吸引力的投资或借款标的。
     * 通常，这些标的对投资者或借款人有一定的要求，例如较高的信用评分、较高的收入水平或其他条件。
     * 优选标通常具有相对较好的利率和较长的期限，因此它们可能更受投资者或借款人的青睐。*/
    public static final int PRODUCT_TYPE_PREFERENCE=1;
    /**散标是指那些不属于特定分类（如新手标或优选标）的一般投资或借款标的*/
    public static final int PRODUCT_TYPE_LOAD=2;

    /**产品状态**/
    public static final int PRODUCT_STATUS_NOT_SELLOUT=0;//未满标
    public static final int PRODUCT_STATUS_SELLOUT=1;//满标
    public static final int PRODUCT_STATUS_PLAN=2;//收益计划

    /**投资状态**/
    public static final int BID_STATUS_SUCCESS=1;//成功
    public static final int BID_STATUS_FAIL=2;//失败

    /**定时任务的收益计划**/
    public static final int INCOME_STATUS_PLAN=0;//生成收益计划
    public static final int INCOME_STATUS_BACK=1;//成功返回利息

    /**
     * 快钱接口的充值状态
     **/
    public static final int RECHARGE_STATUS_PROCESSING = 0;//充值中
    public static final int RECHARGE_STATUS_SUCCESS = 1;//成功
    public static final int RECHARGE_STATUS_FAIL = 2;//失败


}
