package org.koreait.controllers.buyer;

import lombok.RequiredArgsConstructor;
import org.koreait.entities.Buyer;
import org.koreait.models.buyer.BuyerListService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller("BuyerController")
@RequestMapping("/admin/buyer")
@RequiredArgsConstructor
public class BuyerController {

    private final BuyerListService listService;

    // 주문 목록 조회
    @RequestMapping("/list")
    public String list(Model model) {

   //    List<Buyer> buyers = listService.getBuyerDone();
   //    model.addAttribute("items", buyers);

       return "admin/buyer/list";
    }

}
