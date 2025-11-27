FROM gradle:8.12.1-jdk21

WORKDIR /java-project-99

COPY . .

RUN ["./gradlew", "clean", "build"]

CMD ["./build/install/app/bin/app"]
