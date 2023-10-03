package com.rick.financial_web.view;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageInfo {

    //页号
    private Integer pageNo;
    //每页大小
    private Integer pageSize;
    //总页数
    private Integer totalPage;
    //总记录数
    private Long totalRecord;

    public PageInfo(Integer pageNo, Integer pageSize, Long totalRecord) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalRecord = totalRecord;

        //计算总页数
        if (this.totalRecord % this.pageSize == 0) {
            this.totalPage = Math.toIntExact(this.totalRecord / this.pageSize);
        } else {
            this.totalPage = Math.toIntExact(this.totalRecord / this.pageSize) + 1;
        }

    }
}
