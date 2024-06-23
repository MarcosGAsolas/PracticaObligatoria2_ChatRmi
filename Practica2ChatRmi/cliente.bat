set CLASSPATH=.\target\Practica2ChatRmi-0.0.1-SNAPSHOT.jar

java -Djava.security.policy=registerit.policy ^
-Djava.rmi.server.codebase=http://localhost:8080/Practica2ChatRmi-Web/ ^
es.ubu.lsi.client.ChatClientDynamic %1