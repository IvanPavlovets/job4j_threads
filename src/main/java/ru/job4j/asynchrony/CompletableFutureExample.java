package ru.job4j.asynchrony;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureExample {

    /**
     * метод имитирует главный поток.
     * @throws InterruptedException
     */
    private static void iWork() throws InterruptedException {
        int count = 0;
        while (count < 10) {
            System.out.println("Вы: Я работаю");
            TimeUnit.SECONDS.sleep(1);
            count++;
        }
    }

    /**
     * метод имитирует выполнение асинхронной задачи.
     * В методе выполняеться runAsync().
     * @return CompletableFuture<Void>
     */
    public static CompletableFuture<Void> goToTrash() {
        return CompletableFuture.runAsync(
                () -> {
                    System.out.println("Сын: Я пошел выносить мусор");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.fillInStackTrace();
                    }
                    System.out.println("Сын: я вернулся");
                }
        );
    }

    /**
     * метод имитирует выполнение асинхронной задачи.
     * В методе выполняеться supplyAsync() -
     * Возвращает значение - CompletableFuture<String>
     * @param product
     * @return CompletableFuture<String>
     */
    public static CompletableFuture<String> buyProduct(String product) {
        return CompletableFuture.supplyAsync(
                () -> {
                    System.out.println("Сын: Я пошел в магазин");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Сын: Я купил " + product);
                    return product;
                }
        );
    }

    /**
     * в методе происходит запуск основного потока и асинхронной задачи.
     * @throws Exception
     */
    public static void runAsyncExample() throws Exception {
        CompletableFuture<Void> grr = goToTrash();
        iWork();
    }

    /**
     * Запуск происходит в разных thread с помощью Fork/Join
     * @throws Exception
     */
    public static void  supplyAsyncExample() throws Exception {
        CompletableFuture<String> bm = buyProduct("Молоко");
        iWork();
        System.out.println("Куплено: " + bm.get());
    }

    /**
     * метод запускает асинхронную задачу, с методом runAsync.
     * Затем сразу запускает Callback-метод - thenRun().
     * Этот метод ничего не возвращает, а позволяет выполнить
     * задачу типа Runnable после выполнения асинхронной задачи.
     * @throws Exception
     */
    public static void thenRunExample() throws Exception {
        CompletableFuture<Void> gtt = goToTrash();
        gtt.thenRun(
                () -> {
                    int count = 0;
                    while (count < 3) {
                        System.out.println("Сын: я мою руки");
                        try {
                            TimeUnit.MILLISECONDS.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        count++;
                    }
                    System.out.println("Сын: Я помыл руки");
                });
        iWork();
        System.out.println(gtt.get());
    }

    /**
     * метод аналогичен thenRunExample(), за исключением того что thenAccept()
     * имеет доступ к результату через get() - аналагичен FutureTask().get()
     * @throws Exception
     */
    public static void thenAcceptExample() throws Exception {
        CompletableFuture<String> bm = buyProduct("Молоко");
        bm.thenAccept(
                (product) -> System.out.println("Сын: Я убрал " + product + " в холодильник "));
        iWork();
        System.out.println("Куплено: " + bm.get());
    }

    /**
     * callBack метод, принимает Function и преобразовывает результат.
     * @throws Exception
     */
    public static void thenApplyExample() throws Exception {
        CompletableFuture<String> bm = buyProduct("Молоко")
                .thenApply(
                        (product) -> "Сын: я налил тебе в кружку " + product + ". Держи.");
        iWork();
        System.out.println(bm.get());
    }

    /**
     * Если действия зависимы, сначала 1 действие затем 2 действие.
     * @throws Exception
     */
    public static void thenComposeExample() throws Exception {
        CompletableFuture<Void> result = buyProduct("Молоко")
                .thenCompose(a -> goToTrash());
        iWork();
    }

    /**
     * Если действия могут быть выполнены независимо друг от друга.
     * henCombine дожидается, когда процессы r1, r2 завершатся,
     * и после этого обьявляет результат в что то одно:
     * @throws Exception
     */
    public static void thenCombineExample() throws Exception {
        CompletableFuture<String> result = buyProduct("Молоко")
                .thenCombine(buyProduct("Хлеб"),
                        (r1, r2) -> "Куплены " + r1 + " и " + r2);
        iWork();
        System.out.println(result.get());
    }


    /**
     * типовая задача, мойки рук всеми членами семьи.
     * @param name
     * @return CompletableFuture<Void>
     */
    public static CompletableFuture<Void> washHands(String name) {
        return CompletableFuture.runAsync(
                () -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(name + ", моет руки");
                });
    }

    /**
     * Совмещеное выполнение всех задач, мойка рук всех членов семьи,
     * метод allOf.
     * @throws Exception
     */
    public static void allOfExample() throws Exception {
        CompletableFuture<Void> all = CompletableFuture.allOf(
                washHands("Папа"), washHands("Мама"),
                washHands("Ваня"), washHands("Боря")
        );
        TimeUnit.SECONDS.sleep(3);
    }

    public static CompletableFuture<String> whoWashHands(String name) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return name + ", моет руки";
                });
    }

    /**
     * Совмещеное выполнение всех задач, метод anyOf.
     * Метод возвращает ComputableFuture<Object>.
     * Результатом будет первая выполненная задача.
     * @throws Exception
     */
    public static void anyOfExample() throws Exception {
        CompletableFuture<Object> first = CompletableFuture.anyOf(
                whoWashHands("Папа"), whoWashHands("Мама"),
                whoWashHands("Ваня"), whoWashHands("Боря")
        );
        System.out.println("Кто сейчас моет руки?");
        TimeUnit.SECONDS.sleep(1);
        System.out.println(first.get());
    }


    public static void main(String[] args) throws Exception {
        //CompletableFutureExample.runAsyncExample();
        CompletableFutureExample.supplyAsyncExample();
        //CompletableFutureExample.thenRunExample();
        //CompletableFutureExample.thenAcceptExample();
        //CompletableFutureExample.thenApplyExample();
        //CompletableFutureExample.thenComposeExample();
        //CompletableFutureExample.thenCombineExample();
        //CompletableFutureExample.allOfExample();
        //CompletableFutureExample.anyOfExample();

    }
}
