import cats.effect.std.Random
import cats.effect.{ExitCode, IO, IOApp, Resource}
import cats.implicits.toTraverseOps
import fs2.text
import org.http4s.Uri
import org.http4s.blaze.client.BlazeClientBuilder

import java.util.concurrent.Executors
import scala.concurrent.duration.{Duration, DurationInt}
import scala.concurrent.{ExecutionContext, duration}

object StreamClient extends IOApp {

  private val ec = ExecutionContext.fromExecutorService(Executors.newCachedThreadPool())
  // Define the client
  def run(args: List[String]): IO[ExitCode] =
    BlazeClientBuilder[IO]
      .withExecutionContext(ec)
      .resource
      .use { client =>
        val request = org.http4s.Request[IO](org.http4s.Method.GET, Uri.fromString("http://localhost:8080/data").toOption.get)

        // Define a single fiber to run multiple requests
        val badWorker = for {
          _ <- IO {IO.sleep(1.seconds)}
          _ <- IO {println("Making bad request")}
          stream = Resource.eval(IO(client.stream(request).flatMap(response => response.body)))
          head <- stream.use(s => for {
            r <- s.through(text.utf8.decode).compile.string
            _ <- IO.raiseWhen(r != (1 to 100).map(n => s"$n").mkString(", ") + "\n")(new Throwable(s"response body not expected got: $r"))
            h <- s.take(20).compile.toList.map(_.map(_.toChar).mkString)
          } yield h )
          _ <- IO {println(s"Finished request and got first characters: $head")}
        } yield head

        var error = false

        badWorker.onError(e => IO(println(s"an error happened: $e")) &> IO({error = true})).iterateUntil(_ => error)

      }.as(ExitCode.Success)
}
