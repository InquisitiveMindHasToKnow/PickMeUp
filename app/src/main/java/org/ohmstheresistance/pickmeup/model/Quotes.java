package org.ohmstheresistance.pickmeup.model;

public class Quotes {

    private String quote;
    private String saidby;

    public Quotes(String quote, String saidby) {
        this.quote = quote;
        this.saidby = saidby;
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
}
