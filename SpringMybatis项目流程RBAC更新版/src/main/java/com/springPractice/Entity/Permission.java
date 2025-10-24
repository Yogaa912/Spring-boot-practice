package com.springPractice.Entity;
import lombok.Data;

@Data
public class Permission {
    private Integer id;
    private String uni;
    private String name;
    private Boolean c;
    private Boolean r;
    private Boolean u;
    private Boolean d;
}