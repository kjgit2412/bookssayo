package org.koreait.controllers.orders;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.koreait.commons.*;
import org.koreait.commons.constants.PaymentType;
import org.koreait.entities.Cart;
import org.koreait.entities.OrderInfo;
import org.koreait.models.books.BookSearch;
import org.koreait.models.member.MemberInfo;
import org.koreait.models.order.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController implements CommonProcess, ScriptExceptionProcess {

    private final CartService cartService;
    private final CartDeleteService cartDeleteService;
    private final OrderSaveService saveService;
    private final OrderInfoService infoService;
    private final MemberUtil memberUtil;
    private final Utils utils;

    @GetMapping
    public String index(@ModelAttribute OrderForm form, Model model, @ModelAttribute BookSearch search) {
        try {
            commonProcess(model, "form", form);

            return utils.view("order/index");
        } catch (CommonException e) {
            throw new AlertBackException(e.getMessage());
        }
    }

    @PostMapping
    public String indexPs(@Valid OrderForm form, Errors errors, Model model, @ModelAttribute BookSearch search) {
        commonProcess(model, "form", form);

        if (errors.hasErrors()) {
            return utils.view("order/index");
        }

        saveService.save(form, search);

      //  PaymentType paymentType = PaymentType.valueOf(form.getPaymentType());
     //   String script = "";
      //  if (paymentType == PaymentType.LBT) { // 무통장 입금인 경우는 주문 완료 페이지로 이동
            // 주문완료 후에  장바구니 상품 삭제
            cartDeleteService.delete(form.getCartNo());
            return "redirect:/order/end?id=" + form.getId();
     //   } else { // 그외 결제는 PG사를 통한 결제 연동
            // 추후...

     //   }

    //    return "common/_execute_script";
    }

    @GetMapping("/end")
    public String orderEnd(Long id, Model model, @ModelAttribute BookSearch search) {
        commonProcess(model, "end");
        OrderInfo data = infoService.get(id);
        model.addAttribute("data", data);
        log.info(data.toString());
        return utils.view("order/end");
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model, @ModelAttribute BookSearch search) {
        commonProcess(model, "view");

        if (id == null) {
            throw new BadRequestException();
        }

        OrderInfo data = infoService.get(id);

        model.addAttribute("data", data);

        return utils.view("order/view");  // template/front/order/view.html, front에서 주문
    }

    public void commonProcess(Model model, String mode) {
        commonProcess(model, mode, null);
    }

    public void commonProcess(Model model, String mode, OrderForm form) {
        String pageTitle = "주문서 작성";
        if (mode.equals("end")) {
            pageTitle = "주문완료";
        } else if (mode.equals("view")) {
            pageTitle = "주문리스트";
        }

        CommonProcess.super.commonProcess(model, pageTitle);

        List<String> addScript = new ArrayList<>();
        List<String> addCommonScript = new ArrayList<>();
        if (mode.equals("form")) {
            addCommonScript.add("address");
            addScript.add("order/order");

            /** 주문서 양식인 경우 장바구니 상품 조회 S */
            List<Long> cartNo = form.getCartNo();
            List<Cart> items = null;
            if (cartNo == null || cartNo.isEmpty()) { // 바로 구매
                items = cartService.getList("direct");
            } else { // 장바구니 또는 cartNo로 구매
                items = cartService.getList(cartNo);
            }

            if (items == null || items.isEmpty()) {
                throw new CartItemNotFoundException();
            }

            int totalPrice = cartService.getTotalPrice(items);
            form.setTotalPrice(totalPrice);
            model.addAttribute("items", items);
            /** 주문서 양식인 경우 장바구니 상품 조회 E */

            /** 로그인한 경우 - 회원정보로 주문자 정보 완성 */
            if (memberUtil.isLogin()) {
                MemberInfo member = memberUtil.getMember();
                form.setOrderName(Objects.requireNonNullElse(form.getOrderName(), member.getUserNm()));
                form.setOrderEmail(Objects.requireNonNullElse(form.getOrderEmail(), member.getEmail()));
                form.setOrderMobile(Objects.requireNonNullElse(form.getOrderMobile(), member.getMobile()));
            }
        }
        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addScript", addScript);
    }
}
