package io.xorshift.hips;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * @author Samer Kanjo
 * @since 0.6.0 3/20/18 4:39 PM
 */
public class HttpServer extends AbstractVerticle {

  private static final Handler<RoutingContext> DEFAULT_HANDLER = event -> event.response().setStatusCode(444).end();

  private final int port;
  private final People people;
  private String failMessage;

  HttpServer(int port, People people) throws IOException {
    this.port = port;
    this.people = people;

    URL url = Resources.getResource("io/xorshift/hips/fail_whale.txt");
    failMessage = Resources.toString(url, Charsets.UTF_8);
  }

  @Override
  public void start(Future<Void> startFuture) {
    final Router r = Router.router(vertx);

    r.route("/records").handler(BodyHandler.create().setBodyLimit(1024));
    r.post("/records").handler(this::addPerson);
    r.get("/records/gender").handler(this::peopleSortedByGender);
    r.get("/records/birthdate").handler(this::peopleSortedByBirthDate);
    r.get("/records/name").handler(this::peopleSortedByLastName);
    r.get("/healthcheck").handler(this::healthCheck);
    r.route().handler(DEFAULT_HANDLER);
    r.route().failureHandler(this::failure);

    final io.vertx.core.http.HttpServer hs = vertx.createHttpServer();

    hs.requestHandler(r::accept).listen(port, ar -> {
      if (ar.succeeded()) {
        startFuture.complete();
      } else {
        System.out.println("failed to start HttpServer");
        startFuture.fail(ar.cause());
      }
    });
  }

  void addPerson(RoutingContext event) {
    final HttpServerResponse httpResp = event.response();

    final String body = event.getBodyAsString();
    if (Strings.isNullOrEmpty(body)) {
      httpResp.setStatusCode(400);

    } else {
      final Person p = Person.createFromCsv(body);
      people.add(p);

      httpResp.setStatusCode(204);
    }

    httpResp.end();
  }

  void peopleSortedByGender(RoutingContext event) {
    sendPeopleResponse(event, people.sortedByGender());
  }

  void peopleSortedByBirthDate(RoutingContext event) {
    sendPeopleResponse(event, people.sortedByBirthDate());
  }

  void peopleSortedByLastName(RoutingContext event) {
    sendPeopleResponse(event, people.sortedByLastName());
  }

  private void sendPeopleResponse(RoutingContext event, List<Person> data) {
    final Gson gson = new GsonBuilder().setDateFormat("M/d/yyyy").setPrettyPrinting().create();
    final String json = gson.toJson(data);

    final HttpServerResponse httpResp = event.response();
    httpResp.putHeader(HttpHeaders.CONTENT_TYPE, "application/json");
    httpResp.setStatusCode(200);
    httpResp.end(json);
  }

  void failure(RoutingContext event) {
    final HttpServerResponse httpResp = event.response();
    httpResp.putHeader(HttpHeaders.CONTENT_TYPE, "text/plain");
    httpResp.setStatusCode(500);
    httpResp.end(failMessage);
  }

  void healthCheck(RoutingContext event) {
    final HttpServerResponse httpResp = event.response();
    httpResp.putHeader(HttpHeaders.CONTENT_TYPE, "text/plain");
    httpResp.setStatusCode(200);
    httpResp.end("OK");
  }

}
