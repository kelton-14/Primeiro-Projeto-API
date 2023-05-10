package br.com.inmetrics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.intuit.karate.Results;
import com.intuit.karate.core.Tag;

/**
 * Classe utilitária do Microsoft Teams<BR>
 *
 * @since 17 de out de 2022 13:15:00
 * @author Amanda Belarmino / Renato Vieira<BR>
 *         Inmetrics<BR>
 */
public class TeamsUtils {

	private static final Logger log = LoggerFactory.getLogger(TeamsUtils.class);

	public void sendTeamsMessage(Results results, boolean fail) throws IOException {

		String JSON_FILE = "src\\test\\java\\utils\\data\\create_usuarios.json";

		URL url = new URL(
				"https://inmetricscorp.webhook.office.com/webhookb2/34aa896b-9da3-4e99-81db-b7bc36f3367a@53c7812f-472c-4b2a-bd47-8123b68303bf/IncomingWebhook/35bb7b6b4dc74fd28488c78af2eae16f/b158a286-137a-42e2-8243-e88298f59e61");

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");

		String input = generateStringFromResource(JSON_FILE);
		String mensagem;

		if (fail) {
			mensagem = "⚠️ Erro na execução dos cenários:\n"
					+ getErrors(results) + ".\n"
					+ "Favor checar!";
		} else {
			mensagem = "👌 Testes executados com sucesso";
		}

		input = input.replace("Erro na execução dos cenários", mensagem);

		log.debug(input);

		OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();

		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

		String output;
		log.debug("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			log.debug(output);
		}

		conn.disconnect();
	}

	public String generateStringFromResource(String path) throws IOException {

		return new String(Files.readAllBytes(Paths.get(path)));
	}

	private String getErrors(Results results) {

		StringBuffer sb = new StringBuffer();

		results.getScenarioResults().forEach((scenarioResult) -> {
			if (scenarioResult.isFailed()) {
				String feature = KarateUtils.getFeatureName(scenarioResult);
				String scenario = KarateUtils.getScenarioName(scenarioResult);

				List<Tag> responsibles = KarateUtils.getResponsibles(scenarioResult);

				sb.append("- ");
				sb.append(feature + " - " + scenario + getNameTeams(responsibles));
				sb.append("\r");
			}
		});

		return sb.toString();
	}

	private static String getNameTeams(List<Tag> tag) {

		Map<String, String> map = new HashMap<String, String>();
		map.put("renato_vieira", "renato.vieira@inmetrics.com.br");
		map.put("amanda_belarmino", "amanda.belarmino@inmetrics.com.br");

		if (tag.size() < 1) {
			return "\n*Responsável:* Tagname was not found feature ";
		}

		try {
			return "\n*Responsável:* " + map.get(tag.get(0).toString().replace("@#", "")).toString();
		} catch (Exception e) {
			return "\n*Responsável:* Tagname was not found ";
		}
	}
}
