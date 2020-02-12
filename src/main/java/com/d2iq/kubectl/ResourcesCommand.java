package com.d2iq.kubectl;


import picocli.CommandLine;

@CommandLine.Command(
        name = "resources",
        description = "lists resources in the k8s cluster"
)
public class ResourcesCommand implements Runnable{
    @Override
    public void run() {
        System.out.println("resources");
    }
}
