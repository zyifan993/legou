package com.zyf.legou.pay.config;

import java.io.FileWriter;
import java.io.IOException;

public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCL1mWoNGuOcGU/Q7pkv57fp22aWansVebq1/cpOMqqb6/GKt2Toj6YuvWT+y9P25HIg6J2bca3oy//HhFG33UlSzDsCAggyRsrwhah/VKsKZgGF0K+afFxDClJWjfLi7Qi8d3Walv9L7/rxJFLKWF+okzr4zgExRDjLXhF9om5u+QAg8k0wHACeKN1G6Hp3EWIXSQKT+WiyqpZT5UOne8hXASTM/tQ9h7IPVPfESvQ0ilOfTNbYlQcyuhj8L+bSwHr8fFo2hQJ2Z9GLt4gYH6SR7CfgvdheJsXLat7MQVX81sRkHnXzE9DTT0tOPa6RLSCyHyb+1VCTFzCIdkJj50nAgMBAAECggEAXQpwpwMB0UsdQoO4wBHzDlJjDYC9G59oPjcln0rHJbGcHVkmBnW7+njiruxA5XxtOiHDddLClirA45jI3AQP+JlX2qPZbJ8CMhI4Zt2bJSujl+HgKbtpxtLVoZWibA7u8xVEicZIoGQl6SPnnD/TO6OXVJfhDONlwhHd5YcMygOqBgveyYRFcdnjBOL44KUwJDhKowvHuYoOgUsC2rUqeb8QRn6OJTv7DwpMHsiFeNLeJF+wpG9IEhe1l2HM7dU3lN/9H4LV1fUUx21au4fLhIshV9mekRVk8rTLslQIS8MNbsroevgZoIdZYWEh+LMmdq0mNGsB/T9XG6WvYIhUWQKBgQDOn4Hppe4D9DwyBp/oNK51CljPCz+8LyE1Y6xfMOx3qj7G0ranoII8QDGTJn87/l2Q/BgT2VOSvIN7il2dEBPyR88fchbbrvgGFeoPNozAm9gY6vB7QwI3afERRVptUuS7rB4lv2TjBk5GS7Bxxz8jfZfKcFhtIlViIFIorKUAkwKBgQCtQS0N+4tod8Bo3gY6FTDo3aOuMlTFO+DIqiEIzX7OQtAuCcPcooIyRorWxYR5LmK65+RqYPPAkE9dTyMO0d1N90gzOtXzvL/ZJJvBUSzTm0re8VwNq/f6LSEu18JG4qPS/xRvDh7j76KrMHJcTN57qDodE3JyMGI57pqLpqmRnQKBgF3QVm/7gsIQ1nPwRhJsOPmzJPd8qAeNboqLIuxljjgW+EHV0lDxs7AfhWLGi+Z1a440p8n7VtK8/QXOoQp7DlNm/0fLyU0deEdrE8asgePB3rARm/abShpW47lvv8r7ZBdB4r5I1Emvx0Jrg0VlrekzUQP9VmjWueW/vw3wtUo5AoGAaNqMGO1GxrfK0+yjGh3fFt+kgvVlBmUhum/0RC6R8uTOtz4jsnELmL/GoQqzDN/80p8rRxJcEwuDbLZLQlnStK/Wm2KdP8DY3uLf/NRS69INMvYhvwh3sIoFjiL4KgxaF4VW6YtDOLG74h0U6TWAZJhAa35sgh00JVl5YNFByKUCgYEAyMkdFIZFNKWdOFh2rJYHIviZ3fKuRfkDsts4e2wFA0StNJmoCsON5+22AcY7xGvvuqN/MJ/DkTTTA4vEquWRDc5YcpZk6mdf62AHbB8GF/kq99qzonNwaEs0IhaNp7eQ5xRStD+9ZJOT/bSQHSJBKYs+SXmLREAAbcWj77S0nXM=";

    // 支付宝公钥
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAi9ZlqDRrjnBlP0O6ZL+e36dtmlmp7FXm6tf3KTjKqm+vxirdk6I+mLr1k/svT9uRyIOidm3Gt6Mv/x4RRt91JUsw7AgIIMkbK8IWof1SrCmYBhdCvmnxcQwpSVo3y4u0IvHd1mpb/S+/68SRSylhfqJM6+M4BMUQ4y14RfaJubvkAIPJNMBwAnijdRuh6dxFiF0kCk/losqqWU+VDp3vIVwEkzP7UPYeyD1T3xEr0NIpTn0zW2JUHMroY/C/m0sB6/HxaNoUCdmfRi7eIGB+kkewn4L3YXibFy2rezEFV/NbEZB518xPQ009LTj2ukS0gsh8m/tVQkxcwiHZCY+dJwIDAQAB";

    // 服务器异步通知页面路径
    //需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "";

    // 页面跳转同步通知页面路径
    //需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关,注意这些使用的是沙箱的支付宝网关，与正常网关的区别是多了dev
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}