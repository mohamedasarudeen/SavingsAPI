package irisDashbord;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

import com.temenos.interaction.core.DashboardUtility;
/**
 * TODO: Document me!
 *
 * @author mohamedasarudeen
 *
 */
public class DASHBOARD {

public static DASHBOARD d2 = new   DASHBOARD();
    
    public static String Currentfieldstatus;
    public static String Currentteststatus;
    public static String ReportFilePath;
    public static int testcasecount;
    
    public static BufferedWriter testDriverReport; 
    static BufferedWriter HTMLINDEXReportFile;
    static BufferedWriter DBHTMLINDEXReportFile;
    static BufferedWriter intialloadreportfile;
    static BufferedWriter HTMLReportFile;
     public static int indexpass=0;
     public static int indexfail=0;
    public void indexDriver(String[] args) throws IOException
    
    {
        Properties prop = new Properties();
        String curDir;
        curDir = System.getProperty("user.dir");
      //  InputStream input = new FileInputStream(curDir + "\\Config\\config.properties");
      //  prop.load(input);
        ReportFilePath =curDir + "\\result\\" +"automationresult"+"\\";
        File td2File = new File(ReportFilePath+args[0]+".htm");
        HTMLINDEXReportFile = new BufferedWriter(new FileWriter(td2File));
    }
public void indexDBDriver(String[] args) throws IOException
    
