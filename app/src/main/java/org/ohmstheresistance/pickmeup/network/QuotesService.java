package org.ohmstheresistance.pickmeup.network;

import org.ohmstheresistance.pickmeup.model.Quotes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QuotesService {

    @GET("InquisitiveMindHasToKnow/5c531b75cbd2ca94a35f1b3fcef4e9d5/raw/903a29ee00c9017b5d802f7cedc2fc7e7039320c/MotivationalQuotes")
    Call<List<Quotes>> getQuotes();
}
