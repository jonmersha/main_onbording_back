package com.coopay.soap.soapBody.onboarding;

import com.coopay.onboarding.model.ImageCapture;
import com.coopay.security.RequestCredentials;

public class ImageCaptureSoap {

    public String body(ImageCapture imageCapture, RequestCredentials requestCredentials){
String soapBopdy= "<soapenv:Envelope \n" +
                "xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
                "xmlns:cus=\"http://temenos.com/CUSTONBRD\"\n" +
                "xmlns:imd=\"http://temenos.com/IMDOCUMENTIMAGECAPTUREAPI\">\n" +
                "\t<soapenv:Header/>\n" +
                "\t<soapenv:Body>\n" +
                "\t<cus:IMAGECAPURE>\n" +
                "\t\t<WebRequestCommon>\n" +
                "\t\t\t<company>"+requestCredentials.getBranchCode()+"</company>\n" +
                "\t\t\t<password>"+requestCredentials.getPassword()+"</password>\n" +
                "\t\t\t<userName>"+requestCredentials.getUserName()+"</userName>\n" +
                "\t\t</WebRequestCommon>\n" +
                "\t\t<OfsFunction>\n" +
                "\t\t\t<messageId>"+imageCapture.getMessageId()+"</messageId>\n" +
                "\t\t</OfsFunction>\n" +
                "\t\t<IMDOCUMENTIMAGECAPTUREAPIType id=\"\">\n" +
                "\t\t\t<imd:ImageType>"+imageCapture.getImageType()+"</imd:ImageType>\n" +
                "\t\t\t<imd:Application>ACCOUNT</imd:Application>\n" +
                "\t\t\t<imd:AccountNo>"+imageCapture.getAccountNumber()+"</imd:AccountNo>\n" +
                "\t\t\t<imd:ShortDescription>"+imageCapture.getShortDescription()+"</imd:ShortDescription>\n" +
                "\t\t\t<imd:gDESCRIPTION g=\"1\">\n" +
                "\t\t\t\t<imd:Description>"+imageCapture.getDescription()+"</imd:Description>\n" +
                "\t\t\t</imd:gDESCRIPTION>\n" +
                "\t\t<imd:MultiMediaType>IMAGE</imd:MultiMediaType>\n" +
                "\t\t</IMDOCUMENTIMAGECAPTUREAPIType>\n" +
                "\t</cus:IMAGECAPURE>\n" +
                "\t</soapenv:Body>\n" +
                "</soapenv:Envelope>\n";
        System.out.println(soapBopdy);
        return  soapBopdy;

    }
}
