package jp.co.ebase.parser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import jp.co.ebase.model.MappinModel;

public class XmlReader
{
	List<MappinModel> mList = new ArrayList<MappinModel>();
	public List<MappinModel> domRead(String file) throws SAXException, IOException, ParserConfigurationException
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = factory.newDocumentBuilder();
		Document document = documentBuilder.parse(file);
		Element root = document.getDocumentElement();
		//ルート要素の子ノードを取得する
		NodeList rootChildren = root.getChildNodes();

		for(int i=0; i < rootChildren.getLength(); i++)
		{
			MappinModel mm =  new MappinModel();
			Node node = rootChildren.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE)
			{
				Element element = (Element)node;
				if (element.getNodeName().equals("URL"))
				{
					NodeList personChildren = node.getChildNodes();
					for (int j=0; j < personChildren.getLength(); j++)
					{
						Node personNode = personChildren.item(j);
						if (personNode.getNodeType() == Node.ELEMENT_NODE)
						{
							if (personNode.getNodeName().equals("PATH"))
							{
								mm.setPath(personNode.getTextContent());
							}
							else if (personNode.getNodeName().equals("TYPE"))
							{
								mm.setType(personNode.getTextContent());
							}
							else if (personNode.getNodeName().equals("FORM"))
							{
								mm.setForm(personNode.getTextContent());
							}
							else if (personNode.getNodeName().equals("SUCCESS"))
							{
								mm.setSuccess(personNode.getTextContent());
							}
							else if (personNode.getNodeName().equals("INPUT"))
							{
								mm.setInput(personNode.getTextContent());
							}
							else if (personNode.getNodeName().equals("ICONS_MATRIX"))
							{
								mm.setIcons_matrix(personNode.getTextContent());
							}
						}
					}
				}
			}
			if(mm.getPath()!=null && !mm.getPath().equals(""))
			{
				mList.add(mm);
			}
		}
		return mList;
	}
}
