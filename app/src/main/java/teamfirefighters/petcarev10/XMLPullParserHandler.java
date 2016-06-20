package teamfirefighters.petcarev10;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by karm on 17/6/16.
 */
public class XMLPullParserHandler {
    List<Card> Cards;
    private Card card;
    private String text;
    List<String> foo;
        public XMLPullParserHandler(){
        Cards = new ArrayList<Card>();
    }

    public List<Card> getCards(){return Cards;}
    public List<Card> parse(InputStream is) {
        XmlPullParserFactory factory = null;
        XmlPullParser parser = null;
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            parser = factory.newPullParser();

            parser.setInput(is, null);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();
                if(tagname!=null)
                {
                    Log.d("Tag",tagname);}
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("card")) {
                            // create a new instance of employee
                            card = new Card();
                        }else if(tagname.equalsIgnoreCase("list")){
                            foo=new ArrayList<String>();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("card")) {
                            // add employee object to list
                            Cards.add(card);
                            Log.d("Card: ",card.toString());
                        } else if (tagname.equalsIgnoreCase("title")) {
                            card.setTitle(text);
                        } else if (tagname.equalsIgnoreCase("text")) {
                            card.setText(text);
                        } else if (tagname.equalsIgnoreCase("image")) {
                            card.setImage(text);
                        } else if (tagname.equalsIgnoreCase("classification")) {
                            card.setClassification(text);
                        } else if (tagname.equalsIgnoreCase("subclassification")) {
                            card.setSubclassification(text);
                        } else if (tagname.equalsIgnoreCase("subsubclassification")) {
                            card.setSubsubclassification(text);
                        } else if (tagname.equalsIgnoreCase("list")) {
                            card.setList(foo);
                        } else if(tagname.equalsIgnoreCase("li")){
                            foo.add(text);
                        }

                        break;

                    default:
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Cards;
    }
}

