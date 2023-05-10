package examples;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;

import br.com.inmetrics.TeamsUtils;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ExamplesTest {

    private static final Logger log = LoggerFactory.getLogger(ExamplesTest.class);

    @Test
    void testParallel() {

        String threadsProperty = System.getProperty("threads");

        int numberOfThreads = 1;

        // Obter a quantidade de Threads
        if (threadsProperty != null) {
            try {
                numberOfThreads = Integer.parseInt(threadsProperty);
            } catch (NumberFormatException e) {
                log.warn("Erro ao obter o parametro de quantidade de Threads");
                log.warn(e.toString());
            }
        }

        // Executa os testes e gera o relatório
        Results results = Runner.path("classpath:examples")
                .outputCucumberJson(true)
                .parallel(numberOfThreads);

        // Envia mensagem via Microsoft Teams
        try {
            new TeamsUtils().sendTeamsMessage(results, results.getFailCount() > 0);
        } catch (IOException e) {
            log.warn("Erro ao enviar mensagem via Microsoft Teams");
            log.warn(e.toString());
        }

        assertEquals(0, results.getFailCount(), results.getErrorMessages());
    }
}
