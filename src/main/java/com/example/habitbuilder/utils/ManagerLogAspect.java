package com.example.habitbuilder.utils;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.habitbuilder.annotation.ManagerLog;
import com.example.habitbuilder.pojo.AdminOperationLog;
import com.example.habitbuilder.service.AdminOperationLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import jakarta.servlet.http.HttpServletRequest;

// import javax.servlet.http.HttpServletRequest; 这里的问题
import java.lang.reflect.Method;

@Aspect
@Component
public class ManagerLogAspect {

    private static final Logger log = LoggerFactory.getLogger(ManagerLogAspect.class);
    @Autowired
    private AdminOperationLogService adminOperationLogService;

    @Autowired
    private JwtUtil jwtUtil;

    //基于注解切入
    @Pointcut("@annotation(com.example.habitbuilder.annotation.ManagerLog)")
    public void operLogPoinCUt(){

    }

    @Before("operLogPoinCUt()")
    public void beforeMethod(JoinPoint joinPoint){
    }

    @AfterReturning(value = "operLogPoinCUt()",returning = "result")
    public void saveOperLog(JoinPoint joinPoint,Object result){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        try {
            if (requestAttributes != null) {
                HttpServletRequest request = requestAttributes.getRequest();
                //获取请求信息
                //HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
                String token = request.getHeader("Authorization");
                DecodedJWT jwt = jwtUtil.resolveJwt(token);
                if (jwt != null) {
                    //获取操作的managerId
                    LoginManager manager = jwtUtil.toManager(jwt);

                    // 从切面织入点处通过反射机制获取织入点处的方法
                    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
                    // 获取切入点所在的方法
                    Method method = signature.getMethod();
                    // 获取操作
                    ManagerLog Log = method.getAnnotation(ManagerLog.class);

                    AdminOperationLog operationLog = new AdminOperationLog();

                    if (Log != null) {
                        operationLog.setManagerId(manager.getManager().getManagerId());
                        operationLog.setOperation(Log.opreation());
                        log.info(Log.toString());
                        adminOperationLogService.add(operationLog);
                    }
                }

            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
