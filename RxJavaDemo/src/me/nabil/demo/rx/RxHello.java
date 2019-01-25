package me.nabil.demo.rx;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

/**
 * RxHello
 *
 * @author nabilzhang
 * @date 2019/1/25
 */
public class RxHello {
    public static void main(String[] args) {
        Flowable.just("Hello world").subscribe(System.out::println);

        Flowable.range(1, 10)
                .flatMap(v ->
                        Flowable.just(v)
                                .subscribeOn(Schedulers.computation())
                                .map(w -> w * w)
                )
                .blockingSubscribe(System.out::println);
    }
}
