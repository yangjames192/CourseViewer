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

import android.util.Log;


/**
 * Information about a Course offered.
 *
 */
public class CourseInfo {
	private String subject;
	private String catalog;
	private String title;
	private String units;
	//private String des;
	private String section;
	private int enrollment_capacity;
	private int enrollment_total;
	private int waiting_capacity;
	private int waiting_total;
	private String topic;
	private String academic_level;
	//private Reserves reserves;
	private Classes classes;
	
	//private String url;
	
	/** Construct a course from provided data.
	 * 
	 * @param sub  Subject (e.g. "CS")
	 * @param cat  Catalog number (e.g. "349" or "129R")
	 * @param ttl  Title (e.g. "User Interfaces")
	 * @param sections	An array of sections offered for this course.
	 */
	public CourseInfo(String sub, String cat, String ttl, String u, String sec,
			int ec, int et, int wc, int wt, String topic,
			Classes c, String level) {
		this.subject = sub;
		this.catalog = cat;
		this.title = ttl;
		this.units = u;
		this.section = sec;
		this.enrollment_capacity = ec;
		this.enrollment_total = et;
		this.waiting_capacity = wc;
		this.waiting_total = wt;
		this.topic = topic;
		/*this.reserves = r;*/
		this.classes = c;
		this.academic_level = level;
	}

	/** Get this course's subject. */
	public String getSubject() {
		return subject;
	}

	/** Get this course's catalog number. */
	public String getCatalog() {
		return catalog;
	}

	/** Get this course's title. */
	public String getTitle() {
		return title;
	}
	
	public String getUnits() {
		return this.units;
	}
	
	/** Return a string representing this course. */
	public String courseInfo1() {
		
		return this.subject + " " + this.catalog + "     " + this.units + " units" + "\n"
				+ this.title + "\n" + this.academic_level;
	}
	
	/** Return a string representing this course. */
	public String courseInfo2() {
		String sec = this.section.trim();
		if (sec.isEmpty())
			sec = "TBA";

		String ins = "";
		if(this.classes.getInstructors() == null) Log.w("message ", "null");
		if(this.classes != null) ins = this.classes.getInstructors().get(0).trim();
		if (ins.isEmpty()) 
			ins = "TBA";
		
		String build = this.classes.getLocation().getBuilding().trim();
		if (build.isEmpty())
			build = "TBA";

		String room = this.classes.getLocation().getRoom().trim();
		if (room.isEmpty())
			room = "TBA";
		
		//section
		String section = sec + "     " + ins + "\n";

		//location
		String loc = build + " " + room + "     ";
		
		//time
		String st = this.classes.getDates().getStartTime().trim();
		String time = "";
		if(st.isEmpty()) {
			time = "TBA";
		} else {
			time = st + "-"	+ this.classes.getDates().getEndTime() + this.classes.getDates().getWeekdays() + " "
					+ this.classes.getDates().getStartDate();
		}
		
		return section + loc + time;
	}
	
