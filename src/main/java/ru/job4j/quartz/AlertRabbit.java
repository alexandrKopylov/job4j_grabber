package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

//
//public class AlertRabbit {
//    public static void main(String[] args) {
//        try {
//            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
//            scheduler.start();
//            JobDetail job = newJob(Rabbit.class).build();
//            SimpleScheduleBuilder times = simpleSchedule()
//                    .withIntervalInSeconds(initRabbit())
//                    .repeatForever();
//            Trigger trigger = newTrigger()
//                    .startNow()
//                    .withSchedule(times)
//                    .build();
//            scheduler.scheduleJob(job, trigger);
//        } catch (SchedulerException se) {
//            se.printStackTrace();
//        }
//    }
//
//    public static int initRabbit() {
//        try (InputStream in = AlertRabbit.class.getClassLoader().getResourceAsStream("rabbit.properties")) {
//            Properties config = new Properties();
//            config.load(in);
//            return Integer.parseInt(config.getProperty("rabbit.interval"));
//        } catch (Exception e) {
//            throw new IllegalStateException(e);
//        }
//    }
//
//    public static class Rabbit implements Job {
//        @Override
//        public void execute(JobExecutionContext context) throws JobExecutionException {
//            System.out.println("Rabbit runs here ...");
//        }
//    }
//}



public class AlertRabbit {

  private   Properties config;
    private Connection cn;


    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        AlertRabbit ar = new AlertRabbit();
        ar.initRabbit();
        ar.initConnection();
        try {
            List<Long> store = new ArrayList<>();
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDataMap data = new JobDataMap();
            data.put("store", store);
            JobDetail job = newJob(Rabbit.class)
                    .usingJobData(data)
                    .build();
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(Integer.parseInt(ar.config.getProperty("rabbit.interval")))
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(5000);
            scheduler.shutdown();
            System.out.println(store);
        } catch (Exception se) {
            se.printStackTrace();
        }
    }


    public  void initRabbit() {
        try (InputStream in = AlertRabbit.class.getClassLoader().getResourceAsStream("rabbit.properties")) {
            config = new Properties();
            config.load(in);

        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public  void initConnection() throws ClassNotFoundException, SQLException {
            Class.forName(config.getProperty("driver-class-name"));
        cn = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );

    }
    public static class Rabbit implements Job {

        public Rabbit() {
            System.out.println(hashCode());
        }

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println("Rabbit runs here ...");
            List<Long> store = (List<Long>) context.getJobDetail().getJobDataMap().get("store");
            store.add(System.currentTimeMillis());
        }
    }
}