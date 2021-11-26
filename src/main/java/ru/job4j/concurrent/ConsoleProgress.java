package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    /**
     * В цикле метода проверяеться флаг прерывания а также
     * происходит задержка в 500 мс и вывод в консоль с
     * меняющимся символом косой черты - "эффект крутящегося шара - Петр А." - ржу немогу.
     *
     */
    @Override
    public void run() {
        int count = 0;
        String[] process = {"\\", "|", "/", "|"};
        while (!Thread.currentThread().isInterrupted()) {
            try {
                System.out.print("\rLoading: " + process[count]);
                count = (count + 1) % process.length;
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(8000);
        progress.interrupt();
        System.out.println("\n\rMain");
    }
}
