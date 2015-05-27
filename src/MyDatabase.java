import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.RandomAccessFile;
import java.util.Scanner;


public class MyDatabase {

	public MyDatabase() {
		// TODO Auto-generated constructor stub
	}
	private static String dbfile = "data.db";
public static void main(String[] args){

	System.out.println("Enter any of the Following Options?");
	System.out.println(" 1.id \n 2.Comp name \n 3. Drug id \n 4.trial \n 5.patients \n 6.dosage \n 7. reading \n 8. Controlled Study Flag \n 9. DataBlind Flag Flag\n 10. Govt FUnded FLag \n 11.FDA Approved Flag\n");
	String filename=null;
	//ParseCSV File
	CSVParser.parse();
	
	//Take the input from the user
	Scanner in = new Scanner(System.in);
	String s = in.nextLine();
	String stringValue;
	String operator;
	
	int  intValue;
	switch(s)
	{
	case "1": filename="id";
			 //Take the integer from the User
			 System.out.println("Enter value");
			 intValue=in.nextInt();
			 System.out.println("Enter operator");
			 operator=in.next();
			 searchByInt(filename,intValue,operator);
			 break;
			 
	case "2":filename="company";
			 //searchByInt(map);
			 System.out.println("Enter value");
			 stringValue=in.nextLine();
			 searchByString(filename,stringValue);
			 break;
	case "3":filename="drug";
			//searchByInt(map);
			System.out.println("Enter value");
			stringValue=in.next();
			searchByString(filename,stringValue);
			break;
	case "4" :filename="trial";

              //searchByInt(map);
			  System.out.println("Enter value");
			  intValue=in.nextInt();
			  System.out.println("Enter operator");
			  operator=in.next();
			  searchByInt(filename,intValue,operator);
			  break;
	case "5":filename="patient";

	        //searchByInt(map);
			System.out.println("Enter value");
			intValue=in.nextInt();
			System.out.println("Enter operator");
			operator=in.next();
			searchByInt(filename,intValue,operator);
			break;
	case "7":filename="reading";
	
	//searchByInt(map);
			System.out.println("Enter value");
			float value=in.nextFloat();
			System.out.println("Enter operator");
			operator=in.next();
			searchByFloat(filename,value,operator);
			break;
	case "6":filename="dosage";

			//searchByInt(map);
			System.out.println("Enter value");
			intValue=in.nextInt();
			System.out.println("Enter operator");
			operator=in.next();
			searchByInt(filename,intValue,operator);
			break;
	case "8":filename="cstudy";

			//searchByInt(map);
			System.out.println("Enter value");
			stringValue=in.nextLine();

			searchByString(filename,stringValue);
			break;
	case "9":filename="dblind";

			//searchByInt(map);
			System.out.println("Enter value");
			stringValue=in.nextLine();

			searchByString(filename,stringValue);
			break;
	case "10":filename="govtfund";

			//searchByInt(map);
			System.out.println("Enter value");
			stringValue=in.nextLine();

			searchByString(filename,stringValue);
			break;
	case "11":filename="fda";

			//searchByInt(map);
			System.out.println("Enter value");
			stringValue=in.nextLine();

			searchByString(filename,stringValue);
			break;
	default:System.out.println("You need to provide input within Range i.e 1-11");
			System.exit(0);
	}
	
	
	in.close();
}
static void searchByInt(String filename,int value,String operator){
	//ArrayList map;
	//System.out.println("here");
	try{
    
    BufferedReader in = new BufferedReader(new FileReader(""+filename+".ndx"));
    String line = "";
    int count = 0;
    while ((line = in.readLine()) != null) {
    	String parts[] = line.split("=");
         int comp=Integer.parseInt(parts[0]);
         
   
         
         if(operator.equals("<"))
         {
     		if(comp<value)
     		{
     			  String part2 = parts[1];
     	         System.out.println("part2 is " + part2);
     	         //replace all space, brackets with ""
     	         String result = part2.replace("[", "").replace("]", "").replace(" ", "");
     	        
     	         String[] addresses = result.split(",");
     	         System.out.println("addresses :");
     			for(int i =0;i<addresses.length;i++)
     				fetchAndPrint(Integer.parseInt(addresses[i]));
     		}
         }
     	if(operator.equals(">"))
     	{
     		if(comp> value)
     		{
     		  String part2 = parts[1];
              System.out.println("part2 is " + part2);
              //replace all space, brackets with ""
              String result = part2.replace("[", "").replace("]", "").replace(" ", "");
             
              String[] addresses = result.split(",");
              System.out.println("addresses :");
 			for(int i =0;i<addresses.length;i++)
 				fetchAndPrint(Integer.parseInt(addresses[i]));
     		}
 		}
     	if(operator.equals("="))
     	{
     		
     		if(comp == value)
     		{
     			System.out.println(count++);
     		System.out.println("oprator is " + operator);
     		  String part2 = parts[1];
              System.out.println("part2 is " + part2);
              //replace all space, brackets with ""
              String result = part2.replace("[", "").replace("]", "").replace(" ", "");
             
              String[] addresses = result.split(",");
              System.out.println("addresses :");
 			for(int i =0;i<addresses.length;i++)
 			{
 				fetchAndPrint(Integer.parseInt(addresses[i]));
     		}
     		System.out.println("count is :" + count++);
     		}
 		}
     	
     	if(operator.equals("<="))
     	{
     		if(comp<=value){
     		  String part2 = parts[1];
              System.out.println("part2 is " + part2);
              //replace all space, brackets with ""
              String result = part2.replace("[", "").replace("]", "").replace(" ", "");
             
              String[] addresses = result.split(",");
              System.out.println("addresses :");
 			for(int i =0;i<addresses.length;i++)
 			{
 				fetchAndPrint(Integer.parseInt(addresses[i]));
     		}
 		}
     	}
     	if(operator.equals(">="))
     	{
     		if(comp>=value){
     		  String part2 = parts[1];
              System.out.println("part2 is " + part2);
              //replace all space, brackets with ""
              String result = part2.replace("[", "").replace("]", "").replace(" ", "");
             
              String[] addresses = result.split(",");
              System.out.println("addresses :");
 			for(int i =0;i<addresses.length;i++)
 			{
 				fetchAndPrint(Integer.parseInt(addresses[i]));
     		}
     		}
 		}
     	
     	if(operator.equals("!="))
     	{
     		if(comp!=value){
     		  String part2 = parts[1];
              System.out.println("part2 is " + part2);
              //replace all space, brackets with ""
              String result = part2.replace("[", "").replace("]", "").replace(" ", "");
             
              String[] addresses = result.split(",");
              System.out.println("addresses :");
 			for(int i =0;i<addresses.length;i++)
 			{
 				fetchAndPrint(Integer.parseInt(addresses[i]));
     		}
     		
 		}
     	}
         
    }
    
    in.close();
}
	catch(Exception e){
		System.out.println(e);
	}
	 
	 
}

    
  
    
    static void searchByString(String filename,String value)
    {
    	try{
            BufferedReader in = new BufferedReader(new FileReader(""+filename+".ndx"));
            String line = "";
            while ((line = in.readLine()) != null) {
                String parts[] = line.split("=");
                  String comp=(parts[0]);
                 
                  String part2 = parts[1];
                  //replace all space, brackets with ""
                  String result = part2.replace("[", "").replace("]", "").replace(" ", "");
                 
                  String[] addresses = result.split(",");
                 if(comp.equalsIgnoreCase(value))
                 {
             		//Fetch all the address
                	 for(int i=0;i < addresses.length;i++)
                	 {
                		 fetchAndPrint(Integer.parseInt(addresses[i]));
                	 }
                 }
            }
            in.close();
        }
        	catch(Exception e){
        		System.out.println(e);
        	}
    }
    
