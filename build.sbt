name := "180623_using_docker_in_unit_test_of_scala"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  "com.whisk"     %% "docker-testkit-scalatest"    % "0.9.5" % Test,
  "com.whisk"     %% "docker-testkit-impl-spotify" % "0.9.5" % Test,
  "com.amazonaws" % "aws-java-sdk-dynamodb"        % "1.11.353"
)
