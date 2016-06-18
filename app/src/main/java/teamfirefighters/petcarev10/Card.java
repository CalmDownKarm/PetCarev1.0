package teamfirefighters.petcarev10;

import java.util.List;

/**
 * Created by karm on 17/6/16.
 */
public class Card {

    private String text;
    private String title;
    private String image;
    private String classification;
    private String subclassification;
    private String subsubclassification;
    List<String> list;

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
