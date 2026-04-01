import com.example.feedback.DBUtil;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DBUtilTest {

    @Test
    public void testConnection() {
        assertDoesNotThrow(() -> {
            DBUtil.getConnection();
        });
    }
}