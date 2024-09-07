package com.atguigu.daijia.customer.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class WxConfigOperator {
    @Autowired
    private WxConfigProperties wxConfigProperties;
    @Bean
    public WxMaService wxMaService(){
        WxMaDefaultConfigImpl wxMaDefaultConfig=new WxMaDefaultConfigImpl();
        wxMaDefaultConfig.setAppid(wxConfigProperties.getAppId());
        wxMaDefaultConfig.setSecret(wxConfigProperties.getSecret());
        WxMaService service=new WxMaServiceImpl();
        service.setWxMaConfig(wxMaDefaultConfig);
        return service;
    }
}
