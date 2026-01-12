package projet.soa.fr.Rules_MS;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Rules_Scheduler {

    private final Rules rules;

    public Rules_Scheduler(Rules rules) {
        this.rules = rules;
    }

    // Exécution toutes les 10 secondes
    @Scheduled(fixedRate = 10000)
    public void runRules() {
        String result = rules.evaluateByRoomId(1);
        System.out.println("Rules executed : " + result);
        result = rules.evaluateByRoomId(2);
        System.out.println("Rules executed : " + result);
        result = rules.evaluateByRoomId(3);
        System.out.println("Rules executed : " + result);
    }
}
