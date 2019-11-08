package org.ohmstheresistance.pickmeup.network;

import org.ohmstheresistance.pickmeup.model.Quotes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QuotesService {

    @GET("InquisitiveMindHasToKnow/5c531b75cbd2ca94a35f1b3fcef4e9d5/raw/8d00cf048063a399af3da6d0fbdded6c6b3b141b/MotivationalQuotes")
    Call<List<Quotes>> getQuotes();
}
