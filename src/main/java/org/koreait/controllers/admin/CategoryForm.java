package org.koreait.controllers.admin;

import lombok.Data;

import java.util.List;

@Data
public class CategoryForm {

    private String mode = "add";

    private String cateCd; // 분류코드

    private String cateNm; // 분류명

    private boolean use; // 사용여부

    private List<Integer> chkNo;
}
