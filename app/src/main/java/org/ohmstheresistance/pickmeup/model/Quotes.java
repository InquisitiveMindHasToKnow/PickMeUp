package org.ohmstheresistance.pickmeup.model;

public class Quotes {

    private String quote;
    private String saidby;

    public Quotes(String quote, String saidby) {
        this.quote = quote;
        this.saidby = saidby;
    }

    public Quotes() {

    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getSaidby() {
        return saidby;
    }

    public void setSaidby(String saidby) {
        this.saidby = saidby;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "favorite_quote=" + quote +
                ", favorite_quote_said_by='" +saidby + '\'' +
                '}';
    }

    public static Quotes from(String favoriteQuote, String quoteSaidBy) {
        Quotes quotes = new Quotes();
        quotes.quote = favoriteQuote;
        quotes.saidby = quoteSaidBy;
        return quotes;
    }
}
