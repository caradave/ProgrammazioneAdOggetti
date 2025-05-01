package clinic;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.*;
import java.util.*;

/**
 * Represents a clinic with patients and doctors.
 * 
 */
public class Clinic {
	HashMap<String, Patient> patientMap = new HashMap<>();
	HashMap<Integer, Doctor> doctorMap = new HashMap<>();
	HashMap<Integer, HashSet<String>> docToPatientMap = new HashMap<>();
	HashMap<String, Integer> patientToDocMap = new HashMap<>();	
	
	/**
	 * Add a new clinic patient.
	 * 
	 * @param first first name of the patient
	 * @param last last name of the patient
	 * @param ssn SSN number of the patient
	 */
	public void addPatient(String first, String last, String ssn) {
		this.patientMap.put(ssn, new Patient(first, last, ssn));
	}


	/**
	 * Retrieves a patient information
	 * 
	 * @param ssn SSN of the patient
	 * @return the object representing the patient
	 * @throws NoSuchPatient in case of no patient with matching SSN
	 */
	public String getPatient(String ssn) throws NoSuchPatient {
		if(this.patientMap.get(ssn) == null) {
			throw new NoSuchPatient();
		}
		
		return this.patientMap.get(ssn).getSurname() + " " + this.patientMap.get(ssn).getName() + " (" + this.patientMap.get(ssn).getSSN() + ")";
	}

	/**
	 * Add a new doctor working at the clinic
	 * 
	 * @param first first name of the doctor
	 * @param last last name of the doctor
	 * @param ssn SSN number of the doctor
	 * @param docID unique ID of the doctor
	 * @param specialization doctor's specialization
	 */
	public void addDoctor(String first, String last, String ssn, int docID, String specialization) {
		this.doctorMap.put(docID, new Doctor(first, last, ssn, docID, specialization));
		this.patientMap.put(ssn, new Patient(first, last, ssn));
	}

	/**
	 * Retrieves information about a doctor
	 * 
	 * @param docID ID of the doctor
	 * @return object with information about the doctor
	 * @throws NoSuchDoctor in case no doctor exists with a matching ID
	 */
	public String getDoctor(int docID) throws NoSuchDoctor {
		if(this.doctorMap.get(docID) == null) {
			throw new NoSuchDoctor();
		}
		//"<Last> <First> (<SSN>) [<ID>]: <Specialization>"
		return doctorMap.get(docID).getSurname() + " " + doctorMap.get(docID).getName() + " (" + doctorMap.get(docID).getSSN() + ") [" + doctorMap.get(docID).getBadge() + "]: " + doctorMap.get(docID).getSpec();
	}
	
	/**
	 * Assign a given doctor to a patient
	 * 
	 * @param ssn SSN of the patient
	 * @param docID ID of the doctor
	 * @throws NoSuchPatient in case of not patient with matching SSN
	 * @throws NoSuchDoctor in case no doctor exists with a matching ID
	 */
	public void assignPatientToDoctor(String ssn, int docID) throws NoSuchPatient, NoSuchDoctor {
		if(this.doctorMap.get(docID) == null) {
			throw new NoSuchDoctor();
		}
		if(this.patientMap.get(ssn) == null) {
			throw new NoSuchPatient();
		}
		if(this.docToPatientMap.get(docID) == null)
			this.docToPatientMap.put(docID, new HashSet<String>());
		HashSet<String> temp = this.docToPatientMap.get(docID);
		temp.add(ssn);
		this.docToPatientMap.replace(docID, temp);
		this.patientToDocMap.put(ssn, docID);
	}

	/**
	 * Retrieves the id of the doctor assigned to a given patient.
	 * 
	 * @param ssn SSN of the patient
	 * @return id of the doctor
	 * @throws NoSuchPatient in case of not patient with matching SSN
	 * @throws NoSuchDoctor in case no doctor has been assigned to the patient
	 */
	public int getAssignedDoctor(String ssn) throws NoSuchPatient, NoSuchDoctor {
		if(this.patientMap.get(ssn) == null) {
			throw new NoSuchPatient();
		}
		if(this.patientToDocMap.get(ssn) == null) {
			throw new NoSuchDoctor();
		}
		return this.patientToDocMap.get(ssn);
	}
	
