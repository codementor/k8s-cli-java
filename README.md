# k8s-cli-java project

This project is an example of how to extend kubernetes cli (kubectl) with a Java application.  It demonstrates the use of Kubernetes [client-java](https://github.com/kubernetes-client/java) and access the Kubernetes API.  In order to be a kubectl plugin it is necessary to have a file which uses the defined naming convention of `kube-<plugin-name>`.  This is a challenge in Java.  This project shows the use of [Graal](https://www.graalvm.org/) in order to build a native image using the [gradle palantir plugin](https://github.com/palantir/gradle-graal).  In order to provide a good CLI experience [picocli](https://picocli.info/) is used.


## Prerequisites

* Running Kubernetes 1.15+ cluster.  [Kind 0.7.0](https://github.com/kubernetes-sigs/kind) was used for testing.
* Java 1.8 and Graal
* Gradle


## Getting Started

Run `./gradlew native`

This should boot strap gradle (version 6.1.1), it will also pull down graalvm and `nativeImage` command used to build the nativeImage.

The output will show a number of graal warnings which for the purposes of this demonstration can be ignored.  

example output:

```bash
21:12 $ ./gradlew clean native

> Task :nativeImage
Build on Server(pid: 38354, port: 55534)
[kubectl-example:38354]    classlist:   5,357.88 ms
[kubectl-example:38354]        (cap):   1,602.01 ms
[kubectl-example:38354]        setup:   1,785.98 ms
[kubectl-example:38354]   (typeflow):   3,291.87 ms
[kubectl-example:38354]    (objects):   3,970.93 ms
[kubectl-example:38354]   (features):     199.88 ms
[kubectl-example:38354]     analysis:   7,622.07 ms
[kubectl-example:38354]     (clinit):     139.81 ms
[kubectl-example:38354]     universe:     244.68 ms
Warning: Reflection method java.lang.Class.forName invoked at org.bouncycastle.pqc.jcajce.provider.BouncyCastlePQCProvider$2.run(Unknown Source)
Warning: Reflection method java.lang.Class.forName invoked at org.bouncycastle.jcajce.provider.symmetric.util.ClassUtil$1.run(Unknown Source)
Warning: Reflection method java.lang.Class.forName invoked at picocli.CommandLine$BuiltIn$ClassConverter.convert(CommandLine.java:12467)
Warning: Reflection method java.lang.Class.newInstance invoked at picocli.CommandLine$DefaultFactory.create(CommandLine.java:4814)
Warning: Reflection method java.lang.Class.newInstance invoked at org.bouncycastle.jce.provider.BouncyCastleProvider.loadAlgorithms(Unknown Source)
Warning: Reflection method java.lang.Class.newInstance invoked at org.bouncycastle.pqc.jcajce.provider.BouncyCastlePQCProvider.loadAlgorithms(Unknown Source)
Warning: Reflection method java.lang.Class.getMethods invoked at picocli.CommandLine.getCommandMethods(CommandLine.java:2980)
Warning: Reflection method java.lang.Class.getDeclaredMethods invoked at picocli.CommandLine.getCommandMethods(CommandLine.java:2982)
Warning: Reflection method java.lang.Class.getDeclaredMethods invoked at picocli.CommandLine$Model$CommandReflection.initFromAnnotatedFields(CommandLine.java:9784)
Warning: Reflection method java.lang.Class.getDeclaredConstructor invoked at picocli.CommandLine$DefaultFactory.create(CommandLine.java:4808)
Warning: Reflection method java.lang.Class.getDeclaredConstructor invoked at picocli.CommandLine$DefaultFactory.create(CommandLine.java:4817)
Warning: Reflection method java.lang.Class.getDeclaredFields invoked at picocli.CommandLine$Model$CommandReflection.initFromAnnotatedFields(CommandLine.java:9781)
Warning: Reflection method java.lang.ClassLoader.loadClass invoked at org.bouncycastle.pqc.jcajce.provider.BouncyCastlePQCProvider.loadClass(Unknown Source)
Warning: Reflection method java.lang.ClassLoader.loadClass invoked at org.bouncycastle.jcajce.provider.symmetric.util.ClassUtil.loadClass(Unknown Source)
Warning: Aborting stand-alone image build due to reflection use without configuration.
Warning: Use -H:+ReportExceptionStackTraces to print stacktrace of underlying exception
Build on Server(pid: 38354, port: 55534)
[kubectl-example:38354]    classlist:      86.63 ms
[kubectl-example:38354]        (cap):   1,163.05 ms
[kubectl-example:38354]        setup:   1,325.03 ms
[kubectl-example:38354]   (typeflow):   1,351.53 ms
[kubectl-example:38354]    (objects):   1,487.00 ms
[kubectl-example:38354]   (features):      84.36 ms
[kubectl-example:38354]     analysis:   2,962.47 ms
[kubectl-example:38354]     (clinit):      46.62 ms
[kubectl-example:38354]     universe:     102.52 ms
[kubectl-example:38354]      (parse):     109.68 ms
[kubectl-example:38354]     (inline):     530.39 ms
[kubectl-example:38354]    (compile):     422.22 ms
[kubectl-example:38354]      compile:   1,174.70 ms
[kubectl-example:38354]        image:     159.67 ms
[kubectl-example:38354]        write:     114.39 ms
[kubectl-example:38354]      [total]:   5,952.42 ms
Warning: Image 'kubectl-example' is a fallback image that requires a JDK for execution (use --no-fallback to suppress fallback image generation).
native image available at build/graal/kubectl-example (3 MB)

Deprecated Gradle features were used in this build, making it incompatible with Gradle 7.0.
Use '--warning-mode all' to show the individual deprecation warnings.
See https://docs.gradle.org/6.1.1/userguide/command_line_interface.html#sec:command_line_warnings

BUILD SUCCESSFUL in 23s
4 actionable tasks: 4 executed
```

This will result in an executable `kubectl-example` being built in `build/graal/` under the project root.

Start a cluster:  `kind create cluster`

and run one of the commands:  `./build/graal/kubectl-example pod list`

Example:

```bash
./build/graal/kubectl-example pod list
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
________________________________________________________________
| Pod Name                                  | namespace         |
|===============================================================|
| coredns-6955765f44-f966p                  | kube-system       |
| coredns-6955765f44-xnzbg                  | kube-system       |
| etcd-kind-control-plane                   | kube-system       |
| kindnet-cznll                             | kube-system       |
| kube-apiserver-kind-control-plane         | kube-system       |
| kube-controller-manager-kind-control-plane| kube-system       |
| kube-proxy-tw9cb                          | kube-system       |
| kube-scheduler-kind-control-plane         | kube-system       |
| local-path-provisioner-7745554f7f-gk5j9   | local-path-storage|
```

## List of Commands

* pod list
* pod list2
* pod add <pod-name> [-n namespace] [-i image]
* resources

## Adding as a kubectl plugin

The executable needs to be the path.  From the root of the project run: `export PATH=$PATH:$PWD/build/graal/`

Now give `kubectl` a try with `example`.  run: `kubectl example pod list`
You should get the same output as above.
