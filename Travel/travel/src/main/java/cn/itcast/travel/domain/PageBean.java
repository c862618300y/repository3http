package cn.itcast.travel.domain;

import java.util.List;

public class PageBean<T> {
    private int totalCount;//总记录数
    private int totalPage;//总页数
    private int pageSize;//每页显示数据的条数
    private int currentPage;//当前的页码
    private List<T> list;//每页展示数据的集合

    public PageBean() {
    }

    public PageBean(int totalCount, int totalPage, int pageSize, int currentPage, List<T> list) {
        this.totalCount = totalCount;
        this.totalPage = totalPage;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.list = list;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
