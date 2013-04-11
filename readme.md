This project is a sample of how to set up GWT-Driver and GXT-Driver for use in a maven build. Take a look at each commit to see what is added as an example of how you can build tests before code, or go back and write more tests.

This is *not* a best practice of writing Selenium/WebDriver code, nor is it a best practice of integration tests in general. Instead, it is *only* a demonstration of how to use GWT-Driver, plus a relatively straightforward way of building maven out to run these tests.

Things this does:

 * Build a HelloWorld style app, and some simple tests demonstrating how to poke it and be sure it behaves.

Things this does not do:

 * Show how to make a good GWT app. As long as you are using Widgets in your app, you can use gwt-driver to find them.
 * Demonstrate good project setup. This does an _okay_ job of breaking tests out from the project, and building almost the same way
 * Demonstrate how to run tests in multiple browsers. There are many right ways to do this - this does none of them, but only works with a local Firefox instance.

To run the project and see the app for yourself, use `mvn jetty:run`. To build a war file, use `mvn install`. To build a war file if and only if integration tests pass, use `mvn -Pintegration-test`.

Walk through this build one commit at a time - the important parts are these:

 * [Initial project](https://github.com/niloc132/gwt-driver-sample/commit/05438895af547f81a42c1cbfa6166cac28825f43), with a simple pom, gwt module, and entrypoint 
 * [First pass at test](https://github.com/niloc132/gwt-driver-sample/commit/71422937a46e896818bd52b3f89342c73df8d012) - added g\*t-driver dependencies, profile to run them (and exclude from `test:test`), a sample util to make static find calls easier, and two simple test methods that both fail
 * [Actual (dummy) wiring](https://github.com/niloc132/gwt-driver-sample/commit/1dc5fa4aa6e442976c08488a3d1590f368f6f2e8) in the login button, and a `Thread.sleep` (should be a `Wait`!) in the test to make the login test pass
 * [Finished wiring and added one more test](https://github.com/niloc132/gwt-driver-sample/commit/d482b4a351237f614e71442ebbc6d17036d335c9) - this is the first point where `mvn -Pintegration-test` actually finishes successfully.