	/**
	 * Read the list of courses from the web service and parse it 
	 * into a list of Course objects.
	 * @return
	 */
	public static List<CourseInfo> coursesFactory(String subject, String catalog, int term, boolean isNum) {
		List<CourseInfo> lst = new LinkedList<CourseInfo>();
		String url = "https://api.uwaterloo.ca/v2/terms/"+term+"/" + subject;
		if(isNum) {
			url = url +"/"+catalog;
		}
		url = url + "/schedule.xml?key=423f3f28629748e7329303da051ec178";
		//get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {

			//get a new instance of parser
			SAXParser sp = spf.newSAXParser();

			//parse the input and also register a private class for call backs
			sp.parse(url, new CourseParserCallBacksModel(lst));

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


/** 
 * A class providing the callbacks necessary to parse the list of courses.
 * @author bwbecker
 *
 */
class CourseParserCallBacksModel extends DefaultHandler {
	private List<CourseInfo> lst;
	
	private String tmpVal;
	private String ins;
	
	// values to add to the course currently being parsed.
	private String subject;
	private String catalog;
	private String title;
	private String units;
	//private String des;
	private String section;
	private int enrollment_capacity;
	private int enrollment_total;
	private int waiting_capacity;
	private int waiting_total;
	private String topic;
	private String academic_level;
	
	//for date
	private String startTime;
	private String endTime;
	private String weekdays;
	private String startDate;
	private String endDate;
	
	//for location
	private String building;
	private String room;
	
	private Location location;
	private Classes classes;
	private Dates date;
	
	private List<String> instructors;
	
	private boolean isSet;
	
	public CourseParserCallBacksModel(List<CourseInfo> lst) {
		this.lst = lst;
		isSet = false;
		instructors = new LinkedList<String>();
	}
	
	public void startElement(String uri, 
							String localName,
							String qName, 
							Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase("item")) {
			
		}
		
		if (qName.equalsIgnoreCase("instructors")) {
			instructors = new LinkedList<String>();
		}
	}
	
	public void characters(char[] ch, int start, int length) throws SAXException {
		this.tmpVal = new String(ch, start, length);
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equalsIgnoreCase("item")) {
			if(isSet) { 
				CourseInfo c = new CourseInfo(this.subject, this.catalog,
						this.title, this.units, this.section,
						this.enrollment_capacity, this.enrollment_total,
						this.waiting_capacity, this.waiting_total, this.topic,
						this.classes, this.academic_level);
				if(!this.lst.contains(c)) this.lst.add(c);
			}
			this.ins = this.tmpVal;
			isSet = false;
		}  else if (qName.equalsIgnoreCase("date")) { 
			this.date = new Dates(this.startTime, this.endTime, this.weekdays, this.startDate, this.endDate);
		} else if (qName.equalsIgnoreCase("location")) { 
			this.location = new Location(this.building, this.room);
		} else if (qName.equalsIgnoreCase("classes")) { 
			this.classes = new Classes(this.date, this.location, this.instructors);
		} else if (qName.equalsIgnoreCase("subject")) {
			this.subject = this.tmpVal;
		} else if (qName.equalsIgnoreCase("catalog_number")) {
			this.catalog = this.tmpVal;
		} else if (qName.equalsIgnoreCase("title")) {
			this.title = this.tmpVal;
		} else if (qName.equalsIgnoreCase("units")) {
			this.units = this.tmpVal;
		} else if (qName.equalsIgnoreCase("section")) {
			this.section = this.tmpVal;
		} else if (qName.equalsIgnoreCase("academic_level")) {
			this.academic_level = this.tmpVal;
		} else if (qName.equalsIgnoreCase("last_updated")) {
			isSet = true;
		} else if (qName.equalsIgnoreCase("start_time")) {
			this.startTime = this.tmpVal;
		} else if (qName.equalsIgnoreCase("end_time")) {
			this.endTime = this.tmpVal;
		} else if (qName.equalsIgnoreCase("weekdays")) {
			this.weekdays = this.tmpVal;
		} else if (qName.equalsIgnoreCase("start_date")) {
			this.startDate = this.tmpVal;
		} else if (qName.equalsIgnoreCase("end_date")) {
			this.endDate = this.tmpVal;
		} else if (qName.equalsIgnoreCase("building")) {
			this.building = this.tmpVal;
		} else if (qName.equalsIgnoreCase("room")) {
			this.room = this.tmpVal;
		} else if (qName.equalsIgnoreCase("enrollment_capacity")) {
			this.enrollment_capacity = 0;
			/*if(this.tmpVal == null) {
				this.enrollment_capacity = 0;
			} else {
				this.enrollment_capacity = Integer.parseInt(this.tmpVal);
			}*/
		} else if (qName.equalsIgnoreCase("enrollment_total")) {
			this.enrollment_total = 0;
			/*if(this.tmpVal == null) {
				this.enrollment_total = 0;
			} else {
				this.enrollment_total = Integer.parseInt(this.tmpVal);
			}*/
		} else if (qName.equalsIgnoreCase("waiting_capacity")) {
			this.waiting_capacity = 0;
			/*if(this.tmpVal == null) {
				this.waiting_capacity = 0;
			} else {
				this.waiting_capacity = Integer.parseInt(this.tmpVal);
			}*/
		} else if (qName.equalsIgnoreCase("waiting_total")) {
			this.waiting_total = 0;
			/*if(this.tmpVal == null) {
				this.waiting_total = 0;
			} else {
				this.waiting_total = Integer.parseInt(this.tmpVal);
			}*/
		} else if (qName.equalsIgnoreCase("instructors")) {
			instructors.add(this.ins);
		} else if (qName.equalsIgnoreCase("topic")) {
			this.topic = this.tmpVal;
		} else {
			
		}
	}
}
