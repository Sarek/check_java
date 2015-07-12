/*
 * Copyright (c) 2015, Sebastian Schäfer <me@sebastianschaefer.net>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *  * Neither the name of check_java nor the
 *    names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
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


import java.util.Iterator;

/**
 * Transports and parses command line argument information
 */
public class Options {
    public enum CheckType {
        HeapMem,
        NonHeapMem,
        MemPoolEden,
        MemPoolSurvivor,
        MemPoolCodeCache,
        GCMarkSweepCount,
        GCMarkSweepTime,
        ClassLoading,
        ThreadCount
    }

    public Options(String[] args) throws IllegalArgumentException {
        // parse command line
        ArrayIterator<String> it = new ArrayIterator<String>(args);

        while(it.hasNext()) {
            String arg = it.next();
            switch(arg) {
                case "-t":  if(it.hasNext()) {
                                String arg2 = it.next();
                                switch (arg2) {
                                    case "HeapMem":
                                        setCheckType(CheckType.HeapMem);
                                        break;
                                    case "NonHeapMem":
                                        setCheckType(CheckType.NonHeapMem);
                                        break;
                                    case "MemPoolEden":
                                        setCheckType(CheckType.MemPoolEden);
                                        break;
                                    case "MemPoolSurvivor":
                                        setCheckType(CheckType.MemPoolSurvivor);
                                        break;
                                    case "MemPoolCodeCache":
                                        setCheckType(CheckType.MemPoolCodeCache);
                                        break;
                                    case "GCMarkSweepCount":
                                        setCheckType(CheckType.GCMarkSweepCount);
                                        break;
                                    case "GCMarkSweepTime":
                                        setCheckType(CheckType.GCMarkSweepTime);
                                        break;
                                    case "ClassLoading":
                                        setCheckType(CheckType.ClassLoading);
                                        break;
                                    case "ThreadCount":
                                        setCheckType(CheckType.ThreadCount);
                                        break;
                                    default:
                                        throw new IllegalArgumentException("Unknown type " + arg2);
                                }
                            } else {
                                throw new IllegalArgumentException("-t needs to be followed a type");
                }
                            break;

                case "-w":  if(it.hasNext()) {
                                String arg2 = it.next();
                                setWarnThreshold(Integer.parseInt(arg2));
                            } else {
                                throw new IllegalArgumentException("-w needs to be followed by a number");
                            }
                            break;

                case "-c":  if(it.hasNext()) {
                                String arg2 = it.next();
                                setCritThreshold(Integer.parseInt(arg2));
                            } else {
                                throw new IllegalArgumentException("-c needs to be followed by a number");
                            }
                            break;
            }
        }
    }

    private CheckType checkType;
    private int warnThreshold;
    private int critThreshold;

    public CheckType getCheckType() {
        return checkType;
    }

    public void setCheckType(CheckType checkType) {
        this.checkType = checkType;
    }

    public int getWarnThreshold() {
        return warnThreshold;
    }

    public void setWarnThreshold(int warnThreshold) {
        this.warnThreshold = warnThreshold;
    }

    public int getCritThreshold() {
        return critThreshold;
    }

    public void setCritThreshold(int critThreshold) {
        this.critThreshold = critThreshold;
    }

    private class ArrayIterator<E> implements Iterator<E> {
        private E[] array;
        private int pos;

        public ArrayIterator(E[] array) {
            this.array = array;
            this.pos = 0;
        }

        @Override
        public boolean hasNext() {
            return pos < (array.length - 1);
        }

        @Override
        public E next() {
            if(pos < (array.length - 1)) {
                pos++;
                return array[pos];
            } else {
                return null;
            }
        }

        @Override
        public void remove() {
             // Not implemented
        }
    }
}
