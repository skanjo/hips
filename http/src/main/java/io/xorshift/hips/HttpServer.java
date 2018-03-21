package io.xorshift.hips;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

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

  HttpServer(int port, People people) {
    this.port = port;
    this.people = people;
  }

  @Override
  public void start(Future<Void> startFuture) throws Exception {
    URL url = Resources.getResource("io/xorshift/hips/fail_whale.txt");
    failMessage = Resources.toString(url, Charsets.UTF_8);

    final Router r = Router.router(vertx);

    r.route("/records").handler(BodyHandler.create().setBodyLimit(1024));
    r.post("/records").handler(this::addPerson);
    r.get("/records/gender").handler(this::peopleSortedByGender);
    r.get("/records/birthdate").handler(this::peopleSortedByBirthDate);
    r.get("/records/name").handler(this::peopleSortedByLastName);
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

  @Override
  public void stop(Future<Void> stopFuture) throws Exception {
    super.stop(stopFuture);
  }

  private void addPerson(RoutingContext event) {
    final String body = event.getBodyAsString();
    if (Strings.isNullOrEmpty(body)) {
      event.response().setStatusCode(400).end();

    } else {
      final Person p = Person.createFromCsv(body);
      people.add(p);
      event.response().setStatusCode(204).end();
    }
  }

  private void peopleSortedByGender(RoutingContext event) {
    sendPeopleResponse(event, people.sortedByGender());
  }

  private void peopleSortedByBirthDate(RoutingContext event) {
    sendPeopleResponse(event, people.sortedByBirthDate());
  }

  private void peopleSortedByLastName(RoutingContext event) {
    sendPeopleResponse(event, people.sortedByLastName());
  }

  private void sendPeopleResponse(RoutingContext event, List<Person> data) {
    final Gson gson = new GsonBuilder().setDateFormat("M/d/yyyy").setPrettyPrinting().create();
    final String json = gson.toJson(data);

    final HttpServerResponse httpResp = event.response();
    httpResp.setStatusCode(200);
    httpResp.putHeader("Content-Type", "application/json");
    httpResp.end(json);
  }

  private void failure(RoutingContext event) {
    final HttpServerResponse httpResp = event.response();
    httpResp.setStatusCode(500);
    httpResp.putHeader("Content-Type", "text/plain");
    httpResp.end(failMessage);
  }

}
