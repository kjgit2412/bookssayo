package org.koreait.controllers.orders;

import lombok.RequiredArgsConstructor;
import org.koreait.commons.*;
import org.koreait.entities.Cart;
import org.koreait.models.books.BookSearch;
import org.koreait.models.order.CartSaveService;
import org.koreait.models.order.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/order/cart")
@RequiredArgsConstructor
public class CartController implements CommonProcess, ScriptExceptionProcess {

    private final CartSaveService cartSaveService;
    private final CartService cartService;
    private final Utils utils;

    @GetMapping
    public String index(@ModelAttribute CartForm form, Model model, @ModelAttribute BookSearch search) {
        commonProcess(model, "cart");

        List<Cart> items = cartService.getList("cart");
        model.addAttribute("items", items);

        items.stream().forEach(System.out::println);

        return utils.view("cart/index");
    }


    @PostMapping
    public String cartPs(CartForm form, Model model, @ModelAttribute BookSearch search) {

        try {
            cartSaveService.save(form);

            String mode = form.getMode();
            String url = mode.equals("direct") ? "/order" : "/order/cart";

            String script = String.format("parent.location.replace('%s');", url);
            model.addAttribute("script", script);

            return "common/_execute_script";
        } catch (CommonException e) {
            e.printStackTrace();
            throw new AlertException(e.getMessage());
        }
    }


    public void commonProcess(Model model, String mode) {
        String pageTitle = "장바구니";
        CommonProcess.super.commonProcess(model, pageTitle);
        List<String> addScript = new ArrayList<>();
        if (mode.equals("cart")) {
            addScript.add("order/cart");
        }

        model.addAttribute("addScript", addScript);
    }
}

