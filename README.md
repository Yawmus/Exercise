# Exercise
Unicity exercise

Run these commands to start the server
docker run -p 80:80 --rm -v $PWD:/app -w /app java:8 javac Main.java
docker run -p 80:80 --rm -v $PWD:/app -w /app java:8 java Main