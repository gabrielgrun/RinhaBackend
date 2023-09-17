package com.ggrun.simulations;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class RinhaSimulation extends Simulation {

    private final HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:9999")
            .userAgentHeader("Agente do Caos - 2023");

    private final ScenarioBuilder criacaoEConsultaPessoas = scenario("Criação E Talvez Consulta de Pessoas")
            .feed(tsv("pessoas-payloads.tsv").circular())
            .exec(
                    http("criação")
                            .post("/pessoas").body(StringBody("${payload}"))
                            .header("content-type", "application/json")
                            .check(status().in(201, 422, 400))
                            .check(header("Location").optional().saveAs("location"))
            )
            .pause(Duration.ofMillis(1), Duration.ofMillis(30))
            .doIf(session -> session.contains("location"))
            .then(
                    exec(
                            http("consulta")
                                    .get(session -> session.get("location"))
                    )
            );

    private final ScenarioBuilder buscaPessoas = scenario("Busca Válida de Pessoas")
            .feed(tsv("termos-busca.tsv").circular())
            .exec(
                    http("busca válida")
                            .get("/pessoas?t=${t}")
            );

    private final ScenarioBuilder buscaInvalidaPessoas = scenario("Busca Inválida de Pessoas")
            .exec(
                    http("busca inválida")
                            .get("/pessoas")
                            .check(status().is(400))
            );

    public RinhaSimulation() {
        setUp(
                criacaoEConsultaPessoas.injectOpen(
                        constantUsersPerSec(2).during(Duration.ofSeconds(10)),
                        constantUsersPerSec(5).during(Duration.ofSeconds(15)).randomized(),
                        rampUsersPerSec(6).to(600).during(Duration.ofMinutes(3))
                ),
                buscaPessoas.injectOpen(
                        constantUsersPerSec(2).during(Duration.ofSeconds(25)),
                        rampUsersPerSec(6).to(100).during(Duration.ofMinutes(3))
                ),
                buscaInvalidaPessoas.injectOpen(
                        constantUsersPerSec(2).during(Duration.ofSeconds(25)),
                        rampUsersPerSec(6).to(40).during(Duration.ofMinutes(3))
                )
        );
    }
}