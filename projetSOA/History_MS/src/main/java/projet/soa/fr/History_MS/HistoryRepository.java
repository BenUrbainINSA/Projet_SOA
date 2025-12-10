package projet.soa.fr.History_MS;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Integer> {

    // pour /History/Room/{roomId}
    List<History> findByRoomId(Integer roomId);
}