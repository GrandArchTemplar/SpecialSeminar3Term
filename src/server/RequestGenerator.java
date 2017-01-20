package server;

import java.util.Random;

/**
 * Created by grandarchtemplar on 10/30/16.
 * <p>
 * This code may have problems
 */

public class RequestGenerator {
    private final Random GENERATOR;
    private final long MAX_DELAY;
    private final int MAX_RESOURCE;
    private final long MAX_WORK_TIME;
    private final Server SERVER;

    //constructors(first constructor for determinate sequence from @GENERATOR)
    public RequestGenerator(long maxWorkTime, long maxDelay, int maxResource, int seed, Server server) {
        this.GENERATOR = new Random(seed);
        this.MAX_DELAY = maxDelay;
        this.MAX_RESOURCE = maxResource;
        this.MAX_WORK_TIME = maxWorkTime;
        this.SERVER = server;
    }

    public RequestGenerator(long maxWorkTime, long maxDelay, int maxResource, Server server) {
        this.GENERATOR = new Random();
        this.MAX_DELAY = maxDelay;
        this.MAX_RESOURCE = maxResource;
        this.MAX_WORK_TIME = maxWorkTime;
        this.SERVER = server;
    }

    //generate next request
    public Server.Request nextRequest() {
        try {
            //wait some time to unexpected generating request
            Thread.sleep(Math.abs(GENERATOR.nextLong()) % (MAX_DELAY + 1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Server.Request(
                Math.abs(GENERATOR.nextLong()) % (MAX_WORK_TIME + 1),
                GENERATOR.nextInt(MAX_RESOURCE),
                SERVER);
    }
}