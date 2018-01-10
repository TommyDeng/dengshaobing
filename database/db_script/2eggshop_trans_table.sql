--order 
DROP TABLE IF EXISTS ES_ORDER;
CREATE TABLE ES_ORDER(
    UNIQUE_CODE UUID PRIMARY KEY,
    USER_UC UUID,
    SUMMARY VARCHAR,
    TOTAL_AMT DECIMAL(20, 2),
    RECEIVER_NAME VARCHAR,
   	PROVINCE VARCHAR,
    CITY VARCHAR,
    COUNTRY VARCHAR,
    TOWN VARCHAR,
    ADDRESS VARCHAR,
    CONTACT VARCHAR,
    PAYMENT_TYPE VARCHAR,
    STATUS VARCHAR,
    REMARK VARCHAR,
    PAYMENT_UC UUID,
    PAY_MESSAGE VARCHAR,
    PAY_SUCC_TIME TIMESTAMP,
    CREATOR VARCHAR,
    CREATE_TIME TIMESTAMP DEFAULT NOW()
);
--order item
DROP TABLE IF EXISTS ES_ORDER_ITEM;
CREATE TABLE ES_ORDER_ITEM(
    ORDER_UC UUID,
    PRODUCT_UC UUID,
    PRODUCT_COUNT DECIMAL(20, 2),
    PRODUCT_PRICE DECIMAL(20, 2),
    SUB_AMT DECIMAL(20, 2),
    CREATOR VARCHAR,
    CREATE_TIME TIMESTAMP DEFAULT NOW()
);

--product
DROP TABLE IF EXISTS ES_PRODUCT;
CREATE TABLE ES_PRODUCT(
    UNIQUE_CODE UUID PRIMARY KEY,
    NAME VARCHAR,
    PRICE DECIMAL(20, 2),
    CATEGORY UUID,
    THUMBNAIL UUID,
    BRIEF VARCHAR,
    DETAIL_DESCIPTION VARCHAR(5000),
    CREATOR VARCHAR,
    CREATE_TIME TIMESTAMP DEFAULT NOW()
);

--product media
DROP TABLE IF EXISTS ES_PRODUCT_MEDIA;
CREATE TABLE ES_PRODUCT_MEDIA(
    UNIQUE_CODE UUID PRIMARY KEY,
    PRODUCT_UC UUID,
    ORDER_NUM INT,
    THUMBNAIL UUID,
    MEDIA_TYPE VARCHAR,
    CREATOR VARCHAR,
    CREATE_TIME TIMESTAMP DEFAULT NOW()
);

--shopping cart
DROP TABLE IF EXISTS ES_SHOPPING_CART;
CREATE TABLE ES_SHOPPING_CART(
    UNIQUE_CODE UUID PRIMARY KEY,
    SELECTED BOOLEAN DEFAULT TRUE,
    USER_UC UUID,
    PRODUCT_UC UUID,
    PRODUCT_COUNT DECIMAL(20, 2),
    REMARK VARCHAR,
    CREATOR VARCHAR,
    CREATE_TIME TIMESTAMP DEFAULT NOW()
);

--main swiper
DROP TABLE IF EXISTS ES_MAIN_SWIPER;
CREATE TABLE ES_MAIN_SWIPER(
    UNIQUE_CODE UUID PRIMARY KEY,
    PRODUCT_UC UUID,
    THUMBNAIL UUID,
   	ORDER_NUM INT,
   	VALID_TIME TIMESTAMP,
    REMARK VARCHAR,
    CREATOR VARCHAR,
    CREATE_TIME TIMESTAMP DEFAULT NOW()
);

--product category
DROP TABLE IF EXISTS ES_PRODUCT_CATEGORY;
CREATE TABLE ES_PRODUCT_CATEGORY(
    UNIQUE_CODE UUID PRIMARY KEY,
    THUMBNAIL UUID,
   	ORDER_NUM INT,
   	NAME VARCHAR,
    REMARK VARCHAR,
    CREATOR VARCHAR,
    CREATE_TIME TIMESTAMP DEFAULT NOW()
);

--deliverty address
DROP TABLE IF EXISTS ES_DELIVERY_ADDRESS;
CREATE TABLE ES_DELIVERY_ADDRESS(
    UNIQUE_CODE UUID PRIMARY KEY,
    USER_UC UUID,
    RECEIVER_NAME VARCHAR,
   	PROVINCE VARCHAR,
    CITY VARCHAR,
    COUNTRY VARCHAR,
    TOWN VARCHAR,
    ADDRESS VARCHAR,
    CONTACT VARCHAR,
    DEFAULT_FLAG VARCHAR,
    CREATOR VARCHAR,
    LAST_SELECT_TIME TIMESTAMP DEFAULT NOW(),
    CREATE_TIME TIMESTAMP DEFAULT NOW()
);

--payment
DROP TABLE IF EXISTS ES_PAYMENT;
CREATE TABLE ES_PAYMENT(
    UNIQUE_CODE UUID PRIMARY KEY,
    PAYSTATUS VARCHAR,
    PAYSTATUS_MSG VARCHAR,
    NONCE_STR VARCHAR,
    SIGN VARCHAR,
    PREPAY_ID VARCHAR,
    CODE_URL VARCHAR,
   	DEVICE_INFO VARCHAR,
   	OUT_TRADE_NO VARCHAR,
    TRADE_TYPE VARCHAR,
    TOTAL_FEE VARCHAR,
    TIME_START TIMESTAMP,
    TIME_EXPIRE TIMESTAMP,
    CREATOR VARCHAR,
    CREATE_TIME TIMESTAMP DEFAULT NOW()
);
