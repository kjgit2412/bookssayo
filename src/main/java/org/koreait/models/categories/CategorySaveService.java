package org.koreait.models.categories;

import lombok.RequiredArgsConstructor;
import org.koreait.controllers.admin.CategoryForm;
import org.koreait.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategorySaveService {
    private final CategoryRepository repository;

    public void save(CategoryForm form) {

    }

}
