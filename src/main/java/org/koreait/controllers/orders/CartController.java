package org.koreait.controllers.orders;

import lombok.RequiredArgsConstructor;
import org.koreait.commons.CommonProcess;
import org.koreait.commons.ScriptExceptionProcess;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order/cart")
@RequiredArgsConstructor
public class CartController implements CommonProcess, ScriptExceptionProcess {

}