    {
        Properties prop = new Properties();
        String curDir;
        curDir = System.getProperty("user.dir");
        InputStream input = new FileInputStream(curDir + "\\Config\\config.properties");
        prop.load(input);
        ReportFilePath =curDir + "\\HTMLResults\\" +prop.getProperty("resultsFolderName").trim()+"\\";
        File td3File = new File(ReportFilePath+args[0]+".htm");
        DBHTMLINDEXReportFile = new BufferedWriter(new FileWriter(td3File));
    }
public void initialloadDriver(String[] args) throws IOException

{
    Properties prop = new Properties();
    String curDir;
    curDir = System.getProperty("user.dir");
    InputStream input = new FileInputStream(curDir + "\\Config\\config.properties");
    prop.load(input);
    ReportFilePath =curDir + "\\HTMLResults\\" +prop.getProperty("resultsFolderName").trim()+"\\";
    File td4File = new File(ReportFilePath+args[0]+".htm");
    intialloadreportfile = new BufferedWriter(new FileWriter(td4File));
}
public void initialloadReportHeader()
        throws IOException
      {
      intialloadreportfile.write("<html>");
      intialloadreportfile.write("\n<head><link rel=\"stylesheet\" type=\"text/css\" href=\"../ReportInfo/report_theme.css\"></link></head>");
      intialloadreportfile.write("\n<body>");
      intialloadreportfile.write("\r \n<hr class=\"divline\">");
      intialloadreportfile
          .write("\r \n<table class=\"rephead\" width='1400px'><tr>");
      intialloadreportfile.write("\r \n<td height='300px'>" + "WealthSuite Testing" + "</td>");
      intialloadreportfile
          .write("\r \n<td height='63px' align='right'><img src = '../ReportInfo\\Images\\New-Temenos-logo.png'></td>");
      intialloadreportfile.write("\r \n</tr></table><hr class=\"divline\"> <BR>");
      intialloadreportfile.write("\n<table width='1500px' class=\"tsteps\">");
      intialloadreportfile.write("\n<td class=\"tshead\" width='50px'>Sl.NO</td>");
      intialloadreportfile.write("\n<td class=\"tshead\" width='200px'>TEST CASE OBJECTIVE</td>");
      intialloadreportfile.write("\n<td class=\"tshead\" width='200px'>SOURCE TABLE(T24)</td>");
      intialloadreportfile.write("\n<td class=\"tshead\" width='200px'>TARGET TABLE(TAP)</td>");
      intialloadreportfile.write("\n<td class=\"tshead\" width='100px'>SOURCE COUNT(T24)</td>");
      intialloadreportfile.write("\n<td class=\"tshead\" width='100px'>TARGET COUNT(TAP)</td>");
      intialloadreportfile.write("\n<td class=\"tshead\" width='200px'>SOURCE CHECKSUM(T24)</td>");
      intialloadreportfile.write("\n<td class=\"tshead\" width='200px'>TARGET CHECKSUM(TAP)</td>");
      intialloadreportfile
    .write("\n<td class=\"tshead\" width='50px'>Status</td>");
       intialloadreportfile.write("\n</tr>");
      }
/*public void initialloadreportbody(String args[])
{
    
    try {
        intialloadreportfile.write("<tr>");
        intialloadreportfile.write("<td class=\"tsnorm"+checksum.teststatus+"\" width='50px'>" + args[0] + "</td>");
        intialloadreportfile.write("<td class=\"tsnorm"+checksum.teststatus+"\"  width='200px'>" + args[1] + "</td>");
        intialloadreportfile.write("<td class=\"tsnorm"+checksum.teststatus+"\"  width='200x'><a class=\tcindex\" href=\""+"T24"+args[11].trim() + ".htm\">" + args[2] + "</a></td>");
        intialloadreportfile.write("<td class=\"tsnorm"+checksum.teststatus+"\"  width='200px'> <a class=\tcindex\" href=\"" + "TAP"+args[12].trim() + ".htm\">" +args[3] + "</a></td>");
      
        intialloadreportfile.write("<td class=\"tsnorm"+checksum.teststatus+"\"  width='100px'>" + args[4] + "</td>");
        intialloadreportfile.write("<td class=\"tsnorm"+checksum.teststatus+"\"  width='100px'>" + args[5] + "</td>");
        intialloadreportfile.write("<td class=\"tsnorm"+checksum.teststatus+"\"  width='200px'>" + args[6] + "</td>");
        intialloadreportfile.write("<td class=\"tsnorm"+checksum.teststatus+"\"  width='200px'>" + args[7] + "</td>");
      
        if(checksum.teststatus == "fail")
        {
        intialloadreportfile.write("<td class=\"tsind\" width='50px'><img src = \"../ReportInfo/images/fail.jpg\" width=\"20\" height=\"20\"></td>");  
        }
        else
        {
            intialloadreportfile.write("<td class=\"tsind\" width='50px'><img src = \"../ReportInfo/images/pass.gif\" width=\"20\" height=\"20\"></td>");  
        }
        intialloadreportfile.write("</tr>");
        
    }
    catch(Exception e)
    {
        System.out.println("reportbody" + e);
    }
    
}*/
public static void indexFooter(){
    try{
        HTMLINDEXReportFile.write("\r \n</tr></table><hr class=\"divline\"> <BR>");
        HTMLINDEXReportFile.write("<tr></tr>");
        HTMLINDEXReportFile.write("<table width='250px' class=\"pfsummary\">");
        HTMLINDEXReportFile
        .write("<tr><td colspan='2' class=\"tshead\">Test Case Summary</td></tr>");
        HTMLINDEXReportFile.write("<tr>");
        HTMLINDEXReportFile.write("<td class=\"pfhead\" width='200px'>Total Tests</td>");
        HTMLINDEXReportFile.write("<td class=\"pfind\" width='50px'>" + DashboardUtility.slno
                + "</td>");
        HTMLINDEXReportFile.write("</tr><tr>");
        HTMLINDEXReportFile
        .write("<td class=\"pfhead\" width='200px'>PASS </td>");
        HTMLINDEXReportFile.write("<td class=\"pfind\" width='50px'>"
                + indexpass + "</td>");
        HTMLINDEXReportFile.write("</tr><tr>");
        HTMLINDEXReportFile.write("<td class=\"pfhead\" width='200px'>FAIL </td>");
        HTMLINDEXReportFile.write("<td class=\"pfind\" width='50px'>"
                + indexfail + "</td>");
        HTMLINDEXReportFile.write("</tr><tr>");
        HTMLINDEXReportFile.write("</body>\n");
        HTMLINDEXReportFile.write("</html>\n");
        HTMLINDEXReportFile.close();           
    }catch(Exception e){
        
    }
}

public static void reportFooter(){
    try{
        HTMLReportFile.write("\r \n</tr></table><hr class=\"divline\"> <BR>");
        HTMLReportFile.write("<tr></tr>");
        HTMLReportFile.write("<table width='250px' class=\"pfsummary\">");
        HTMLReportFile
        .write("<tr><td colspan='2' class=\"tshead\">Test Case Summary</td></tr>");
        HTMLReportFile.write("<tr>");
        HTMLReportFile.write("<td class=\"pfhead\" width='200px'>Execution Start Time</td>");
        HTMLReportFile.write("<td class=\"pfind\" width='50px'>" + DashboardUtility.startTimeStamp+ "</td>");
        HTMLReportFile.write("</tr><tr>");
        HTMLReportFile
        .write("<td class=\"pfhead\" width='200px'>Execution End Time </td>");
        HTMLReportFile.write("<td class=\"pfind\" width='50px'>"
                + DashboardUtility.endTimeStamp + "</td>");
        HTMLReportFile.write("</tr><tr>");
        HTMLReportFile.write("<td class=\"pfhead\" width='200px'>Total Execution Time </td>");
        HTMLReportFile.write("<td class=\"pfind\" width='50px'>"
                + indexfail + "</td>");
        HTMLReportFile.write("</tr><tr>");
        HTMLReportFile.write("</body>\n");
        HTMLReportFile.write("</html>\n");
        HTMLReportFile.close();           
    }catch(Exception e){
        
    }
}

public void initialloadFooter(String args[]){
    try{
        intialloadreportfile.write("<table width='250px' class=\"pfsummary\">");
        intialloadreportfile
        .write("<tr><td colspan='2' class=\"tshead\">Test Case Summary</td></tr>");
        intialloadreportfile.write("<tr>");
        intialloadreportfile.write("<td class=\"pfhead\" width='200px'>Total Tests</td>");
        intialloadreportfile.write("<td class=\"pfind\" width='50px'>" + args[3]
                + "</td>");
        intialloadreportfile.write("</tr><tr>");
        intialloadreportfile
        .write("<td class=\"pfhead\" width='200px'>Total Count Check </td>");
        intialloadreportfile.write("<td class=\"pfind\" width='50px'>"
                + args[0] + "</td>");
        intialloadreportfile.write("</tr><tr>");
        intialloadreportfile.write("<td class=\"pfhead\" width='200px'>Total Checksum Check </td>");
        intialloadreportfile.write("<td class=\"pfind\" width='50px'>"
                + args[1] + "</td>");
        intialloadreportfile.write("</tr><tr>");
        intialloadreportfile
        .write("<td class=\"pfhead\" width='200px'>Total Table Check </td>");
        intialloadreportfile.write("<td class=\"pfind\" width='50px'>"
                + args[2] + "</td>");
        intialloadreportfile.write("</body>\n");
        intialloadreportfile.write("</html>\n");
        intialloadreportfile.close();           
    }catch(Exception e){
        
    }
}
    public void intialloadclose() throws IOException
    {intialloadreportfile.close();
    }
    public void indexclose() throws IOException
    {HTMLINDEXReportFile.close();
    }
    public void indexDBclose() throws IOException
    {DBHTMLINDEXReportFile.close();
    }
    public void testDriver(String[] args) throws IOException
    {
        Properties prop = new Properties();
        String curDir;
        curDir = System.getProperty("user.dir");
        //InputStream input = new FileInputStream(curDir + "\\Config\\config.properties");
      //  prop.load(input);
        ReportFilePath =curDir + "\\result\\" +"automationresult"+"\\";
        File tdFile = new File(ReportFilePath+args[0]+".htm");
        File td1File = new File(ReportFilePath+args[0]+".htm");
       testDriverReport = new BufferedWriter(new FileWriter(tdFile));
        this.HTMLReportFile = new BufferedWriter(new FileWriter(td1File));
    }
    public void testDriverReportHeader()
    {
      try
      {
        testDriverReport.write("<html>\n");
        testDriverReport.write("<head>\n");
        testDriverReport.write("<title>Regression Report</title>\n");
        testDriverReport.write("</head>\n");
        testDriverReport.write("<body>\n");
        testDriverReport.write("<table BORDER=\"3\" BORDERCOLOR=\"clbg.gif\" BGCOLOR=\"#800080\" CELLPADDING=\"1\" CELLSPACING=\"1\" WIDTH=\"80%\" VALIGN=\"MIDDLE\" ALIGN=\"CENTER\">\n");
        testDriverReport.write("<thead>\n");
        testDriverReport.write("<tr>\n");
        testDriverReport.write("<th><font face=\"Verdana\" size=\"8\">SCB </font><BR><BR><font face=\"Verdana\" size=\"6\">DASHBOARD</font><br>" );
        testDriverReport.write("</tr>\n");
        testDriverReport.write("</thead>\n");
        testDriverReport.write("</table>\n");
        testDriverReport.write("<br>\n");
      }
      catch (Exception e)
      {
       System.out.println("testDriverReportHeader" + e);
      }
    }
    
    
    public void WriteExecutionTimeInHeader() throws IOException
          {
            //String timeStamp =new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(Calendar.getInstance().getTime());
            HTMLReportFile.write("<html>");
            HTMLReportFile.write("\n<head><link rel=\"stylesheet\" type=\"text/css\" href=\"../ReportInfo/report_theme.css\"></link></head>");
            HTMLReportFile.write("\n<body>");
            HTMLReportFile.write("\r \n<hr class=\"divline\">");
            HTMLReportFile
              .write("\r \n<table class=\"rephead\" width='1200px'><tr>");
            //HTMLReportFile.write("\r \n<td height='63px'>" + "Automation Testing" + "</td>");
            //HTMLReportFile
              //.write("\r \n<td height='63px' align='right'><img src = '..\\ReportInfo\\Images\\New-Temenos-logo.png'></td>");
            HTMLReportFile.write("\r \n</tr></table><hr class=\"divline\"> <BR>");
            HTMLReportFile.write("\n<table class=\"subhead\" width='1100px'><tr>");
     /*     HTMLReportFile.write("\n<td width='400px' class=\"subhead\">System</td>");
            HTMLReportFile.write("\n<td width='200px' class=\"subhead\">Checkpoint</td>");*/
            //HTMLReportFile.write("\n<td width='200px' class=\"subhead\">Testcase</td>");
            HTMLReportFile.write("\n<td width='300px' class=\"subhead\">Execution Start Time</td>");
            HTMLReportFile.write("\n<td width='300px' class=\"subhead\">Execution End Time</td>");
            HTMLReportFile.write("\n<td width='300px' class=\"subhead\">Total Execution Time In Seconds</td>");
            HTMLReportFile.write("\n</tr>");
            HTMLReportFile.write("\n<tr>");
           /* HTMLReportFile.write("\n<td class=\"subcont\" width='400px'>" + args[0] + "</td>");
            HTMLReportFile.write("\n<td class=\"subcont\" width='200px'>" + args[1] + "</td>");*/
            //HTMLReportFile.write("\n<td class=\"subcont\" width='200px'>" + args[0] + "</td>");
            HTMLReportFile.write("\n<td class=\"subcont\" width='300px'>" +DashboardUtility.startTimeStamp  + "</td>");
            HTMLReportFile.write("\n<td class=\"subcont\" width='300px'>" +DashboardUtility.endTimeStamp  + "</td>");
            HTMLReportFile.write("\n<td class=\"subcont\" width='300px'>" +DashboardUtility.endTimeStamp  + "</td>");
            //HTMLReportFile.write("\n<td class=\"subcont\" width='300px'>" +timeStamp  + "</td>");
          /*   HTMLReportFile.write("\r </tr></table> <hr class=\"divline\"> <BR>");
            HTMLReportFile.write("");
            HTMLReportFile.write("\n<table class=\"subhead\" width='900px'><tr>");
            HTMLReportFile.write("\n<td width='300px' class=\"subhead\">Table name</td>");
            HTMLReportFile.write("\n<td width='300px' class=\"subhead\">Record ID</td>");
            HTMLReportFile.write("\n<td width='300px' class=\"subhead\">Table or Record Error </td></tr>");
            HTMLReportFile.write("\n<tr><td width='300px' class=\"subcont\">" + args[3] + "</td>");
            HTMLReportFile.write("\n<td width='300px' class=\"subcont\">" + args[4] + "</td>");
            if (args[5] == "No Error")
            {
            HTMLReportFile.write("\n<td width='300px' class=\"tsnorm\">" + args[5] + "</td></tr>");
            
          }
            else
            {
                HTMLReportFile.write("\n<td width='300px' class=\"tsnormfail\">" + args[5] + "</td></tr>");
            }
            HTMLReportFile.write("\r \n</tr></table> <hr class=\"divline\"> <BR>");*/
            //HTMLReportFile.write("\n<table width='1000px' class=\"tsteps\">");
            //HTMLReportFile.write("\n<td class=\"tshead\" width='50px'>Step No </td>");
            //HTMLReportFile.write("\n<td class=\"tshead\" width='225px'>Expected Result</td>");
           // HTMLReportFile
              //.write("\n<td class=\"tshead\" width='300px'>Actual Result</td>");
           // HTMLReportFile
           //   .write("\n<td class=\"tshead\" width='285px'>Error Details</td>");
           // HTMLReportFile
            //.write("\n<td class=\"tshead\" width='50px'>Status</td>");
           
            HTMLReportFile.write("\n</tr>");
          }
    
    
    
    
    public void WriteReportHeader(String... args)
            throws IOException
          {
            //String timeStamp =new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(Calendar.getInstance().getTime());
            HTMLReportFile.write("<html>");
            HTMLReportFile.write("\n<head><link rel=\"stylesheet\" type=\"text/css\" href=\"../ReportInfo/report_theme.css\"></link></head>");
            HTMLReportFile.write("\n<body>");
            HTMLReportFile.write("\r \n<hr class=\"divline\">");
            HTMLReportFile
              .write("\r \n<table class=\"rephead\" width='1200px'><tr>");
            HTMLReportFile.write("\r \n<td height='63px'>" + "IRIS Automation Testing" + "</td>");
            HTMLReportFile
              .write("\r \n<td height='63px' align='right'><img src = '..\\ReportInfo\\Images\\New-Temenos-logo.png'></td>");
            HTMLReportFile.write("\r \n</tr></table><hr class=\"divline\"> <BR>");
            HTMLReportFile.write("\n<table class=\"subhead\" width='1100px'><tr>");
     /*     HTMLReportFile.write("\n<td width='400px' class=\"subhead\">System</td>");
            HTMLReportFile.write("\n<td width='200px' class=\"subhead\">Checkpoint</td>");*/
            HTMLReportFile.write("\n<td width='200px' class=\"subhead\">Testcase Name</td>");
            //HTMLReportFile.write("\n<td width='300px' class=\"subhead\">Execution Start Time</td>");
            //HTMLReportFile.write("\n<td width='300px' class=\"subhead\">Execution End Time</td>");
            //HTMLReportFile.write("\n<td width='300px' class=\"subhead\">Total Execution Time In Seconds</td>");
            HTMLReportFile.write("\n</tr>");
            HTMLReportFile.write("\n<tr>");
           /* HTMLReportFile.write("\n<td class=\"subcont\" width='400px'>" + args[0] + "</td>");
            HTMLReportFile.write("\n<td class=\"subcont\" width='200px'>" + args[1] + "</td>");*/
            HTMLReportFile.write("\n<td class=\"subcont\" width='200px'>" + args[0] + "</td>");
            //HTMLReportFile.write("\n<td class=\"subcont\" width='300px'>" +StepDefinitionITCase.startTimeStamp  + "</td>");
            //HTMLReportFile.write("\n<td class=\"subcont\" width='300px'>" +StepDefinitionITCase.endTimeStamp  + "</td>");
            //HTMLReportFile.write("\n<td class=\"subcont\" width='300px'>" +StepDefinitionITCase.endTimeStamp  + "</td>");
            //HTMLReportFile.write("\n<td class=\"subcont\" width='300px'>" +timeStamp  + "</td>");
          /*   HTMLReportFile.write("\r </tr></table> <hr class=\"divline\"> <BR>");
            HTMLReportFile.write("");
            HTMLReportFile.write("\n<table class=\"subhead\" width='900px'><tr>");
            HTMLReportFile.write("\n<td width='300px' class=\"subhead\">Table name</td>");
            HTMLReportFile.write("\n<td width='300px' class=\"subhead\">Record ID</td>");
            HTMLReportFile.write("\n<td width='300px' class=\"subhead\">Table or Record Error </td></tr>");
            HTMLReportFile.write("\n<tr><td width='300px' class=\"subcont\">" + args[3] + "</td>");
            HTMLReportFile.write("\n<td width='300px' class=\"subcont\">" + args[4] + "</td>");
            if (args[5] == "No Error")
            {
            HTMLReportFile.write("\n<td width='300px' class=\"tsnorm\">" + args[5] + "</td></tr>");
            
          }
            else
            {
                HTMLReportFile.write("\n<td width='300px' class=\"tsnormfail\">" + args[5] + "</td></tr>");
            }
            HTMLReportFile.write("\r \n</tr></table> <hr class=\"divline\"> <BR>");*/
            HTMLReportFile.write("\n<table width='1000px' class=\"tsteps\">");
            HTMLReportFile.write("\n<td class=\"tshead\" width='50px'>Step No </td>");
            HTMLReportFile.write("\n<td class=\"tshead\" width='225px'>Expected Result</td>");
            HTMLReportFile
              .write("\n<td class=\"tshead\" width='300px'>Actual Result</td>");
           // HTMLReportFile
           //   .write("\n<td class=\"tshead\" width='285px'>Error Details</td>");
            HTMLReportFile
            .write("\n<td class=\"tshead\" width='50px'>Status</td>");
           
            HTMLReportFile.write("\n</tr>");
          }
    public void WriteindexReportHeader(String args[])
            throws IOException
          {
        HTMLINDEXReportFile.write("<html>");
        HTMLINDEXReportFile.write("\n<head><link rel=\"stylesheet\" type=\"text/css\" href=\"../ReportInfo/report_theme.css\"></link></head>");
        HTMLINDEXReportFile.write("\n<body>");
        HTMLINDEXReportFile.write("\r \n<hr class=\"divline\">");
        HTMLINDEXReportFile
              .write("\r \n<table class=\"rephead\" width='1200px'><tr>");
        HTMLINDEXReportFile.write("\r \n<td height='120px'>" + "IRIS Automation" + "</td>");
        HTMLINDEXReportFile
              .write("\r \n<td height='63px' align='right'><img src = '../ReportInfo\\Images\\New-Temenos-logo.png'></td>");
        HTMLINDEXReportFile.write("\r \n</tr></table><hr class=\"divline\"> <BR>");
        HTMLINDEXReportFile.write("\n<table width='1200px' class=\"tsteps\">");
        HTMLINDEXReportFile.write("\n<td class=\"tshead\" width='10px'>S.No</td> ");
        HTMLINDEXReportFile.write("\n<td class=\"tshead\" width='65px'>Test Scenario Name</td>");
        HTMLINDEXReportFile.write("\n<td class=\"tshead\" width='270px'>Test Scenario Description</td>");
    /*    HTMLINDEXReportFile.write("\n<td class=\"tshead\" width='40px'>Execution Start Time</td>");
        HTMLINDEXReportFile.write("\n<td class=\"tshead\" width='40px'>Execution End Time</td>");
        HTMLINDEXReportFile.write("\n<td class=\"tshead\" width='40px'>Total Execution Time</td>");*/
        HTMLINDEXReportFile
              .write("\n<td class=\"tshead\" width='50px'>Status</td>");
        HTMLINDEXReportFile.write("\n</tr>");
          }
    public void DBWriteindexReportHeader(String args[])
            throws IOException
          {DASHBOARD.testcasecount=0;
        DBHTMLINDEXReportFile.write("<html>");
        DBHTMLINDEXReportFile.write("\n<head><link rel=\"stylesheet\" type=\"text/css\" href=\"../ReportInfo/report_theme.css\"></link></head>");
        DBHTMLINDEXReportFile.write("\n<body>");
        DBHTMLINDEXReportFile.write("\r \n<hr class=\"divline\">");
        DBHTMLINDEXReportFile
              .write("\r \n<table class=\"rephead\" width='1200px'><tr>");
         DBHTMLINDEXReportFile.write("\r \n<td height='300px'>" + "WealthSuite Testing" + "</td>");
         DBHTMLINDEXReportFile
              .write("\r \n<td height='63px' align='right'><img src = '../ReportInfo\\Images\\New-Temenos-logo.png'></td>");
        DBHTMLINDEXReportFile.write("\r \n</tr></table><hr class=\"divline\"> <BR>");
        DBHTMLINDEXReportFile.write("\n<table class=\"subhead\" width='900px'><tr>");
        DBHTMLINDEXReportFile.write("\n<td width='400px' class=\"subhead\">System</td>");
        DBHTMLINDEXReportFile.write("\n<td width='200px' class=\"subhead\">Checkpoint</td>");
        DBHTMLINDEXReportFile.write("\n</tr>");
        DBHTMLINDEXReportFile.write("\n<tr>");
        DBHTMLINDEXReportFile.write("\n<td class=\"subcont\" width='400px'>" + args[0] + "</td>");
        DBHTMLINDEXReportFile.write("\n<td class=\"subcont\" width='200px'>" + args[1] + "</td>");
        DBHTMLINDEXReportFile.write("\r </tr></table> <hr class=\"divline\"> <BR>");
        DBHTMLINDEXReportFile.write("\n<table width='1200px' class=\"tsteps\">");
        DBHTMLINDEXReportFile.write("\n<td class=\"tshead\" width='50px'>Check ID</td>");
        DBHTMLINDEXReportFile.write("\n<td class=\"tshead\" width='400px'>Test Case Description</td>");
        DBHTMLINDEXReportFile.write("\n<td class=\"tshead\" width='400px'>Table Name</td>");
        DBHTMLINDEXReportFile
              .write("\n<td class=\"tshead\" width='300px'>Record ID</td>");
        DBHTMLINDEXReportFile
        .write("\n<td class=\"tshead\" width='50px'>Status</td>");
        DBHTMLINDEXReportFile.write("\n</tr>");
          }
    public void testDriverReporFooter()
    {
      try
      {
        testDriverReport.write("</tbody>\n");
        testDriverReport.write("</table>\n");
        testDriverReport.write("<br>\n");
        testDriverReport.write("<table BORDER=\"3\" BORDERCOLOR=\"#000000\" CELLPADDING=\"0\" CELLSPACING=\"0\" VALIGN=\"MIDDLE\" ALIGN=\"CENTER\" width='350px' height='180px'>\n");
        testDriverReport.write("<tr>\n");
        testDriverReport.write("<td colspan='2' ALIGN=\"CENTER\" BGCOLOR=\"#33ADFF\"><font size=\"5\" face=\"Verdana\"><b>Execution Time</b></font></td>\n");
        testDriverReport.write("</tr>\n");
        testDriverReport.write("<tr ALIGN=\"CENTER\" BGCOLOR=\"#b0c4de\">\n");
        testDriverReport.write("<td><font size=\"3\" face=\"Verdana\"><b>Start Time</b></font></td>\n");
        testDriverReport.write("<td><font size=\"3\" face=\"Verdana\"><b>" + "</b></font></td>\n");
        testDriverReport.write("</tr>\n");
        testDriverReport.write("<tr ALIGN=\"CENTER\" BGCOLOR=\"#b0c4de\">\n");
        testDriverReport.write("<td><font size=\"3\" face=\"Verdana\"><b>End Time</b></font></td>\n");
        testDriverReport.write("<td><font size=\"3\" face=\"Verdana\"><b>"  + "</b></font></td>\n");
        testDriverReport.write("</tr>\n");
        testDriverReport.write("<tr ALIGN=\"CENTER\" BGCOLOR=\"#b0c4de\">\n");
        testDriverReport.write("<td><font size=\"3\" face=\"Verdana\"><b>Duration</font></td>\n");
        testDriverReport.write("<td><font size=\"3\" face=\"Verdana\"><b>"  + "</b></font></td>\n");
        testDriverReport.write("</tr>\n");
        testDriverReport.write("</table>\n");
        testDriverReport.write("</body>\n");
        testDriverReport.write("</html>\n");
      }
      catch (Exception e)
      {
          System.out.println("testDriverReporFooter" + e);
      }
    }
    
