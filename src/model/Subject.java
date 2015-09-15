package model;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 * Information about a Course offered.
 *
 */
public class Subject {
	private String name;
	
	/** Construct a terms list.
	 */
	public Subject(String s) {
		name = s;
	}
	
	/**
	 * Read the list of courses from the web service and parse it 
	 * into a list of Course objects.
	 * @return
	 */
	public static List<String> listSubjects() {
		List<String> lst = new LinkedList<String>();
		String url = "https://api.uwaterloo.ca/v2/codes/subjects.xml?key=423f3f28629748e7329303da051ec178";
		//get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {

			//get a new instance of parser
			SAXParser sp = spf.newSAXParser();

			//parse the input and also register a private class for call backs
			sp.parse(url, new SubjectParserCallBacksModel(lst));

		}catch(SAXException se) {
			se.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (IOException ie) {
			ie.printStackTrace();
		}
		
		return lst;
	}
}


class SubjectParserCallBacksModel extends DefaultHandler {
	private String tmpVal;
	private List<String> lst;
	private String name;
	
	public SubjectParserCallBacksModel(List<String> lst) {
		this.lst = lst;
	}
	
	public void startElement(String uri, 
							String localName,
							String qName, 
							Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase("item")) {
			
		}
		
		
	}
	
	public void characters(char[] ch, int start, int length) throws SAXException {
		this.tmpVal = new String(ch, start, length);
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equalsIgnoreCase("item")) {
			lst.add(name);
		}  else if (qName.equalsIgnoreCase("subject")) { 
			name = tmpVal;
		} else {
		
		}
	}
}