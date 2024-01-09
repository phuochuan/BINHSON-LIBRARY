package com.library.binhson.userservice.service.impl;

import com.library.binhson.userservice.service.IEmailService;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailServiceImpl implements IEmailService {
    @Value("${email.forgot-password.format-file-path}")
    private String xmlFilePath;
    @Override
    public void sendMailToForgotPassword(String username, String email, String confirmCode) {
        var content= generateContent(username, confirmCode);
    }

    private String generateContent(String username, String confirmCode) {
        try {
            Document document = readXmlFile(xmlFilePath);
            modifyLinkContent(document, "hello_tag", "Xin Chao "+ username);
            modifyLinkContent(document, "reset_password_tag", null, "http://localhost:9999/api/v1/user-service/forgot-password/"+confirmCode);
            log.info(document.asXML());
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
//            linkElement.setText(newText);
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
