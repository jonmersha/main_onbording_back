package com.coopay.soap.soapBody.onboarding;

import com.coopay.onboarding.model.ImageUpload;
import com.coopay.security.RequestCredentials;

public class ImageUploadSoap {
    public String body(ImageUpload imageUpload, RequestCredentials requestCredentials){
        String soapBopdy= "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
                "xmlns:cus=\"http://temenos.com/CUSTONBRD\"\n" +
                "xmlns:imd=\"http://temenos.com/IMDOCUMENTUPLOADCAPTURE\">\n" +
                "\t<soapenv:Header/>\n" +
                "\t<soapenv:Body>\n" +
                "\t\t<cus:IMAGEUPLOAD>\n" +
                "\t\t\t<WebRequestCommon>\n" +
                "\t\t\t\t<company>"+requestCredentials.getBranchCode()+"</company>\n" +
                "\t\t\t\t<password>"+requestCredentials.getPassword()+"</password>\n" +
                "\t\t\t\t<userName>"+requestCredentials.getUserName()+"</userName>\n" +
                "\t\t\t</WebRequestCommon>\n" +
                "\t\t\t\n" +
                "\t\t\t<OfsFunction>\n" +
                "\t\t\t\t<messageId>"+imageUpload.getImageId()+"</messageId>\n" +
                "\t\t\t</OfsFunction>\n" +
                "\t\t\t\n" +
                "\t\t\t<IMDOCUMENTUPLOADCAPTUREType id=\""+imageUpload.getTransactionId()+"\">\n" +
                "\t\t\t\t<imd:ImageId>"+imageUpload.getImageId()+"</imd:ImageId>\n" +
                "\t\t\t\t<imd:UploadImage>"+imageUpload.getImageName()+"</imd:UploadImage>\n" +
                "\t\t\t</IMDOCUMENTUPLOADCAPTUREType>\n" +
                "\t\t</cus:IMAGEUPLOAD>\n" +
                "\t</soapenv:Body>\n" +
                "</soapenv:Envelope>";
        System.out.println(soapBopdy);
        return  soapBopdy;
    }
}
