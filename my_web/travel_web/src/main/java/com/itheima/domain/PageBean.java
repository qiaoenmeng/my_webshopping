package com.itheima.domain;

import java.util.List;

public class PageBean<T> {
    /**
     * 六个
     * 2个传
     * pageNumber 当前页 页码
     * pageSize 每页显示个数
     * 2个查
     * data 查询的数据
     * totalRecord 总记录数
     * 2个算
     * totalPage 总页数
     * startIndex 开始索引
     *
     * 2个扩展
     * start 循环的开始
     * end 循环的结束
     */
    private int pageNumber ;
    private int pageSize ;

    private int totalRecord ;
    private int totalPage ;

    private int startIndex ;

    private List<T> data ;

    private int start ;
    private int end;

    //计算循环的开始和循环结束的值
    //前四后五动态条 扩展 了解 ==>> 根据当前页而来
    private void jisuan(){

        //1.总页数不满足十页 不需要前四后五
        if( getTotalPage() < 10){ //执行时机 代码的执行顺序问题   getTotalPage()至少需要执行一次 否则 totalPage没有值
            start= 1;
            end = totalPage;//结束页等于最大值即可
        }else{
            //2.总页数大于十页 才需要特殊计算
            start = pageNumber - 4; //前四
            end = pageNumber + 5; //后五

            //极限值判断 start 有可能小于1    1 2 3 4
            if(start < 1){ //没有必要前四后五
                start = 1 ;
                end = 10;
            }
            //极限值判断  页码已经大于总页数 没有必要前四后五
            if(end > totalPage){
                start = totalPage-9;
                end = totalPage;
            }

        }

    }
    public int getStart() {
        jisuan();
        return start;
    }
    public int getEnd() {
        jisuan();
        return end;
    }



    /**
     * 计算总页数 需要的前提是  有 totalRecord pageSize(创建就有)   pb.setTotalRecord(totalRecord);
     * @return
     */
    public int getTotalPage() {
        //4.3 计算总页数
        if(totalRecord % pageSize == 0 ){
            totalPage = totalRecord / pageSize;
        }else{
            totalPage = totalRecord / pageSize+1;
        }
        return totalPage;
    }

    /**
     * 直接在get方法计算  不需要手动再次调用set方法
     * @return
     */
    public int getStartIndex() {
        //调用的时候计算 开始索引
        startIndex =  (pageNumber-1)*pageSize;
        return startIndex;
    }

    /**
     * 只给有参构造 不要无参构造(防止有的人忘了给两个参数)
     * 有参构造只有两个参数  pageNumber  pageSize
     * @param pageNumber
     * @param pageSize
     */
    public PageBean(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }


    public void setStart(int start) {
        this.start = start;
    }



    public void setEnd(int end) {
        this.end = end;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }



    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }



    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
