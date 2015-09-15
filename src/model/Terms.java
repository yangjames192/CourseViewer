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
public class Terms {
	private int prevTerm;
	private int curTerm;
	private int nextTerm;
	
	/** Construct a terms list.
	 */
	public Terms() {
		this.prevTerm = 0;
		this.curTerm = 0;
		this.nextTerm = 0;
	}

	/** Get previous term. */
	public int getPrevTerm() {
		return prevTerm;
	}

	/** Get previous term. */
	public int getCurTerm() {
		return curTerm;
	}


	/** Get previous term. */
	public int getNextTerm() {
		return nextTerm;
	}
	
	public void setPrevTerm(int i) {
		this.prevTerm = i;
	}
	
	public void setCurTerm(int i) {
		this.curTerm = i;
	}
	
	public void setNextTerm(int i) {
		this.nextTerm = i;
	}
	
	
	/**
	 * Read the list of courses from the web service and parse it 
	 * into a list of Course objects.
	 * @return
	 */
	public static Terms listTerms() {
		Terms terms = new Terms();
		String url = "https://api.uwaterloo.ca/v2/terms/list.xml?key=423f3f28629748e7329303da051ec178";
		//get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {

			//get a new instance of parser
			SAXParser sp = spf.newSAXParser();

			//parse the input and also register a private class for call backs
			sp.parse(url, new TermsParserCallBacksModel(terms));

		}catch(SAXException se) {
			se.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (IOException ie) {
			ie.printStackTrace();
		}
		
		return terms;
	}
}



class TermsParserCallBacksModel extends DefaultHandler {
	private String tmpVal;
	private Terms term;
	
	public TermsParserCallBacksModel(Terms t) {
		this.term = t;
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
		if (qName.equalsIgnoreCase("next_term")) {
			term.setNextTerm(Integer.parseInt(this.tmpVal));
		
		}  else if (qName.equalsIgnoreCase("previous_term")) { 
			term.setPrevTerm(Integer.parseInt(this.tmpVal));
		
		} else if (qName.equalsIgnoreCase("current_term")) { 
			
			term.setCurTerm(Integer.parseInt(this.tmpVal));
		} else {
		
		}
	}
}
