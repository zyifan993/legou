package com.zyf.legou.order.controller;

import com.zyf.legou.core.controller.BaseController;
import com.zyf.legou.order.config.TokenDecode;
import com.zyf.legou.order.po.Address;
import com.zyf.legou.order.service.impl.AddressServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "address")
public class AddressController extends BaseController<AddressServiceImpl, Address> {
    @Autowired
    private TokenDecode tokenDecode;

    @Override
    public List<Address> list(Address entity) {
        Map<String, String> user = null;
        try {
            user = tokenDecode.getUserInfo();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String username = user.get("user_name");
        entity.setUsername(username);
        return service.list(entity);
    }
}
