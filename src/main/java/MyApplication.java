import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api/university")
public class MyApplication extends ResourceConfig {

    public MyApplication() {
        packages("controllers", "filters");
    }

}