package org.koreait.models.categories;

import lombok.RequiredArgsConstructor;
import org.koreait.commons.Utils;
import org.koreait.commons.validators.RequiredValidator;
import org.koreait.controllers.admin.CategoryForm;
import org.koreait.entities.Category;
import org.koreait.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
            utils.getMessage("Duplicate.cateCd", "validation");
        }

        Category category = new ModelMapper().map(form, Category.class);

        repository.saveAndFlush(category);
    }

}
