/*
 * check_java - Tool to easily monitor Java applications with Nagios/Shinken
 * through JMX, the Java Monitoring Extensions.
 *
 * Copyright (c) 2015, Sebastian Schäfer <me@sebastianschaefer.net>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   * Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *   * Neither the name of check_java nor the
 *     names of its contributors may be used to endorse or promote products
 *     derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL SEBASTIAN SCHÄFER BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package de.l8systems.check_java;

import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            Options opts = new Options(args);

            List<AttributeIdentifier> attrs = new LinkedList<AttributeIdentifier>();
            String hdr = "UNKNOWN";
            switch (opts.getCheckType()) {
                case HeapMem:
                    attrs.add(new AttributeIdentifier("java.lang:type=Memory", "HeapMemoryUsage", "used", "B"));
                    attrs.add(new AttributeIdentifier("java.lang:type=Memory", "HeapMemoryUsage", "init", "B"));
                    attrs.add(new AttributeIdentifier("java.lang:type=Memory", "HeapMemoryUsage", "committed", "B"));
                    attrs.add(new AttributeIdentifier("java.lang:type=Memory", "HeapMemoryUsage", "max", "B"));
                    hdr = "Heap Memory Usage";
                    break;

                case NonHeapMem:
                    attrs.add(new AttributeIdentifier("java.lang:type=Memory", "NonHeapMemoryUsage", "used", "B"));
                    attrs.add(new AttributeIdentifier("java.lang:type=Memory", "NonHeapMemoryUsage", "init", "B"));
                    attrs.add(new AttributeIdentifier("java.lang:type=Memory", "NonHeapMemoryUsage", "committed", "B"));
                    attrs.add(new AttributeIdentifier("java.lang:type=Memory", "NonHeapMemoryUsage", "max", "B"));
                    hdr = "Non-Heap Memory Usage";
                    break;

                case MemPoolEden:
                    attrs.add(new AttributeIdentifier("java.lang:type=MemoryPool,name=PS Eden Space", "Usage", "used", "B"));
                    attrs.add(new AttributeIdentifier("java.lang:type=MemoryPool,name=PS Eden Space", "Usage", "init", "B"));
                    attrs.add(new AttributeIdentifier("java.lang:type=MemoryPool,name=PS Eden Space", "Usage", "committed", "B"));
                    attrs.add(new AttributeIdentifier("java.lang:type=MemoryPool,name=PS Eden Space", "Usage", "max", "B"));
                    hdr = "Memory Pool PS Eden Space Usage";
                    break;

                case MemPoolSurvivor:
                    attrs.add(new AttributeIdentifier("java.lang:type=MemoryPool,name=PS Survivor Space", "Usage", "used", "B"));
                    attrs.add(new AttributeIdentifier("java.lang:type=MemoryPool,name=PS Survivor Space", "Usage", "init", "B"));
                    attrs.add(new AttributeIdentifier("java.lang:type=MemoryPool,name=PS Survivor Space", "Usage", "committed", "B"));
                    attrs.add(new AttributeIdentifier("java.lang:type=MemoryPool,name=PS Survivor Space", "Usage", "max", "B"));
                    hdr = "Memory Pool PS Survivor Space Usage";
                    break;

                case MemPoolCodeCache:
                    attrs.add(new AttributeIdentifier("java.lang:type=MemoryPool,name=Code Cache", "Usage", "used", "B"));
                    attrs.add(new AttributeIdentifier("java.lang:type=MemoryPool,name=Code Cache", "Usage", "init", "B"));
                    attrs.add(new AttributeIdentifier("java.lang:type=MemoryPool,name=Code Cache", "Usage", "committed", "B"));
                    attrs.add(new AttributeIdentifier("java.lang:type=MemoryPool,name=Code Cache", "Usage", "max", "B"));
                    hdr = "Memory Pool Code Cache Usage";
                    break;

                case MemPoolOldGen:
                    attrs.add(new AttributeIdentifier("java.lang:type=MemoryPool,name=PS Old Gen", "Usage", "used", "B"));
                    attrs.add(new AttributeIdentifier("java.lang:type=MemoryPool,name=PS Old Gen", "Usage", "init", "B"));
                    attrs.add(new AttributeIdentifier("java.lang:type=MemoryPool,name=PS Old Gen", "Usage", "committed", "B"));
                    attrs.add(new AttributeIdentifier("java.lang:type=MemoryPool,name=PS Old Gen", "Usage", "max", "B"));
                    hdr = "Memory Pool PS Old Gen Usage";
                    break;

                case GCMarkSweepCount:
                    attrs.add(new AttributeIdentifier("java.lang:type=GarbageCollector,name=PS MarkSweep", "CollectionCount", ""));
                    hdr = "Garbage Collection Count";
                    break;

                case GCMarkSweepTime:
                    attrs.add(new AttributeIdentifier("java.lang:type=GarbageCollector,name=PS MarkSweep", "CollectionTime", "ms"));
                    hdr = "Garbage Collection Time";
                    break;

                case ClassLoading:
                    attrs.add(new AttributeIdentifier("java.lang:type=ClassLoading", "LoadedClassCount", ""));
                    attrs.add(new AttributeIdentifier("java.lang:type=ClassLoading", "TotalLoadedClassCount", ""));
                    attrs.add(new AttributeIdentifier("java.lang:type=ClassLoading", "UnloadedClassCount", ""));
                    hdr = "Loaded Classes";
                    break;

                case ThreadCount:
                    attrs.add(new AttributeIdentifier("java.lang:type=Threading", "ThreadCount", ""));
                    attrs.add(new AttributeIdentifier("java.lang:type=Threading", "TotalStartedThreadCount", ""));
                    attrs.add(new AttributeIdentifier("java.lang:type=Threading", "DaemonThreadCount", ""));
                    hdr = "Threads";
                    break;
            }

            List<Result> res = new LinkedList<Result>();
            JMXClient client = new JMXClient(opts.getUrl());

            for (AttributeIdentifier attr : attrs) {
                res.add(attr.getValue(client));
            }

            client.close();

            String status = "OK";
            int statval = 0;
            if (res.get(0).getValue() > opts.getWarnThreshold()) {
                status = "WARN";
                statval = 1;

                if (res.get(0).getValue() > opts.getCritThreshold()) {
                    status = "CRITICAL";
                    statval = 2;
                }
            }

            System.out.println(status + ": " + hdr + ": " + res.get(0).getValue() + " " + res.get(0).getUnit() + "/" + opts.getWarnThreshold() + "/" + opts.getCritThreshold());
            for(int i = 0; i < res.size(); i ++) {
                Result r = res.get(i);
                if(i == 0) {
                    System.out.println("'" + r.getName() + "'=" + r.getValue() + r.getUnit() + ";" + opts.getWarnThreshold() + ";" + opts.getCritThreshold());
                } else {
                    System.out.println("'" + r.getName() + "'=" + r.getValue() + r.getUnit());
                }
            }
            System.exit(statval);
        } catch (Exception e) {
            System.out.println("Could not retrieve data: " + e.getClass().getName() + ": " + e.getMessage());
            System.exit(3);
        }
    }
}
