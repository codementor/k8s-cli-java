package com.d2iq.kubectl;


import picocli.CommandLine;

@CommandLine.Command(
        name = "add",
        description = "Adds a pod to a k8s cluster"
)
public class PodAddCommand implements Runnable {
    @Override
    public void run() {
        System.out.println("pod add");
    }
}
