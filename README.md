## OMS Workflow Demo
Simple project demonstrating usage of [Temporal](https://www.temporal.io/) Worklow engine.

---
- Multi-module project
  - oms-driver - REST API that allows to initiate a workflow, query existing workflows and send signals to them 
  - oms-worker - Workflow implementation and activities. More than one instance of the worker could be run
  - oms-common - Workflow interface and domain objects
- Technologies used:
  - Language: [Kotlin](https://kotlinlang.org/)
  - Build tool: Gradle with Kotlin DSL
  - Dependency injection and Web framework: [Micronaut](https://micronaut.io/) 

### Implementation Notes
Domain object that are submitted to workflows and activities must be serializable.
Temporal uses pluggable mechanism of such serialization. By default, objects are serialized to/from JSON using [Jackson](https://github.com/FasterXML/jackson).
In order to use Kotlin data classes, Jackson's ObjectMapper must be customized to register kotlin support module.
ObjectMapper used by Micronaut already registers this module, so it could be passed to Temporal *JacksonJsonPayloadConverter*. See *com.jpragma.oms.WorkflowClientFactory.Companion.customPayloadConverter* as example.

Jackson still doesn't support Kotlin's [Inline Value classes](https://kotlinlang.org/docs/inline-classes.html). This is the reason I used Strings as IDs in domain objects. 
I tried to create a custom Temporal PayloadConvertor using Kotlin's native [Kotlin Serialization](https://github.com/Kotlin/kotlinx.serialization). While kotlin-serialization supports data classes, inline classes, etc. 
it requires specifying serialized/deserialized classes at compile time. So far, I couldn't find how to implement generic PayloadConverter
that can take care of serialization/deserialization of an arbitrary domain object.  