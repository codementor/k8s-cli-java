package com.d2iq.kubectl;


import picocli.CommandLine;

@CommandLine.Command(
        name = "example",
        description = "An example kubectl plugin",
        subcommands = {
                PodCommand.class,
                ResourcesCommand.class
        }

)
public class ExampleCommand implements Runnable {

    @Override
    public void run() {
        System.out.printf("example");
    }
}
