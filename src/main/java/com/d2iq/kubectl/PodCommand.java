package com.d2iq.kubectl;

import picocli.CommandLine;

@CommandLine.Command(
        name = "pod",
        subcommands = {
                PodAddCommand.class,
                PodListCommand.class,
                PodList2Command.class
        }
)
public class PodCommand {
}
