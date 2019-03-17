package ch.christianmenz.r4j;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

import java.util.function.Supplier;
import java.util.stream.IntStream;

public class Main {


    public static void main(String[] args) {
        CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.ofDefaults();
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("service");

        Supplier<String> stringSupplier = CircuitBreaker.decorateSupplier(circuitBreaker, () -> {
            throw new NullPointerException("Some Exception");
        });

        IntStream.range(0, 1000).forEach((i) -> {
            try {
                // circuitBreaker.transitionToClosedState(); // close an open circuit breaker
                stringSupplier.get();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }
}
