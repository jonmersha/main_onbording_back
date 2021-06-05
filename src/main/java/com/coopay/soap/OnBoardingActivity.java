package com.coopay.soap;
import com.coopay.onboarding.entity.BankOfficer;
import com.coopay.onboarding.model.AccountCreate;
import com.coopay.onboarding.model.BankCustomer;
import com.coopay.onboarding.model.ImageCapture;
import com.coopay.onboarding.model.ImageUpload;
import com.coopay.security.RequestCredentials;
import com.coopay.soap.apioperation.*;
import com.coopay.soap.apioperation.OnBoardingResponseProcessor;
import com.coopay.soap.soapBody.onboarding.*;
import com.coopay.utility.RandomNumber;
import com.sun.xml.internal.ws.addressing.WsaClientTube;


import javax.xml.soap.SOAPMessage;


public class OnBoardingActivity {
	
    //======================Customer and Account Creation
    public static CustomerCreateResponse customerCreateResponse(BankCustomer bankCustomer, BankOfficer officer){
        //getting

        //RequestCredentials requestCredentials=new RequestCredentials("ET0010001","MMTUSER1","123123");

        RequestCredentials requestCredentials=new RequestCredentials(officer.getBranchCode(),officer.getUserName(),officer.getCorePass());
        String customerCreateBody=new CustomerCreateSoap()
                .CustomerCreateBody(requestCredentials,bankCustomer);
        System.out.println(customerCreateBody);
        SOAPMessage soapMessage=new CallRemoteAPI()
                .soapRequest(customerCreateBody);
        CustomerCreateResponse customerinformation=new ResponseProcessor()
                .customerCreationResponseToObject(soapMessage);

            if(customerinformation!=null)
            if(customerinformation.getMessages().equals("Customer Created")){
                try{
                    customerinformation.setAuthStatus("Not Authorized");

                    customerinformation.setShortName(bankCustomer.getFirstName());
                    customerinformation.setMiddleName(bankCustomer.getMiddleName());
                    customerinformation.setLastName(bankCustomer.getLastName());
                    customerinformation.setEmail(bankCustomer.getEmailId());
                    customerinformation.setMobileNumber(bankCustomer.getMobilePhoneNumber());
                    String authorizationMessage=new CustomerAuthorizeSoapBody().customerAuthorizationSoapBody(customerinformation.getTransactionId(),requestCredentials);
                    soapMessage=new RequestProcess().soapRequest(authorizationMessage);
                    String[] statStatus=new OnBoardingResponseProcessor().customerAuthorization(soapMessage);
                    if(statStatus[0].equals("Success")){
                        customerinformation.setAuthStatus("Authorized");
                    }
                    else{
                        customerinformation.setAuthStatus("Not Authorized:"+statStatus[1]);
                        customerinformation.setMessages(statStatus[1]);

                    }
                }catch (Exception ex){

                }
            }

            return  customerinformation;

    }
    public static AccountCreateResponse createBankAccount(AccountCreate accountCreate){
            RequestCredentials requestCredentials=new RequestCredentials("ET0010001","MMTUSER1","123123");
            String soapBody=new BankAccountCreateSoap().body(accountCreate,requestCredentials);
             //System.out.println(soapBody);
            SOAPMessage soapMessage=new RequestProcess().soapRequest(soapBody);
            AccountCreateResponse response=new OnBoardingResponseProcessor().createAccount(soapMessage);
            //Account authorization unit
            if(response.getMessages().equals("Account Created")){
                try{
                    String accountAuthSoapBOdy=new AccountAuthorizeSoap().body(response.getTransactionId(),requestCredentials);
                    SOAPMessage   soapAuthMessage=new RequestProcess().soapRequest(accountAuthSoapBOdy);
                    String[] statStatus=new OnBoardingResponseProcessor().accountAuthorization(soapAuthMessage);
                    //System.out.println(statStatus.length);
                    if(statStatus[0].equals("Success")){
                        response.setAuthStatus("Authorized");
                    }
                    else{
                        response.setAuthStatus("Not Authorized:"+statStatus[3]);
                        response.setMessages(statStatus[3]);
                    }
                }catch (Exception ex){
                }
            }
            return  response;
    }




    //===========================Image Acpturing
    public static ImageCaptureResponse imageCapture(ImageCapture imageCapture){
            RequestCredentials requestCredentials=new RequestCredentials("ET0010001","MMTUSER1","123123");
            String soapBody=new ImageCaptureSoap().body(imageCapture,requestCredentials);
             //System.out.println(soapBody);
             
            SOAPMessage soapMessage=new RequestProcess().soapRequest(soapBody);
            ImageCaptureResponse response=new OnBoardingResponseProcessor().imageCapture(soapMessage);

      //  System.out.println(response.getStatus());    
            
        if(response.getStatus().equals("Success")){
        	
            //Call image upload function
            ImageUpload imageUpload=new ImageUpload();
            imageUpload.setImageName(imageCapture.getImageName());
            imageUpload.setTransactionId(response.getTransactionId());
            
            String messageID="UP"+ RandomNumber.getRandom();
            imageUpload.setImageId(response.getTransactionId());
            
//            System.out.println(response.getTransactionId());
            
            
            imageUpload.setMessageId(messageID);
            try{
                ImageUploadResponse uploadResponse= OnBoardingActivity.imageUpload(imageUpload);
                if(!uploadResponse.getStatus().equals("Success")){
                    response.setStatus("Image uploading Error");
                    return  response;
                }
            }
            catch (Exception e){
            	
            	System.out.println(e.getLocalizedMessage());

            }
        }

            return  response;
    }




    public static ImageUploadResponse imageUpload(ImageUpload imageUpload){
            RequestCredentials requestCredentials=new RequestCredentials("ET0010001","MMTUSER1","123123");
            String soapBody=new ImageUploadSoap().body(imageUpload,requestCredentials);
            SOAPMessage soapMessage=new RequestProcess().soapRequest(soapBody);
            ImageUploadResponse response=new OnBoardingResponseProcessor().imageUpload(soapMessage);
            
            if(response.getStatus().equals("Success")){
                soapBody=new ImageUploadAuthorizeSoap().body(response.getImageId(),requestCredentials);
                SOAPMessage spm=new RequestProcess().soapRequest(soapBody);
                String status =new OnBoardingResponseProcessor().imageUploadAuthorization(soapMessage);
                
                if(!status.equals("Success")){
                    response.setErrorMessage("Image upload Authorizations Failed");
                }

            }
            return  response;
    }
    
    
    public static String imageUploadAuthorized(ImageUploadResponse responses){
            RequestCredentials requestCredentials=new RequestCredentials("ET0010001","MMTUSER1","123123");
            String soapBody=new ImageUploadAuthorizeSoap().body(responses.getImageId(),requestCredentials);
             //System.out.println(soapBody);
            
             
             SOAPMessage soapMessage=new RequestProcess().soapRequest(soapBody);
             return new OnBoardingResponseProcessor().imageUploadAuthorization(soapMessage);


    }

}
