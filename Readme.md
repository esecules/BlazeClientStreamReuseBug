# Blaze Client Stream Reuse Bug Demo
## Running
```bash
sbt "project server" run
sbt "project client" run
```

### Example Client output
```bash
Making bad request
Finished request and got first characters: 1, 2, 3, 4, 5, 6, 7,
Making bad request
Finished request and got first characters: 1, 2, 3, 4, 5, 6, 7,
Making bad request
Finished request and got first characters: 1, 2, 3, 4, 5, 6, 7,
Making bad request
an error happened: org.http4s.blaze.http.parser.BaseExceptions$BadMessage: Bad HTTP version: 0

HTTP/1.1
[error] org.http4s.blaze.http.parser.BaseExceptions$BadMessage: Bad HTTP version: 0
[error]
[error] HTTP/1.1
[error] 	at org.http4s.blaze.http.parser.Http1ClientParser.parseResponseLine(Http1ClientParser.java:144)
[error] 	at org.http4s.blaze.client.BlazeHttp1ClientParser.finishedResponseLine(BlazeHttp1ClientParser.scala:67)
[error] 	at org.http4s.blaze.client.Http1Connection.parsePrelude(Http1Connection.scala:317)
[error] 	at org.http4s.blaze.client.Http1Connection.$anonfun$handleRead$1(Http1Connection.scala:297)
[error] 	at org.http4s.blaze.client.Http1Connection.$anonfun$handleRead$1$adapted(Http1Connection.scala:296)
[error] 	at scala.concurrent.impl.Promise$Transformation.run(Promise.scala:448)
[error] 	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
[error] 	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
[error] 	at java.base/java.lang.Thread.run(Thread.java:829)
[error] 	at delay @ org.http4s.blaze.client.Http1Connection.$anonfun$receiveResponse$1(Http1Connection.scala:263)
[error] 	at async @ org.http4s.blaze.client.Http1Connection.receiveResponse(Http1Connection.scala:262)
[error] 	at map @ org.http4s.blaze.client.Http1Connection.$anonfun$executeRequest$6(Http1Connection.scala:239)
[error] 	at main$ @ StreamClient$.main(StreamClient.scala:12)
[error] stack trace is suppressed; run last Compile / run for the full output
[error] (Compile / run) org.http4s.blaze.http.parser.BaseExceptions$BadMessage: Bad HTTP version: 0
[error]
[error] HTTP/1.1
[error] Total time: 4 s, completed Aug. 10, 2023, 10:25:07 a.m.
```
