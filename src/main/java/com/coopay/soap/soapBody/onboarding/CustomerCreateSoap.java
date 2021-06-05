package com.coopay.soap.soapBody.onboarding;

import com.coopay.onboarding.model.BankCustomer;
import com.coopay.security.RequestCredentials;

public class CustomerCreateSoap {
    public String CustomerCreateBody(RequestCredentials requestCredentials, BankCustomer bankCustomer){

        String customerCreateBody="<soapenv:Envelope\n" +
                "        xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
                "        xmlns:cus=\"http://temenos.com/CUSTONBRD\"\n" +
                "        xmlns:cus1=\"http://temenos.com/CUSTOMERETMBPOC\">\n" +
                "    <soapenv:Header/>\n" +
                "    <soapenv:Body>\n" +
                "        <cus:CUSTCREATE>\n" +
                "            <WebRequestCommon>\n" +
                "                \t<company>"+requestCredentials.getBranchCode()+"</company>\n" +
                "           \t<password>"+requestCredentials.getPassword()+"</password>\n" +
                "            \t<userName>"+requestCredentials.getUserName()+"</userName>\n" +
                "            </WebRequestCommon>\n" +
                "            <OfsFunction>\n" +
                "                <messageId>"+bankCustomer.getMessageID()+"</messageId>\n" +
                "            </OfsFunction>\n" +
                "            <CUSTOMERETMBPOCType id=\"\">\n" +
                "\n" +
                "                <cus1:Mnemonic>"+bankCustomer.getMnemonic()+"</cus1:Mnemonic>\n" +
                "                <cus1:gSHORTNAME g=\"1\">\n" +
                "                    <cus1:ShortName>"+bankCustomer.getFirstName()+"</cus1:ShortName>\n" +
                "                </cus1:gSHORTNAME>\n" +
                "                <cus1:gNAME1 g=\"1\">\n" +
                "                    <cus1:FullName>"+bankCustomer.getFirstName()+"  "+bankCustomer.getMiddleName()+" "+bankCustomer.getFirstName() + "</cus1:FullName>\n" +
                "                </cus1:gNAME1>\n" +
                "                <cus1:gSTREET g=\"1\">\n" +
                "                    <cus1:Street>"+bankCustomer.getStreetName()+"</cus1:Street>\n" +
                "                </cus1:gSTREET>\n" +
                "                <cus1:gLLADDRESS g=\"1\">\n" +
                "                    <cus1:mLLADDRESS m=\"1\">\n" +
                "                        <cus1:sgLLADDRESS sg=\"1\">\n" +
                "                            <cus1:Address s=\"1\">\n" +
                "                                <cus1:Address>"+bankCustomer.getCity()+"</cus1:Address>\n" +
                "                            </cus1:Address>\n" +
                "                        </cus1:sgLLADDRESS>\n" +
                "                    </cus1:mLLADDRESS>\n" +
                "                </cus1:gLLADDRESS>\n" +
                "                <cus1:gTOWNCOUNTRY g=\"1\">\n" +
                "                    <cus1:TownCity>"+bankCustomer.getCity()+"</cus1:TownCity>\n" +
                "                </cus1:gTOWNCOUNTRY>\n" +
                "                <cus1:gPOSTCODE g=\"1\">\n" +
                "                    <cus1:PostCode>"+bankCustomer.getPoBox()+"</cus1:PostCode>\n" +
                "                </cus1:gPOSTCODE>\n" +
                "                <cus1:gCOUNTRY g=\"1\">\n" +
                "                    <cus1:Country>"+bankCustomer.getCountry()+"</cus1:Country>\n" +
                "                </cus1:gCOUNTRY>\n" +
                "\n" +
                "                <cus1:Sector>"+bankCustomer.getSector()+"</cus1:Sector>\n" +
                "                <cus1:AccountOfficer>1</cus1:AccountOfficer>\n" +
                "                <cus1:Industry>"+bankCustomer.getIndustry()+"</cus1:Industry>\n" +
                "                <cus1:Target>"+bankCustomer.getTarget()+"</cus1:Target>\n" +
                "                <cus1:Nationality>"+bankCustomer.getCountry()+"</cus1:Nationality>\n" +
                "                <cus1:CustomerStatus>1</cus1:CustomerStatus>\n" +
                "                <cus1:Residence>"+bankCustomer.getCountry()+"</cus1:Residence>\n" +
                "\n" +
                "\n" +
                "\n" +
                "<!--Customer Identification Card-->\n" +
                "                <cus1:gLEGALID g=\"1\">\n" +
                "                    <cus1:mLEGALID m=\"1\">\n" +
                "                        <cus1:LegalID>"+bankCustomer.getIdNumber()+"</cus1:LegalID>\n" +
                "                        <cus1:DocumentName>"+bankCustomer.getDocumentName()+"</cus1:DocumentName>\n" +
                "                        <cus1:NameonID>Y"+bankCustomer.getNameOnDocument()+" </cus1:NameonID>\n" +
                "                        <cus1:IssueAuthority>"+bankCustomer.getIssueAuthority()+"</cus1:IssueAuthority>\n" +
                "                        <cus1:IssueDate>"+bankCustomer.getIssueDate()+"</cus1:IssueDate>\n" +
                "                        <cus1:ExpirationDate>"+bankCustomer.getExpirationDate()+"</cus1:ExpirationDate>\n" +
                "                    </cus1:mLEGALID>\n" +
                "                </cus1:gLEGALID>\n" +
                "\n" +
                "\n" +
                "                <cus1:Title>"+bankCustomer.getTitle()+"</cus1:Title>\n" +
                "                <cus1:GivenNames>"+bankCustomer.getFirstName()+"</cus1:GivenNames>\n" +
                "                <cus1:FamilyName>"+bankCustomer.getLastName()+"</cus1:FamilyName>\n" +
                "                <cus1:Gender>"+bankCustomer.getGender()+"</cus1:Gender>\n" +
                "                <cus1:DATEOFBIRTH>"+bankCustomer.getDob()+"</cus1:DATEOFBIRTH>\n" +
                "                <cus1:MARITALSTATUS>"+bankCustomer.getMaritalStatus()+"</cus1:MARITALSTATUS>\n" +
                "<!--Customer Phone Number-->\n" +
                "\n" +
                "                <cus1:gPHONE1 g=\"1\">\n" +
                "                    <cus1:mPHONE1 m=\"1\">\n" +
                "                        <cus1:PhoneNumbersRes>"+bankCustomer.getOfficeTelNumber()+"</cus1:PhoneNumbersRes>\n" +
                "                        <cus1:MobilePhoneNumbers>"+bankCustomer.getMobilePhoneNumber()+"</cus1:MobilePhoneNumbers>\n" +
                "                        <cus1:EmailAddress>"+bankCustomer.getEmailId()+"</cus1:EmailAddress>\n" +
                "                    </cus1:mPHONE1>\n" +
                "                </cus1:gPHONE1>\n" +
                "\n" +
                "            </CUSTOMERETMBPOCType>\n" +
                "        </cus:CUSTCREATE>\n" +
                "    </soapenv:Body>\n" +
                "</soapenv:Envelope>\n";

        System.out.println(customerCreateBody);

        return customerCreateBody;
    }
}
