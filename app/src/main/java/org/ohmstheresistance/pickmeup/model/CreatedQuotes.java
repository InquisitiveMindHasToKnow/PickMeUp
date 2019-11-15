package org.ohmstheresistance.pickmeup.model;

public class CreatedQuotes {

    private String createdQuote;
    private String dateCreated;

    public CreatedQuotes() {

    }

    public CreatedQuotes(String createdQuote, String dateCreated) {
        this.createdQuote = createdQuote;
        this.dateCreated = dateCreated;
    }

    public String getCreatedQuote() {
        return createdQuote;
    }

    public void setCreatedQuote(String createdQuote) {
        this.createdQuote = createdQuote;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "Country{" +
                "ccreated_quote=" + createdQuote +
                ", date_created='" +dateCreated + '\'' +
                '}';
    }

    public static CreatedQuotes from(String quote, String date) {

        CreatedQuotes createdQuotes = new CreatedQuotes();
        createdQuotes.createdQuote = quote;
        createdQuotes.dateCreated = date;
        return createdQuotes;
    }
}
