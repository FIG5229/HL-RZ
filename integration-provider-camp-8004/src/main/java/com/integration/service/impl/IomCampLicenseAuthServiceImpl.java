package com.integration.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.integration.config.IomPlatformParam;
import com.integration.dao.IomCampLicenseAuthMapper;
import com.integration.dao.IomCampLicenseAuthServerMapper;
import com.integration.entity.IomCampLicenseAuth;
import com.integration.entity.IomCampLicenseAuthServer;
import com.integration.service.IomCampLicenseAuthService;
import com.integration.utils.LicenseUtil;
import com.integration.utils.SeqUtil;
import com.integration.utils.token.TokenUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
/**
* @Package: com.integration.service.impl
* @ClassName: IomCampLicenseAuthServiceImpl
* @Author: ztl
* @Date: 2021-08-06
* @Version: 1.0
* @description: 权限管理
*/
@Service
public class IomCampLicenseAuthServiceImpl implements IomCampLicenseAuthService {

    @Resource
    private IomCampLicenseAuthMapper authMapper;

    @Resource
    private IomCampLicenseAuthServerMapper authServerMapper;

    @Value("${iomplatform.auth.system.url:http://baidu.com}")
    private String authSystemUrl;

    private static final String PREFIX = "?clientCode=";

    private boolean license = IomPlatformParam.LICENSES_ENABLE;

    @Override
    public List<IomCampLicenseAuth> selectList(IomCampLicenseAuth record) {
        return authMapper.selectList(record);
    }

    @Override
    public IomCampLicenseAuth get(IomCampLicenseAuth record) {
        BigDecimal userId = BigDecimal.valueOf(Double.parseDouble(TokenUtils.getTokenUserId()));
        List<IomCampLicenseAuth> list =  selectList(record);


        IomCampLicenseAuth auth = null;

        if(list == null || list.size() == 0){
            //没有记录，新增一条

            auth = new IomCampLicenseAuth();

            auth.setId(BigDecimal.valueOf(SeqUtil.nextId()));
            if(license){
                auth.setClientCode(LicenseUtil.createClientCode());
            }
            String url = authSystemUrl+ PREFIX + auth.getClientCode();
            auth.setAuthImg(LicenseUtil.createQRCode(url));
            auth.setCjsj(new Date());
            auth.setCjrId(userId);


            authMapper.insert(auth);

        }else{
            auth = list.get(0);
        }

        List<IomCampLicenseAuthServer> serverList = authServerMapper.selectList(null);
        if(serverList == null || serverList.size() == 0){
            //没有记录，新增记录
            List<String> ipList = new ArrayList<>();
            ipList.add(IomPlatformParam.authServerIp);

            final BigDecimal authId = auth.getId();
            ipList.forEach(ip->{
                IomCampLicenseAuthServer server = new IomCampLicenseAuthServer();
                server.setServerIp(ip);
                server.setAuthId(authId);
                server.setCjsj(new Date());
                server.setCjrId(userId);
                server.setXgsj(new Date());
                server.setXgrId(userId);
                server.setId(BigDecimal.valueOf(SeqUtil.nextId()));

                authServerMapper.insert(server);
            });

            auth.setServerIPList(ipList);

        }else{
            List<String> ipList = serverList.stream().map(server->server.getServerIp()).collect(Collectors.toList());
            auth.setServerIPList(ipList);
        }

        return auth;
    }


    @Override
    public String getClientCode() {
        BigDecimal userId = BigDecimal.valueOf(Double.parseDouble(TokenUtils.getTokenUserId()));
        String clientCode = LicenseUtil.createClientCode();
        IomCampLicenseAuth auth = get(null);
        auth.setClientCode(clientCode);
        String url = authSystemUrl+ PREFIX + auth.getClientCode();
        auth.setAuthImg(LicenseUtil.createQRCode(url));
        auth.setXgsj(new Date());
        auth.setXgrId(userId);

        authMapper.updateByPrimaryKey(auth);
        return clientCode;
    }


    @Override
    public boolean register(IomCampLicenseAuth record) {

        BigDecimal userId = BigDecimal.valueOf(Double.parseDouble(TokenUtils.getTokenUserId()));

        IomCampLicenseAuth authInfo = transaction(record.getAuthCode());

        if(authInfo == null){
            return false;
        }

        IomCampLicenseAuth auth = get(null);


        auth.setClientCode(LicenseUtil.createClientCode());
        auth.setAuthCode(record.getAuthCode());
        auth.setAuthCabinetNum(authInfo.getAuthCabinetNum());
        auth.setAuthDoorNum(authInfo.getAuthDoorNum());

        String url = authSystemUrl+ PREFIX + auth.getClientCode();
        auth.setAuthImg(LicenseUtil.createQRCode(url));
        auth.setAuthSceneNum(authInfo.getAuthSceneNum());
        auth.setAuthUserNum(authInfo.getAuthUserNum());
        auth.setAuthViewNum(authInfo.getAuthViewNum());
        auth.setValidDate(authInfo.getValidDate());
        auth.setRegisterTime(authInfo.getRegisterTime());
        auth.setRegisterMan(TokenUtils.getTokenUserMc());


        LocalDate startDate = dateToLocalDate(auth.getRegisterTime());
        LocalDate endDate = dateToLocalDate(auth.getValidDate());
        int dayCount = Long.valueOf(endDate.toEpochDay() - startDate.toEpochDay() + 1).intValue();
        auth.setDayCount(dayCount);

        auth.setIsBlocked(IomCampLicenseAuth.BLOCKED.N.name());
        auth.setXgsj(new Date());
        auth.setXgrId(userId);

        auth.setAllowModuleName(authInfo.getAllowModuleName());

        return authMapper.updateByPrimaryKey(auth) > 0;
    }

    private LocalDate dateToLocalDate(Date date){
        if(date == null){
            return LocalDate.now();
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private LocalDateTime dateToLocalDateTime(Date date){
        if(date == null){
            return LocalDateTime.now();
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    private IomCampLicenseAuth transaction(String authCode){

        Map<String, Object> authMap = LicenseUtil.decodeAuthCode(authCode);
        if(authMap == null){
            return null;
        }
        return JSONObject.parseObject(JSONObject.toJSONString(authMap),IomCampLicenseAuth.class);
    }

}
