package guru.springframework.sfgpetclinic.junitextension;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;
import java.util.logging.Logger;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class TimingExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    private static final Logger LOGGER = Logger.getLogger(TimingExtension.class.getName());
    private static final String START_TIME = "start time";

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        Method testMethod = context.getRequiredTestMethod();
        Instant startTime = getStore(context).remove(START_TIME, Instant.class);
        Instant endTime = Instant.now();

        LOGGER.info(() -> String.format("Method [%s] took %dms", testMethod.getName(), Duration.between(startTime, endTime).toMillis()));
    }
    
    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        getStore(context).put(START_TIME, Instant.now());
    }

    private ExtensionContext.Store getStore(ExtensionContext context) {
        return context.getStore(ExtensionContext.Namespace.create(getClass(), context.getRequiredTestMethod()));
    }
}
