package com.coopay.utility;

public class PathString {
    //User Creation Link
    public static final String ADMIN_CREATE="/admin/create";
    public static final String MAIN_CASHIER_CREATE="/main_cashier/create";
    public static  final String AGENT_CREATE="/agent/create";
    public static  final String CUSTOMER_CREATE="/customer/create";
    public static  final String MERCHANT_CREATE="/merchant/create";
    public static  final String AGENT_CUSTOMER_CREATE="/agent/customer/create";

    //User login link
    public static  final String ADMIN_LOGIN="/admin/login";
    public static  final String MAIN_CASHIER_LOGIN="/main_cashier/login";
    public static  final String AGENT_LOGIN="/agent/login";
    public static  final String CUSTOMER_LOGIN="/customer/login";
    public static  final String MERCHANT_LOGIN="/merchant/login";
    public static final String SUPER_ADMIN_LOGIN = "/super/login";

    //User list
    public static  final String ADMIN_LIST="/admin/list";
    public static  final String MAIN_CASHIER_LIST="/main_cashier/list";
    public static  final String AGENT_LIST="/agent/list";
    public static  final String CUSTOMER_LIST="/customer/list";
    public static  final String MERCHANT_LIST="/merchant/list";


    //User SEARCH LINK
    public static  final String ADMIN_SEARCH="/admin/search";
    public static  final String MAIN_CASHIER_SEARCH="/main_cashier/search";
    public static  final String AGENT_SEARCH="/agent/search";
    public static  final String CUSTOMER_SEARCH="/customer/search";
    public static  final String MERCHANT_SEARCH="/merchant/search";


    //users Balance
    public static  final String CUSTOMER_BALANCE="/customer/balance";
    public static  final String MERCHANT_BALANCE="/merchant/balance";
    public static  final String AGENT_BALANCE="/agent/balance";
    public static  final String CASHIER_BALANCE="/cashier/balance";
    public static final String MAIN_CASHIER_BALANCE = "/maincashier/balance";



    //User Management Link PASS WORD CHANGE
    public static  final String ADMIN_PASS="/admin/pass_ch";
    public static  final String MAIN_CASHIER_PASS="/main_cashier/pass_ch";
    public static  final String AGENT_PASS="/agent/pass_ch";
    public static  final String CUSTOMER_PASS="/customer/pass_ch";
    public static  final String MERCHANT_PASS="/merchant/pass_ch";

    //User Management lock/unlock
    public static  final String ADMIN_LOCK="/admin/lock";
    public static  final String AGENT_LOCK="agent/unlock";
    public static  final String CUSTOMER_LOCK="/customer/lock";
    public static  final String MERCHANT_LOCK="/merchant/lock";
    public static final String ADMIN_MAIN_CASHIER_LOCK = "/main_cashier/lock";



    public static final String ADD_CUSTOMER_RESET = "/customer/address";



    //Fund Transfer and internal transaction
    public static  final String FT_W2W="/ft/w2w";
    public static  final String FT_W2B="/ft/w2b";
    public static  final String FT_B2W="/ft/b2w";
    public static  final String FT_B2B="/ft/b2b";
    public static  final String FT_NON_CUSTOMER="/ft/non_customer";


    //Customer Cash Out/IN
    public static  final String FT_CASH_IN="/ft/customer/cash_in";
    public static  final String FT_CASH_OUT="/ft/customer/cash_out";
    //internal transaction
    public static  final String PUSH_TO_MAIN_CASHIER="/main/main_cashier/push";
    public static  final String POOL_FROM_MAIN_CASHIER="/main/main_cashier/PULL";
    public static  final String  PULL_FROM_CASHIER="/cashier/working_balance/pull";
    public static  final String  PUSH_TO_CASHIER="/cashier/working_balance/push";
    public static final String BANK_INVEST = "/bank/invest";
    public static final String MAIN_CASHIER_PUSH_TO_CASHIER = "/main_cashier/cashier/push";



    //User Account Reset
    public static final String MERCHANT_RESET = "/merchant/reset";
    public static final String CUSTOMER_RESET = "/customer/reset";
    public static final String AGENT_RESET = "/agent/reset";
    public static final String MAIN_CASHIER_RESET = "/main_cashier/reset";
    public static final String ADMIN_RESET = "/admin/reset";

    //Language Settig units
    public static final String CUSTOMER_LANGUAGE_SETTING = "/customer/sett_language";
    public static final String MERCHANT_LANGUAGE = "/merchant/set_lang";
    public static final String GET_CUSTOMER_LANGUAGE = "/customer/language";


    //FT
    public static final String CUSTOMER_TRANSACTION ="/customer/account";
    public static final String CUSTOMER_RECENT_TRANSACTION ="/customer/recent_transaction";


    public static final String CUSTOMER_FROM_TO_TRANSACTION ="/customer/recent_from_to_transaction";
    public static final String CUSTOMER_FROM_TRANSACTION ="/customer/after_date_transaction";
    public static final String CUSTOMER_BEFORE_TRANSACTION ="/customer/before_date_transaction";

//Merchant Fund Transfer
    public static final String FT_M2M = "/ft/m2m";
    public static final String FT_M2B = "/ft/m2b";
    public static final String FT_B2M = "/ft/b2m";
    public static final String FT_MB2B = "/ft/merchant/b2b";
//Merchant Unit

    public static final String MERCHANT_PAYMENT = "/ft/merchant/payment";


    public static final String MERCHANT_BILL_PAYMENT = "/ft/bill/payment";

    public static final String ADMIN_AUTH_CASHIER = "/auth/cashier";
    public static final String MAIN_CASHIER_ENABLE = "/main_cashier/enable";
    public static final String MAIN_CASHIER_AGENT_BALANCE = "/agent/balance";
    public static final String CREATE_AGENT_STORE = "/main_cashier/agent/store/create";
    public static final String MAIN_CASHIER_POOL_LIST = "/main_cashier/pool_account_list";




//    public static final Object MMT_URL ="http://196.189.180.26:9095/TWSMMT/services" ;
    public static final Object ON_BOARDING_URL ="http://196.189.180.26:9095/CUSTONBRD/services" ;
//    
//    
//    public static final Object MMT_URL ="http://10.1.245.189:9080/TWSMMT/services" ;
//    public static final Object ON_BOARDING_URL ="http://10.1.245.189:9080/CUSTONBRD/services" ;
    

    
    
    public static final String CUSTOMER_CASH_OUT = "/customer/cash_out";
}
