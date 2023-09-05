package org.koreait.controllers.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.koreait.commons.*;
import org.koreait.commons.constants.OrderStatus;
import org.koreait.controllers.orders.OrderForm;
import org.koreait.controllers.orders.OrderSearch;
import org.koreait.entities.OrderInfo;
import org.koreait.models.books.BookSearch;
import org.koreait.models.order.OrderInfoService;
import org.koreait.models.order.OrderSaveService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller("adminOrderController")
@RequestMapping("/admin/order")
@RequiredArgsConstructor
public class OrderController implements CommonProcess {

    private String tplCommon = "admin/order/";
    private final HttpServletRequest request;
    private final OrderInfoService orderInfoService;
    private final OrderSaveService orderSaveService;

    @GetMapping
    public String index(@ModelAttribute OrderSearch search, Model model, @ModelAttribute BookSearch bookSearch) {
        commonProcess(model, "list");
        ListData<OrderInfo> data = orderInfoService.getList(search);

        model.addAttribute("items", data.getContent());
        model.addAttribute("pagination", data.getPagination());

        return tplCommon + "index";     // template/admin/order/index.html, 관리자페이지에서 주문목록
    }

    @GetMapping("/{orderNo}")
    public String view(@PathVariable Long orderNo, Model model, @ModelAttribute BookSearch search) {
        commonProcess(model, "view");
        OrderInfo data = orderInfoService.get(orderNo);
        model.addAttribute("data", data);


        return tplCommon + "view";      // template/admin/order/index.html, 관리자페이지에서 주문서 수정
    }

    @PostMapping
    public String save(@Valid OrderForm orderForm, Errors errors, Model model, @ModelAttribute BookSearch search) {
        commonProcess(model, "view");
        orderInfoService.updateInfo(orderForm);

        if (errors.hasErrors()) {
            return tplCommon + "view";
        }
        try {
            orderSaveService.update(orderForm);

            return "redirect:/admin/order"; // 주문서 수정 완료 후 주문서 목록으로 이동
        } catch (CommonException e) {
            e.printStackTrace();
            throw new AlertException(e.getMessage());
        }
    }


    public void commonProcess(Model model, String mode) {
        String pageTitle = "주문 목록";

        CommonProcess.super.commonProcess(model, pageTitle);

        List<String> addScript = new ArrayList<>();
        List<String> addCommonScript = new ArrayList<>();

        if (mode.equals("view")) { // 주문서 관리
            addCommonScript.add("address");
        }

        model.addAttribute("addScript", addScript);
        model.addAttribute("addCommonScript", addCommonScript);

        model.addAttribute("menuCode", "order");
        // 서브 메뉴 처리
        String subMenuCode = Menu.getSubMenuCode(request);
        if (mode.equals("view")) subMenuCode = "order";

        model.addAttribute("subMenuCode", subMenuCode);

        // 서브 메뉴 조회
        List<MenuDetail> submenus = Menu.gets("order");
        model.addAttribute("submenus", submenus);

        model.addAttribute("orderStatuses", OrderStatus.getList());
    }

}
