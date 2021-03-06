package cc.nexdoor.android.module;

import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class RSSSourceXMLHandler extends DefaultHandler {
	
	// ===========================================================
	// Fields
	// ===========================================================
	String TAG = "CTHome:RSSSourceXMLHandler";
	LogD log = new LogD(TAG, false);
//	private StringBuffer buf;
	
	private boolean in_channeltag = false;
	//private boolean in_itemtag = false;
	//private boolean in_titletag = false;
	//private boolean in_linktag = false;
	
	public ArrayList channelAttributesList = new ArrayList(); 
	
	private String idName;
	private String key;
//	public HashMap<String, String> item = new HashMap();
	public ArrayList channelContentsList = new ArrayList(); 


	
	// ===========================================================
	// Override Methods
	// ===========================================================
	
	@Override
	public void startDocument() throws SAXException {
//		this.buf = new StringBuffer();
	}

	@Override
	public void endDocument() throws SAXException {
	}

	@Override
	public void startPrefixMapping(String prefix, String uri) {
		log.d("Forward Mapping : " + prefix + " Start!" + "  URI : " + uri);
	}

	@Override
	public void endPrefixMapping(String prefix) {
		log.d("Forward Mapping : " + prefix + " end!");
	}
	

	// ===========================================================
	// Calling Methods
	// ===========================================================
	public void readRSSSourceXML(String input){
        try {
            // init reader
        	SAXParserFactory factory = SAXParserFactory.newInstance();
        	SAXParser parser = factory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            // Create DefaultHandler Instance
//            RSSSourceXMLHandler xmlHandler = new RSSSourceXMLHandler();
            reader.setContentHandler(this);
            reader.setErrorHandler(this);
            // Start Analyze
            URL url = new URL(input);
        	InputSource is = new InputSource(url.openStream());
            reader.parse(is);
            
			// get info
			channelAttributesList = this.channelAttributesList;
//			for (int i = 0; i < channelAttributesList.size(); i++) {
//				HashMap<String, String> hm = (HashMap) channelAttributesList.get(i);
//				for (String key : hm.keySet()) {
//					log.d(key + " : " + hm.get(key));
//				}
//			}

			// get item
			channelContentsList = this.channelContentsList;
			log.d("channelContentsList : "+channelContentsList.size());
//			for (int i = 0; i < channelContentsList.size(); i++) {
//				HashMap<String, String> hm = (HashMap) channelContentsList.get(i);
//				for (String key : hm.keySet()) {
//					log.d(key + " : " + hm.get(key));
//				}
//			}
            
            
        } catch ( IOException e ) {
            log.d("Error on Read : " + e.getMessage());
        } catch ( SAXException e ) {
            log.d("Error on Analyze : " + e.getMessage());
        } catch (ParserConfigurationException e) {
            log.d("Error on Factory : " + e.getMessage());
		} 
    }
	

	
	/*
	@Override
	public void startElement(String namespaceURI, String localName,
			String fullName, Attributes attributes) throws SAXException {
		// print element start
//		log.d("Element : " + "[" + localName + "]" + " Start Analyze!");
		
		
		
		if (localName.equals("channel")) {
			this.in_channeltag = true;
			
			// package attribute
			HashMap<String, String> attributeMap = new HashMap();
			for (int i = 0; i < attributes.getLength(); i++) {
				attributeMap.put(attributes.getLocalName(i), attributes.getValue(i));
			}
			channelAttributesList.add(attributeMap);
			
			
			idName = attributes.getValue("id");
			
		}else if (localName.equals("item")) {
			this.in_itemtag = true;
		}else if (localName.equals("title")) {
			this.in_titletag = true;
		}else if (localName.equals("link")) {
			this.in_linktag = true;
		}
		
		
		
		// print attribute info
//		for (int i = 0; i < attributes.getLength(); i++) {
//			log.d("Attribute : " + attributes.getLocalName(i) + " Value : "
//					+ attributes.getValue(i));
//		}
		
	}
	*/
	
	/* ToDo : prepare to modify 20100813 */
	@Override
	public void startElement(String namespaceURI, String localName,
			String fullName, Attributes attributes) throws SAXException {
		// print element start
//		log.d("Element : " + "[" + localName + "]" + " Start Analyze!");
		
		
		
		if (localName.equals("channel")) {
			this.in_channeltag = true;
			
			// package attribute
			HashMap<String, String> attributeMap = new HashMap();
			for (int i = 0; i < attributes.getLength(); i++) {
				attributeMap.put(attributes.getLocalName(i), attributes.getValue(i));
			}
			channelAttributesList.add(attributeMap);
			
			//log.d("url : "+attributes.getValue("url"));
			String url = attributes.getValue("url");
			getChannelItem(url);
			combineChannelItem();
			
			idName = attributes.getValue("id");
			
		}else{}
		
		
		// print attribute info
		for (int i = 0; i < attributes.getLength(); i++) {
			log.d("Attribute : " + attributes.getLocalName(i) + " : "
					+ attributes.getValue(i));
		}
		
	}
	

	public ArrayList<HashMap> channelItemAttributesList; 
	public ArrayList<HashMap> channelItemContentsList; 
	private void getChannelItem(String url){
    	RSSSourceItemXMLHandler rssSourceItem = new RSSSourceItemXMLHandler();
    	rssSourceItem.readRSSSourceXML(url);
    	channelItemAttributesList = rssSourceItem.channelItemAttributesList;
        channelItemContentsList = rssSourceItem.channelItemContentsList;
    }
	
	private void combineChannelItem(){
		for(int i = 0 ; i < channelItemContentsList.size() ; i++){
			channelContentsList.add(channelItemContentsList.get(i));
		}
	}
	
	
	
	
	/*
	@Override
	public void endElement(String namespaceURI, String localName,
			String fullName) throws SAXException {


		if (localName.equals("channel")) {
			this.in_channeltag = false;
		}else if (localName.equals("item")) {
			this.in_itemtag = false;
		}else if (localName.equals("title")) {
			if(in_itemtag){
				key = idName + "_" + buf.toString().trim();
				buf.setLength(0);
				this.in_titletag = false;
			}
		}else if (localName.equals("link")) {
			if(in_itemtag){
				content = buf.toString().trim();
				buf.setLength(0);
				log.d(key + " : " + content );
				HashMap<String, String> item = new HashMap();
				item.put(key, content);
				channelContentsList.add(item);
				this.in_linktag = false;
			}
		} else {
			buf.setLength(0);
		}
		
		
		
		// print element end
//		log.d("Element : " + "[" + localName + "]" + " END!");
	}
	*/
	

	/* ToDo : prepare to modify 20100813 */
	@Override
	public void endElement(String namespaceURI, String localName,
			String fullName) throws SAXException {


		if (localName.equals("channel")) {
			this.in_channeltag = false;
		}else {
			buf.setLength(0);
		}
		
		// print element end
//		log.d("Element : " + "[" + localName + "]" + " END!");
	}
	
	
	


	StringBuffer buf = new StringBuffer();
	String content;
	int flag = 0;
	@Override
	public void characters(char[] chars, int start, int length)
			throws SAXException {
		//if (this.in_itemtag || this.in_channeltag) {
		if (this.in_channeltag) {
			/* 將char[]加入StringBuffer */
			buf.append(chars, start, length);
		}
		
	}

	
	
	
	
	
	@Override
	public void warning(SAXParseException exception) {
		log.d("*******WARNING******");
		log.d("line : " + exception.getLineNumber());
		log.d("row : " + exception.getColumnNumber());
		log.d("error : " + exception.getMessage());
		log.d("********************");
	}

	@Override
	public void error(SAXParseException exception) throws SAXException {
		log.d("******* ERROR ******");
		log.d("line : " + exception.getLineNumber());
		log.d("row : " + exception.getColumnNumber());
		log.d("error : " + exception.getMessage());
		log.d("********************");
	}

	public void fatalError(SAXParseException exception) throws SAXException {
		log.d("******** FATAL ERROR ********");
		log.d("line : " + exception.getLineNumber());
		log.d("row : " + exception.getColumnNumber());
		log.d("error : " + exception.getMessage());
		log.d("*****************************");
	}
}