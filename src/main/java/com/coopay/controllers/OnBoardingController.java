package com.coopay.controllers;
import com.coopay.imageUpload.storage.StorageService;
import com.coopay.onboarding.entity.CustomerAccount;
import com.coopay.onboarding.model.AccountCreate;
import com.coopay.onboarding.model.BankCustomer;

import com.coopay.onboarding.model.ImageCapture;
import com.coopay.onboarding.model.ImageUpload;
import com.coopay.service.CustomerService;

import com.coopay.soap.apioperation.CustomerCreateResponse;
import com.coopay.utility.CommonMessage;
import com.coopay.utility.RandomNumber;
import com.google.common.io.Files;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

@RestController
//@Controller
public class OnBoardingController {
	
	private static final String REMOTE_HOST = "10.1.245.189";
	private static final String USERNAME = "t24prod";
    private static final String PASSWORD = "t24prod";
//    
//    
//    private static final String REMOTE_HOST = "10.0.19.54";
//    private static final String REMOTE_HOST = "10.1.11.101";
//
//    private static final String USERNAME = "yohannes";
//       private static final String PASSWORD = "Yohannes.1";
//
    
    private static final int REMOTE_PORT = 22;
    private static final int SESSION_TIMEOUT = 10000;
    private static final int CHANNEL_TIMEOUT = 5000;
    
    
    @Autowired
    private final StorageService storageService = null;
    
//   
//	@Autowired
//	public OnBoardingController(StorageService storageService) {
//		this.storageService = storageService;
//	}
//	
	
	
    @Autowired
    CustomerService customerService;

    @PostMapping("/onboarding/customer/create")
    public CustomerCreateResponse createCustomer(@RequestBody BankCustomer bankCustomer){
        System.out.println("Cashier Code = "+bankCustomer.getCashierCode());

        String initial=bankCustomer.getFirstName().substring(0,1).toUpperCase();
       // String

        String mobileNumber=bankCustomer.getMobilePhoneNumber();
        int len=mobileNumber.length();
        if(len>=10)
            mobileNumber=mobileNumber.substring(len-9,len);
        bankCustomer.setMessageID(initial+bankCustomer.getMobilePhoneNumber());
        bankCustomer.setMnemonic(initial+mobileNumber);
   	return    customerService.createCustomer(bankCustomer);
    }

    @PostMapping("/onboarding/account/create")
    public CommonMessage createAccount(@RequestBody AccountCreate accountCreate){

    	String messageID="AC"+RandomNumber.getRandom();
    	accountCreate.setMessageId(messageID);
    	
    	//System.out.println(accountCreate.getProductCode());
        return customerService.createAccountForCustomer(accountCreate);
    }
    
    
   
    @PostMapping("/onboarding/image/capture")
    public CommonMessage imageCapture(@RequestBody ImageCapture imageCapture){
    	return customerService.imageCapture(imageCapture);
    }
    
   

   
//    @PostMapping("/onboarding/image/upload")
//    public CommonMessage imageUpload(@RequestBody ImageUpload imageUpload){
//        return customerService.imageUpload(imageUpload);
//    }
    


    @GetMapping("/onboarding/customer/list")
    public List<CustomerAccount> customerList(){
       return  customerService.getAllCustomerAccount();
    }
    
    @PostMapping("/onboarding/customer/lists")
    public List<CustomerAccount> customerLists(@RequestBody String user){
       return  customerService.getAllCustomerAccount();
    }
    
    
    @GetMapping("/acc/str")
    public AccountCreate accountCreteStructure(){
       return new AccountCreate();

    }

	@PostMapping("/upload")
	public String up(@RequestParam(value="file") MultipartFile file,@RequestParam(value="fileName") String transactionId,@RequestParam(value="photo") String type) {
		String extenssion=Files.getFileExtension(file.getOriginalFilename());
		String fileName="";
		
		//System.out.println(par +" and "+type );
		
		if(type.equals("SIGNATURES")) {
			fileName="SIGN"+transactionId+"."+extenssion;
			//customerService.saveSinature(fileName);
		}
		if(type.equals("PHOTOS")) {
			fileName="PHOTOS"+transactionId+"."+extenssion;
			//customerService.savePhoto(fileName);

		}
		System.out.println(fileName);
		sendtoSftp(file,fileName);
		
		customerService.saveImage(transactionId,fileName,type);
		return "Image Uploaded Succefully";
	}
	
	@GetMapping("/imc")
	public ImageUpload imcap() {
		return new ImageUpload();
		
	}
	
	public void sendtoSftp(MultipartFile file,String fileName) {

//use ByteArrayInputStream to get the bytes of the String and convert them to InputStream.
//String remoteFile = "/u01/WebSphere/AppServer/profiles/Prod1/installedApps/webserver189Node01Cell/CBOCBSTEST_war.ear/CBOCBSTEST.war/im.images/signatures/"+fileName;
//String remoteFile = "/u01/WebSphere/AppServer/profiles/Prod1/installedApps/webserver189Node01Cell/CBOCBSTEST_war.ear/CBOCBSTEST.war/im.images/signatures/"+fileName;

  //String remoteFile = "/home/yohannes/Desktop/"+fileName;
  String remoteFile = "/u01/WebSphere/AppServer/profiles/Prod1/installedApps/webserver189Node01Cell/CBOCBSTEST_war.ear/CBOCBSTEST.war/im.images/photos/"+fileName;

    Session jschSession = null;
try {
	InputStream inputStream;
	
	inputStream = new ByteArrayInputStream(file.getBytes());
	
            JSch jsch = new JSch();
          
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            
           // jsch.setKnownHosts(allowAnyHosts);
            
            jschSession = jsch.getSession(USERNAME, REMOTE_HOST, REMOTE_PORT);
            jschSession.setConfig(config);
            jschSession.setPassword(PASSWORD);
            jschSession.connect(SESSION_TIMEOUT);
            Channel sftp = jschSession.openChannel("sftp");
            sftp.connect(CHANNEL_TIMEOUT);
            ChannelSftp channelSftp = (ChannelSftp) sftp;
            channelSftp.put(inputStream, remoteFile);
            
            
            channelSftp.exit();
        } catch (JSchException | SftpException | IOException e) {
            e.printStackTrace();

        } finally {
            if (jschSession != null) {
                jschSession.disconnect();
            }
        }

        System.out.println("Done");
    }

}