	/**
	 * Retrieves the patients assigned to a doctor
	 * 
	 * @param id ID of the doctor
	 * @return collection of patient SSNs
	 * @throws NoSuchDoctor in case the {@code id} does not match any doctor 
	 */
	public Collection<String> getAssignedPatients(int id) throws NoSuchDoctor {
		if(this.doctorMap.get(id) == null) {
			throw new NoSuchDoctor();
		}
		if(this.docToPatientMap.get(id) == null)
			this.docToPatientMap.put(id, new HashSet<String>());
		return this.docToPatientMap.get(id);
	}
	
	/**
	 * Loads data about doctors and patients from the given stream.
	 * <p>
	 * The text file is organized by rows, each row contains info about
	 * either a patient or a doctor.</p>
	 * <p>
	 * Rows containing a patient's info begin with letter {@code "P"} followed by first name,
	 * last name, and SSN. Rows containing doctor's info start with letter {@code "M"},
	 * followed by badge ID, first name, last name, SSN, and speciality.<br>
	 * The elements on a line are separated by the {@code ';'} character possibly
	 * surrounded by spaces that should be ignored.</p>
	 * <p>
	 * In case of error in the data present on a given row, the method should be able
	 * to ignore the row and skip to the next one.<br>

	 * 
	 * @param reader reader linked to the file to be read
	 * @throws IOException in case of IO error
	 */
	public int loadData(Reader reader) throws IOException {
		int count = 0;
		int ch = reader.read();;
		String str = "";
		
		while(ch != -1) {
			str = str + (char) ch;
			ch = reader.read();
		}
		
		String[] lis = str.trim().split("\n");
		
		
		for(String s:lis) {
			String[] rege = s.trim().split(";");
			
			
			if(rege[0].equals("P") && rege.length == 4 && this.patientMap.get(rege[3]) == null) {
				this.addPatient(rege[1].trim(), rege[2].trim(), rege[3].trim());
				count += 1;
			}else if(rege[0].equals("M") && rege.length == 6 && this.doctorMap.get(Integer.parseInt(rege[1])) == null) {
				this.addDoctor(rege[2].trim(), rege[3].trim(), rege[4].trim(), Integer.parseInt(rege[1].trim()), rege[5].trim());
				count += 1;
			}			
		}
		
		return count;
	}


	/**
	 * Loads data about doctors and patients from the given stream.
	 * <p>
	 * The text file is organized by rows, each row contains info about
	 * either a patient or a doctor.</p>
	 * <p>
	 * Rows containing a patient's info begin with letter {@code "P"} followed by first name,
	 * last name, and SSN. Rows containing doctor's info start with letter {@code "M"},
	 * followed by badge ID, first name, last name, SSN, and speciality.<br>
	 * The elements on a line are separated by the {@code ';'} character possibly
	 * surrounded by spaces that should be ignored.</p>
	 * <p>
	 * In case of error in the data present on a given row, the method calls the
	 * {@link ErrorListener#offending} method passing the line itself,
	 * ignores the row, and skip to the next one.<br>
	 * 
	 * @param reader reader linked to the file to be read
	 * @param listener listener used for wrong line notifications
	 * @throws IOException in case of IO error
	 */
	public int loadData(Reader reader, ErrorListener listener) throws IOException {
		int count = 0;
		int ch = reader.read();;
		String str = "";
		
		while(ch != -1) {
			str = str + (char) ch;
			ch = reader.read();
		}
		
		String[] lis = str.trim().split("\n");
		
		for(String s:lis) {
			String[] rege = s.trim().split(";");
			
			if(rege[0].equals("P")) {
				try {
					this.addPatient(rege[1], rege[2], rege[3]);
					count += 1;
				}catch(Exception e) {
					listener.offending(s);
					
				}
			}else if(rege[0].equals("M")) {
				try {
					this.addDoctor(rege[2], rege[3], rege[4], Integer.parseInt(rege[1]), rege[5]);
					count += 1;
				}catch(Exception e) {
					listener.offending(s);
				}
			}else {
				listener.offending(s);
				}
			}
		return count;
	}

	
	
