package com.coopay.soap;

import com.coopay.utility.PathString;

import javax.xml.soap.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class CallRemoteAPI {



    public SOAPMessage soapRequest(String soapMessage){
        SOAPConnectionFactory soapConnectionFactory = null;
        try {
            soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
            SOAPMessage responseMassage=soapConnection.call(getSoapMessageFromString(soapMessage), PathString.ON_BOARDING_URL);
            return responseMassage;
        } catch (SOAPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public SOAPMessage getSoapMessageFromString(String xml) throws SOAPException, IOException {
        MessageFactory factory = MessageFactory.newInstance();
        SOAPMessage message = factory.createMessage(new MimeHeaders(),
                new ByteArrayInputStream(xml.getBytes(Charset.forName("UTF-8"))));
        return message;
    }

}
