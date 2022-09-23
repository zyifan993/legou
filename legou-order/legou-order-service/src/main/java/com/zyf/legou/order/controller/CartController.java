package com.zyf.legou.order.controller;

import com.zyf.legou.order.config.TokenDecode;
import com.zyf.legou.order.po.OrderItem;
import com.zyf.legou.order.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cart")
@CrossOrigin
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private TokenDecode tokenDecode;


    @RequestMapping("/add")
    public ResponseEntity add(Long id, Integer num) throws IOException {
        String username = tokenDecode.getUserInfo().get("user_name");
        cartService.add(id, num, username);
        return ResponseEntity.ok("添加成功");
    }

    @RequestMapping("/list")
    public ResponseEntity<List<OrderItem>> list() throws IOException {
//        String username = "lxs";
        Map<String, String> userInfo = tokenDecode.getUserInfo();
        String username = userInfo.get("user_name");

        List<OrderItem> list = cartService.list(username);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
