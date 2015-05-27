



import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import com.opencsv.CSVReader;


public class CSVParser {

	public CSVParser() {
		// TODO Auto-generated constructor stub
	}

	
	
	/* **************************************************DECLARATIONS******************************************************************************************************** */
	private static String csvFilePath = "PHARMA_TRIALS_1000B.csv";
	private static String dbFile = "data.db";
	private static String idFilePath = "id.ndx";
	private static String companyFilePath = "company.ndx";
	private static String drugFilePath = "drug.ndx";
	private static String trialFilePath = "trial.ndx";
	private static String patientFilePath = "patient.ndx";
	private static String dosageFilePath = "dosage.ndx";
	private static String readingFilePath = "reading.ndx";
	private static String blindFilePath = "dblind.ndx";
	private static String studyFilePath = "cstudy.ndx";
	private static String fundFilePath = "govtfund.ndx";
	private static String fdaFilePath = "fda.ndx";
	
	final static byte doubleBlind     = 8;    
	final static byte controlledStudy  = 4;    
	final static byte govtFunded       = 2;    
	final static byte fdaApproved      = 1;
	
	static TreeMap<String,Long> primaryIndex = new TreeMap<String,Long>();
	static TreeMap<String,ArrayList<Long>> companyIndex = new TreeMap<String,ArrayList<Long>>();
	static TreeMap<String,ArrayList<Long>> drugIndex = new TreeMap<String,ArrayList<Long>>();
	static TreeMap<String,ArrayList<Long>> trialIndex = new TreeMap<String,ArrayList<Long>>();
	static TreeMap<String,ArrayList<Long>> patientIndex = new TreeMap<String,ArrayList<Long>>();
	static TreeMap<String,ArrayList<Long>> dosageIndex = new TreeMap<String,ArrayList<Long>>();
	static TreeMap<String,ArrayList<Long>> readingIndex = new TreeMap<String,ArrayList<Long>>();
	static TreeMap<String,ArrayList<Long>> doubleBlindIndex = new TreeMap<String,ArrayList<Long>>();
	static TreeMap<String,ArrayList<Long>> controlledStudyIndex = new TreeMap<String,ArrayList<Long>>();
	static TreeMap<String,ArrayList<Long>> govtFundedIndex = new TreeMap<String,ArrayList<Long>>();
	static TreeMap<String,ArrayList<Long>> fdaApprovedIndex = new TreeMap<String,ArrayList<Long>>();
	
	
	public static void parse() {
		System.out.println("Parsing CSV to Binary ..... ");
		
		try {
			
			File f1 = new File(dbFile);
            RandomAccessFile raf = new RandomAccessFile(f1, "rw");
            CSVReader reader = new CSVReader(new FileReader(csvFilePath));
            
			String[] currLine = null;
			currLine = reader.readNext();
			
			while ((currLine = reader.readNext()) != null) {
	 
				primaryIndex.put(currLine[0], raf.getFilePointer());
				
				String id=currLine[0];
				int idInt=Integer.parseInt(id);
				
				String company=currLine[1];
				int companyLen = company.length();
				
				String drugId=currLine[2];
				
				String trial=currLine[3];
				short trialShort=Short.parseShort(trial);
				
				String patients=currLine[4];
				short patientsShort=Short.parseShort(patients);
				
				String dosage=currLine[5];
				short dosageShort=Short.parseShort(dosage);
				
				String reading=currLine[6];
				float readingFloat=Float.parseFloat(reading);
			
				//System.out.println(currLine[7]+","+currLine[8]+","+currLine[9]+","+currLine[10]);
				byte commonByte = 0x00;	
				if(currLine[7].equalsIgnoreCase("TRUE")) 
					commonByte = (byte)(commonByte | doubleBlind);
					
				
				
				if(currLine[8].equalsIgnoreCase("TRUE")) 
					commonByte = (byte)(commonByte | controlledStudy);
					
				
				if(currLine[9].equalsIgnoreCase("TRUE")) 
					commonByte = (byte)(commonByte | govtFunded);
					
				
								
				if(currLine[10].equalsIgnoreCase("TRUE")) 
					commonByte = (byte)(commonByte | fdaApproved);
					
			
				
				
				//System.out.println(raf.getFilePointer());
				//System.out.println(raf.readByte()); 
								
			
				raf.writeInt(idInt);
				
				raf.writeInt(companyLen);
				raf.writeBytes(company);
				
				raf.writeBytes(drugId);
				raf.writeShort(trialShort);
				raf.writeShort(patientsShort);
				raf.writeShort(dosageShort);
				raf.writeFloat(readingFloat);
				raf.writeByte(commonByte);
				
			}
			
			 raf.close();
			 reader.close();
			 createIndex(primaryIndex);
			 createIndexRest(primaryIndex);
	 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
		}
		
	
	  }

