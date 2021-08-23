package com.github.eric.course.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.eric.course.model.CourseOrder;
import com.github.eric.course.model.HttpException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Service
public class AlipayService {
    public ObjectMapper objectMapper = new ObjectMapper();

    @Value("${alipay.gatewayUrl}")
    private String gatewayUrl;
    @Value("${alipay.appId}")
    private String appId;
    @Value("${alipay.merchantPrivateKey}")
    private String merchantPrivateKey;
    @Value("${alipay.alipayPublicKey}")
    private String alipayPublicKey;

    AlipayClient alipayClient;

    @PostConstruct
    public void setup() {
        alipayClient = new DefaultAlipayClient(
                gatewayUrl,
                appId,
                merchantPrivateKey,
                "json",
                "utf-8",
                alipayPublicKey,
                "RSA2");
    }


    public String getPayPageHtml(CourseOrder courseOrder) {
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();

        alipayRequest.setReturnUrl("http://localhost:8080/api/v1/checkPay?orderId=" + courseOrder.getId());
        BizContent bizContent = new BizContent();
        bizContent.setOut_trade_no(courseOrder.getId().toString());
        bizContent.setTotal_amount(courseOrder.getPrice().toString());
        bizContent.setSubject("课程：" + courseOrder.getCourse().getName());
        bizContent.setBody(courseOrder.getCourse().getDescription());
        bizContent.setProduct_code("FAST_INSTANT_TRADE_PAY");

        String result = "";
        try {
            alipayRequest.setBizContent(objectMapper.writeValueAsString(bizContent));
            result = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n" +
                "<html>\n" +
                "<head>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
                "<title>付款</title>\n" +
                "</head>\n" +
                result +
                "<body>\n" +
                "</body>\n" +
                "</html>";
    }

    public String checkOrderStatus(CourseOrder courseOrder, String alipayTradeNo) {
        //设置请求参数
        AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();

        TradeNoAndOrderNo tradeNoAndOrderNo=new TradeNoAndOrderNo();
        tradeNoAndOrderNo.setOut_trade_no(courseOrder.getId().toString());
        tradeNoAndOrderNo.setTrade_no(alipayTradeNo);

        //请求
        try {
            alipayRequest.setBizContent(objectMapper.writeValueAsString(tradeNoAndOrderNo));
            AlipayTradeQueryResponse execute = alipayClient.execute(alipayRequest);

            if (!execute.getCode().equals("10000")){
                checkOutTradeNo(courseOrder, execute.getOutTradeNo());
                return execute.getTradeStatus();
            }else if(!execute.getCode().equals("40004")){
                checkOutTradeNo(courseOrder, execute.getOutTradeNo());
                return execute.getSubMsg();
            }else {
                throw new HttpException(400,execute.getMsg());
            }
        } catch (AlipayApiException | JsonProcessingException e) {
            throw new HttpException(400,"请求中包含错误");
        }
    }

    private void checkOutTradeNo(CourseOrder courseOrder, String outTradeNo) {
        if(!Objects.equals(outTradeNo,courseOrder.getId().toString())){
            throw new HttpException(400,"商户订单号错误");
        }
    }

    private static class TradeNoAndOrderNo{
        //商户订单号，商户网站订单系统中唯一订单号
        String out_trade_no;
        //支付宝交易号
        String trade_no;

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public String getTrade_no() {
            return trade_no;
        }

        public void setTrade_no(String trade_no) {
            this.trade_no = trade_no;
        }
    }

    private static class BizContent {
        String out_trade_no;
        String total_amount;
        String subject;
        String body;
        String product_code;

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public String getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(String total_amount) {
            this.total_amount = total_amount;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getProduct_code() {
            return product_code;
        }

        public void setProduct_code(String product_code) {
            this.product_code = product_code;
        }
    }
}
