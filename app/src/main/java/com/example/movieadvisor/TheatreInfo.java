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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

@RequiresApi(api = Build.VERSION_CODES.O)
public class TheatreInfo {

    // Create lists where movie information is stored on Finnkino's website
    public ArrayList<Theatre> theatrelist=new ArrayList<Theatre>();
    public ArrayList<String> namelist=new ArrayList<>();
    public ArrayList<String> timelist=new ArrayList<>();
    ArrayList<String> IDlist=new ArrayList<String>();
    public ArrayList<String> placelist =new ArrayList<String>();
    // Create list for evaluation
    public ArrayList<Object> ratelist=new ArrayList<>();
    // Create lists in which the information desired by the user is stored
    public ArrayList<String> namestorage=new ArrayList<>();
    public ArrayList<String> timestorage=new ArrayList<>();
    public ArrayList<String> placestorage=new ArrayList<>();
    public ArrayList<String> namestorageforrating=new ArrayList<>();

    private static TheatreInfo ti=new TheatreInfo();


    private TheatreInfo() {
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Add to list 1-10 for evaluations
        ratelist.add(new Rate("One star", 1));
        ratelist.add(new Rate("Two stars", 2));
        ratelist.add(new Rate("Three stars", 3));
        ratelist.add(new Rate("Four stars", 4));
        ratelist.add(new Rate("Five stars", 5));
        ratelist.add(new Rate("Six stars", 6));
        ratelist.add(new Rate("Seven stars", 7));
        ratelist.add(new Rate("Eight stars", 8));
        ratelist.add(new Rate("Nine stars", 9));
        ratelist.add(new Rate("Ten stars", 19));

        // The names and id information of different theaters are retrieved from Finnkino's website and saved in the list
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
        // Add different theatre ID in list
        if(wantedtheatre.equals("1029")){
            IDlist.add("1014");
            IDlist.add("1012");
            IDlist.add("1039");
            IDlist.add("1038");
            IDlist.add("1002");
            IDlist.add("1045");
            IDlist.add("1031");
            IDlist.add("1032");
            IDlist.add("1033");
            IDlist.add("1013");
            IDlist.add("1034");
            IDlist.add("1035");
            IDlist.add("1047");
            IDlist.add("1046");
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
        // Put the times and dates in the correct format
        DateTimeFormatter dtf=DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate now= LocalDate.now();
        String date1;
        LocalDateTime datetime;
        LocalTime starttime = LocalTime.parse("00:00",DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime endtime=LocalTime.parse("23:59",DateTimeFormatter.ofPattern("HH:mm"));
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy ");

        // Report if the date is in the wrong format
        if(datet.length()>0){
            try{
                LocalDate date=LocalDate.parse(datet,dtf);
                date1=datet;
            }
            catch (Exception e){
                namelist.add("Set date dd.mm.yyyy");
                timelist.add(" ");
                placelist.add(" ");
                return;
            }

        }
        else{
            date1=dtf.format(now);
        }
        // Report if the earliest time is in the wrong format
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
        // Report if the latest time is in the wrong format
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
        // Search the rest of the film 's information on Finnkino' s website and saves it in the list
        //
        try {
            DocumentBuilder builder= DocumentBuilderFactory.newInstance().newDocumentBuilder();
            for(int x = 0; x < IDlist.size(); x++){
                Document doc = builder.parse("https://www.finnkino.fi/xml/Schedule/?area="+IDlist.get(x).toString()+"&dt="+date1);
                doc.getDocumentElement().normalize();
                NodeList nList = doc.getDocumentElement().getElementsByTagName("Show");
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
            System.out.println(ti.namelist);
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