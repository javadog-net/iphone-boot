package net.javadog.iphone;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Description: 主启动类
 * @Author: hdx
 * @Date: 2022/1/29 15:38
 * @Version: 1.0
 */
@SpringBootApplication
@Slf4j
@EnableSwagger2
@EnableKnife4j
@MapperScan("net.javadog.iphone.mapper")
@EnableScheduling
@EnableAsync
public class Application {

    public static void main(String[] args) throws UnknownHostException {
        // 启动类
        ConfigurableApplicationContext application = SpringApplication.run(Application.class, args);
        // 打印基础信息
        info(application);
    }

    static void info(ConfigurableApplicationContext application) throws UnknownHostException {
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String contextPath = env.getProperty("server.servlet.context-path");
        if (contextPath == null) {
            contextPath = "";
        }
        log.info("\n----------------------------------------------------------\n\t" +
                "欢迎访问  \thttps://www.javadog.net\n\t" +
                "程序已启动! 地址如下:\n\t" +
                "Local: \t\thttp://localhost:" + port + contextPath + "\n\t" +
                "External: \thttp://" + ip + ':' + port + contextPath + '\n' +
                "Swagger文档: \thttp://" + ip + ":" + port + contextPath + "/doc.html\n" +
                "----------------------------------------------------------");
    }
}
