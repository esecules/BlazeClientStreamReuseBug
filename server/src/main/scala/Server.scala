import cats.effect.{ExitCode, IO, IOApp}
import org.http4s.HttpRoutes
import org.http4s.dsl.io._
import org.http4s.implicits._
import fs2.Stream
import org.http4s.blaze.server.BlazeServerBuilder

import scala.util.Random

object Server extends IOApp {

  // Generate a stream of random English letters and numbers
  def randomLetterOrNumberStream: Stream[IO, Byte] = {
    val lettersAndNumbers = ('a' to 'z') ++ ('A' to 'Z') ++ ('0' to '9')
    val randomStream = Stream.repeatEval(IO(Random.nextInt(lettersAndNumbers.length)))
    randomStream.map(index => lettersAndNumbers(index).toByte)
  }

  // Define the HTTP route
  val randomDataRoute: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "data" => {
      val respBody = (1 to 100).map(n => s"$n").mkString(", ") + "\n"
      IO(println("Received a request for random data")) *>
        Ok(Stream.emits[IO, Byte](respBody.getBytes))
    }

  }

  // Build and start the server
  def run(args: List[String]): IO[ExitCode] = {
    IO(println("*************************************")) *>
      IO(println("          Server starting")) *>
      IO(println("*************************************")) *>
    BlazeServerBuilder[IO]
      .bindHttp(8080, "0.0.0.0")
      .withHttpApp(randomDataRoute.orNotFound)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
  }
}
