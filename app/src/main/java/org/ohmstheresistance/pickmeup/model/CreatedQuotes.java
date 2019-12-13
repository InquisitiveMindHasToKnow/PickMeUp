package org.ohmstheresistance.pickmeup.model;

public class CreatedQuotes {

    private int _id;
    private String createdQuote;
    private String dateCreated;

    public CreatedQuotes() {

    }

    public CreatedQuotes(int _id, String createdQuote, String dateCreated) {
        this._id = _id;
        this.createdQuote = createdQuote;
        this.dateCreated = dateCreated;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
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
        return "Quote{" +
                "_id = " + _id +
                "created_quote=" + createdQuote +
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
