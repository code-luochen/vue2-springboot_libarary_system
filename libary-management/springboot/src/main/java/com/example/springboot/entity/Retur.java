package com.example.springboot.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Retur extends BaseEntity{
    private String bookName;
    private String bookNo;
    private String userNo;
    private String userName;
    private String userPhone;
    private Integer score;

    private String status;
    private Integer days;
    private LocalDate returnDate;


//    实际还书时间
    private LocalDate realDate;

}
