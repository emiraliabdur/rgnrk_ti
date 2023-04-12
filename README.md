<h1 align="center"> Poker Planning API </h1> <br>

<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Technical Stack](#tech-stack)
- [Build Process](#build-process)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

## Introduction
The middleware developer should create an API design to cover all the detected user cases, also should create a quick implementation of the SpringBoot application.
We recommend use a H2 database for persistence and simulate the interaction with a relational database.
All the Spring Framework is suitable to be used.


## Features
The following features and scenarios are covered:
- Feature: Session management
  - Scenario: New session
  - Scenario: Enter session
  - Scenario: Destroy poker planing session
- Feature: Votes management
  - Scenario: Start voting a user story
  - Scenario: Vote a user story
  - Scenario: Listen to the votation status
  - Scenario: Finish voting a user story
- Feature: User stories management
  - Scenario: Add a user story
  - Scenario: Delete a user story

## Technical Stack
To implement API the following stack was used:
- Java 17
- Spring Boot 3
- Build tool: Maven 
- Database: H2
- OpenAPI 3.0.1
- Maven Plugin to generate API Java classes: openapi-generator-maven-plugin

## Build Process
In order to follow API-first approach the `openapi-generator-maven-plugin` is used.
The API description is located under the path: `src/main/resources/spec/poker_planning_api.yaml`.
To generate model, configs, api classes run the maven command:
```shell
mvn clean package
```
All generated classes will reside in these packages (please check pom.xml file for more details): 
```xml
    <basePackage>com.rgnrk.rgnrk_ti</basePackage>
    <modelPackage>com.rgnrk.rgnrk_ti.model</modelPackage>
    <apiPackage>com.rgnrk.rgnrk_ti.api</apiPackage>
    <configPackage>com.rgnrk.rgnrk_ti.config</configPackage>
```
To run the application use the command or your IDE:
```shell
mvn spring-boot:run
```

