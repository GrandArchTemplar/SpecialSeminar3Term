package test.server;

import org.junit.Test;
import server.Server;

import java.io.FileDescriptor;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Created by grandarchtemplar on 10/30/16.
 * <p>
 * This code may have problems
 */
public class ServerTest {
    Server server;
    @org.junit.Before
    public void setUp() throws Exception {
        server = new Server("conf.properties");
    }

    @Test
    public void singleRun() {
        server.run();
    }

    @Test
    public void tenTimesRun() {
        FileWriter out;
        try {
            out = new FileWriter("TenTimesRun.log");
        } catch (IOException e) {
            out = new FileWriter(FileDescriptor.out);
        }
        for (int i = 0; i < 10; i++) {
            new Server("tentimesrun.properties").run();
            try {
                for (String s : Files.readAllLines(Paths.get("temp.log"))) {
                    out.write(s);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}