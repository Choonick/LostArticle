package com.example.demo;

public class SearchLostArticleService {
    private Integer list_total_count;
    private Object RESULT;
    private Object[] row;

    public void setList_total_count(Integer list_total_count) {
        this.list_total_count = list_total_count;
    }

    public void setRESULT(Object RESULT) {
        this.RESULT = RESULT;
}

    public void setRow(Object[] row) {
        this.row = row;
    }

    public Integer getList_total_count() {

        return list_total_count;
    }

    public Object getRESULT() {
        return RESULT;
    }

    public Object[] getRow() {
        return row;
    }
}
