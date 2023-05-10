package br.com.inmetrics;

import java.util.List;
import java.util.stream.Collectors;

import com.intuit.karate.core.ScenarioResult;
import com.intuit.karate.core.Tag;

/**
 * Classe utilitária do Karate<BR>
 *
 * @since 17 de out de 2022 18:15:00
 * @author Renato Vieira<BR>
 *         Inmetrics<BR>
 */
public class KarateUtils {

    public static String getFeatureName(ScenarioResult scenarioResult) {
        return scenarioResult.getScenario().getFeature().getName();
    }

    public static String getScenarioName(ScenarioResult scenarioResult) {
        return scenarioResult.getScenario().getName();
    }

    public static String getResponsibleName(ScenarioResult scenarioResult) {
        return scenarioResult.getScenario()
                .getTags()
                .stream()
                .filter(tag -> tag.toString().contains("@#"))
                .collect(Collectors.toList())
                .get(0)
                .toString()
                .replace("@#", "");
    }

    public static boolean isErrorMessage(ScenarioResult scenarioResult) {
        try {
            scenarioResult.getError().getMessage();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getErrorMessage(ScenarioResult scenarioResult) {
        try {
            final String message = scenarioResult.getError().getMessage();
            return message;
        } catch (Exception e) {
            return "N/A";
        }
    }

    public static List<Tag> getResponsibles(ScenarioResult scenarioResult) {

        return scenarioResult.getScenario().getTags().stream()
                .filter(tag -> tag.toString().contains("@#")).collect(Collectors.toList());
    }
}
