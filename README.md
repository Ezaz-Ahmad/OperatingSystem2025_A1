# Operating Systems Scheduling Simulator

This repository contains a Java implementation of four scheduling algorithms (FCFS, RR, SRR, and FB) for the COMP2240/COMP6240 Assignment 1. The program simulates process scheduling, computes turnaround and waiting times, and provides a comparative summary.

## Description
A Java implementation of FCFS, RR, SRR, and FB scheduling algorithms for operating systems assignment.

## Features
- Simulates First Come First Serve (FCFS), Round Robin (RR), Selfish Round Robin (SRR), and Feedback (FB) scheduling.
- Calculates per-process and average turnaround and waiting times.
- Outputs results in a formatted table for easy comparison.

## Requirements
- Java Development Kit (JDK) 21 or higher.

## Installation
1. Ensure all `.java` files (`A1.java`, `Process.java`, `Scheduler.java`, `FCFSScheduler.java`, `RRScheduler.java`, `SRRScheduler.java`, `FBScheduler.java`) are in the same directory.

## Usage
1. Compile the code: `javac A1.java`
2. Run the program with an input file: `java A1 datafile1.txt`
   - Input file should follow the format specified in the assignment (e.g., `datafile1.txt`).

## Input Format
- `DISP:` followed by dispatcher time.
- Each process block with `PID:`, `ArrTime:`, and `SrvTime:`.

## Output
- Process execution order with timestamps (e.g., `T1: p1`).
- Tabulated turnaround and waiting times per process.
- Average turnaround and waiting times.
- Summary table comparing all algorithms.


--- 
*Created on August 30, 2025, 06:57 PM AEST*
