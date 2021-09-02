package com.vladveretilnyk.clinic.model.pagination;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Sorting {
    public static final String ASC = "ASC";
    public static final String DESC = "DESC";

    public static final Sorting USER_DEFAULT = new Sorting("user_id",DESC);
    public static final Sorting NOTE_DEFAULT = new Sorting("id",DESC);

    private String column;
    private String direction;

    public Sorting(String column, String direction) {
        setColumn(column);
        setDirection(direction);
    }

    public void setColumn(String column) {
        if (column != null) {
            this.column = column;
        }
    }

    public void setDirection(String direction) {
        if (direction != null) {
            this.direction = direction;
        }
    }


//    private String sort = "user_id";
//    private String direction = DESC;
//
//    public Sorting(String sort, String direction) {
//        setSort(sort);
//        setDirection(direction);
//    }
//
//    public void setSort(String sort) {
//        if (sort != null) {
//            this.sort = sort;
//        }
//    }
//
//    public void setDirection(String direction) {
//        if (direction != null) {
//            this.direction = direction;
//        }
//    }
}
