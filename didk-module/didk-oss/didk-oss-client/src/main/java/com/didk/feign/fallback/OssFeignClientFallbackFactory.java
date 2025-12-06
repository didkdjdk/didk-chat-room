package com.didk.feign.fallback;

import com.didk.commons.tools.utils.Result;
import com.didk.feign.OssFeignClient;
import com.didk.dto.OssDTO;
import com.didk.dto.UploadDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * OSS FallbackFactory
 *
 * @author jiujingz@126.com
 */
@Slf4j
@Component
public class OssFeignClientFallbackFactory implements FallbackFactory<OssFeignClient> {

    @Override
    public OssFeignClient create(Throwable throwable) {
        log.error("{}", throwable);
        return new OssFeignClient() {
            @Override
            public Result<UploadDTO> upload(MultipartFile file) {
                return new Result<>();
            }

            @Override
            public Result<OssDTO> selectInfoById(String id) {
                return new Result<>();
            }
        };
    }
}
