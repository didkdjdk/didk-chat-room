package com.didk.controller;

import cn.hutool.core.map.MapUtil;
import com.didk.cloud.CloudStorageConfig;
import com.didk.cloud.OssFactory;
import com.didk.commons.log.annotation.LogOperation;
import com.didk.commons.tools.page.PageData;
import com.didk.commons.tools.utils.JsonUtils;
import com.didk.commons.tools.utils.Result;
import com.didk.commons.tools.validator.ValidatorUtils;
import com.didk.dto.OssDTO;
import com.didk.dto.UploadDTO;
import com.didk.entity.OssEntity;
import com.didk.exception.ModuleErrorCode;
import com.didk.remote.ParamsRemoteService;
import com.didk.service.OssService;
import com.didk.utils.ModuleConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传
 *
 * @author jiujingz@126.com
 */
@RestController
@RequestMapping("file")
@Tag(name = "文件上传")
public class OssController {
    @Resource
    private OssService ossService;
    @Resource
    private ParamsRemoteService paramsRemoteService;

    private final static String KEY = ModuleConstant.CLOUD_STORAGE_CONFIG_KEY;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('sys:oss:all')")
    public Result<PageData<OssEntity>> page(@Parameter(hidden = true) @RequestParam Map<String, Object> params) {
        PageData<OssEntity> page = ossService.page(params);

        return new Result<PageData<OssEntity>>().ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "根据id获取详细信息")
    @PreAuthorize("hasAuthority('sys:oss:all')")
    public Result<OssDTO> selectInfoById(@Parameter(hidden = true) @RequestParam String id) {
        OssEntity ossEntity = ossService.selectById(id);
        // 类型转换
        OssDTO ossDTO = new OssDTO();
        BeanUtils.copyProperties(ossEntity, ossDTO);
        return new Result<OssDTO>().ok(ossDTO);
    }

    @GetMapping("info")
    @Operation(summary = "云存储配置信息")
    @PreAuthorize("hasAuthority('sys:oss:all')")
    public Result<CloudStorageConfig> info() {
        CloudStorageConfig config = paramsRemoteService.getValueObject(KEY, CloudStorageConfig.class);

        return new Result<CloudStorageConfig>().ok(config);
    }

    @PostMapping
    @Operation(summary = "保存云存储配置信息")
    @LogOperation("保存云存储配置信息")
    @PreAuthorize("hasAuthority('sys:oss:all')")
    public Result saveConfig(@RequestBody CloudStorageConfig config) throws Exception {
        // 校验类型
        ValidatorUtils.validateEntity(config);

        paramsRemoteService.updateValueByCode(KEY, JsonUtils.toJsonString(config));

        return new Result();
    }

    @PostMapping("upload")
    @Operation(summary = "上传文件")
    public Result<UploadDTO> upload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return new Result<UploadDTO>().error(ModuleErrorCode.UPLOAD_FILE_EMPTY);
        }

        // 上传文件
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        String url = OssFactory.build().uploadSuffix(file.getBytes(), extension);

        // 保存文件信息
        OssEntity ossEntity = new OssEntity();
        ossEntity.setUrl(url);
        ossEntity.setCreateDate(new Date());
        ossService.insert(ossEntity);

        // 文件信息
        UploadDTO dto = new UploadDTO();
        dto.setUrl(url);
        dto.setSize(file.getSize());

        return new Result<UploadDTO>().ok(dto);
    }

    @PostMapping("tinymce/upload")
    @Operation(summary = "TinyMCE上传文件")
    public Map<String, String> tinymceUpload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return MapUtil.newHashMap();
        }

        // 上传文件
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String url = OssFactory.build().uploadSuffix(file.getBytes(), extension);

        // 保存文件信息
        OssEntity ossEntity = new OssEntity();
        ossEntity.setUrl(url);
        ossEntity.setCreateDate(new Date());
        ossService.insert(ossEntity);

        Map<String, String> data = new HashMap<>(1);
        data.put("location", url);

        return data;
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @LogOperation("删除")
    @PreAuthorize("hasAuthority('sys:oss:all')")
    public Result delete(@RequestBody Long[] ids) {
        ossService.deleteBatchIds(Arrays.asList(ids));

        return new Result();
    }

}