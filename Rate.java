package com.example.finnkinoharjt;

public class Rate {
    public String text;
    public double rate;

    public Rate (String ch, double n){
        text=ch;
        rate=n;
    }

    public String toString(){return text;}
    public double getRate() { return rate;}
}

