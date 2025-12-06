package io.didk.service;


import com.didk.commons.mybatis.service.CrudService;
import com.didk.commons.tools.utils.Result;
import io.didk.dto.SysMailTemplateDTO;
import io.didk.entity.SysMailTemplateEntity;

/**
 * 邮件模板
 *
 * @author jiujingz@126.com
 */
public interface SysMailTemplateService extends CrudService<SysMailTemplateEntity, SysMailTemplateDTO> {

    /**
     * 发送邮件
     * @param id           邮件模板ID
     * @param mailTo       收件人
     * @param mailCc       抄送
     * @param params       模板参数
     */
    boolean sendMail(Long id, String mailTo, String mailCc, String params) throws Exception;

    /**
     * @param mailTo 收件人
     * @return
     * @throws Exception
     */
    Result sendMail(String mailTo) throws Exception;
}