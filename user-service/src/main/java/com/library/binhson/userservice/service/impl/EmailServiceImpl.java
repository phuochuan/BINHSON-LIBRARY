package com.library.binhson.userservice.service.impl;

import com.library.binhson.userservice.service.IEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements IEmailService {
    private static final String TITLE_PORGOT_PASSWORD = "YÊU CẦU ĐẶT LẠI MẬT KHẨU";
    @Value("${email.forgot-password.format-file-path}")
    private String xmlFilePath;
    @Value("${spring.mail.username}")
    private String fromEmail;


    private final JavaMailSender mailSender;
    @Override
    public void sendMailToForgotPassword(String username, String email, String confirmCode) {
        var content= generateContent(username, confirmCode);
        if(Objects.isNull(content))
            return;
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromEmail);
            helper.setTo(email);
            helper.setSubject(TITLE_PORGOT_PASSWORD);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    private String generateContent(String username, String confirmCode) {
        try {
            Document document = readXmlFile(xmlFilePath);
            modifyLinkContent(document, "hello_tag", "Xin Chao "+ username);
            modifyLinkContent(document, "reset_password_tag", null, "http://localhost:9999/api/v1/user-service/forgot-password/"+confirmCode);
            String content=document.asXML();
           return content;

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    private static Document readXmlFile(String filePath) throws DocumentException {
        SAXReader reader = new SAXReader();
        return reader.read(filePath);
    }

    private static void modifyLinkContent(Document document, String id, String newText, String newHref) {
        Element linkElement = findLinkById(document.getRootElement(), id, "a");
        if (linkElement != null) {
            linkElement.attribute("href").setValue(newHref);
        } else {
            log.error("*****");
        }
    }

    private static void modifyLinkContent(Document document, String id, String newText) {
        Element linkElement = findLinkById(document.getRootElement(), id, "h1");
        if (linkElement != null) {
            linkElement.setText(newText);
        } else {
            log.error("*****");
        }
    }

    private static Element findLinkById(Element root, String id,String tag) {
        return (Element) root.selectSingleNode("//"+tag+"[@id='" + id + "']");

    }
}
