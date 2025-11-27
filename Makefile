clean:
	./gradlew clean

install:
	./gradlew installDist

build:
	./gradlew build

test:
	./gradlew test

report:
	./gradlew jacocoTestReport

sonar:
	./gradlew sonar

lint:
	./gradlew checkstyleMain

run:
	./gradlew bootRun

.PHONY: build
