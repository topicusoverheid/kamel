# Using Kamel

Kamel heavily relies on Kotlin's [extensions](https://kotlinlang.org/docs/reference/extensions.html). This means that to use Kamel's functionality, no special superclasses or anything are required. You can easily convert your Java class(es) to Kotlin code and start enjoying Kamel's ease-of-use immediately.

## Including the library

The first step to start using Kamel is to include the library. To do so, we use Maven. Add the following dependency to your list of Maven dependencies:
```xml
<dependency>
  <groupId>nl.topicus.overheid</groupId>
  <artifactId>kamel</artifactId>
  <version>1.0</version>
</dependency>
```

After that, you're ready to go.

## Start using Kamel

To start using Kamel, you can either declare a new Kotlin file or convert an existing `RouteBuilder` class to Kotlin. An example for an empty `RouteBuilder` could be:
```kotlin
package com.foo.bar

import org.apache.camel.builder.RouteBuilder

class ExampleRouteBuilder: RouteBuilder() {
	override fun configure() {
		// TODO: Implement using Kamel
	}
}
```

The example above is a very simple class declaration in which we can start declaring a new `RouteBuilder`. Traditionally, you might start with a call to Camel's `from(String)` method to start building a route using the Java Fluid-DSL, like so:
```java
package com.foo.bar;

import org.apache.camel.builder.RouteBuilder;

class ExampleRouteBuilder extends RouteBuilder {
	@Override
	public void configure() {
		from("direct:example")
			.log("This is an example")
...
```

Kamel's way of starting a route is very similar:
```kotlin
package com.foo.bar

import org.apache.camel.builder.RouteBuilder

class ExampleRouteBuilder: RouteBuilder() {
	override fun configure() {
		from("direct:example") {
			log("This is an example")
			...
		}
...
```

The main difference is the block scoping that Kamel offers. Java's Fluid DSL starts with a call to `from` and after that, any succeeding call is added to the method chain until you finish the route. Instead of chaining methods together, Kamel requires you to start a scope by adding a `{`. After that curly brace, anything within the block is scoped to that specific `Route` instead of the `RouteBuilder`. By doing this, separation of routes is much clearer, which becomes even more apparent when defining sub-scopes such as for example a `choice`, which could look somewhat like this in Java:
```java
package com.foo.bar;

import org.apache.camel.builder.RouteBuilder;

class ExampleRouteBuilder extends RouteBuilder {
	@Override
	public void configure() {
		from("direct:example")
			.log("This is an example")
			.choice()
				.when(exchangeProperty("someprop").isNull())
					.log("someprop was null")
				.otherwise()
					.log("someprop wasn't null")
			.end()
...
```

The code above will become the following in Kotlin/Kamel:
```kotlin
package com.foo.bar

import org.apache.camel.builder.RouteBuilder

class ExampleRouteBuilder: RouteBuilder() {
	override fun configure() {
		from("direct:example") {
			choice {
				`when`(exchangeProperty("someprop").isNull) {
					log("someprop was null")
				} otherwise {
					log("someprop wasn't null")
				}
			}
...
```
By defining the choices in their own blocks, a Kamel choice looks much more like an `if`-/`else`-structure you'd normally see. An added benefit is the fact that formatting can be enforced by formatting tools, whereas in the Java sample most code formatters would simply pull all chained calls to the same indentation level making your code much less readable.

>Note: One disadvantage of the previous sample is the fact that `when` is a reserved keyword in Kotlin, adding the need to declare a call to `when(Predicate)` in backticks. To overcome this, an alias for the method has been declared called `given`, in the example above the call to ````when`(exchangeProperty("someprop").isNull)```` would become ````given(exchangeProperty("someprop").isNull)````, eliminating the need to add the backticks.

## Defining REST endpoints with Kamel

The examples in the previous chapter showed how you'd declare a direct `Route` in Camel. Another large usecase for which we use Camel is to expose various REST endpoints. Kamel offers various features to make declaring such services a lot easier as well.

Lets start with another example. Say you have the following (start of your) Java `RouteBuilder`:
```java
package com.foo.bar;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestParamType;

class ExampleRouteBuilder extends RouteBuilder {
	@Override
	public void configure() {
		rest("/rest/examples")
			.produces("application/json")
			.get("/")
				.description("Gives you a list of all examples")
				.outType(Example[].class)
				.to("direct:getAllExamples")
			.put("/")
				.description("Save a new example")
				.type(Example.class)
				.to("direct:saveNewExample")
			.get("/{id}")
				.description("Gets the example with given ID")
				.outType(Example.class)
				.param().name("id").type(RestParamType.path).description("The ID of the example").endParam()
				.to("direct:getExampleById")
...
```
As was shown before, the fact that this code is quite readable is mainly due to the fact that the indendation shows the structure of your service.

Translating the above to Kotlin, you'd end up with the following code:
```kotlin
package com.foo.bar

import org.apache.camel.builder.RouteBuilder

class ExampleRouteBuilder: RouteBuilder() {
	override fun configure() {
		rest("/rest/examples") {
			produces("application/json")

			get("/") {
				description("Gives you a list of all examples")
				outType(Array<Example>::class.java)
				to("direct.getAllExamples")
			}

			put("/") {				
				description("Save a new example")
				type(Example.class)
				to("direct:saveNewExample")
			}

			get("/{id}") {
				description("Gets the example with given ID")
				outType(Example.class)
				pathParam {
					name("id")
					description("The ID of the example")
				}
				to("direct:getExampleById")				
			}
...
```
All REST calls end up with their own little block definition, adding a lot of clarity and enforcing the structure you want. 

Another benefit is the more concise way to define your call's parameters, as shown above each parameter gets its own little block with a block name declaring the parameter type (`pathParam` in the example above). There can be blocks for all possible types of parameters, the following types are supported by Camel and thus by Kamel:
 - `bodyParam`
 - `formDataParam`
 - `headerParam`
 - `pathParam`
 - `queryParam`

## Unavailable features?
Kamel has been created to improve the way we at [Topicus Overheid](http://github.com/topicusoverheid) work with Apache Camel. This means that the functionality offered by the framework is largely adapted to our method of using Camel and it might not cover your use case entirely.

If you stumble upon such a situation, you can always fall back to using the Java Fluid DSL intermixed with Kamel, which is possible due to the Java interoperability in Kotlin. This might mean your code becomes a bit less clear, so if your feature might benefit others, feel free to [contribute](CONTRIBUTING.md) a change to the project.