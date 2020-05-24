package com.utils;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendJMail {



    public static boolean sendMail(String email,String emailMsg){
        final String smtp_host = "smtp.163.com";
        final String from = "15989566325@163.com";//发件人邮箱地址
        final String username = "15989566325@163.com";//发件人邮箱账号
        final String password = "gyfitedu";//发件人邮箱密码

        String to = email;//收件人邮箱地址
        //设置环境信息
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host",smtp_host);//指定的smtp服务器地址
        props.setProperty("mail.smtp.auth","true");//是否授权
        props.setProperty("mail.transport.protocol","smtp");//设置邮件传输的协议

        //session对象表示整个邮件的环境信息
        Session session = Session.getInstance(props);
        //设置输出调试信息
        session.setDebug(true);

        try {
            MimeMessage message = new MimeMessage(session);//实例对象表示一封邮件
            message.setFrom(new InternetAddress(from));//设置发件人地址
            message.setSubject("用户激活");//设置主题
            message.setContent(emailMsg,"text/html;charset=utf-8");//设置内容
            //从session的环境中获取发送邮件的对象
            Transport transport = session.getTransport();
            transport.connect(smtp_host,25,username,password);
            //设置收件人地址，并发送消息
            transport.sendMessage(message,new Address[]{new InternetAddress(to)});
            transport.close();
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }


    }
}
