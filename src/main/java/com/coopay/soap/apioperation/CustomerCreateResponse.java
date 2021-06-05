package com.coopay.soap.apioperation;

public class CustomerCreateResponse {

    private String transactionId;
    private String messageId;
    private String shortName;
    private String middleName;
    private String lastName;
    private String email;
    private boolean customerAuthorized;
    private boolean accountAuthorized;
    private String mobileNumber;
    private String status;
    private String application;
    private String messages;
    private String fullName;
    private String authStatus;



    public boolean isCustomerAuthorized() {
        return customerAuthorized;
    }

    public void setCustomerAuthorized(boolean customerAuthorized) {
        this.customerAuthorized = customerAuthorized;
    }

    public boolean isAccountAuthorized() {
        return accountAuthorized;
    }

    public void setAccountAuthorized(boolean accountAuthorized) {
        this.accountAuthorized = accountAuthorized;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
