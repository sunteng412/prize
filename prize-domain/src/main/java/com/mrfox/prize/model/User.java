package com.mrfox.prize.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class User implements Serializable {
    private Long id;

    private String name;

    private String mobile;

    private Integer point;

    private Integer realPoint;

    private static final long serialVersionUID = 1L;
}