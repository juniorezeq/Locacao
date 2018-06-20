package br.com.klund.locacao.bean.ws;


import java.io.IOException;
import java.io.Serializable;

import br.com.klund.locacao.modelo.webservice.CNPJ_RWS;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiCNPJ implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CNPJ_RWS rws;

	public CNPJ_RWS test(String cnpj) {

		okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();
		Retrofit retrofit = new Retrofit.Builder().baseUrl("http://www.receitaws.com.br/")
				.addConverterFactory(GsonConverterFactory.create()).client(client).build();
		RetrofitApiInterface apiInterface = retrofit.create(RetrofitApiInterface.class);

		Call<CNPJ_RWS> call = apiInterface.getCNPJ(cnpj);
		try {
			rws = call.execute().body();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(rws);
		return rws;

	}

	public CNPJ_RWS getRws() {
		return rws;
	}

	public void setRws(CNPJ_RWS rws) {
		this.rws = rws;
	}

}
