Source code from my lecture: TODO

## Used commands

### RSS vs PSS
```shell script
java -Xms80G -Xmx80G HelloWorld
java -Xms2G -Xmx2G -XX:+AlwaysPreTouch HelloWorld
java -XX:+UnlockExperimentalVMOptions -Xms2G -Xmx2G -XX:+AlwaysPreTouch -XX:+UseZGC HelloWorld
smem -c "pid command rss pss" -ak -P "Hello"
```

### Heap
```shell script
java -Xlog:gc,heap*=debug HelloWorld
java -Xms1G -Xlog:gc,heap*=debug HelloWorld
java -Xms100M -XX:InitialHeapSize=2G -Xlog:gc,heap*=debug HelloWorld
jcmd `pgrep -f HelloWorld` GC.heap_info
jcmd `pgrep -f HelloWorld` GC.run
jcmd `pgrep -f HelloWorld` GC.class_histogram
jcmd `pgrep -f HelloWorld` GC.heap_dump /tmp/dump.hprof
grep "gc .*\-> " gc.log | sed "s/.*\[\(.*s\]\)/\1/" | sed "s/s\].*>/;/" | sed "s/M.*//" | sed "s/\./,/" > out.csv
```

### Class
```shell script
jcmd `pgrep -f HelloWorld` VM.class_hierarchy
jcmd `pgrep -f HelloWorld` VM.classloaders
jcmd `pgrep -f HelloWorld` VM.classloader_stats
java -Xlog:class+unload,class+load=info Classes
java -agentpath:/opt/profiler/build/libasyncProfiler.so=start,file=/tmp/prof.svg,event=java.lang.ClassLoader.loadClass Classes
```

### JIT
```shell script
java -Xlog:codecache+sweep*=trace,jit+compilation=debug HelloWorld
jcmd `pgrep -f HelloWorld` Compiler.codelist
```

### Threads
```shell script
jcmd `pgrep -f HelloWorld` Thread.print
java -Xms1G -Xmx1G -XX:+AlwaysPreTouch -XX:NativeMemoryTracking=summary ThreadMemoryConsume
smem -c "pid command rss pss" -ak -P "ThreadMemoryConsume"
```

### NMT
```shell script
jcmd `pgrep -f ThreadMemoryConsume` VM.native_memory scale=MB
jcmd `pgrep -f HelloWorld` Thread.print | grep tid | wc -l
java -XX:NativeMemoryTracking=summary HelloWorld
jcmd  `pgrep -f HelloWorld` VM.native_memory``
```

### GC
```shell script
java -Xms2G -Xmx2G -XX:+AlwaysPreTouch -XX:+UseConcMarkSweepGC -XX:NativeMemoryTracking=summary GCMem
java -Xms2G -Xmx2G -XX:+AlwaysPreTouch -XX:+UseG1GC -XX:NativeMemoryTracking=summary GCMem
ps aux | grep GCMem
jcmd <pid> VM.native_memory
```

### String table
```shell script
java -cp ".:commons-lang3-3.9.jar" StringPool
jcmd `pgrep -f StringPool` VM.stringtable
java -cp ".:commons-lang3-3.9.jar" -agentpath:/home/pasq/Aplikacje/AsyncProfiler/build/libasyncProfiler.so=start,file=/tmp/prof.svg,event=Java_java_lang_String_intern StringPool

java -XX:ParallelGCThreads=2 -Xlog:gc=debug -cp ".:commons-lang3-3.9.jar" StringPoolG1
java -XX:ParallelGCThreads=2 -Xlog:gc=debug -cp ".:commons-lang3-3.9.jar" StringPoolG1NoPool
java -XX:ParallelGCThreads=2 -Xlog:gc,gc+phases=debug -cp ".:commons-lang3-3.9.jar" StringPoolG1
```

### Off heap
```shell script
java -Xms1G -Xmx1G -XX:+AlwaysPreTouch -XX:NativeMemoryTracking=summary FileChannelExample
jcmd `pgrep -f FileChannelExample` VM.native_memory scale=MB
smem -c "pid command rss pss" -ak -P "FileChannelExample"
pmap -x `pgrep -f FileChannelExample`
```

### Malloc
```shell script
java -Xmx500M -XX:MaxDirectMemorySize=4G -XX:NativeMemoryTracking=summary ByteBufferFragmentationExample
jcmd `pgrep -f ByteBufferFragmentationExample` VM.native_memory scale=MB
smem -c "pid command rss pss" -ak -P "ByteBufferFragmentationExample"
```

### Native invocation
```shell script
java -Xms1G -Xmx1G -XX:+AlwaysPreTouch -XX:NativeMemoryTracking=summary Zip
smem -c "pid command rss pss" -ak -P "Zip"
jcmd  `pgrep -f Zip` VM.native_memory scale=MB
```

### OS cache
```shell script
java -Xms3G -Xmx3G -XX:+AlwaysPreTouch Database
```

## My JVM log
```
codecache+sweep*=trace,jit+compilation=debug
class+unload,class+load,
os+thread,
safepoint,
gc*,gc+ergo=trace,gc+age=trace,gc+phases=trace,gc+humongous=trace
```
