package me.nabil.thread.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhangbi
 */
public class ThreadPoolDemoMain {

    public static void main(String[] args) throws IOException {
        System.out.println("args:" + args);

        ServerSocket listener = new ServerSocket(8080);
        if (args == null || args.length < 1) {
            singleThread(listener);
        } else if ("thread".equals(args[0])) {
            noThreadPoolMain(listener);
        } else if ("poolThread".equals(args[0])) {
            pooledMain(listener);
        }

    }

    /**
     * 单线程模型
     *
     * @param listener
     * @throws IOException
     */
    private static void singleThread(ServerSocket listener) throws IOException {
        try {
            while (true) {
                Socket socket = listener.accept();
                try {
                    HandleRequestRunnable.handleRequest(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            listener.close();
        }
    }

    /**
     * 现成池的多线程实现
     *
     * @param listener
     * @throws IOException
     */
    private static void pooledMain(ServerSocket listener) throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        try {
            while (true) {
                Socket socket = listener.accept();
                executor.submit(new HandleRequestRunnable(socket));
            }
        } finally {
            listener.close();
        }
    }

    /**
     * 无线程池的多线程实现
     *
     * @param listener
     * @throws IOException
     */
    private static void noThreadPoolMain(ServerSocket listener) throws IOException {
        try {
            while (true) {
                Socket socket = listener.accept();
                new Thread(new HandleRequestRunnable(socket)).start();
            }
        } finally {
            listener.close();
        }
    }


    /**
     * 处理器线程
     */
    private static class HandleRequestRunnable implements Runnable {
        final Socket socket;

        public HandleRequestRunnable(Socket socket) {
            this.socket = socket;
        }

        final static String response =
                "HTTP/1.0 200 OK\r\n" +
                        "Content-type: text/plain\r\n" +
                        "\r\n" +
                        "Hello World\r\n";

        public static void handleRequest(Socket socket) throws IOException, InterruptedException {
            // Read the input stream, and return "200 OK"
            try {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String request;

                while ((request = in.readLine()) != null) {
                    System.out.println(request);
                }

//                Thread.sleep(10000L);
                System.out.println(Thread.currentThread().getName());

                OutputStream out = socket.getOutputStream();
                out.write(response.getBytes(StandardCharsets.UTF_8));
            } finally {
                socket.close();
            }
        }

        public void run() {
            try {
                handleRequest(socket);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
