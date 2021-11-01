/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laptq.sax;

import java.util.ArrayList;
import java.util.List;
import laptq.dtos.CakeDTO;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Taco
 */
public class CakeHandler extends DefaultHandler {

    private int from, to;
    private String currentTagName;
    private List<CakeDTO> result;
    private CakeDTO tmp;

    public List<CakeDTO> getResult() {
        return result;
    }

    public CakeHandler(String from, String to) {
        this.from = Integer.parseInt(from);
        this.to = Integer.parseInt(to);
        this.result = new ArrayList<>();
        this.tmp = new CakeDTO();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentTagName = qName;
        if(qName.equals("cake")){
            tmp.setIsAvailable(false);
        }
        if (qName.equals("id")) {
            tmp.setIsAvailable(attributes.getValue("isAvailable").equals("true"));
            tmp.setCookingTime(Integer.parseInt(attributes.getValue("cookingTime")));
        }

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (tmp.isIsAvailable()) {
            String str = new String(ch, start, length);
            switch (currentTagName) {
                case "name":
                    tmp.setName(str);
                    break;
                case "price":
                    tmp.setPrice(Integer.parseInt(str));
                    break;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        currentTagName = "";
        if (qName.equals("cake")) {
            if (tmp.isIsAvailable() && tmp.getCookingTime() >= from && tmp.getCookingTime() <= to) {
                result.add(tmp);
                tmp = new CakeDTO();
            }
        }

    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

}
