# Kamel: Kotlin DSL for Apache Camel

Kamel is a library which allows easier definition of routes in [Apache Camel](https://camel.apache.org/) using a custom DSL in [Kotlin](https://kotlinlang.org/). The DSL is crafted to make working with Camel a breeze. To do so, the library primarily utilizes two very powerful Kotlin features: [extensions](https://kotlinlang.org/docs/reference/extensions.html) and [higher-order functions](https://kotlinlang.org/docs/reference/lambdas.html). When using Kamel, writing Camel routes feels like using a Camel-specific programming language.

## Getting Started

Using Kamel is very easy. All you have to do is add the dependency to your project. This can easily be done using Maven. Add the following dependency to your POM:
```xml
<dependency>
  <groupId>nl.topicus.overheid</groupId>
  <artifactId>kamel</artifactId>
  <version>1.0</version>
</dependency>
```

As Kamel uses [extensions](https://kotlinlang.org/docs/reference/extensions.html) on exisiting classes, you do not need to extend a different `RouteBuilder` class and can start using Kamel in existing `RouteBuilders` immediately. Find out more about using Kamel in the [usage document](USAGE.md)

If you want to contribute to Kamel, follow the steps below to get a development environment set up.

### Prerequisites

The library uses Maven for dependency management, so you'll have to install it. I use [SDKMAN!](http://sdkman.io/) for managing Maven, which makes installation very easy, but install it in any way you like.

Git is used for version management so you'll have to install that as well.

My IDE of choise is [IntelliJ IDEA](https://www.jetbrains.com/idea/) which has very rich Kotlin capabilities, but you can use any IDE you're familiar with.

### Installing

First, start by cloning this repository:
```
git clone git@github.com:topicusoverheid/kamel.git kamel && cd kamel
```
>Note: The above assumes you're using Git over SSH, substitute the repository URL for `https://github.com/topicusoverheid/kamel.git` to use Git over HTTPS.

After cloning has finished, a good first step is to try and compile the library:
```
mvn clean compile
```

Import the project into your IDE of choice and start developing!

## Running the tests

Automated tests can be executed using Maven:
```
mvn test
```

Maven will execute them anytime you packge the library as well.

## Built With

* [Kotlin](http://kotlinlang.org) - Programming language
* [Maven](https://maven.apache.org/) - Dependency Management
* [Camel](https://camel.apache.org) - Framework for routing messages across various processors from and to endpoints

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

The versioning scheme we use is explained in the [Maven documentation](https://cwiki.apache.org/confluence/display/MAVEN/Version+number+policy). For the versions available, see the [tags on this repository](REPOURI/tags). 

## Authors

* **Bas Dalenoord** - *Initial work* - [Topicus Overheid](https://github.com/TopicusOverheid)

See also the full list of [contributors](https://github.com/topicusoverheid/kamel/contributors) for everybody who participated in this project.

## License

Copyright (c) 2018 Topicus Overheid    
This project is licensed under the Apache 2.0 - see the [LICENSE](LICENSE) file for details