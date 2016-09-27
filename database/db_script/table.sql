--测试表
DROP TABLE IF EXISTS TEST;
CREATE TABLE TEST(
    ID BIGINT IDENTITY PRIMARY KEY,
    NAME VARCHAR(255),
    AGE TINYINT,
    CASH DECIMAL(20, 2),
    UNIQUE_CODE UUID,
    BIRTHDATE DATE,
    CREATE_TIME TIMESTAMP
);

--日志表：HTTP请求
DROP TABLE IF EXISTS LOG_HTTP;
CREATE TABLE LOG_HTTP(
    ID BIGINT IDENTITY PRIMARY KEY,
    METHOD_NAME VARCHAR(50),
    URI VARCHAR(2500),
    REQUEST VARCHAR(2500),
    RESPONSE VARCHAR(2500),
    CREATE_TIME TIMESTAMP
); 

--日志表：用户登录记录
DROP TABLE IF EXISTS LOG_VISIT;
CREATE TABLE LOG_VISIT(
    ID BIGINT IDENTITY PRIMARY KEY,
    NAME VARCHAR(255),
    REMARK VARCHAR(255),
    CREATE_TIME TIMESTAMP
); 

--日志表：调用http返回错误消息日志
DROP TABLE IF EXISTS LOG_ERRORABLE;
CREATE TABLE LOG_ERRORABLE(
    ID BIGINT IDENTITY PRIMARY KEY,
    URI VARCHAR(2500),
    ERRORABLE VARCHAR(255),
    CREATE_TIME TIMESTAMP
); 