package com.d2iq.kubectl;

import picocli.CommandLine;

@CommandLine.Command(
        name = "list",
        description = "lists pods in the cluster using a structured approach"
)
public class PodListCommand implements Runnable {
    @Override
    public void run() {
        System.out.println("pod list");
    }
}
