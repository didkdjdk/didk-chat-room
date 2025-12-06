package com.didk.cloud;

import com.didk.commons.tools.utils.SpringContextUtils;
import com.didk.enums.OssTypeEnum;
import com.didk.remote.ParamsRemoteService;
import com.didk.utils.ModuleConstant;

/**
 * 文件上传Factory
 */
public final class OssFactory {
    private static ParamsRemoteService paramsRemoteService;

    static {
        OssFactory.paramsRemoteService = SpringContextUtils.getBean(ParamsRemoteService.class);
    }

    public static AbstractCloudStorageService build(){
        //获取云存储配置信息
        CloudStorageConfig config = paramsRemoteService.getValueObject(ModuleConstant.CLOUD_STORAGE_CONFIG_KEY, CloudStorageConfig.class);

        if(config.getType() == OssTypeEnum.LOCAL.value()){
            return new LocalCloudStorageService(config);
        }else if(config.getType() == OssTypeEnum.MINIO.value()){
            return new MinioCloudStorageService(config);
        }

        return null;
    }

}