    public void reportindexbody(String args[])
    {
        try {
            HTMLINDEXReportFile.write("<tr>");
            HTMLINDEXReportFile.write("<td class=\"tsnorm\" width='125px'>" + args[0] + "</td>");
            HTMLINDEXReportFile.write("\n<td class=\"tsnorm\" width='200px'> <a class=\tcindex\" href=\"" +args[1].trim() + ".htm\">" + args[1] + "</a></td>");
            HTMLINDEXReportFile.write("<td class=\"tsnorm\"  width='155px'>" + args[2] + "</td>");
            /*HTMLINDEXReportFile.write("<td class=\"tsnorm\"  width='155px'>" + StepDefinitionITCase.startTimeStamp + "</td>");
            HTMLINDEXReportFile.write("<td class=\"tsnorm\"  width='155px'>" + StepDefinitionITCase.endTimeStamp + "</td>");
            HTMLINDEXReportFile.write("<td class=\"tsnorm\"  width='155px'>" + StepDefinitionITCase.differenceValue + "</td>");*/
            if(DASHBOARD.Currentteststatus.equals("fail"))
            {
                indexfail+=1;
                HTMLINDEXReportFile.write("<td class=\"tsind\" width='50px'><img src = \"../ReportInfo/images/fail.jpg\" width=\"20\" height=\"20\"></td>");
            }
            else if(DASHBOARD.Currentteststatus.equals("Warn"))
            {
                HTMLINDEXReportFile.write("<td class=\"tsind\" width='50px'><img src = \"../ReportInfo/images/warning.gif\" width=\"20\" height=\"20\"></td>");  
            }
            else
            {
                indexpass+=1;
                HTMLINDEXReportFile.write("<td class=\"tsind\" width='50px'><img src = \"../ReportInfo/images/pass.gif\" width=\"20\" height=\"20\"></td>");  
            }

            HTMLINDEXReportFile.write("</tr>");
        }
        catch(Exception e)
        {
            System.out.println("reportbody" + e);
        }
        
    }
    public void DBreportindexbody(String args[])
    {
        try {
            DBHTMLINDEXReportFile.write("<tr>");
            DBHTMLINDEXReportFile.write("\n<td class=\"tsnorm"+DASHBOARD.Currentteststatus+"\"  width='50px'>" + args[5] + "</td>");
            DBHTMLINDEXReportFile.write("<td class=\"tsnorm"+DASHBOARD.Currentteststatus+"\" width='400px'> <a class=\tcindex\" href=\"" +args[0]+"_"+ args[1]+"_"+args[2].trim() + ".htm\">" + args[2] + "</a></td>");
            DBHTMLINDEXReportFile.write("<td class=\"tsnorm"+DASHBOARD.Currentteststatus+"\" width='400px'>" + args[3] + "</td>");
            DBHTMLINDEXReportFile.write("<td class=\"tsnorm"+DASHBOARD.Currentteststatus+"\"  width='300px'>" + args[4] + "</td>");
            if(DASHBOARD.Currentteststatus.equals("fail"))
            {
                DBHTMLINDEXReportFile.write("<td class=\"tsind\" width='50px'><img src = \"../ReportInfo/images/fail.jpg\" width=\"20\" height=\"20\"></td>");
            }
            else if(DASHBOARD.Currentteststatus.equals("Warn"))
            {
                DBHTMLINDEXReportFile.write("<td class=\"tsind\" width='50px'><img src = \"../ReportInfo/images/warning.gif\" width=\"20\" height=\"20\"></td>");  
            }
            else
            {
                DBHTMLINDEXReportFile.write("<td class=\"tsind\" width='50px'><img src = \"../ReportInfo/images/pass.gif\" width=\"20\" height=\"20\"></td>");  
            }
            DBHTMLINDEXReportFile.write("</tr>");
            DASHBOARD.Currentteststatus="";
            
        }
        catch(Exception e)
        {
            System.out.println("reportbody" + e);
        }
        
    }
    public void reportbody(String args[])
    {
        try {
            HTMLReportFile.write("<tr>");
            HTMLReportFile.write("<td class=\"tsnorm"+DASHBOARD.Currentfieldstatus+"\" width='75px'>" + args[0] + "</td>");
            HTMLReportFile.write("<td class=\"tsnorm"+DASHBOARD.Currentfieldstatus+"\"  width='155px'>" + args[1] + "</td>");
            HTMLReportFile.write("<td class=\"tsnorm"+DASHBOARD.Currentfieldstatus+"\" width='285px'>" +args[2] + "</td>");
          //  HTMLReportFile.write("<td class=\"tsnorm"+DASHBOARD.Currentfieldstatus+"\" width='285px'>" +args[3] + "</td>");
            if(DASHBOARD.Currentfieldstatus == null ||  DASHBOARD.Currentfieldstatus =="" )
            {
                HTMLReportFile.write("<td class=\"tsind\" width='50px'><img src = \"../ReportInfo/images/pass.gif\" width=\"20\" height=\"20\"></td>");
            }
            else if(DASHBOARD.Currentfieldstatus.equals("Warn"))
            {
                HTMLReportFile.write("<td class=\"tsind\" width='50px'><img src = \"../ReportInfo/images/warning.gif\" width=\"20\" height=\"20\"></td>");  
            }
            else if(DASHBOARD.Currentfieldstatus.equals("fail"))
            {
                HTMLReportFile.write("<td class=\"tsind\" width='50px'><img src = \"../ReportInfo/images/fail.jpg\" width=\"20\" height=\"20\"></td>");  
            }
            HTMLReportFile.write("</tr>");
            DASHBOARD.Currentfieldstatus = "";
        }
        catch(Exception e)
        { 
            e.printStackTrace();
            System.out.println("reportbody" + e);
        }
        
    }
    public void reportclose()
    {try {
        HTMLReportFile.close();   
    }
    catch(Exception e)
    {
        System.out.println("reportbody" + e);
    }
        
    }
    public static void main(String[] args) throws IOException,SQLException
    {
        DASHBOARD d1 = new   DASHBOARD();
        String arr[] = new String[3];
        arr[0]= "";
      //  d1.testDriver(arr);
       // d1.testDriverReportHeader();
     
       // d1.reportbody(arr);
       // testDriverReport.close();
       // HTMLReportFile.close();
    }
    

}

