package org.koreait.models.categories;

import lombok.RequiredArgsConstructor;
import org.koreait.commons.BadRequestException;
import org.koreait.commons.Utils;
import org.koreait.commons.validators.RequiredValidator;
import org.koreait.controllers.admin.CategoryForm;
import org.koreait.entities.Category;
import org.koreait.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategorySaveService implements RequiredValidator {
    private final CategoryRepository repository;
    private final Utils utils;

    public void save(CategoryForm form) {
        /* 필수 항목 검증 - cateCd, cateNm */
        requriedCheck(form.getCateCd(), utils.getMessage("NotBlank.cateCd", "validation"));
        requriedCheck(form.getCateNm(), utils.getMessage("NotBlank.cateNm", "validation"));

        /* 분류 코드 중복 여부 체크 */
        if (repository.exists(form.getCateCd())) {
            throw new BadRequestException(utils.getMessage("Duplicate.cateCd", "validation"));
        }

        Category category = new ModelMapper().map(form, Category.class);

        repository.saveAndFlush(category);
    }

    /** 목록 저장 처리 */
    public void saveList(CategoryForm form) {

        List<Integer> chks = form.getChkNo();
        nullCheck(chks, utils.getMessage("NotSelected.edit", "validation"));

        for (Integer chk : chks) {
            String cateCd = utils.getParam("cateCd_" + chk);
            Category item = repository.findById(cateCd).orElse(null);
            if (item == null) continue;

            item.setCateNm(utils.getParam("cateNm_" + chk));
            item.setUse(Boolean.parseBoolean(utils.getParam("use_" + chk)));
            item.setListOrder(Long.parseLong(utils.getParam("listOrder_" + chk)));
        }
        repository.flush();
    }


}
