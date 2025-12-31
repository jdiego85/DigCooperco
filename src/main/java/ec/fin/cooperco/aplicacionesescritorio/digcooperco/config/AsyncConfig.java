package ec.fin.cooperco.aplicacionesescritorio.digcooperco.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.CountDownLatch;


@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    @Value("${core_pool_size}")
    private int corePoolSize;
    @Value("${start_thread}")
    private int startThread;
    @Value("${count_down_latch}")
    private int cdl;

    @Override
    @Bean("asyncExecutor")
    public ThreadPoolTaskExecutor getAsyncExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(this.corePoolSize); // Número mínimo de hilos en el pool
        executor.setThreadNamePrefix("Digitalizador-"); // Prefijo para el nombre de los hilos
        executor.afterPropertiesSet();
        CountDownLatch countDownLatch = new CountDownLatch(this.cdl);
        this.startThreads(executor, countDownLatch, this.cdl);
        System.out.println("Se inicia el Executor");
        return executor;
    }

    public void startThreads(ThreadPoolTaskExecutor taskExecutor, CountDownLatch countDownLatch, int numThreads) {
        System.out.println("En StartTherads");
        for (int i = 0; i < numThreads; i++) {
            taskExecutor.execute(() -> {
                try {
                    Thread.sleep(this.startThread);
                    countDownLatch.countDown();
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
    }
}