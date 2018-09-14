package com.yuchai.community.zuulgateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.Base64;

/**
 * 进行授权访问处理
 * Created by Haven
 * 2018/9/6 16:42
 *
 * @author Haven
 */
public class AuthorizedRequesFilter extends ZuulFilter {
    private static Logger logger = LoggerFactory.getLogger(AuthorizedRequesFilter.class);

    /**
     * 过滤器执行的位置，有以下类型：
     * 1.pre 在请求发错之前执行过滤，如果要进行访问，肯定在请求前设置头信息
     * 2.route 在进行路由请求的时候被调用
     * 3.post 在路由之后发生请求信息的时候被调用
     * 4.error 在出现错误之后进行调用
     *
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 设置优先级，数字越大优先级越低，0是最高
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 该Filter是否要执行，默认是false
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 具体的过滤执行操作
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext(); // 获取当前请求的上下文
        HttpServletRequest request = ctx.getRequest();
        Object token = request.getParameter("token");

        //校验token
//        if (token == null) {
//            logger.info("token为空，禁止访问!");
//            ctx.setSendZuulResponse(false);
//            ctx.setResponseStatusCode(401);
//            return null;
//        } else {
//            //TODO 根据token获取相应的登录信息，进行校验（略）
//        }

        String auth = "admin:welcome1"; // 认证的原始信息
        byte[] encodedAuth = Base64.getEncoder()
                .encode(auth.getBytes(Charset.forName("US-ASCII"))); // 进行一个加密的处理
        // 在进行授权的头信息内容配置的时候加密的信息一定要与“Basic”之间有一个空格
        String authHeader = "Basic " + new String(encodedAuth);
        ctx.addZuulRequestHeader("Authorization", authHeader);
        return null;
    }

}
