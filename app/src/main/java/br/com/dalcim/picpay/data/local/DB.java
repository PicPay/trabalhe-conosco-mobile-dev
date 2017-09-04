package br.com.dalcim.picpay.data.local;

/**
 * @author Wiliam
 * @since 03/09/2017
 */

public final class DB {

    private DB(){}

    public final static class  CREDIT_CARD{
        private CREDIT_CARD(){}

        public static String TABLE_NAME = "CREDIT_CARD";
        public static String CARD_NUMBER = "CARD_NUMBER";
        public static String CVV = "CVV";
        public static String EXPIRY_DATE = "EXPIRY_DATE";

        public static String createQuery(){
            StringBuilder stb = new StringBuilder();

            stb.append("CREATE TABLE IF NOT EXISTS ")
                    .append(TABLE_NAME)
                    .append(" (")
                    .append(CARD_NUMBER).append(" CHAR(16) PRIMARY KEY, ")
                    .append(EXPIRY_DATE).append(" CHAR(5), ")
                    .append(CVV).append(" INTEGER)");
            return stb.toString();
        }
    }
}
