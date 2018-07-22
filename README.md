# check_java


This is a Nagios/Shinken plugin that allows the easy monitoring of Java
applications through Java's JMX interface.

# Usage

`check_java` will connect to a Java application and check one type of property
per run against user-defined limits.
This is governed by command line parameters to `check_java`, it is called like this:

    check_java [-u URL] -t TYPE -w WARN -c CRIT

The meaning of the parameters is as follows:

    -u URL: The JMX service URL check_java will connect to. For details on how the
            URL is constructed see the section "URL". If no URL is given, a default
            URL is used:
               service:jmx:rmi:///jndi/rmi://:9000/jmxrmi

    -t TYPE: The property type to check. See the Section "Types" for more
             information about the available types.

    -w WARN: The warning threshold. If a property value is greater than this number,
             check_java will return a WARNING state to Nagios.

    -c CRIT: The critical threshold. If a property value is greater than this number,
             check_java will return a CRITICAL state to Nagios.

# Starting a JMX agent

You can turn any Java application into a JMX server (aka JMX agent) by supplying
some additional parameters to the JVM. Call your JMX application like this:

    java \
    -Dcom.sun.management.jmxremote \
    -Dcom.sun.management.jmxremote.authenticate=false \
    -Dcom.sun.management.jmxremote.ssl=false \
    -Dcom.sun.management.jmxremote.port=9000 \
    -Djava.rmi.server.hostname=localhost \

This will start a JMX agent on the loopback interface that listens for incoming
connections on port `9000`. Authentication and SSL are turned off as they are
currently unsupported by `check_java`.
In case your application is also using RMI, you should probably drop the last
parameter so no hostname and thus interface is set. Most applications will take
care of this automatically, so you should not interfere. In this case, you should
be extra careful not to expose the JMX port to the whole world, as anyone could
just connect and use JMX to potentially even control your JVM.

# URL

After you have started your JMX agent, you need to tell `check_java` how it can
connect to that agent. This is done through the URL parameter `-u`. JMX agent URLs
are constructed in the following manner:

    service:jmx:rmi:///jndi/rmi://<TARGET_MACHINE>:<RMI_REGISTRY_PORT>/jmxrmi

`<TARGET_MACHINE>` is the IP address (or resolvable name) of the machine you want
to connect to, normally the same as the java.rmi.server.hostname property you
set when starting the agent. the `<RMI_REGISTRY_PORT>` is the port number set
using the `com.sun.management.jmxremote.port` property you set when starting the
agent.

If the URL parameter `-u` is not given on the command line, `check_java` uses the
default URL `service:jmx:rmi:///jndi/rmi://:9000/jmxrmi`. Using this URL,
`check_java` will connect to a JMX agent on localhost at port `9000`.

# Types

`check_java` is able to check several standard JMX attributes provided by the
JVM itself, thus independent of the actually running program. Currently it is
not possible to check arbitrary JMX attributes.

A check can be invoked by giving the check name to the `check_java` type parameter
`-t`.

## HeapMem

This check will return the amount of used heap memory. In the performance data
fields, also the values for the initial allocation size of the heap memory,
the current allocation of the heap memory, and the maximum allowed heap memory
allocation are returned.

## NonHeapMem

This check will return the amount of used non-heap memory. In the performance
data fields, also the values for the initial allocation size of the non-heap
memory, the current allocation of the heap memory, and the maximum allowed heap
memory allocation are returned.

## MemPoolEden

This check will return the amount of used memory in the memory pool Eden. In the
performance data fiels, the values for the initial size of the memory pool, the
current size of the memory pool, and the current maximum size of the memory pool
are returned.

## MemPoolSurvivor

This check will return the amount of used memory in the memory pool Survivor. In the
performance data fiels, the values for the initial size of the memory pool, the
current size of the memory pool, and the current maximum size of the memory pool
are returned.

## MemPoolCodeCache

This check will return the amount of used memory in the memory pool Code Cache. In the
performance data fiels, the values for the initial size of the memory pool, the
current size of the memory pool, and the current maximum size of the memory pool
are returned.

## GCMarkSweepCount

This check will return the number of runs of the Mark and Sweep Garbage Collector.
This is currently an experimental feature and not yet thoroughly tested.

## GCMarkSweepTime

This check will return the amount of time consumed by the Mark and Sweep Garbage
Collector.
This is currently an experimental feature and not yet thoroughly tested.

## ClassLoading

This check will return the number of currently loaded classes. In the performance
data fields, the number of total loaded classed, and the number of unloaded classes
are returned as well.

## ThreadCount

This check will return the number of currently existing threads. In the performance
data fields, the number of total started threads, and the number of currently
existing daemon threads are returned as well.

# License

Copyright (c) 2015, Sebastian Schäfer <me@sebastianschaefer.net>
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
   notice, this list of conditions and the following disclaimer in the
   documentation and/or other materials provided with the distribution.
 * Neither the name of check_java nor the
   names of its contributors may be used to endorse or promote products
   derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL SEBASTIAN SCHÄFER BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

