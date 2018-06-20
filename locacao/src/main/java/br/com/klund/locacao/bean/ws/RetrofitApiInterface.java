package br.com.klund.locacao.bean.ws;

import br.com.klund.locacao.modelo.webservice.CNPJ_RWS;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitApiInterface {
		
	@GET("v1/cnpj/{cnpj}")
	Call<CNPJ_RWS> getCNPJ(@Path("cnpj") String cnpj); 

}
