package org.koreait.controllers.admin;

import lombok.Data;

@Data
public class CategoryForm {

    private String mode = "add";

    private String cateCd;

    private String cateNm;

    private boolean use;


}
