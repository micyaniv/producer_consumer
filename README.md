# Simple Producer-Consumer code in Java
A small task in Operating Systems course, done for fun.
Show the use of Threads in Java.

## The Task:
Iterate through the root directory and its sub-directories and find all files with a certain suffix.
Copy all those files to the output path.

I changed the output of my "Copier" to copy the file path and not the file itself.

## Short description of the files:
 * SynchronizedQueue, a queue that can be used by multiple threads.
 * DiskSearcher, the main thread, starts the rest of the threads and configures the searched suffix, root and the results paths.
 * Scouter, scouts the root directory for all sub-directories.
 * Searcher, scans through file of a certain directory for a file with a certain suffix.
 * Copier, copies those file (names only) to an output file.

## Tasks
 - Make the DiskSearcher accept arguments for the root and result destinations as well as searched suffix.
 - Add time performance/throughput tests

## General info
IDC Computer Science, OS course, Ex2, 2014-2015
