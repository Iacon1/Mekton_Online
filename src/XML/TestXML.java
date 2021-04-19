//By Iacon1
//Created 4/18/2021
//Testbed for XML

package XML;

public class TestXML {

	public static void main(String[] args) {
		XMLObj obj = new XMLObj();
		obj.loadFromFile("amogus.xml");
		System.out.println(obj.getAttr("susVal"));
		System.out.println(obj.getChild("red").getAttr("isImp"));
		XMLObj blueObj = obj.getChild("red");
		blueObj.setAttr("isImp", "no");
		obj.setChild("blue", blueObj);
		System.out.println(obj.getChild("blue").getAttr("isImp"));
		obj.saveToFile("amogus2.xml");
	}

}
