package com.wdjk.webdemo624.controller.api;

import com.alibaba.fastjson.JSON;
import com.wdjk.webdemo624.WebDemoApplication;
import com.wdjk.webdemo624.constant.api.ApiMessage;
import com.wdjk.webdemo624.constant.api.ParamConst;
import com.wdjk.webdemo624.constant.api.SetConst;
import com.wdjk.webdemo624.controller.filter.ApiFilter;
import com.wdjk.webdemo624.controller.interceptor.ApiInterceptor;
import com.wdjk.webdemo624.entity.User;
import com.wdjk.webdemo624.exception.ParamsErrorException;
import com.wdjk.webdemo624.exception.PermissionException;
import com.wdjk.webdemo624.exception.ServiceException;
import com.wdjk.webdemo624.exception.UtilClassException;
import com.wdjk.webdemo624.utils.SecretUtil;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import javax.servlet.http.Cookie;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = WebDemoApplication.class)

public class AccountControllerTest {



    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setMockMvc(){
        AccountController accountController = (AccountController) webApplicationContext.getBean("accountController");
        StandaloneMockMvcBuilder standaloneMockMvcBuilder = MockMvcBuilders.standaloneSetup(accountController);
        standaloneMockMvcBuilder.addInterceptors(new ApiInterceptor());
        standaloneMockMvcBuilder.addFilter(new ApiFilter());
        this.mockMvc = standaloneMockMvcBuilder.build();
    }

    /**
     * 检查存在 Key 选项
     *      - 检测 MvcResult 的 mode 字段，存在指定 Key items
     *
     * @param result MvcResult 结果集
     * @param keyItems 多个 Key Item（可变参数）
     * @throws UnsupportedEncodingException 不支持编码异常
     */
    void isExistKeyItems(MvcResult result, String... keyItems) throws UnsupportedEncodingException {
        Map resultMap = (Map) JSON.parse(result.getResponse().getContentAsString());
        Map resultModelMap = (Map) resultMap.get("model");

        Assert.assertEquals(keyItems.length, resultModelMap.size());
        Assert.assertThat((Set<String>) resultModelMap.keySet(), CoreMatchers.hasItems(keyItems));
    }
    /**
     * 获取已经登陆用户 Cookie
     *      - 设置 linxi 账户
     *      - 具备已激活和管理员权限
     *
     * @return Cookie 已经登录用户Cookie
     */
    Cookie getAlreadyLoginUserCookie() {
        User user = new User();
        user.setFuId(65);
        user.setFuName("linxi");
        user.setFuRank(0);
        user.setFuState(SetConst.ACCOUNT_ACTIVATED_STATE);

        return new Cookie(ParamConst.AUTHENTICATION, SecretUtil.generateUserInfoToken(user));
    }
    /**
     * 登录功能测试
     *
     */
    @Test
    public void testLoginAccountSuccess() throws Exception {

        String username = "linxi";
        String password = "123456";
        String requestBody ="{\"username\":\""+ username+"\", \"password\":\""+password+"\"}";
        System.out.println("input requestBody: "+ requestBody);

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/account/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.cookie().exists(ParamConst.AUTHENTICATION))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(""))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").exists())
                .andReturn();
        isExistKeyItems(result,"state","authentication");
    }
    /**
     * 登录错误
     *  - 账户登录异常
     *      - 参数异常
     *          - 空值
     *          - 格式错误
     *      - 服务类异常
     *          - 用户不存在
     *          - 密码错误
     */
    @Test
    public void testLoginAccountException() throws Exception{
        String[][] params = {
                {null,null},{"linxi",null},{null,"123456"},{"l","123456"},
                {"linxi*-=","123456"},{"noUser","123456"},{"test@gmail.com","123456"},
                {"1007664276@qq.com","xxxxxx"},{"linxi","123"},{"linxi","xxxxxx"}
        };
        for (String[] param :params
             ) {
            String username = param[0];
            String password = param[1];
            String requestBody ="{\"username\":\""+ username+"\", \"password\":\""+password+"\"}";
            System.out.println("input requestBody: "+ requestBody);

            try {
                mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/account/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(""))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.model").exists());
            } catch (NestedServletException ne){
                System.out.println(ne.getRootCause());
                Assert.assertThat(ne.getRootCause(),CoreMatchers.anyOf(
                        CoreMatchers.is(CoreMatchers.instanceOf(ParamsErrorException.class)),
                        CoreMatchers.is(CoreMatchers.instanceOf(ServiceException.class))
                ));
            }
        }
    }
    /**
     * 注销测试
     *  - 注销账户成功
     *  - 需要权限：@LoginAuthorization
     */
    @Test
    public void testLogoutAccountSuccess() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/account/logout")
                .cookie(getAlreadyLoginUserCookie())
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(""))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").exists());
    }

}