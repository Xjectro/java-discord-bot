.PHONY: build run clean test format

run:
	gradle run

build:
	gradle build

clean:
	gradle clean

test:
	gradle test

format:
	gradle spotlessApply