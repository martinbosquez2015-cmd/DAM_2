package def;

import java.io.IOException;
import java.util.List;

public interface ContactSource {
    List<Contact> load() throws IOException;
}