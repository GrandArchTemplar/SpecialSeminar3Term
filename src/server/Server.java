package server;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by grandarchtemplar on 10/27/16.
 * <p>
 * This code may have problems
 */
public class Server implements Runnable {

    public static class Request implements Runnable {
        private final long WORK_TIME;
        private final int RESOURCES_SIZE;
        private final Server SERVER;

        public Request(long workTime, int resourceSize, Server server) {
            this.WORK_TIME = workTime;
            this.RESOURCES_SIZE = resourceSize;
            this.SERVER = server;
        }

        private int getResourceSize() {
            return RESOURCES_SIZE;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(WORK_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            SERVER.release(this);
        }
    }

    private int resources;
    private final int MAX_RESOURCES;
    private final long WORK_TIME;
    private final FileWriter LOG;
    private boolean ready = true;
    private final RequestGenerator REQUEST_GENERATOR;

    public Server(String confFileName) {
        //log1 creating for solving not init problem of LOG
        FileWriter log1;
        //use properties as easier way of config file
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(confFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //initializing fields from props
        this.resources = Integer.parseInt(prop.getProperty("resources.size"));
        this.WORK_TIME = Long.parseLong(prop.getProperty("work.time"));
        try {
            log1 = new FileWriter(prop.getProperty("protocol.file.name"));
        } catch (IOException e) {
            log1 = new FileWriter(FileDescriptor.out);
        }
        this.LOG = log1;
        //initializing @REQUEST_GENERATOR and @MAX_RESOURCES
        this.REQUEST_GENERATOR = new RequestGenerator(400, 400, 200, this);
        this.MAX_RESOURCES = this.resources;
    }

    //dedicated getting time as string for clear code
    private String getTime() {
        return new Date(System.currentTimeMillis()).toString();
    }

    //dedicated print for clear code
    private void print(int resourceSize, boolean isRelease) {
        try {
            LOG.write(getTime()
                    + " "
                    + Integer.valueOf(resourceSize).toString()
                    + " resources "
                    + ((isRelease) ? "released" : "allocate")
                    + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //releasing
    private synchronized void release(Request request) {
        int released = request.getResourceSize();
        print(resources, true);
        //must be after print because stream can close before print and it isn't good situation
        resources += released;
    }

    //allocating
    private synchronized void allocate(Request request) {
        int require = request.getResourceSize();
        //check on availability allocation
        if (resources >= require) {
            //allocate and print to log
            resources -= require;
            new Thread(request).start();
            print(resources, false);
        } else {
            //print to log about refusing
            try {
                LOG.write(getTime() + " " + Integer.valueOf(resources).toString() + " allocation refused\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        //execute parallel thread which show server when it must stop
        Executors
                .newSingleThreadScheduledExecutor()
                .schedule(() -> ready = false, WORK_TIME, TimeUnit.MILLISECONDS);

        //cycle which work while server ready to work
        while (ready) {
            //allocate(or not if can't) resources for requests from REQUEST_GENERATOR
            allocate(REQUEST_GENERATOR.nextRequest());
        }

        //wait returning all resources
        while (resources != MAX_RESOURCES) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //close log stream
        try {
            LOG.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
