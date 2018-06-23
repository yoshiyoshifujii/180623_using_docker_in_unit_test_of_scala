import com.amazonaws.services.dynamodbv2.model._
import com.whisk.docker.DockerContainer
import docker.DockerDynamoDBSpecSupport
import org.scalatest.{ FlatSpec, Matchers }

import scala.collection.JavaConverters._

class DynamoDBSpec extends FlatSpec with Matchers with DockerDynamoDBSpecSupport {

  private val TableName: String = "sample-table"

  override def dockerContainers: List[DockerContainer] =
    dynamoDBContainer :: super.dockerContainers

  override def beforeAll(): Unit = {
    super.beforeAll()

    val createTableRequest = new CreateTableRequest()
      .withTableName(TableName)
      .withAttributeDefinitions(
        new AttributeDefinition("id", ScalarAttributeType.S)
      )
      .withKeySchema(
        new KeySchemaElement("id", KeyType.HASH)
      )
      .withProvisionedThroughput(
        new ProvisionedThroughput(1L, 1L)
      )

    dynamoDBClient.createTable(createTableRequest)
  }

  override def afterAll(): Unit = {
    dynamoDBClient.deleteTable(TableName)
    super.afterAll()
  }

  "DynamoDB" should "success" in {
    dynamoDBClient
      .putItem(
        TableName,
        Map(
          "id" -> new AttributeValue().withS("aaaaa")
        ).asJava
      )
      .getAttributes shouldBe null

    dynamoDBClient
      .getItem(
        TableName,
        Map(
          "id" -> new AttributeValue().withS("aaaaa")
        ).asJava
      )
      .getItem
      .asScala("id")
      .getS shouldBe "aaaaa"
  }

}
