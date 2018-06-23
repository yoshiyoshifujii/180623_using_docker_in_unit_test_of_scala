package docker

import com.spotify.docker.client.{ DefaultDockerClient, DockerClient }
import com.whisk.docker.DockerFactory
import com.whisk.docker.impl.spotify.SpotifyDockerFactory
import com.whisk.docker.scalatest.DockerTestKit
import org.scalatest.Suite

trait DockerSpecSupport extends DockerTestKit { this: Suite =>

  protected val dockerClient: DockerClient =
    DefaultDockerClient.fromEnv().build()

  override implicit def dockerFactory: DockerFactory =
    new SpotifyDockerFactory(dockerClient)

}
