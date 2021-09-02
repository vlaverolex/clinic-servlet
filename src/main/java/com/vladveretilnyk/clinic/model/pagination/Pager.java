package com.vladveretilnyk.clinic.model.pagination;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Pager {

    private Pager() {
    }

    public static Pager of(Integer currentPage, Sorting sorting) {
        Pager pager = new Pager();
        pager.setCurrentPage(currentPage);
        pager.setSorting(sorting);
        return pager;
    }

    public static Pager of(Integer currentPage, long totalSize, Sorting sorting) {
        Pager pager = new Pager();
        pager.setCurrentPage(currentPage);
        pager.setTotalSize(totalSize);
        pager.setSorting(sorting);
        return pager;
    }

    private Sorting sorting;

    private int pageSize = 5;
    private Integer currentPage;
    private Long totalSize;

    public void setCurrentPage(Integer currentPage) {
        if (currentPage != null) {
            this.currentPage = currentPage;
        } else {
            this.currentPage = 1;
        }
    }

    public void setTotalSize(Long totalSize) {
        if (totalSize != null) {
            this.totalSize = (long) (Math.ceil((double) totalSize / pageSize));
        } else {
            this.totalSize = 0L;
        }
    }

    //    private Sorting sorting = new Sorting();
//
//    private int pageSize = 5;
//    private int currentPage = 1;
//    private long totalSize = 0;
//
//    public void setTotalSize(long totalSize) {
//        this.totalSize = (int) (Math.ceil((double) totalSize / pageSize));
//    }
}
