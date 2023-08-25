package org.koreait.models.books;

import lombok.RequiredArgsConstructor;
import org.koreait.controllers.admin.BookForm;
import org.koreait.repositories.BookRepository;
import org.koreait.repositories.FileInfoRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookSaveService {
    private final BookRepository bookRepository;
    private final FileInfoRepository fileInfoRepository;

    public void save(BookForm form) {
        String gid = form.getGid();

        fileInfoRepository.processDone(gid);
    }
}
