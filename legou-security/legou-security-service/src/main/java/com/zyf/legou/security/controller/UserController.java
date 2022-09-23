package com.zyf.legou.security.controller;

import com.zyf.legou.core.controller.BaseController;
import com.zyf.legou.core.po.ResponseBean;
import com.zyf.legou.security.po.Role;
import com.zyf.legou.security.po.User;
import com.zyf.legou.security.service.IUserService;
import com.zyf.legou.security.utils.BPwdEncoderUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.security.crypto.keygen.Base64StringKeyGenerator;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Collections;
import java.util.List;

/**
 * @Title:
 * @Description:
 *
 * @Copyright 2019 lxs - Powered By 雪松
 * @Author: lxs
 * @Date:  2019/10/9
 * @Version V1.0
 */
@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseController<IUserService, User> {

	@Autowired
	private OAuth2ClientProperties oAuth2ClientProperties; //client_id, client_secret

	@Autowired
	private OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails; //access-token-uri, grant_type

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping("/login")
	public ResponseEntity<OAuth2AccessToken> login(String username, String password) {
		//1:验证用户
		User user = service.getUserByUserName(username);
		if (null == user) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		if (!BPwdEncoderUtil.matches(password, user.getPassword())) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		//2:使用restTemplate发送请求到授权服务器，申请令牌
		//请求头 "basic auth"
		String client_secret = oAuth2ClientProperties.getClientId() + ":" + oAuth2ClientProperties.getClientSecret();
		client_secret = "Basic " + Base64.getEncoder().encodeToString(client_secret.getBytes());
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", client_secret);

		//请求参数
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.put("username", Collections.singletonList(username));
		map.put("password", Collections.singletonList(password));
		map.put("grant_type", Collections.singletonList(oAuth2ProtectedResourceDetails.getGrantType()));
		map.put("scope",oAuth2ProtectedResourceDetails.getScope());

		//HttpEntity(请求参数，头。。。)
		HttpEntity httpEntity = new HttpEntity(map, headers);

		return restTemplate.exchange(oAuth2ProtectedResourceDetails.getAccessTokenUri(), HttpMethod.POST, httpEntity, OAuth2AccessToken.class);

	}


	@ApiOperation("通过登录获得用户")
	@GetMapping("/get/{userName}")
	public User getByUserName(@PathVariable("userName") String userName) {
		return service.getUserByUserName(userName);
	}

	@ApiOperation("通过用户ID获得角色")
	@GetMapping("/select-roles/{id}")
	public List<Role> selectRolesByUserId(@PathVariable("id") Long id) {
		return service.selectRoleByUser(id);
	}

	/**
	 * 验证用户名是否存在
	 */
	@ApiOperation("验证用户名是否存在")
	@PostMapping("/validate-name/{userName}")
	public String validUserName(@PathVariable String userName,  Long id) {
		long rowCount = service.findCountByUserName(userName);

		//修改时=原来的用户名
		if (id != null) {
			User user = service.getById(id);
			if (null != userName && userName.equals(user.getUserName())) {
				return "{\"success\": true}";
			}
		}

		if (rowCount > 0) {
			return "{\"success\": false}";
		} else {
			return "{\"success\": true}";
		}
	}

	/**
	 * 锁定用户
	 */
	@GetMapping("/lock/{id}")
	@ApiOperation("锁定账户")
	public ResponseBean lock(@PathVariable Long id) throws Exception {
		ResponseBean rm = new ResponseBean();
		try {
			User u = service.getById(id);

			User user = new User();
			user.setId(id);
			if (null != u.getLock() && u.getLock()) {
				rm.setMsg("用户已解锁");
				user.setLock(false);
			} else {
				rm.setMsg("用户已锁定");
				user.setLock(true);
			}
			service.updateById(user);
		} catch (Exception e) {
			e.printStackTrace();
			rm.setSuccess(false);
			rm.setMsg("保存失败");
		}

		return rm;
	}

	@Override
	public void afterEdit(User domain) {
		//生成角色列表, 如：1,3,4
		List<Role> roles = service.selectRoleByUser(domain.getId());
		Long[] ids = new Long[roles.size()];
		for (int i=0; i< roles.size(); i++) {
			ids[i] = roles.get(i).getId();
		}
		domain.setRoleIds(ids);
	}

	/**
	 * 增加积分
	 * @param point
	 * @param username
	 */
	@GetMapping(value = "/add-point")
	public void addPoint(@RequestParam("point") Long point, @RequestParam("username") String username) {
		service.addPoint(point, username);
	}
}
