package com.coopay.soap;

import com.coopay.soap.apioperation.*;
import org.w3c.dom.Node;

import javax.xml.soap.SOAPMessage;

public class ResponseProcessor {

//    public CustomerCreateResponse customerCreationResponseToObjectccccc(SOAPMessage soapMessage) {
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        try {
//            soapMessage.writeTo(out);
//            String strMsg = new String(out.toByteArray());
//            JSONObject data = XML.toJSONObject(strMsg);
//            System.out.println(data);
//           ObjectMapper mapper = new ObjectMapper();
//            CRResponse customerCreateResponse= mapper.readValue(data.toString(),CRResponse.class);
//            String Status= customerCreateResponse.getSEnvelope().getSBody().getNs12CUSTCREATEResponse().getStatus().getSuccessIndicator();
//           // System.out.println(Status);
//
//            Ns12CUSTCREATEResponse createResponse=customerCreateResponse.getSEnvelope().getSBody().getNs12CUSTCREATEResponse();
//            if(createResponse.getStatus().getSuccessIndicator().equals("Success")){
//                CustomerCreateResponse response=new CustomerCreateResponse();
//
//                CUSTOMERType customerType=createResponse.getCUSTOMERType();
//
//                response.setEmail(customerType.getNs6GPHONE1().getNs6MPHONE1().getNs6EMAIL1());
//            }
//
//
//
//        } catch (SOAPException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return new CustomerCreateResponse();
//    }
    public CustomerCreateResponse customerCreationResponseToObject(SOAPMessage soapMessage) {
        try {
            Node node = new XMLNodeElement().getSoapBody(soapMessage);
            CustomerCreateResponse customerCreateResponse = new CustomerCreateResponse();
            String status = node.getChildNodes().item(0).getChildNodes().item(2).getChildNodes().item(0).getNodeValue();
            String transactionID = node.getChildNodes().item(0).getChildNodes().item(0).getChildNodes().item(0).getNodeValue();
            String messageId = node.getChildNodes().item(0).getChildNodes().item(1).getChildNodes().item(0).getNodeValue();
            customerCreateResponse.setStatus(status);
            customerCreateResponse.setTransactionId(transactionID);
            customerCreateResponse.setMessageId(messageId);


            if (status.equals("Success")) {
                String application = node.getChildNodes().item(0).getChildNodes().item(3).getChildNodes().item(0).getNodeValue();
                String shortname = node.getChildNodes().item(1).getChildNodes().item(1).getChildNodes().item(0).getChildNodes().item(0).getNodeValue();
                String fullName = node.getChildNodes().item(1).getChildNodes().item(2).getChildNodes().item(0).getChildNodes().item(0).getNodeValue();
                customerCreateResponse.setShortName(shortname);
                customerCreateResponse.setFullName(fullName);
                customerCreateResponse.setMessages("Customer Created");
            } else {
                String message = node.getChildNodes().item(0).getChildNodes().item(4).getChildNodes().item(0).getNodeValue();
                customerCreateResponse.setMessages(message);
                // System.out.println(message);

            }

            //System.out.println(status);
            return customerCreateResponse;
        }catch (Exception ex){
//            System.out.println(ex.getLocalizedMessage());
            CustomerCreateResponse resp=new CustomerCreateResponse();
            resp.setStatus(ex.getLocalizedMessage());
            return  resp;
        }
    }
    public AccountCreateResponse createAccount(SOAPMessage soapMessage){
        Node node=new XMLNodeElement().getSoapBody(soapMessage);
        String[] statusCode=getStatusCode(node);
        AccountCreateResponse response=new AccountCreateResponse();
        response.setAccountNumber(statusCode[1]);
        response.setStatus(statusCode[0]);
        response.setMessages(statusCode[3]);
        response.setAuthStatus("Not Authorized");
        response.setTransactionId(statusCode[1]);
        if(statusCode[0].equals("Success")){
            response.setMessages("Account Created");
            // response.setMessages();
        }
        return response;
    }
    public ImageUploadResponse imageUpload(SOAPMessage soapMessage){
        Node node=new XMLNodeElement().getSoapBody(soapMessage);
        ImageUploadResponse response=new ImageUploadResponse();

        String[] uploadResponse=getStatusCode(node);
        response.setImageId(uploadResponse[1]);
        response.setStatus(uploadResponse[0]);
        response.setErrorMessage(uploadResponse[3]);
        response.setMessageID(uploadResponse[2]);
        return response;
    }
    public ImageCaptureResponse imageCapture(SOAPMessage soapMessage){

        Node node=new XMLNodeElement().getSoapBody(soapMessage);
        ImageCaptureResponse response=new ImageCaptureResponse();
        String[] uploadResponse=getStatusCode(node);
        response.setTransactionId(uploadResponse[1]);
        response.setStatus(uploadResponse[0]);
        response.setErrorMessage(uploadResponse[3]);
        response.setMessageId(uploadResponse[2]);
        return response;
    }

    public String[] customerAuthorization(SOAPMessage soapMessage) {
        Node node=new XMLNodeElement().getSoapBody(soapMessage);

        String[] messages={"",""};
        messages[0]=node.getChildNodes().item(0).getChildNodes().item(2).getChildNodes().item(0).getNodeValue();
        if(messages[0].equals("T24Error")){
            messages[1]=node.getChildNodes().item(0).getChildNodes().item(4).getChildNodes().item(0).getNodeValue();
        }
        return messages;
    }
    public String[] accountAuthorization(SOAPMessage soapMessage) {
        Node node=new XMLNodeElement().getSoapBody(soapMessage);
        String[] statusCode=getStatusCode(node);

        System.out.println("statusCode[0]");

        if(statusCode[0].equals("Success")){
            statusCode[3]="Account Authorized";
        }
        return statusCode;
    }
    public String imageUploadAuthorization(SOAPMessage soapMessage) {
        Node node=new XMLNodeElement().getSoapBody(soapMessage);
        String status=node.getChildNodes().item(0).getChildNodes().item(2).getChildNodes().item(0).getNodeValue();;
        return status;
    }

    public String[] getStatusCode(Node node){
        String status=node.getChildNodes().item(0).getChildNodes().item(2).getChildNodes().item(0).getNodeValue();
        String transactionID=node.getChildNodes().item(0).getChildNodes().item(0).getChildNodes().item(0).getNodeValue();
        String messageId="";
        String errorMessage="";

        if(status.equals("T24Error")){
            errorMessage=node.getChildNodes().item(0).getChildNodes().item(4).getChildNodes().item(0).getNodeValue();
        }
        else{
            try{
                messageId=node.getChildNodes().item(0).getChildNodes().item(1).getChildNodes().item(0).getNodeValue();
            }catch (Exception ex){

            }

        }
        String[] statusCode={status,transactionID,messageId,errorMessage};
        return statusCode;
    }
}
