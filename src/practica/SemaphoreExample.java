/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica;

/**
 *
 * @author pinar
 */
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SemaphoreExample {
    private static final int NUM_THREADS = 10;
    private static final int NUM_PERMITS = 3;
    private static final Semaphore SEMAPHORE = new Semaphore(NUM_PERMITS);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < NUM_THREADS; i++) {
            executor.submit(new Task(i));
        }

        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        System.out.println("Tiempo total de ejecucion: " + elapsedTime + " milisegundos");
    }

    static class Task implements Runnable {
        private static final AtomicInteger count = new AtomicInteger(0);
        private int id;

        public Task(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
                int threadCount = count.incrementAndGet();
                System.out.println("Hilo " + id + " es el numero " + threadCount + " en ejecutarse");

                // Intentamos obtener un permiso del semáforo
                SEMAPHORE.acquire();
                System.out.println("El hilo " + id + " ha obtenido un permiso del semaforo");

                // Simulamos una operación que tarda un tiempo aleatorio
                Thread.sleep((long) (Math.random() * 1000));

                // Liberamos el permiso del semáforo
                SEMAPHORE.release();
                System.out.println("El hilo " + id + " ha liberado un permiso del semaforo");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

