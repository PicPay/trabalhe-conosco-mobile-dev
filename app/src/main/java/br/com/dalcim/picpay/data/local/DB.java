package br.com.dalcim.picpay.data.local;

final class DB {

    private DB(){}

    public final static class  CREDIT_CARD{
        private CREDIT_CARD(){}

        public static final String TABLE_NAME = "CREDIT_CARD";
        public static final String CARD_NUMBER = "CARD_NUMBER";
        public static final String CVV = "CVV";
        public static final String EXPIRY_DATE = "EXPIRY_DATE";

        public static String createQuery(){
            String stb = "CREATE TABLE IF NOT EXISTS " +
                    TABLE_NAME +
                    " (" +
                    CARD_NUMBER + " CHAR(16) PRIMARY KEY, " +
                    EXPIRY_DATE + " CHAR(5), " +
                    CVV + " INTEGER)";

            return stb;
        }
    }
}
