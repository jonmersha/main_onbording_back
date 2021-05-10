package com.coopay.service;

import com.coopay.onboarding.entity.BankOfficer;
import com.coopay.onboarding.entity.CustomerAccount;
import com.coopay.onboarding.model.AccountCreate;
import com.coopay.onboarding.model.BankCustomer;
import com.coopay.onboarding.model.ImageCapture;
import com.coopay.onboarding.model.ImageUpload;
import com.coopay.repository.BankOfficerRepo;
import com.coopay.repository.CustomerAccountRepo;
import com.coopay.soap.OnBoardingActivity;
import com.coopay.soap.apioperation.AccountCreateResponse;
import com.coopay.soap.apioperation.CustomerCreateResponse;
import com.coopay.soap.apioperation.ImageCaptureResponse;
import com.coopay.utility.CommonMessage;
import com.coopay.utility.RandomNumber;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

@Service
public class CustomerService {
	
	private static final String REMOTE_HOST = "localhost";
	private static final String USERNAME = "yohannesmersha";
    private static final String PASSWORD = "4321";
    private static final int REMOTE_PORT = 22;
    private static final int SESSION_TIMEOUT = 10000;
    private static final int CHANNEL_TIMEOUT = 5000;

    @Autowired
    CustomerAccountRepo customerAccountRepo;
    
    @Autowired
    BankOfficerRepo bankOfficerRepo;
    

    public void saveCustomer(CustomerCreateResponse customerAccount) {
    	
        customerAccountRepo.save(new CustomerAccount(customerAccount));
    }
    public CustomerAccount getCustomer(String mobileNumber) {
       return customerAccountRepo.getCustomerByPhone(mobileNumber);
    }

    public CustomerCreateResponse createCustomer(BankCustomer bankCustomer) {
    	
    	BankOfficer bankOfficer=bankOfficerRepo
    			.getRequestCredentials(bankCustomer.getCashierCode(),bankCustomer.getCashierPassword());
    	System.out.println("Core Password"+bankOfficer.getCorePass());
    	System.out.println("Core UserName"+bankOfficer.getUserName());

    	if(bankOfficer==null) {
    		return new CustomerCreateResponse();
    	}
        CustomerCreateResponse customerAccount= OnBoardingActivity.customerCreateResponse(bankCustomer,bankOfficer);
        if(customerAccount.getStatus().equals("Success")){
            System.out.println("Customer Creation is successfull");
            CustomerAccount ca= new CustomerAccount(customerAccount);
            ca.setAccountCreator(bankCustomer.getCashierCode());
            
            customerAccountRepo.save(ca);
        }
        else{
            System.out.println("Customer Creation failed");
        }

        return customerAccount;

    }
    
    public String saveImmageToServer(MultipartFile file) {



    	InputStream inputStream=null;
    	try {
			inputStream =file.getInputStream();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        		
       String remoteFile = "/Users/yohannesmersha/Desktop/auditPlaninig/"+file.getName()+"."+file.getContentType();        
        Session jschSession = null;
    try {
                JSch jsch = new JSch();
                jsch.setKnownHosts("~/.ssh/known_hosts");
                jschSession = jsch.getSession(USERNAME, REMOTE_HOST, REMOTE_PORT);
                jschSession.setPassword(PASSWORD);
                jschSession.connect(SESSION_TIMEOUT);
                Channel sftp = jschSession.openChannel("sftp");
                sftp.connect(CHANNEL_TIMEOUT);
                ChannelSftp channelSftp = (ChannelSftp) sftp;
                channelSftp.put(inputStream, remoteFile);
                channelSftp.exit();
                
                return "Image Uploaded to server Successfully";
            } catch (JSchException | SftpException e) {
                e.printStackTrace();
                return e.getLocalizedMessage();

            } finally {
                if (jschSession != null) {
                    jschSession.disconnect();
                }
            }

         //   System.out.println("Done");
    	
    }

    public List<CustomerAccount> getAllCustomerAccount() {
        return customerAccountRepo.getAllCustomer();

    }

    public CommonMessage createAccountForCustomer(AccountCreate accountCreate) {
        CustomerAccount customerAccount=customerAccountRepo.getCustomerByTransactionId(accountCreate.getCustomerID());
        if(customerAccount.getAccountNumber()!=null)
        	return new CommonMessage().errorMessage("The Customer Alreaagy Have Bank Account");

        AccountCreateResponse accountCreateResponse=OnBoardingActivity.createBankAccount(accountCreate);
        try{
            if(accountCreateResponse.getMessages().equals("Account Created")){
            customerAccount.setAccountNumber(accountCreateResponse.getAccountNumber());
            customerAccount.setAccountCategory(accountCreate.getProductCode());
          //  customerAccount.setAccountCreator(accountCreate.);
            customerAccountRepo.save(customerAccount);
            return new CommonMessage().successMessage("Customer Account Created Successfully");
            }
            else{
                return new CommonMessage().errorMessage(accountCreateResponse.getMessages());

            }
        }catch (Exception e){
            return new CommonMessage().errorMessage(e.getLocalizedMessage());
        }
    }

    public CommonMessage imageUpload(ImageUpload imageUpload) {
        return new CommonMessage().successMessage("Image Capture Done Properly");
    }
    
    
    public CommonMessage imageCapture(ImageCapture imageCapture) {
       
    	String messageID="CU"+ RandomNumber.getRandom();
        CustomerAccount customerAcc=customerAccountRepo.getCustomerByAccount(imageCapture.getAccountNumber());

        if(customerAcc!=null)
    if(imageCapture.getImageType().equals("SIGNATURES"))
        imageCapture.setImageName(customerAcc.getSignatureImageName());
    else
        imageCapture.setImageName(customerAcc.getPersonImageName());

        imageCapture.setMessageId(messageID);

        System.out.println(imageCapture.getImageName());

        try{
            ImageCaptureResponse response=OnBoardingActivity.imageCapture(imageCapture);
            if(response.getStatus().equals("Success")){
                //get customer Account
              //  CustomerAccount customerAccount=customerAccountRepo.getCustomerByAccount(imageCapture.getAccountNumber());
                if(customerAcc==null)
                    return new CommonMessage().successMessage("Account Not Found");
                if(imageCapture.getImageType().equals("SIGNATURES")){
                	
                	imageCapture.setImageName(customerAcc.getSignatureImageName());
                    customerAcc.setCustomerImmageUploaded(true);
                	
                }
                if(imageCapture.getImageType().equals("PHOTOS")){
                	
                	imageCapture.setImageName(customerAcc.getPersonImageName());
                    customerAcc.setCustomerImmageUploaded(true);
                	
           
                }
                    customerAccountRepo.save(customerAcc);

                System.out.println("");
                return new CommonMessage().successMessage("Image Capture completed");
            }
            else{
                return new CommonMessage().errorMessage(response.getErrorMessage());

            }
        }catch (Exception e){
            return new CommonMessage().errorMessage(e.getLocalizedMessage());


        }

    }
    
	public void saveImage(String transationId,String fileName, String type) {
		
		CustomerAccount account=customerAccountRepo.getCustomerByTransactionId(transationId);
		if(account!=null) {
		
		if(type.equals("SIGNATURES")) {
			account.setSignatureImageName(fileName);
			
		}
		if(type.equals("PHOTOS")) {
			account.setPersonImageName(fileName);
		}
		customerAccountRepo.save(account);
		}
		
		
	}
}

