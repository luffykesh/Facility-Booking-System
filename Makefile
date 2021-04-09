.PHONY: build

build:
	./gradlew assemble

test:
	./gradlew test
run:
	./gradlew bootRun

clean:
	./gradlew clean