package org.koreait.controllers.admin;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.koreait.commons.CommonProcess;
import org.koreait.commons.ListData;
import org.koreait.commons.Menu;
import org.koreait.commons.MenuDetail;
import org.koreait.commons.constants.OrderStatus;
import org.koreait.controllers.orders.OrderSearch;
import org.koreait.entities.OrderInfo;
import org.koreait.models.order.OrderInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller("adminOrderController")
@RequestMapping("/admin/order")
@RequiredArgsConstructor
public class OrderController implements CommonProcess {

    private String tplCommon = "admin/order/";
    private final HttpServletRequest request;
    private final OrderInfoService orderInfoService;

    @GetMapping
    public String index(@ModelAttribute OrderSearch search, Model model) {
        commonProcess(model, "list");
        ListData<OrderInfo> data = orderInfoService.getList(search);

        model.addAttribute("items", data.getContent());
        model.addAttribute("pagination", data.getPagination());

        return tplCommon + "index";
    }

    @GetMapping("/{orderNo}")
    public String view(@PathVariable Long orderNo, Model model) {
        commonProcess(model, "view");
        OrderInfo data = orderInfoService.get(orderNo);
        model.addAttribute("data", data);


        return tplCommon + "view";
    }

    public void commonProcess(Model model, String mode) {
        String pageTitle = "주문 목록";

        CommonProcess.super.commonProcess(model, pageTitle);

        List<String> addScript = new ArrayList<>();
        List<String> addCommonScript = new ArrayList<>();


        model.addAttribute("addScript", addScript);
        model.addAttribute("addCommonScript", addCommonScript);

        model.addAttribute("menuCode", "order");
        // 서브 메뉴 처리
        String subMenuCode = Menu.getSubMenuCode(request);
        model.addAttribute("subMenuCode", subMenuCode);

        // 서브 메뉴 조회
        List<MenuDetail> submenus = Menu.gets("order");
        model.addAttribute("submenus", submenus);

        model.addAttribute("orderStatuses", OrderStatus.getList());
    }

}
