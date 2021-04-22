//By Iacon1
//Created 4/18/2021
//Wrapper for XML stuff

package XML;

import Utils.*;

import org.w3c.dom.*;

import javax.xml.parsers.*;

import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import java.io.File;
import java.io.FileWriter;

public class XMLObj
{
	private Document doc_;
	private Element element_;
	
	static private DocumentBuilderFactory dFactory_ = DocumentBuilderFactory.newInstance();
	static private TransformerFactory tFactory_ = TransformerFactory.newInstance();
	
	public XMLObj() // Instantiates with null doc_ & element_; Don't leave these null!
	{
	}
	public XMLObj(Document doc, String tag) // Instantiates as an element of doc w/ the specified tag
	{
		doc_ = doc;
		element_ = doc_.createElement(tag);
	}
	
	public void loadFromFile(String path) // Loads from rel. path
	{
		try
		{
			DocumentBuilder builder = dFactory_.newDocumentBuilder();
			
			doc_ = builder.parse(new File(path));
			
			element_ = doc_.getDocumentElement();
		}
		catch (Exception e) {Logging.logException(e);}
	}
	public void saveToFile(String path) // Saves to rel. path
	{
		try
		{
			doc_.replaceChild(element_, doc_.getDocumentElement());
			
			DOMSource source = new DOMSource(doc_);
			FileWriter writer = new FileWriter(new File(path));
			StreamResult result = new StreamResult(writer);
			
			Transformer transformer = tFactory_.newTransformer();
			transformer.transform(source, result);
		}
		catch (Exception e) {Logging.logException(e);}
	}
	
	public XMLObj getChild(String tag) // Gets child by tag. Assumes only one element per tag
	{
		XMLObj obj = new XMLObj();
		
		obj.doc_ = doc_;
		obj.element_ = (Element) element_.getElementsByTagName(tag).item(0);
		obj.element_ = (Element) obj.element_.cloneNode(false);
		
		return obj;
	}
	public void setChild(String tag, XMLObj obj) // Adds a child with the tag
	{
		NodeList list = element_.getElementsByTagName(tag);
		if (list.getLength() != 0) {element_.removeChild(list.item(0));} // If something already has that tag, remove it
		
		Node newNode = doc_.adoptNode(obj.element_);
		doc_.renameNode(newNode, null, tag);
		element_.appendChild(newNode);
	}
	
	public String getAttr(String name) // Gets attribute w/ corresponding name
	{
		return element_.getAttribute(name);
	}
	public void setAttr(String name, String value) // Sets attribute with the name to the value
	{
		element_.setAttribute(name,  value);
	}
}
