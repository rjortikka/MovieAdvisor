package com.example.movieadvisor;


import android.os.Build;
import android.os.StrictMode;

import androidx.annotation.RequiresApi;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

@RequiresApi(api = Build.VERSION_CODES.O)
public class TheatreInfo {
    public ArrayList<Theatre> theatrelist=new ArrayList<Theatre>();
    public ArrayList<String> namelist=new ArrayList<>();
    public ArrayList<String> timelist=new ArrayList<>();
    ArrayList<String> IDlist=new ArrayList<String>();
    public ArrayList<String> placelist =new ArrayList<String>();
    private static TheatreInfo ti=new TheatreInfo();


    private TheatreInfo() {
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse("https://www.finnkino.fi/xml/TheatreAreas/");
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getDocumentElement().getElementsByTagName("TheatreArea");
            for (int i = 1; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    Theatre theatre = new Theatre();
                    theatre.name = element.getElementsByTagName("Name").item(0).getTextContent();
                    theatre.ID = element.getElementsByTagName("ID").item(0).getTextContent();
                    theatrelist.add(theatre);
                }
            }
        } catch (
                ParserConfigurationException e) {
            e.printStackTrace();
        } catch (
                IOException e) {
            e.printStackTrace();
        } catch (
                SAXException e) {
            e.printStackTrace();
        }
    }


    public static TheatreInfo getInstance(){return ti;}


    public void findMovie(String wantedtheatre, String datet, String startingtime, String endingtime, String namemovie){
        namelist.clear();
        timelist.clear();
        placelist.clear();
        IDlist.clear();
        if(wantedtheatre.equals("1029")){
            IDlist.add("1014");
            IDlist.add("1015");
            IDlist.add("1016");
            IDlist.add("1017");
            IDlist.add("1041");
            IDlist.add("1018");
            IDlist.add("1019");
            IDlist.add("1021");
            IDlist.add("1022");
        }
        else{
            IDlist.add(wantedtheatre);
        }
        DateTimeFormatter dtf=DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate now= LocalDate.now();
        String date1;
        LocalDateTime datetime;
        LocalTime starttime = LocalTime.parse("00:00",DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime endtime=LocalTime.parse("23:59",DateTimeFormatter.ofPattern("HH:mm"));
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("HH:mm        dd.MM.yyyy");
        if(datet.length()>0){
            try{
                LocalDate date=LocalDate.parse(datet,dtf);
                date1=datet;
            }
            catch (Exception e){
                System.out.println(e);
                namelist.add("Set date dd.mm.yyyy");
                timelist.add(" ");
                placelist.add(" ");
                return;
            }

        }
        else{
            date1=dtf.format(now);
        }
        if(startingtime.length()>0) {
            try {
                starttime = LocalTime.parse(startingtime, DateTimeFormatter.ofPattern("HH:mm"));
            } catch (Exception e) {
                namelist.add("Set earliest time HH:mm");
                timelist.add(" ");
                placelist.add(" ");
                return;
            }
        }
        if(endingtime.length()>0) {
            try {
                endtime = LocalTime.parse(endingtime, DateTimeFormatter.ofPattern("HH:mm"));
            } catch (Exception e) {
                namelist.add("Set latest time HH:mm");
                timelist.add(" ");
                placelist.add(" ");
                return;
            }
        }
        try {
            DocumentBuilder builder= DocumentBuilderFactory.newInstance().newDocumentBuilder();
            for(int x = 0; x < IDlist.size(); x++){
                Document doc = builder.parse("https://www.finnkino.fi/xml/Schedule/?area="+IDlist.get(x).toString()+"&dt="+date1);
                doc.getDocumentElement().normalize();
                NodeList nList = doc.getDocumentElement().getElementsByTagName("Shows");
                for (int i = 0; i < nList.getLength(); i++) {
                    Node node = nList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        if(namemovie.length()>0){
                            if(element.getElementsByTagName("Title").item(0).getTextContent().equals(namemovie)){
                                datetime= LocalDateTime.parse((element.getElementsByTagName("dttmShowStart").item(0).getTextContent()));
                                if(starttime.compareTo(datetime.toLocalTime())<=0) {
                                    if (endtime.compareTo(datetime.toLocalTime()) >= 0) {
                                        namelist.add(element.getElementsByTagName("Title").item(0).getTextContent());
                                        timelist.add(formatter.format(datetime));
                                        placelist.add(element.getElementsByTagName("Theatre").item(0).getTextContent());
                                    }
                                }
                            }
                        }
                        else{
                            datetime= LocalDateTime.parse((element.getElementsByTagName("dttmShowStart").item(0).getTextContent()));
                            if(starttime.compareTo(datetime.toLocalTime())<=0) {
                                if (endtime.compareTo(datetime.toLocalTime()) >= 0) {
                                    namelist.add(element.getElementsByTagName("Title").item(0).getTextContent());
                                    timelist.add(formatter.format(datetime));
                                    placelist.add(element.getElementsByTagName("Theatre").item(0).getTextContent());
                                }
                            }
                        }
                    }
                }}
            if(namelist.size()==0){
                namelist.add("No shows in the theatre.");
                timelist.add(" ");
                placelist.add(" ");
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }}

