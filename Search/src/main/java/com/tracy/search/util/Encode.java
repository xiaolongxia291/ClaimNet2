package com.tracy.search.util;

public class Encode {
    public static String en(String password){
        StringBuilder sb0=new StringBuilder();
        //转为数字
        for(char c:password.toCharArray()){
            sb0.append(c-'0');
        }
        System.out.println("step1: "+sb0);
        //将字符长度填充为3的倍数
        while(sb0.length()%3!=0){
            sb0.append(0);
        }
        //将中间位置的字符作为哈希值
        int hash=Integer.parseInt(sb0.charAt(sb0.length()/2)+"");
        System.out.println("hash: "+hash);
        //每一个字符对哈希值求余
        StringBuilder sb1=new StringBuilder();
        for(char c:sb0.toString().toCharArray()){
            sb1.append(Integer.parseInt(c+"")%hash);
        }
        System.out.println("step2: "+sb1);
        //数字字符串转为混合字符串
        sb0=new StringBuilder();
        for(char c:sb1.toString().toCharArray()){
            sb0.append((char)(Integer.parseInt(c+"")+'a'));
        }
        return sb0.toString();
    }

}
