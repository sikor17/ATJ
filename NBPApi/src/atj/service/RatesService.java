package atj.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import atj.model.ExchangeRatesSeries;

public class RatesService {

	public RatesService() {

	}

	public ExchangeRatesSeries unmarshaller(String table, String code, int topCount)
			throws MalformedURLException, IOException, ProtocolException, JAXBException {
		String urlString = "http://api.nbp.pl/api/exchangerates/rates/" + table + "/" + code + "/last/" + topCount
				+ "/?format=xml";
		URL url = new URL(urlString);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "application/xml");
		connection.connect();
		InputStream xml = connection.getInputStream();
		JAXBContext jaxbContext = JAXBContext.newInstance(ExchangeRatesSeries.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		ExchangeRatesSeries rates = (ExchangeRatesSeries) jaxbUnmarshaller.unmarshal(xml);
		connection.disconnect();
		return rates;
	}
	

}
