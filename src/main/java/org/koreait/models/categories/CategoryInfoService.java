package org.koreait.models.categories;

import lombok.RequiredArgsConstructor;
import org.koreait.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryInfoService {
    private final CategoryRepository repository;
}
