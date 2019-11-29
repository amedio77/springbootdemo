cp target/demo-0.0.1-SNAPSHOT.jar .

docker build -t amedio/springboot-demo:1.0 .
docker push amedio/springboot-demo:1.0
