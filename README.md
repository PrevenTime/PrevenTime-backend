# Install

Ensure Java 11 and Maven 3.6 is installed

Then run `mvn package`

# Development

Open project with IntelliJ IDEA, it should be auto detected as a maven project.
Run `mvn package`. You may need to add `target/generated-sources/java` as auto
generated sources (left click on the directory > mark Directory as > Generated sources).

Before starting the app, start the database by running `docker-compose up`. After
that you can start the app.

You can find endpoints and examples in the insomnia project `preventime_insomnia.json`