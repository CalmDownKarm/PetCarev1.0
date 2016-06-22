package teamfirefighters.petcarev10;

import android.database.Cursor;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

/**
 * Created by karm on 17/6/16.
 */
public class Card {
    private int CardLayoutType; //Card Layout Type should be 1,2,3,4 Too sleepy to write enum
    private String text;
    private String title;
    private String image;
    List<String> list;
    private String classification;
    private String subclassification;
    private String subsubclassification;

    public Card(){
        //DEFAULT CONSTRUCTOR SET EVERYTHING TO NULL
        text = null;
        title = null;
        image = null;
        list = null;
        classification = null;
        subclassification = null;
        subsubclassification = null;

    }

    public Card(Cursor c) {
        text  = c.getString(c.getColumnIndexOrThrow(CardDBContract.CardTable.COLUMN_NAME_CARD_TEXT));
        title = c.getString(c.getColumnIndexOrThrow(CardDBContract.CardTable.COLUMN_NAME_CARD_TITLE));
        image = c.getString(c.getColumnIndexOrThrow(CardDBContract.CardTable.COLUMN_NAME_CARD_IMAGE));
        String tempStr = c.getString(c.getColumnIndexOrThrow(CardDBContract.CardTable.COLUMN_NAME_CARD_LIST));

        if(tempStr!=null) {
            Log.d("PrintList",tempStr);
            list = Arrays.asList(tempStr.split("\\s*#\\s*"));
        }else
            list = null;
        classification = c.getString(c.getColumnIndexOrThrow(CardDBContract.CardTable.COLUMN_NAME_CLASSIFICATION));
        subclassification = c.getString(c.getColumnIndexOrThrow(CardDBContract.CardTable.COLUMN_NAME_SUBCLASSIFICATION));
        subsubclassification = c.getString(c.getColumnIndexOrThrow(CardDBContract.CardTable.COLUMN_NAME_SUBSUBCLASSIFICATION));
        if(title==null)
            title=subsubclassification;
        setCardLayoutType();
    }


    public void setCardLayoutType(){
        if(     text!=null   &&
                title!=null  &&
                image!=null  &&
                list!=null)
            CardLayoutType=1;//Card Layout Type 1 has everything.
        else if(text==null&&image==null)
            CardLayoutType=2;//Card Layout Type 2 should have only Title and List
        else if(image==null&&list==null)
            CardLayoutType=3;//Card Layout Type 3 should have only Title and Text
        else if(list==null)
            CardLayoutType=4;//Card Layout Type 4 has image, title and text
    }

    public int getCardLayoutType(){return CardLayoutType;}

    public void setList(List<String>foo){
        this.list=foo;
    }
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getSubclassification() {
        return subclassification;
    }

    public void setSubclassification(String subclassification) {
        this.subclassification = subclassification;
    }

    public String getSubsubclassification() {
        return subsubclassification;
    }

    public void setSubsubclassification(String subsubclassification) {
        this.subsubclassification = subsubclassification;
    }
    public String getListAsString(){
        String strtemp = "";
        for(int i=0;i<list.size();i++){
            strtemp+=(list.get(i)+"#");
        }
        Log.d("OnStorage",strtemp);
        return strtemp;
    }
    public List<String> getList() {
        return list;
    }
    @Override
    public String toString(){
        String temp="";
        if(title!=null)
            temp+=("TITLE: "+title);
        if(text!=null)
            temp+=("TXT: "+text);
        if(image!=null)
            temp+=("IMG: "+image);
        if(classification!=null)
            temp+=("CLASS: "+classification);
        if( subclassification!=null)
            temp+=("SUBCLASS: "+subclassification);
        if(subsubclassification!=null)
            temp+=("SUBSUBCLASS: "+subsubclassification);
        return temp;
    }
}
