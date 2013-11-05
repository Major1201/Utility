package com.major.commons.util;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * User: Minjie
 * Date: 13-9-20
 * Time: 下午3:17
 */
@SuppressWarnings("UnusedDeclaration")
public class SystemUtil {
    /**
     * Execute the command with a new thread.
     * @param command command to execute
     * @param executor to do what you want to do with the return string
     */
    public static void execute(final String command, final SystemUtilExecutor executor) {
        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(0);
                executor.runAtTheEnd(execAndWait(command));
                return null;
            }
        });
        new Thread(futureTask).start();
    }

    /**
     * Just run the command.
     * @param command command to run.
     */
    public static void execute(String command) {
        execute(command, new SystemUtilExecutor() {
            @Override
            public void runAtTheEnd(String returned) {

            }
        });
    }
    /**
     * Execute the command and return the string.
     * @param command command to execute
     * @return return string
     */
    public static String execAndWait(String command) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
            CommandLine commandline = CommandLine.parse(command);
            DefaultExecutor exec = new DefaultExecutor();
            exec.setExitValues(null);
            PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream,errorStream);
            exec.setStreamHandler(streamHandler);
            exec.execute(commandline);
            String out = outputStream.toString("gbk");

            String error = errorStream.toString("gbk");
            return out + error;
        } catch (Exception e) {
            return e.toString();
        }
    }
}
