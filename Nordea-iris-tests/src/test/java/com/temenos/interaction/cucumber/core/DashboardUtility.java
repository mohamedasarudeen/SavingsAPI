package com.temenos.interaction.cucumber.core;

import irisDashbord.DASHBOARD;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * TODO: Document me!
 *
 * @author mohamedasarudeen
 *
 */
public class DashboardUtility {

    public static int Teststepfailed = 0;
    public static String Actvalue = null;
    public static String Expvalue = null;
    public static int stepno = 1;
    public static DASHBOARD d1 = new DASHBOARD();
    public static int slno = 0;
    public static String startTimeStamp = null;
    public static String endTimeStamp = null;
    public static String differenceValue = null;
    public static String testcaseNameGlobal = null;
    public static String testCaseName = null;
    public static String testCaseDescription = null;

    // DASHBOARD HTML REPORT METHODS

    public static void beforecase(String testCaseName) throws IOException {
        startTimeStamp = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
        String[] reportint = new String[2];
        reportint[0] = testCaseName;
        d1.testDriver(reportint);
        d1.WriteReportHeader(reportint);
        DASHBOARD.Currentteststatus = "";
        stepno = 1;
        Teststepfailed = 1;
    }

    public static void aftercase(String testcaseName, String testCaseDesc) throws IOException {
        endTimeStamp = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        Date startDate, endDate, difference;
        try {
            startDate = sdf.parse(startTimeStamp);
            endDate = sdf.parse(endTimeStamp);
            long diffsec = (endDate.getTime() - startDate.getTime()) / 1000 % 60;
            long diffmin = (endDate.getTime() - startDate.getTime()) / (60 * 1000) % 60;
            long diffhr = (endDate.getTime() - startDate.getTime()) / (60 * 60 * 1000) % 24;
            String seconds = String.valueOf(diffsec);
            String minutes = String.valueOf(diffmin);
            String hours = String.valueOf(diffhr);
            if (seconds.length() <= 1) {
                seconds = "0" + seconds;
            }
            if (minutes.length() <= 1) {
                minutes = "0" + minutes;
            }
            if (hours.length() <= 1) {
                hours = "0" + hours;
            }
            differenceValue = hours + ":" + minutes + ":" + seconds;
        } catch (ParseException e) {
            System.out.println(e.toString());
        }
        String[] bodyarg = new String[5];
        slno += 1;
        bodyarg[0] = String.valueOf(slno);
        bodyarg[1] = testCaseName;// testcasename
        bodyarg[2] = testCaseDescription;// testcase description
        d1.reportindexbody(bodyarg);
        // d1.WriteReportHeader(testcaseName);
        d1.reportFooter();
        d1.reportclose();
    }

    public static void stepcheck(String expValue) {
        String[] reportbody1 = new String[5];
        reportbody1[0] = String.valueOf(stepno);
        reportbody1[1] = expValue;
        reportbody1[2] = Actvalue;
        // reportbody1[3]="";

        stepno += 1;
        if (Actvalue.equals(Expvalue)) {
            DASHBOARD.Currentfieldstatus = "";
        } else {
            DASHBOARD.Currentfieldstatus = "fail";
        }
        if (Teststepfailed == 1 && DASHBOARD.Currentfieldstatus.equals("fail")) {
            DASHBOARD.Currentteststatus = "fail";
            Teststepfailed = 0;
        }
        d1.reportbody(reportbody1);
    }
}
