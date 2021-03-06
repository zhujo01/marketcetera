package org.marketcetera.metrics;

import org.marketcetera.util.misc.ClassVersion;
import org.marketcetera.util.misc.NamedThreadFactory;
import org.marketcetera.core.LoggerConfiguration;
import org.marketcetera.module.ExpectedFailure;
import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;

import java.util.concurrent.Callable;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

/* $License$ */
/**
 * Tests {@link ConditionsFactory}
 *
 * @author anshul@marketcetera.com
 * @version $Id: ConditionsFactoryTest.java 16154 2012-07-14 16:34:05Z colin $
 * @since 2.0.0
 */
@ClassVersion("$Id: ConditionsFactoryTest.java 16154 2012-07-14 16:34:05Z colin $")
public class ConditionsFactoryTest {
    
    @BeforeClass
    public static void setup() {
        LoggerConfiguration.logSetup();
    }

    /**
     * Tests expected failures.
     * @throws Exception if there were errors.
     */
    @Test
    public void invalidInterval() throws Exception {
        new ExpectedFailure<IllegalArgumentException>("0 <= 0"){
            @Override
            protected void run() throws Exception {
                ConditionsFactory.createSamplingCondition(0,"dontmatter");
            }
        };
        new ExpectedFailure<IllegalArgumentException>("-1 <= 0"){
            @Override
            protected void run() throws Exception {
                ConditionsFactory.createSamplingCondition(-1,"dontmatter");
            }
        };
    }

    /**
     * Tests sampling in a single thread.
     *
     * @throws Exception if there were errors.
     */
    @Test
    public void simpleSampling() throws Exception {
        for (int interval = 1; interval < 50; interval++) {
            Callable<Boolean> condition = ConditionsFactory.
                    createSamplingCondition(interval, "sample");
            for(int i = 1; i < 100; i++) {
                assertEquals("Interval " + interval + " iteration " + i,
                        i % interval == 0, condition.call());
            }
        }
    }

    /**
     * Tests using the sampling condition instance from multiple threads.
     *
     * @throws Exception if there were errors.
     */
    @Test
    public void multiThreadSampling() throws Exception {
        final int maxIterations = 100;
        for (int j = 1; j < 37; j++) {
            final int interval = j;
            final Callable<Boolean> condition = ConditionsFactory.
                    createSamplingCondition(interval, "sample");
            List<ExceptionThread> threads = new LinkedList<ExceptionThread>();
            for(int i = 0; i < 20; i++) {
                //Create a new thread instead of using a thread pool as
                //thread reuse may lead to test failures.
                final ExceptionThread<Integer> thread = new ExceptionThread<Integer>("TestCondition:" + i) {
                    @Override
                    public Integer call() throws Exception {
                        int i;
                        for (i = 1; i < maxIterations; i++) {
                            assertEquals("Thread " + Thread.currentThread().getName() +
                                    " Interval " + interval +
                                    " iteration " + i, i % interval == 0,
                                    condition.call());
                            //slow them down to encourage concurrency.
                            Thread.sleep(1);
                        }
                        return i;
                    }
                };
                threads.add(thread);
            }
            //Start all the threads
            for(ExceptionThread thread: threads) {
                thread.start();
            }
            //Wait for all threads to end.
            for(ExceptionThread thread: threads) {
                assertEquals(maxIterations, thread.get());
            }
        }
    }

    /**
     * A Thread subclass that produces a result when it's done. This thread
     * can be subclassed to run operations that return results or fail with
     * an exception.
     *
     * @param <T> the type of result generated by the thread.
     */
    private static abstract class ExceptionThread<T> extends Thread
            implements Callable<T> {
        /**
         * Creates an instance.
         *
         * @param name the thread name.
         */
        public ExceptionThread(String name) {
            super(name);
        }

        @Override
        public abstract T call() throws Exception;

        @Override
        public void run() {
            try {
                mResult = call();
            } catch(Exception t) {
                mFailure = t;
            }
        }

        /**
         * Returns the result of {@link #call()} if it succeeded or
         * throws the exception if it failed.
         *
         * @return the result.
         *
         * @throws Exception the failure exception.
         */
        public T get() throws Exception {
            //Wait for thread to finish.
            join();
            if(mFailure != null) {
                throw mFailure;
            }
            return mResult;
        }
        private volatile T mResult;
        private volatile Exception mFailure;
    }

    /**
     * Tests integration of the sampling condition with {@link Configurator}.
     *
     * @throws Exception if there were errors.
     */
    @Test
    public void samplingConfiguration() throws Exception {
        //Configure a mock configurator.
        Map<String,String> properties = new HashMap<String, String>();
        final String validInterval = "sample.interval";
        properties.put(validInterval, "7");
        final String invalidInterval = "sample.invalid.interval";
        properties.put(invalidInterval, "value");
        Configurator.setInstance(new ConfiguratorTest.MockConfigurator(properties));
        //Test a sampler with a valid default configuration.
        Callable<Boolean> c = ConditionsFactory.createSamplingCondition(10, validInterval);
        for(int i = 1; i < 100; i++) {
            assertEquals(" iteration " + i, i % 7 == 0, c.call());
        }
        //Test a sampler with invalid default configuration.
        c = ConditionsFactory.createSamplingCondition(13, invalidInterval);
        for(int i = 1; i < 100; i++) {
            assertEquals(" iteration " + i, i % 13 == 0, c.call());
        }
    }
}
