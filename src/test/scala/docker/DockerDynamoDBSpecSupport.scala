package docker

import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.{AmazonDynamoDB, AmazonDynamoDBClientBuilder}
import com.whisk.docker.DockerContainer
import org.scalatest.Suite

trait DockerDynamoDBSpecSupport extends DockerSpecSupport { this: Suite =>

  protected val dynamoDBPort: Int = RandomSocket.nextPort()
  protected val region: String = Regions.AP_NORTHEAST_1.getName

  protected val dynamoDBContainer: DockerContainer =
    DockerContainer("fingershock/dynamodb-local:latest")
      .withPorts(8000 -> Some(dynamoDBPort))

  protected val dynamoDBClient: AmazonDynamoDB =
    AmazonDynamoDBClientBuilder.standard()
    .withEndpointConfiguration(new EndpointConfiguration(s"http://127.0.0.1:$dynamoDBPort", region))
    .build()

}
