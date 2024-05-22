
# Virtual Memory Management System

## Introduction

This project aims to develop a virtual memory management system to simulate and evaluate the behavior of a process under different memory allocation scenarios. The system uses the "Not Recently Used" page replacement algorithm to manage memory efficiently.

## Objectives

- Understand the importance of proper memory management considering the support infrastructure, number of running processes, resource demand per process, and decisions to add or remove allocated page frames.
- Build a prototype virtual memory management system to simulate the behavior of a process based on available resources.

## Features

### Memory Management Simulation

- **Page Replacement Algorithm**: Implements the "Not Recently Used" page replacement algorithm as described by Tanenbaum in "Modern Operating Systems."
- **Page Fault Handling**: Calculates the number of page faults and the hit ratio (hits vs. misses).

### Performance Evaluation

- **Access Time Calculation**: Computes the time taken for hits, misses, and compares it with an ideal scenario where all references are in RAM versus all references leading to page faults.
- **Multi-threading**: Simulates concurrent operations with two threads:
  - One updates the page table and RAM frames.
  - The other updates the R bit based on a predefined algorithm.

## Components

### Reference Generation

- Generates a file with page references based on the size of the data matrix and page size.
- Stores matrices in row-major order and includes metadata such as page size, matrix dimensions, and number of references.

### Data Calculation

- Reads the reference file and simulates the paging behavior.
- Calculates the number of page faults, hit ratio, and access times.
- Runs two concurrent threads for page table updates and R bit management.

## How to Run

### Prerequisites

- Java Development Kit (JDK) installed.

### Running the Application

1. **Compile the Application**
   ```bash
   javac App.java
   ```

2. **Run the Application**
   ```bash
   java App
   ```

### Configuration

- Configure the number of concurrent clients and page frames within the application settings.

## Performance Metrics

- **Page Faults**: Number of times a page is not found in RAM.
- **Hit Ratio**: Percentage of references found in RAM.
- **Access Times**: Time taken for hits and misses, comparing with ideal scenarios.

## Data Analysis

- Collect data for different matrix sizes and page frame allocations.
- Generate tables and graphs to visualize performance.
- Analyze and interpret the results to understand the impact of virtual memory on process performance.

## References

- "Modern Operating Systems," A. S. Tanenbaum, 3rd Edition, Pearson, 2009.