	/**
	 * Retrieves the collection of doctors that have no patient at all.
	 * The doctors are returned sorted in alphabetical order
	 * 
	 * @return the collection of doctors' ids
	 */
	public Collection<Integer> idleDoctors(){
		
		List<Integer> docList = this.doctorMap.keySet().stream().filter(s-> this.docToPatientMap.get(s) == null).toList();

		return docList;
	}

	/**
	 * Retrieves the collection of doctors having a number of patients larger than the average.
	 * 
	 * @return  the collection of doctors' ids
	 */
	public Collection<Integer> busyDoctors(){
		ArrayList<Integer> docList = new ArrayList<>();
		int doc=0, pat=0;
		
		for(int n: this.docToPatientMap.keySet()) {
			doc += 1;
			pat += this.docToPatientMap.get(n).size();
		}
		
		long ave = pat/doc;
		
		for(int n: this.docToPatientMap.keySet()) {
			if(ave<= this.docToPatientMap.get(n).size()) {
				docList.add(n);
			}
		}
		
		return docList;
	}

	/**
	 * Retrieves the information about doctors and relative number of assigned patients.
	 * <p>
	 * The method returns list of strings formatted as "{@code ### : ID SURNAME NAME}" where {@code ###}
	 * represent the number of patients (printed on three characters).
	 * <p>
	 * The list is sorted by decreasing number of patients.
	 * 
	 * @return the collection of strings with information about doctors and patients count
	 */
	public Collection<String> doctorsByNumPatients(){
		ArrayList<String> sol = new ArrayList<>();
		
		for(int n: this.doctorMap.keySet()) {
			if(this.docToPatientMap.get(n) != null) {
				if(this.docToPatientMap.get(n).size()<10) {
					sol.add("00" + this.docToPatientMap.get(n).size() + " : " + this.doctorMap.get(n).getBadge() + " " + this.doctorMap.get(n).getSurname() + " " + this.doctorMap.get(n).getName());
				}else if(this.docToPatientMap.get(n).size()>10 && this.docToPatientMap.get(n).size()<100) {
					sol.add("0" + this.docToPatientMap.get(n).size() + " : " + this.doctorMap.get(n).getBadge() + " " + this.doctorMap.get(n).getSurname() + " " + this.doctorMap.get(n).getName());
				}else {
					sol.add(this.docToPatientMap.get(n).size() + " " + this.doctorMap.get(n).getBadge() + " " + this.doctorMap.get(n).getSurname() + " " + this.doctorMap.get(n).getName());
				}
			}else {
				sol.add("000 : " + this.doctorMap.get(n).getBadge() + " " + this.doctorMap.get(n).getSurname() + " " + this.doctorMap.get(n).getName());
			}
		}
		
		
		return sol.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
	}
	
	/**
	 * Retrieves the number of patients per (their doctor's)  speciality
	 * <p>
	 * The information is a collections of strings structured as {@code ### - SPECIALITY}
	 * where {@code SPECIALITY} is the name of the speciality and 
	 * {@code ###} is the number of patients cured by doctors with such speciality (printed on three characters).
	 * <p>
	 * The elements are sorted first by decreasing count and then by alphabetic speciality.
	 * 
	 * @return the collection of strings with speciality and patient count information.
	 */
	public Collection<String> countPatientsPerSpecialization(){
		HashMap <String, Integer> test = new HashMap<>();
		
		for(Integer n: this.doctorMap.keySet()) {
			if(this.docToPatientMap.get(n) != null) {
				if(test.keySet().contains(this.doctorMap.get(n).getSpec()) == false) {
					test.put(this.doctorMap.get(n).getSpec(), this.docToPatientMap.get(n).size());
				}else {
					test.replace(this.doctorMap.get(n).getSpec(), test.get(this.doctorMap.get(n).getSpec())+this.docToPatientMap.get(n).size());
				}
			}
		}
		List<String> end = test.entrySet().stream().map(e-> String.format("%3d - %s", e.getValue(), e.getKey())).collect(Collectors.toList());
		
		return end.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
	}

}
