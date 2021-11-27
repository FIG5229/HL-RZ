/*package com.integration.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(-5)
public class SystemLogAspect {
	

    //@Autowired
    //private SystemLogService systemLogService;

    *//***
     * 定义service切入点拦截规则，拦截SystemServiceLog注解的方法
     *//*
    @Pointcut("execution(public * com.integration.*.*(..))")
    public void serviceAspect(){}

    *//***
     * 定义controller切入点拦截规则，拦截SystemControllerLog注解的方法
     *//*
    @Pointcut("@annotation(com.vesus.springbootlog.annotation.SystemControllerLog)")
    public void controllerAspect(){}

    *//***
     * 拦截控制层的操作日志
     * @param joinPoint
     * @return
     * @throws Throwable
     *//*
    @Around("controllerAspect()")
    public ExecutionResult recordLog(ProceedingJoinPoint joinPoint) throws Throwable {
        SystemLog systemLog = new SystemLog();
        Object proceed = null ;
        //获取session中的用户
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.getSession().getAttribute("user");
        systemLog.setUserid("vesus");
        //获取请求的ip
        String ip = request.getRemoteAddr();
        systemLog.setRequestip(ip);
        //获取执行的方法名
        systemLog.setActionmethod(joinPoint.getSignature().getName());

        //获取方法执行前时间
        Date date=new Date();
        systemLog.setActiondate(date);

        proceed = joinPoint.proceed();
        //提取controller中ExecutionResult的属性
        ExecutionResult result = (ExecutionResult) proceed;

        if (result.getResultCode().equals(ReturnCode.RES_SUCCESS)){
            //设置操作信息
            systemLog.setType("1");
            //获取执行方法的注解内容
            systemLog.setDescription(getControllerMethodDescription(joinPoint)+":"+result.getMsg());
        }else{
            systemLog.setType("2");
            systemLog.setExceptioncode(result.getMsg());
        }

        Object[] params = joinPoint.getArgs() ;
        String returnStr = "" ;
        for (Object param : params) {
            if (param instanceof String){
                returnStr+= param ;
            }else if (param instanceof Integer){
                returnStr+= param ;
            }
        }
        systemLog.setParams(returnStr);

        systemLogService.saveUser(systemLog);

        return result ;
    }

    //异常处理
    @AfterThrowing(pointcut = "controllerAspect()",throwing="e")
    public void doAfterThrowing(JoinPoint joinPoint,Throwable e) throws Throwable{
        SystemLog systemLog = new SystemLog();
        Object proceed = null ;
        //获取session中的用户
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.getSession().getAttribute("user");
        systemLog.setUserid("vesus");
        //获取请求的ip
        String ip = request.getRemoteAddr();
        systemLog.setRequestip(ip);
        systemLog.setType("2");
        systemLog.setExceptioncode(e.getClass().getName());
        systemLog.setExceptiondetail(e.getMessage());
        systemLogService.saveUser(systemLog);
    }


    *//***
     * 获取service的操作信息
     * @param joinpoint
     * @return
     * @throws Exception
     *//*
    public String getServiceMethodMsg(JoinPoint joinpoint) throws Exception{
        //获取连接点目标类名
        String className =joinpoint.getTarget().getClass().getName() ;
        //获取连接点签名的方法名
        String methodName = joinpoint.getSignature().getName() ;
        //获取连接点参数
        Object[] args = joinpoint.getArgs() ;
        //根据连接点类的名字获取指定类
        Class targetClass = Class.forName(className);
        //拿到类里面的方法
        Method[] methods = targetClass.getMethods() ;

        String description = "" ;
        //遍历方法名，找到被调用的方法名
        for (Method method : methods) {
            if (method.getName().equals(methodName)){
                Class[] clazzs = method.getParameterTypes() ;
                if (clazzs.length==args.length){
                    //获取注解的说明
                    description = method.getAnnotation(SystemServiceLog. class).decription();
                    break;
                }
            }
        }
        return description ;
    }

    *//***
     * 获取controller的操作信息
     * @param point
     * @return
     *//*
    public String getControllerMethodDescription(ProceedingJoinPoint point) throws  Exception{
        //获取连接点目标类名
        String targetName = point.getTarget().getClass().getName() ;
        //获取连接点签名的方法名
        String methodName = point.getSignature().getName() ;
        //获取连接点参数
        Object[] args = point.getArgs() ;
        //根据连接点类的名字获取指定类
        Class targetClass = Class.forName(targetName);
        //获取类里面的方法
        Method[] methods = targetClass.getMethods() ;
        String description="" ;
        for (Method method : methods) {
            if (method.getName().equals(methodName)){
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == args.length){
                    description = method.getAnnotation(SystemControllerLog.class).descrption();
                    break;
                }
            }
        }
        return description ;
    }

}
*/