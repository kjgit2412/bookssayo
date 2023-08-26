package org.koreait.models.buyer;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.koreait.entities.Buyer;
import org.koreait.repositories.BuyerListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuyerListService {

    private final HttpServletRequest request;

    private final BuyerListRepository repository;


    public List<Buyer> getList(Options opts) {

        List<Buyer> items = repository.getBuyers(opts.getGid(), opts.getMode().name());

        //items.stream().forEach(this::addFileInfo);

        return items;

    }

    public List<Buyer> getBuyerDone(String gid) {

        Options opts = Options.builder()
                .gid(gid)
                .mode(SearchMode.DONE)
                .build();

        return getList(opts);
    }

    @Data
    @Builder
    static class Options {
        private String gid;
        private SearchMode mode;

    }

    enum SearchMode {
        ALL,
        DONE,
        UNDONE
    }
}
