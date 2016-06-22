package teamfirefighters.petcarev10;

import android.provider.BaseColumns;

/**
 * Created by karm on 17/6/16.
 */
public class CardDBContract {
    public CardDBContract(){}
    public static final String SHARED_PREFERENCES_KEY = "petcareflags";
    //empty constructor to prevent instantiation
    public static abstract class CardTable implements BaseColumns {
        public static final String TABLE_NAME="cards";
        public static final String COLUMN_NAME_CARD_ID="cardid";
        public static final String COLUMN_NAME_CARD_TITLE="cardtitle";
        public static final String COLUMN_NAME_CARD_IMAGE="cardimage";
        public static final String COLUMN_NAME_CLASSIFICATION="classification";
        public static final String COLUMN_NAME_SUBCLASSIFICATION="subclassification";
        public static final String COLUMN_NAME_SUBSUBCLASSIFICATION="subsubclassification";
        public static final String COLUMN_NAME_CARD_TEXT="cardtext";
        public static final String COLUMN_NAME_CARD_LIST="cardlist";
        public static final String COLUMN_NAME_CARD_POSITION="pos";
    }/*
    public static abstract class Classifications implements BaseColumns{
        public static final String TABLE_NAME="classifications";
        public static final String COLUMN_NAME_CLASS_ID = "classid";
        public static final String COLUMN_NAME_CLASSIFICATION="classification";

    }
    public static abstract class SubClassifications implements BaseColumns{
        public static final String TABLE_NAME="subclassifications";
        public static final String COLUMN_NAME_CLASS_ID = "subclassid";
        public static final String COLUMN_NAME_SUBCLASSIFICATION="subclassification";
        public static final String COLUMN_NAME_SUBCLASS_IMAGE = "image";

    }
    public static abstract class SubSubClassifications implements BaseColumns{
        public static final String TABLE_NAME="subsubclassifications";
        public static final String COLUMN_NAME_CLASS_ID = "subsubclassid";
        public static final String COLUMN_NAME_SUBSUBCLASSIFICATION="subsubclassification";

    }*/
}