    static void searchByFloat(String filename,float value,String operator){
    	try{
            
            BufferedReader in = new BufferedReader(new FileReader(""+filename+".ndx"));
            String line = "";
            while ((line = in.readLine()) != null) {
            	String parts[] = line.split("=");
            	float comp=Float.parseFloat(parts[0]);
                      
                 if(operator.equals("<"))
                 {
              		if(comp<value){
                		  String part2 = parts[1];
                         System.out.println("part2 is " + part2);
                         //replace all space, brackets with ""
                         String result = part2.replace("[", "").replace("]", "").replace(" ", "");
                        
                         String[] addresses = result.split(",");
                         System.out.println("addresses :");
            			for(int i =0;i<addresses.length;i++)
            			{
            				fetchAndPrint(Integer.parseInt(addresses[i]));
                		}
                		
            		}
              	}
             	
             	if(operator.equals(">"))
             	{
             		if(comp>value){
               		  String part2 = parts[1];
                        System.out.println("part2 is " + part2);
                        //replace all space, brackets with ""
                        String result = part2.replace("[", "").replace("]", "").replace(" ", "");
                       
                        String[] addresses = result.split(",");
                        System.out.println("addresses :");
           			for(int i =0;i<addresses.length;i++)
           			{
           				fetchAndPrint(Integer.parseInt(addresses[i]));
               		}
               		
           		}
             	}
             	
             	if(operator.equals("="))
             	{
             		if(comp  == value){
               		  String part2 = parts[1];
                        System.out.println("part2 is " + part2);
                        //replace all space, brackets with ""
                        String result = part2.replace("[", "").replace("]", "").replace(" ", "");
                       
                        String[] addresses = result.split(",");
                        System.out.println("addresses :");
           			for(int i =0;i<addresses.length;i++)
           			{
           				fetchAndPrint(Integer.parseInt(addresses[i]));
               		}
               		
           		}
             	}
             	
             	if(operator.equals("<="))
             	{
             		if(comp<=value){
               		  String part2 = parts[1];
                        System.out.println("part2 is " + part2);
                        //replace all space, brackets with ""
                        String result = part2.replace("[", "").replace("]", "").replace(" ", "");
                       
                        String[] addresses = result.split(",");
                        System.out.println("addresses :");
           			for(int i =0;i<addresses.length;i++)
           			{
           				fetchAndPrint(Integer.parseInt(addresses[i]));
               		}
               		
           		}
             	}
             	
             	if(operator.equals(">="))
             	{
             		if(comp>=value){
               		  String part2 = parts[1];
                        System.out.println("part2 is " + part2);
                        //replace all space, brackets with ""
                        String result = part2.replace("[", "").replace("]", "").replace(" ", "");
                       
                        String[] addresses = result.split(",");
                        System.out.println("addresses :");
           			for(int i =0;i<addresses.length;i++)
           			{
           				fetchAndPrint(Integer.parseInt(addresses[i]));
               		}
               		
           		}
             	}
             	
             	if(operator.equals("!="))
             	{
             		if(comp!=value){
               		  String part2 = parts[1];
                        System.out.println("part2 is " + part2);
                        //replace all space, brackets with ""
                        String result = part2.replace("[", "").replace("]", "").replace(" ", "");
                       
                        String[] addresses = result.split(",");
                        System.out.println("addresses :");
           			for(int i =0;i<addresses.length;i++)
           			{
           				fetchAndPrint(Integer.parseInt(addresses[i]));
               		}
               		
           		}
             	}
                 
            }
            in.close();
        }
        	catch(Exception e){
        		System.out.println(e);
        	}
    }

static void fetchAndPrint(int posi){
	try{
		
	File f1 = new File(dbfile);
	RandomAccessFile raf = new RandomAccessFile(f1, "r");
	raf.seek(posi);

	int id = raf.readInt();

	int comlen = raf.readInt();
	byte[] comp = new byte[comlen];
	raf.read(comp, 0, comlen);
	String company = new String(comp);
	

	byte[] drugid = new byte[6];
	raf.read(drugid, 0, 6);
	String drug = new String(drugid);
	

	short trail = raf.readShort();
	short patient = raf.readShort();
	short dosage = raf.readShort();
	float read = raf.readFloat();
	byte combinedByte;
	combinedByte = raf.readByte();
	String double_blind,controolled_study,IsGovtFunded,fda_approved;
	final byte doubleBlind      = 8;    
	final byte controlledStudy  = 4;    
	final byte govtFunded       = 2;    
	final byte fda_approvedApproved      = 1;
	//System.out.println((byte)(combinedByte & doubleBlind));
	if((byte)(combinedByte & doubleBlind)>(byte)0)
		double_blind="true";
	else 
		double_blind="false";


	if((byte)(combinedByte & controlledStudy)>(byte)0)
		controolled_study="true";
	else 
		controolled_study="false";


	if((byte)(combinedByte & govtFunded)>(byte)0)
		IsGovtFunded="true";
	else 
		IsGovtFunded="false";


	if((byte)(combinedByte & fda_approvedApproved)>(byte)0)
		fda_approved="true";
	else 
		fda_approved="false";
	
	
	System.out.println("The values are:");
		System.out.println( "ID :"+id+"\t"+"Company" + company+"\t"+"Drug" +drug+"\t"+"Trail" +trail+"\t" +"patient: "+patient+"\t"+"Dosage :" + dosage+"\t"+"Read :"+ read+"\t" + "Double Blind :"+double_blind+"\t"+ "ControlledStudy :" +controolled_study+"\t"+"Govt Funded :"+IsGovtFunded+"\t"+"FDA Approved :"+fda_approved);

	
	}
	catch(Exception e){
		System.out.println(e);
	}
	
}
}
