package com.d2iq.kubectl;

import picocli.CommandLine;

@CommandLine.Command(
        name = "pod",
        description = "Programmatically accesses pods in a k8s with 'add', 'list' and 'list2'",
        subcommands = {
                PodAddCommand.class,
                PodListCommand.class,
                PodList2Command.class
        }
)
public class PodCommand {
}