	//*******************************************INDEXING****************************************************************************************************/
	
	
	
	public static void createIndex(TreeMap<String,Long> idprimaryIndex) {

		String[] currLine = null;
		try {
			
			File file = new File (idFilePath);
		    PrintWriter printWriter = new PrintWriter (file);
			for(Map.Entry<String, Long> entry : idprimaryIndex.entrySet()) {
				  String key = entry.getKey();
				  Long value = entry.getValue();

				  String line = key + "=" + value;
				  printWriter.write(line);
				  printWriter.println();
				}
			printWriter.close();
			System.out.println("Index created for ID");
			
		} catch(Exception e) {
			e.printStackTrace();
		}

	}


	public static void createIndexRest(TreeMap<String,Long> idprimaryIndex) {

		
		/* --------------------------------------------------------------------COMPANY INDEX------------------------------------------------------------------------------------------------------ */
		try {
			
			File file = new File(companyFilePath);
			PrintWriter printWriterCompany = new PrintWriter (file);
            CSVReader readerCompany = null;
				  readerCompany = new CSVReader(new FileReader(csvFilePath));
				  
				  String[] currLine = null;
				  currLine = readerCompany.readNext();
				  
				//For each entry in CSV filecreate Map for each double blind valur i.e true or false
					while ((currLine = readerCompany.readNext()) != null) {
						ArrayList<Long> readBlindAdresses = new ArrayList<Long>();
						String id=currLine[0];
					//	int idInt=Integer.parseInt(id);
						String readBlindKey = currLine[1];
						//Get the address for that row.
						Long address =  primaryIndex.get(id);
						//put the content in the indexMap
						if(companyIndex.containsKey(readBlindKey))
						{
							readBlindAdresses = companyIndex.get(readBlindKey);
							readBlindAdresses.add(address);
							companyIndex.put(readBlindKey, readBlindAdresses);
						}
						else
						{
							readBlindAdresses.add(address);
							companyIndex.put(readBlindKey, readBlindAdresses);
						}
						
						
					}
					
					//Now put the content of the Map into the File
					for(Map.Entry<String, ArrayList<Long>> entry : companyIndex.entrySet()) {
						  String key = entry.getKey();
						 ArrayList<Long> values = entry.getValue();
						 String line = key + "=" + values.toString();
						  printWriterCompany.write(line);
						  printWriterCompany.println();
						 
					}
			printWriterCompany.close();
			readerCompany.close();
			System.out.println("Index created for Company");
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		/*-------------------------------------------------------------- DRUG INDEX---------------------------------------------------------------------------------- */
		
		try {
			
			File file = new File(drugFilePath);
			PrintWriter printWriterdrug = new PrintWriter (file);
            CSVReader readerdrug = null;
			readerdrug = new CSVReader(new FileReader(csvFilePath));
				  
			String[] currLine = null;
			currLine = readerdrug.readNext();
			//For each entry in CSV filecreate Map for each double blind valur i.e true or false
			while ((currLine = readerdrug.readNext()) != null) {
				ArrayList<Long> readBlindAdresses = new ArrayList<Long>();
				String id=currLine[0];
			//	int idInt=Integer.parseInt(id);
				String readBlindKey = currLine[2];
				//Get the address for that row.
				Long address =  primaryIndex.get(id);
				//put the content in the indexMap
				if(drugIndex.containsKey(readBlindKey))
				{
					readBlindAdresses = drugIndex.get(readBlindKey);
					readBlindAdresses.add(address);
					drugIndex.put(readBlindKey, readBlindAdresses);
				}
				else
				{
					readBlindAdresses.add(address);
					drugIndex.put(readBlindKey, readBlindAdresses);
				}
				
				
			}
			
			//Now put the content of the Map into the File
			for(Map.Entry<String, ArrayList<Long>> entry : drugIndex.entrySet()) {
				  String key = entry.getKey();
				 ArrayList<Long> values = entry.getValue();
				 String line = key + "=" + values.toString();
				  printWriterdrug.write(line);
				  printWriterdrug.println();
				 
			}
			printWriterdrug.close();
			readerdrug.close();
			System.out.println("Index created for Drug");
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		

		/*--------------------------------------------------- TRIALS INDEX---------------------------------------------------------------------- */
		
		try {
			
			File file = new File(trialFilePath);
			PrintWriter printWritertrial = new PrintWriter (file);
            CSVReader readertrial = null;
			readertrial = new CSVReader(new FileReader(csvFilePath));
			String[] currLine = null;
			  currLine = readertrial.readNext();
			
			//For each entry in CSV filecreate Map for each double blind valur i.e true or false
			while ((currLine = readertrial.readNext()) != null) {
				ArrayList<Long> readBlindAdresses = new ArrayList<Long>();
				String id=currLine[0];
			//	int idInt=Integer.parseInt(id);
				String readBlindKey = currLine[3];
				//Get the address for that row.
				Long address =  primaryIndex.get(id);
				//put the content in the indexMap
				if(trialIndex.containsKey(readBlindKey))
				{
					readBlindAdresses = trialIndex.get(readBlindKey);
					readBlindAdresses.add(address);
					trialIndex.put(readBlindKey, readBlindAdresses);
				}
				else
				{
					readBlindAdresses.add(address);
					trialIndex.put(readBlindKey, readBlindAdresses);
				}
				
				
			}
			
			//Now put the content of the Map into the File
			for(Map.Entry<String, ArrayList<Long>> entry : trialIndex.entrySet()) {
				  String key = entry.getKey();
				 ArrayList<Long> values = entry.getValue();
				 String line = key + "=" + values.toString();
				  printWritertrial.write(line);
				  printWritertrial.println();
				 
			}
				  
				  
			printWritertrial.close();
			readertrial.close();
			System.out.println("Index created for Trial");
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
/*--------------------------------------------------------------- PATIENTS INDEX-------------------------------------------- */
		
		try {
			File file = new File(patientFilePath);
			PrintWriter printWriterpatient = new PrintWriter (file);
            CSVReader readerpatient = null;
		    readerpatient = new CSVReader(new FileReader(csvFilePath));
		    String[] currLine = null;
		    currLine = readerpatient.readNext();
					
				//For each entry in CSV filecreate Map for each double blind valur i.e true or false
					while ((currLine = readerpatient.readNext()) != null) {
						ArrayList<Long> readBlindAdresses = new ArrayList<Long>();
						String id=currLine[0];
						int idInt=Integer.parseInt(id);
						String readBlindKey = currLine[4];
						//Get the address for that row.
						Long address =  primaryIndex.get(id);
						//put the content in the indexMap
						if(patientIndex.containsKey(readBlindKey))
						{
							readBlindAdresses = patientIndex.get(readBlindKey);
							readBlindAdresses.add(address);
							patientIndex.put(readBlindKey, readBlindAdresses);
						}
						else
						{
							readBlindAdresses.add(address);
							patientIndex.put(readBlindKey, readBlindAdresses);
						}
						
						
					}
					
					//Now put the content of the Map into the File
					for(Map.Entry<String, ArrayList<Long>> entry : patientIndex.entrySet()) {
						  String key = entry.getKey();
						 ArrayList<Long> values = entry.getValue();
						 String line = key + "=" + values.toString();
						  printWriterpatient.write(line);
						  printWriterpatient.println();
						 
					}
					printWriterpatient.close();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
/*................................................................... DOSAGE INDEX----------------------------------------*/
		
		try {
			
			File file = new File(dosageFilePath);
			PrintWriter printWriterdosage = new PrintWriter (file);
            CSVReader readerdosage = null;
		    
            String[] currLine = null;
            readerdosage = new CSVReader(new FileReader(csvFilePath));
            currLine = readerdosage.readNext();
			  
			  //For each entry in CSV filecreate Map for each double blind valur i.e true or false
				while ((currLine = readerdosage.readNext()) != null) {
					ArrayList<Long> Adresses = new ArrayList<Long>();
					String id=currLine[0];
					int idInt=Integer.parseInt(id);
					String govtFundKey = currLine[5];
					//Get the address for that row.
					Long address =  primaryIndex.get(id);
					//put the content in the indexMap
					if(dosageIndex.containsKey(govtFundKey))
					{
						Adresses = dosageIndex.get(govtFundKey);
						Adresses.add(address);
						dosageIndex.put(govtFundKey, Adresses);
					}
					else
					{
						Adresses.add(address);
						dosageIndex.put(govtFundKey, Adresses);
					}
					
					
				}
				
				//Now put the content of the Map into the File
				for(Map.Entry<String, ArrayList<Long>> entry : dosageIndex.entrySet()) {
					  String key = entry.getKey();
					 ArrayList<Long> values = entry.getValue();
					 String line = key + "=" + values.toString();
					  printWriterdosage.write(line);
					  printWriterdosage.println();
					 
				}
				readerdosage.close();
				printWriterdosage.close();
			System.out.println("Index created for Dosage");
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		
/*.....................................................READING INDEX............................................................*/
		
		try {
			
			File file = new File(readingFilePath);
			PrintWriter printWriterreading = new PrintWriter (file);
            CSVReader readerreading = null;
            String[] currLine = null;
            readerreading = new CSVReader(new FileReader(csvFilePath));
            currLine = readerreading.readNext();
		    
          //For each entry in CSV filecreate Map for each double blind valur i.e true or false
			while ((currLine = readerreading.readNext()) != null) {
				ArrayList<Long> readBlindAdresses = new ArrayList<Long>();
				String id=currLine[0];
				int idInt=Integer.parseInt(id);
				String readBlindKey = currLine[6];
				//Get the address for that row.
				Long address =  primaryIndex.get(id);
				//put the content in the indexMap
				if(readingIndex.containsKey(readBlindKey))
				{
					readBlindAdresses = readingIndex.get(readBlindKey);
					readBlindAdresses.add(address);
					readingIndex.put(readBlindKey, readBlindAdresses);
				}
				else
				{
					readBlindAdresses.add(address);
					readingIndex.put(readBlindKey, readBlindAdresses);
				}
				
				
			}
			
			//Now put the content of the Map into the File
			for(Map.Entry<String, ArrayList<Long>> entry : readingIndex.entrySet()) {
				  String key = entry.getKey();
				 ArrayList<Long> values = entry.getValue();
				 String line = key + "=" + values.toString();
				  printWriterreading.write(line);
				  printWriterreading.println();
				 
			}
			printWriterreading.close();
			System.out.println("Index created for Reading");
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
/* DOUBLE BLIND INDEX------------------------------------------------------------------------------------------------------ */
		
		try {
			
			File file = new File(blindFilePath);
			PrintWriter printWriterblind = new PrintWriter (file);
            CSVReader readerblind = null;   
            String[] currLine = null;
            readerblind = new CSVReader(new FileReader(csvFilePath));
			  
			  
			  currLine = readerblind.readNext();
			  
			  //For each entry in CSV filecreate Map for each double blind valur i.e true or false
				while ((currLine = readerblind.readNext()) != null) {
					ArrayList<Long> readBlindAdresses = new ArrayList<Long>();
					String id=currLine[0];
					int idInt=Integer.parseInt(id);
					String readBlindKey = currLine[7];
					//Get the address for that row.
					Long address =  primaryIndex.get(id);
					//put the content in the indexMap
					if(doubleBlindIndex.containsKey(readBlindKey))
					{
						readBlindAdresses = doubleBlindIndex.get(readBlindKey);
						readBlindAdresses.add(address);
						doubleBlindIndex.put(readBlindKey, readBlindAdresses);
					}
					else
					{
						readBlindAdresses.add(address);
						doubleBlindIndex.put(readBlindKey, readBlindAdresses);
					}
					
					
				}
				
				//Now put the content of the Map into the File
				for(Map.Entry<String, ArrayList<Long>> entry : doubleBlindIndex.entrySet()) {
					  String key = entry.getKey();
					 ArrayList<Long> values = entry.getValue();
					 String line = key + "=" + values.toString();
					  printWriterblind.write(line);
					  printWriterblind.println();
					 
				}
				readerblind.close();
				printWriterblind.close();
				
		} catch(Exception e) {
			System.out.println("Exception found");
			e.printStackTrace();
		}
		
		
		
		
/* CONTROLLED STUDY INDEX------------------------------------------------------------------------------------------------------ */
		
		try {
			
			File file = new File(studyFilePath);
			PrintWriter printWriterstudy = new PrintWriter (file);
            CSVReader readerstudy = null;
            String[] currLine = null;
            
            readerstudy = new CSVReader(new FileReader(csvFilePath));
            currLine = readerstudy.readNext();
		    
          //For each entry in CSV filecreate Map for each double blind valur i.e true or false
			while ((currLine = readerstudy.readNext()) != null) {
				ArrayList<Long> Adresses = new ArrayList<Long>();
				String id=currLine[0];
				int idInt=Integer.parseInt(id);
				String controlledStudyKey = currLine[8];
				//Get the address for that row.
				Long address =  primaryIndex.get(id);
				//put the content in the indexMap
				if(controlledStudyIndex.containsKey(controlledStudyKey))
				{
					Adresses = controlledStudyIndex.get(controlledStudyKey);
					Adresses.add(address);
					controlledStudyIndex.put(controlledStudyKey, Adresses);
				}
				else
				{
					Adresses.add(address);
					controlledStudyIndex.put(controlledStudyKey, Adresses);
				}
				
				
			}
			
			//Now put the content of the Map into the File
			for(Map.Entry<String, ArrayList<Long>> entry : controlledStudyIndex.entrySet()) {
				  String key = entry.getKey();
				 ArrayList<Long> values = entry.getValue();
				 String line = key + "=" + values.toString();
				  printWriterstudy.write(line);
				  printWriterstudy.println();
				 
			}
			readerstudy.close();
			//printWriterblind.close();
			printWriterstudy.close();
			System.out.println("Index created for Controlled Study");
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
/* GOVT FUNDED INDEX------------------------------------------------------------------------------------------------------ */
		
		try {
			
			File file = new File(fundFilePath);
			PrintWriter printWriterFund = new PrintWriter (file);
            CSVReader readerfund = null;
            String[] currLine = null;
            readerfund = new CSVReader(new FileReader(csvFilePath));
            currLine = readerfund.readNext();
			  
			  //For each entry in CSV filecreate Map for each double blind valur i.e true or false
				while ((currLine = readerfund.readNext()) != null) {
					ArrayList<Long> Adresses = new ArrayList<Long>();
					String id=currLine[0];
					int idInt=Integer.parseInt(id);
					String govtFundKey = currLine[9];
					//Get the address for that row.
					Long address =  primaryIndex.get(id);
					//put the content in the indexMap
					if(govtFundedIndex.containsKey(govtFundKey))
					{
						Adresses = govtFundedIndex.get(govtFundKey);
						Adresses.add(address);
						govtFundedIndex.put(govtFundKey, Adresses);
					}
					else
					{
						Adresses.add(address);
						govtFundedIndex.put(govtFundKey, Adresses);
					}
					
					
				}
				
				//Now put the content of the Map into the File
				for(Map.Entry<String, ArrayList<Long>> entry : govtFundedIndex.entrySet()) {
					  String key = entry.getKey();
					 ArrayList<Long> values = entry.getValue();
					 String line = key + "=" + values.toString();
					  printWriterFund.write(line);
					  printWriterFund.println();
					 
				}
				readerfund.close();
				printWriterFund.close();
			System.out.println("Index created for Govt Funded");
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
/* FDA APPROVED INDEX------------------------------------------------------------------------------------------------------ */
		
		try {
			
			File file = new File(fdaFilePath);
			PrintWriter printWriterFda = new PrintWriter (file);
            CSVReader readerfda = null;
		    
		    
            String[] currLine = null;
            readerfda = new CSVReader(new FileReader(csvFilePath));
            currLine = readerfda.readNext();
			  
			  //For each entry in CSV filecreate Map for each double blind valur i.e true or false
				while ((currLine = readerfda.readNext()) != null) {
					ArrayList<Long> Adresses = new ArrayList<Long>();
					String id=currLine[0];
					int idInt=Integer.parseInt(id);
					String govtFundKey = currLine[10];
					//Get the address for that row.
					Long address =  primaryIndex.get(id);
					//put the content in the indexMap
					if(fdaApprovedIndex.containsKey(govtFundKey))
					{
						Adresses = fdaApprovedIndex.get(govtFundKey);
						Adresses.add(address);
						fdaApprovedIndex.put(govtFundKey, Adresses);
					}
					else
					{
						Adresses.add(address);
						fdaApprovedIndex.put(govtFundKey, Adresses);
					}
					
					
				}
				
				//Now put the content of the Map into the File
				for(Map.Entry<String, ArrayList<Long>> entry : fdaApprovedIndex.entrySet()) {
					  String key = entry.getKey();
					 ArrayList<Long> values = entry.getValue();
					 String line = key + "=" + values.toString();
					  printWriterFda.write(line);
					  printWriterFda.println();
					 
				}
				readerfda.close();
				printWriterFda.close();
			System.out.println("Index created for FDA Approved");
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
}

