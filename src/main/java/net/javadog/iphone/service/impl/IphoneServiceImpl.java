package net.javadog.iphone.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.javadog.iphone.entity.IphoneEntity;
import net.javadog.iphone.entity.RecordEntity;
import net.javadog.iphone.mapper.IphoneMapper;
import net.javadog.iphone.service.IphoneService;
import net.javadog.iphone.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Iphone 接口实现类
 *
 * @author hdx
 */
@Service
@Slf4j
public class IphoneServiceImpl extends ServiceImpl<IphoneMapper, IphoneEntity> implements IphoneService {

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Resource
    private RecordService recordService;

    /**
     * 是否自动发送邮件通知-默认false
     */
    @Value("${iphone.listen.autoMail}")
    private boolean autoMail;

    /**
     * 是否自动执行python脚本-默认false
     */
    @Value("${iphone.listen.autoPy}")
    private boolean autoPy;

    /**
     * 监听地址模板
     */
    private String targetTpl = "https://www.apple.com.cn/shop/fulfillment-messages?pl=true&mts.0=regular&mts.1=compact&parts.0={}&location={}";

    /**
     * 型号颜色-对应map
     */
    public static final Map<String, String> COLOR= new HashMap<>();

    /**
     * 型号容量-对应map
     */
    public static final Map<String, String> SIZE= new HashMap<>();

    static {
        COLOR.put("MU2Q3CH/A", "naturaltitanium");
        COLOR.put("MU2V3CH/A", "naturaltitanium");
        COLOR.put("MU603CH/A", "naturaltitanium");

        COLOR.put("MU2R3CH/A", "bluetitanium");
        COLOR.put("MU2W3CH/A", "bluetitanium");
        COLOR.put("MU613CH/A", "bluetitanium");

        COLOR.put("MU2P3CH/A", "whitetitanium");
        COLOR.put("MU2U3CH/A", "whitetitanium");
        COLOR.put("MU2Y3CH/A", "whitetitanium");

        COLOR.put("MU2N3CH/A", "blacktitanium");
        COLOR.put("MU2T3CH/A", "blacktitanium");
        COLOR.put("MU2X3CH/A", "blacktitanium");
    }

    static {
        SIZE.put("MU2Q3CH/A", "256gb");
        SIZE.put("MU2V3CH/A", "512gb");
        SIZE.put("MU603CH/A", "1tb");

        SIZE.put("MU2R3CH/A", "256gb");
        SIZE.put("MU2W3CH/A", "512gb");
        SIZE.put("MU613CH/A", "1tb");

        SIZE.put("MU2P3CH/A", "256gb");
        SIZE.put("MU2U3CH/A", "512gb");
        SIZE.put("MU2Y3CH/A", "1tb");

        SIZE.put("MU2N3CH/A", "256gb");
        SIZE.put("MU2T3CH/A", "512gb");
        SIZE.put("MU2X3CH/A", "1tb");
    }


    @Override
    @Async
    public void topicNotify() {
        List<IphoneEntity> topicList = this.list();
        if(CollectionUtil.isEmpty(topicList)){
            return;
        }
        topicList.forEach(item->{
            String target = StrUtil.format(targetTpl, item.getModel(), item.getLocation());
            String result = HttpUtil.get(target);
            this.analysis(result, item.getModel(), item.getMail(), item.getLocation());
        });
    }

    /**
     * 分析苹果有货接口
     */
    @Async
    void analysis(String result, String model, String mail, String location){
        boolean isJson = JSONUtil.isJson(result);
        if(!isJson){
            log.error("503异常,建议切换动态ip");
            return;
        }
        JSONObject jsonObject = JSONUtil.parseObj(result);
        JSONObject body = jsonObject.getJSONObject("body");
        JSONObject content = body.getJSONObject("content");
        JSONObject pickupMessage = content.getJSONObject("pickupMessage");
        JSONArray stores = pickupMessage.getJSONArray("stores");
        JSONObject store = (JSONObject) stores.get(0);
        if(ObjectUtil.isNull(store)){
            return;
        }
        JSONObject partsAvailability = store.getJSONObject("partsAvailability");
        JSONObject modelObj = partsAvailability.getJSONObject(model);
        String pickupDisplay = modelObj.getStr("pickupDisplay");
        JSONObject messageTypes = modelObj.getJSONObject("messageTypes");
        JSONObject compact = messageTypes.getJSONObject("compact");
        String storePickupProductTitle = compact.getStr("storePickupProductTitle");
        String storeName = store.getStr("storeName");
        if("default".equals(pickupDisplay)){
            JSONObject messageTypesDefault = modelObj.getJSONObject("messageTypes");
            JSONObject compactDefault = messageTypesDefault.getJSONObject("compact");
            String storePickupQuote = compactDefault.getStr("storePickupQuote");
            log.info("storePickupQuote={}",storePickupQuote);
        } else if(!"unavailable".equals(pickupDisplay) && !"ineligible".equals(pickupDisplay)){
            log.info("有货,model={},title={}",model,storePickupProductTitle);
            // 存入有货记录
            this.saveRecord(storeName, storePickupProductTitle);
            // 是否要发送邮件信息
            if(autoMail) {
                this.handleMail(storeName, storePickupProductTitle, mail);
            }
            // 是否要执行自动化脚本
            if(autoPy) {
                this.handlePy(model, location);
            }
        }
    }

    /**
     * 保存有货记录
     */
    void saveRecord(String storeName, String storePickupProductTitle){
        RecordEntity record = new RecordEntity();
        record.setRegion(storeName);
        record.setModel(storePickupProductTitle);
        record.setCreateTime(new Date());
        recordService.save(record);
    }

    /**
     * 处理邮件通知
     */
    @Async
    void handleMail(String storeName, String storePickupProductTitle, String mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        //主题
        mailMessage.setSubject("Iphone有货提醒");
        //内容
        mailMessage.setText(storeName + " " + storePickupProductTitle + " 有货, 时间：" + DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        mailMessage.setTo(mail);
        mailMessage.setFrom("postmaster@javadog.net");
        javaMailSender.send(mailMessage);
    }


    /**
     * 处理邮件通知
     */
    @Async
    void handlePy(String model,String location) {
        String color = COLOR.get(model);
        String size = SIZE.get(model);
        final String[] region = location.split(" ");

        String province = region[0];
        String city = region[1];
        String area = region[2];

        //前面一半是本地环境下的python的启动文件地址，后面一半是要执行的python脚本地址
        String[] arguments = new String[]{"python", "E:\\workgit\\javadog\\apple\\iphone15-pro-max.py", color, size, province, city, area};
        Process proc;
        try {
            proc = Runtime.getRuntime().exec(arguments);// 执行py文件
            //waitFor是用来显示脚本是否运行成功，1表示失败，0表示成功，还有其他的表示其他错误
            int re = proc.waitFor();
            System.out.println(re);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
