package net.javadog.iphone.controller;

import net.javadog.iphone.common.response.ResponseData;
import net.javadog.iphone.entity.MailEntity;
import net.javadog.iphone.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 邮箱控制层
 *
 * @author hdx
 */
@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    @PostMapping("/test")
    public ResponseData testMail(@Valid @RequestBody MailEntity mailEntity){
        mailService.testMail(mailEntity);
        return ResponseData.success();
    }
}
