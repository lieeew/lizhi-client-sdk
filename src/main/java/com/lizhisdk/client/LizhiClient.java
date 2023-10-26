package com.lizhisdk.client;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.lizhisdk.common.BaseResponse;
import com.lizhisdk.model.DevChatRequest;
import com.lizhisdk.model.DevChatResponse;
import org.springframework.beans.BeanUtils;
import java.util.HashMap;
import java.util.Map;
import static com.lizhisdk.utils.SignUtils.*;

/**
 * @author leikooo
 * @Description
 */
public class LizhiClient {
    private static final String HOST = "http://localhost:8090/api/dev";
    private String accessKey;
    private String secretKey;

    public LizhiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    /**
     * 对话
     *
     * @param devChatRequest
     * @return
     */
    public BaseResponse<DevChatResponse> doChat(DevChatRequest devChatRequest) {
        String url = HOST + "/chat";
        // todo 修改传递给 gateway 的数据
        DevChatRequest devChatRequestOne = new DevChatRequest();
        BeanUtils.copyProperties(devChatRequest, devChatRequestOne);
        devChatRequestOne.setUserAccessKey(null);
        devChatRequestOne.setUserSecretKey(null);
        String json = JSONUtil.toJsonStr(devChatRequestOne);
        String result = HttpRequest.post(url)
                .addHeaders(getHeaderMap(devChatRequest))
                .body(json)
                .execute()
                .body();
        TypeReference<BaseResponse<DevChatResponse>> typeRef = new TypeReference<BaseResponse<DevChatResponse>>() {
        };
        return JSONUtil.toBean(result, typeRef, false);
    }

    /**
     * 获取请求头
     *
     * @param devChatRequest 请求参数
     * @return
     */
    private Map<String, String> getHeaderMap(DevChatRequest devChatRequest) {
        Map<String, String> hashMap = new HashMap<>();
        String userAccessKey = devChatRequest.getUserAccessKey();
        String userSecretKey = devChatRequest.getUserSecretKey();
        hashMap.put("userAccessKey", userAccessKey);
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        String encodedBody = SecureUtil.md5(JSONUtil.toJsonStr(devChatRequest));
        hashMap.put("body", encodedBody);
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        hashMap.put("userSign", genSignByAsyEncryption(userSecretKey));
        // 用于在 GateWay 进行解密的公钥
        hashMap.put("PUBLIC_KEY", getPublicKey());
        return hashMap;
    }

    public static void main(String[] args) {
        String accessKey = "你的 accessKey";
        String secretKey = "你的 secretKey";
        LizhiClient yuCongMingClient = new LizhiClient(accessKey, secretKey);
        DevChatRequest devChatRequest = new DevChatRequest();
        devChatRequest.setModelId(1651468516836098050L);
        devChatRequest.setMessage("鱼皮");
        BaseResponse<DevChatResponse> devChatResponseBaseResponse = yuCongMingClient.doChat(devChatRequest);
        System.out.println(devChatResponseBaseResponse);
        DevChatResponse data = devChatResponseBaseResponse.getData();
        if (data != null) {
            String content = data.getContent();
            System.out.println(content);
        }
    }
}
