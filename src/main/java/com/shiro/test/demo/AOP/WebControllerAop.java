package com.shiro.test.demo.AOP;

/**
 * Code by langlang on 2020/2/18
 */

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @Description: 
 * 
 * @auther: 处理AOP
 * @date: 10:51 2020/2/18 
 * @param: 
 * @return: 
 */
@Component
@Aspect
public class WebControllerAop {

    @Pointcut("execution(* com.shiro.test.demo.Controller..*.*(..))")
    public void executeService(){

    }

    @Before("executeService()")
    public void doBeforeAdvice(JoinPoint joinPoint){
        System.out.println("我是前置通知!!!");
        //获取目标方法的参数信息
        Object[] obj = joinPoint.getArgs();
        //AOP代理类的信息
        joinPoint.getThis();
        //代理的目标对象
        joinPoint.getTarget();
        //用的最多 通知的签名
        Signature signature = joinPoint.getSignature();
        //代理的是哪一个方法
        System.out.println(signature.getName());
        //AOP代理类的名字
        System.out.println(signature.getDeclaringTypeName());
//        //AOP代理类的类（class）信息
//        signature.getDeclaringType();
//        //获取RequestAttributes
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        //从获取RequestAttributes中获取HttpServletRequest的信息
//        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
//        //如果要获取Session信息的话，可以这样写：
//        //HttpSession session = (HttpSession) requestAttributes.resolveReference(RequestAttributes.REFERENCE_SESSION);
//        Enumeration<String> enumeration = request.getParameterNames();
//        Map<String,String> parameterMap = Maps.newHashMap();
//        while (enumeration.hasMoreElements()){
//            String parameter = enumeration.nextElement();
//            parameterMap.put(parameter,request.getParameter(parameter));
//        }
//        String str = JSON.toJSONString(parameterMap);
//        if(obj.length > 0) {
//            System.out.println("请求的参数信息为："+str);
//        }
    }

    /**
     * 后置返回通知
     * 这里需要注意的是:
     *      如果参数中的第一个参数为JoinPoint，则第二个参数为返回值的信息
     *      如果参数中的第一个参数不为JoinPoint，则第一个参数为returning中对应的参数
     * returning 限定了只有目标方法返回值与通知方法相应参数类型时才能执行后置返回通知，否则不执行，对于returning对应的通知方法参数为Object类型将匹配任何目标返回值
     * @param joinPoint
     * @param keys
     */
    @AfterReturning(value = "execution(* com.lanjieqi.demo.Controller..*.*(..))",returning = "keys")
    public void doAfterReturningAdvice1(JoinPoint joinPoint,Object keys){

        System.out.println("第一个后置返回通知的返回值："+keys);
    }

    @AfterReturning(value = "execution(* com.lanjieqi.demo.Controller..*.*(..))",returning = "keys",argNames = "keys")
    public void doAfterReturningAdvice2(String keys){

        System.out.println("第二个后置返回通知的返回值："+keys);
    }

}
