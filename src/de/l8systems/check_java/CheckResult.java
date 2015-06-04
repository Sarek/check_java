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

/**
 * Can convey the results of a performed check ready for output.
 */
public class CheckResult {
    public static enum code {
        OK,
        WARNING,
        CRITICAL,
        UNKNOWN
    }

    /**
     * Carries the result code of the check
     */
    private CheckResult.code resultCode;

    /**
     * The message to be displayed in Nagios
     */
    private String message;

    /**
     * Performance data to be sent to Nagios
     */
    private String perfData;

    /**
     * Set the result code that shall be sent to Nagios.
     * @param resultCode the result code
     */
    public void setResultCode(CheckResult.code resultCode) {
        this.resultCode = resultCode;
    }

    /**
     * Return the saved result code.
     * @return the result code
     */
    public CheckResult.code getResultCode() {
        return resultCode;
    }

    /**
     * Set the status message for display in Nagios.
     * @param message the status message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Get the previously saved status message for display in Nagios.
     * @return the status message
     */
    public String getMesssage() {

    }

    /**
     * Set the performance data string for sending to Nagios.
     * @param perfData the performance data string
     */
    public void setPerfData(String perfData) {
        this.perfData = perfData;
    }

    /**
     * Return the previously saved performance data string for sending to Nagios.
     * @return the performance data string
     */
    public String getPerfData() {
        return perfData;
    }
}
