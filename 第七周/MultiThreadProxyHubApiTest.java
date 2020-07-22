package web_test;

import java.text.NumberFormat;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



/**
 * java模拟多线程高并发
 * 原文地址：https://blog.csdn.net/u010642004/article/details/50042781
 * @author Administrator
 *
 */
public class MultiThreadProxyHubApiTest {
    static int count = 0;
    // 总访问量是clientNum，并发量是threadNum
    int threadNum = 4;
    int clientNum = 20000;

    float avgExecTime = 0;
    float sumexecTime = 0;
    long firstExecTime = Long.MAX_VALUE;
    long lastDoneTime = Long.MIN_VALUE;
    float totalExecTime = 0;
    String url ="https://www.baidu.com/";

    public static void main(String[] args) {
        new MultiThreadProxyHubApiTest().run();
        System.out.println("finished!");
    }

    public void run() {

        final ConcurrentHashMap<Integer, ThreadRecord> records = new ConcurrentHashMap<Integer, ThreadRecord>();

        // 建立ExecutorService线程池，threadNum个线程可以同时访问
        ExecutorService exec = Executors.newFixedThreadPool(threadNum);
        // 模拟clientNum个客户端访问
        final CountDownLatch doneSignal = new CountDownLatch(clientNum);

        for (int i = 0; i < clientNum; i++) {
            Runnable run = new Runnable() {
                public void run() {
                    int index = getIndex();
                    long systemCurrentTimeMillis = System.currentTimeMillis();
                    try {
                       // String sendGet = HttpClientUtil.sendGet("http://localhost:8080/Dima3773Web/Simulate", "");
//                        String sendRecvGet =GetPostTest.sendGet(url," ");
//                        System.out.println(System.currentTimeMillis() + sendRecvGet);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    records.put(index, new ThreadRecord(systemCurrentTimeMillis, System.currentTimeMillis()));
                    doneSignal.countDown();// 每调用一次countDown()方法，计数器减1
                }
            };
            exec.execute(run);
        }

        try {
            // 计数器大于0 时，await()方法会阻塞程序继续执行
            doneSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /**
         * 获取每个线程的开始时间和结束时间
         */
        for (int i : records.keySet()) {
            ThreadRecord r = records.get(i);
            sumexecTime += ((double) (r.endTime - r.startTime)) / 1000;

            if (r.startTime < firstExecTime) {
                firstExecTime = r.startTime;
            }
            if (r.endTime > lastDoneTime) {
                this.lastDoneTime = r.endTime;
            }
        }

        this.avgExecTime = this.sumexecTime / records.size();
        this.totalExecTime = ((float) (this.lastDoneTime - this.firstExecTime)) / 1000;
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(4);

        System.out.println("======================================================");
        System.out.println("线程数量:\t\t" + threadNum);
        System.out.println("客户端数量:\t" + clientNum);
        System.out.println("平均执行时间:\t" + nf.format(this.avgExecTime) + "秒");
        System.out.println("总执行时间:\t" + nf.format(this.totalExecTime) + "秒");
        System.out.println("吞吐量:\t\t" + nf.format(this.clientNum / this.totalExecTime) + "次每秒");
    }

    public static int getIndex() {
        return ++count;
    }

}

class ThreadRecord {
    long startTime;
    long endTime;

    public ThreadRecord(long st, long et) {
        this.startTime = st;
        this.endTime = et;
    }

}

//        二、服务器端servlet处理
//
//        import java.io.IOException;
//        import java.io.PrintWriter;
//
//        import javax.servlet.ServletException;
//        import javax.servlet.ServletResponse;
//        import javax.servlet.http.HttpServlet;
//        import javax.servlet.http.HttpServletRequest;
//        import javax.servlet.http.HttpServletResponse;
//
///**
// * java模拟多线程高并发--模拟服务器servlet
// */
//public class SimulateServserServlet extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//
//    public SimulateServserServlet() {
//        super();
//    }
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String content = "测试数据";
//        System.out.println(System.currentTimeMillis() + "\t" + request.getRequestURL());
//        outPrintContent(response, content);
//    }
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        doGet(request, response);
//    }
//
//    /**
//     * 返回输出 @Title: outPrintContent @Description: TODO @param @param
//     * content @param @param response 设定参数 @return void 返回类型 @throws
//     */
////    public static void outPrintContent(ServletResponse response, String content) {
////        response.setCharacterEncoding("UTF-8");
////        response.setContentType("text/html;charset=UTF-8");
////        PrintWriter out = null;
////        try {
////            out = response.getWriter();
////            out.print(content);
////            out.flush();
////        } catch (IOException e) {
////            System.out.println("outprint content error : "+e);
////        } finally {
////            if (null != out) {
////                out.close();
////            }
////        }
////    }
//}
//
