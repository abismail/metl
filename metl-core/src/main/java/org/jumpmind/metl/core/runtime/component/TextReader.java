/**
 * Licensed to JumpMind Inc under one or more contributor
 * license agreements.  See the NOTICE file distributed
 * with this work for additional information regarding
 * copyright ownership.  JumpMind Inc licenses this file
 * to you under the GNU General Public License, version 3.0 (GPLv3)
 * (the "License"); you may not use this file except in compliance
 * with the License.
 *
 * You should have received a copy of the GNU General Public License,
 * version 3.0 (GPLv3) along with this library; if not, see
 * <http://www.gnu.org/licenses/>.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jumpmind.metl.core.runtime.component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.jumpmind.exception.IoException;
import org.jumpmind.metl.core.runtime.Message;
import org.jumpmind.metl.core.runtime.flow.ISendMessageCallback;

public class TextReader extends AbstractComponentRuntime {

    public static final String TYPE = "Text Reader";

    public static final String SETTING_ROWS_PER_MESSAGE = "rows.per.message";

    public static final String SETTING_TEXT = "text";

    @Override
    protected void start() {
    }

    @Override
    public void handle(Message inputMessage, ISendMessageCallback callback, boolean unitOfWorkBoundaryReached) {
        int linesInMessage = 0;
        int textRowsPerMessage = context.getFlowStep().getComponent().getInt(SETTING_ROWS_PER_MESSAGE, 1000);
        ArrayList<String> payload = new ArrayList<String>();

        BufferedReader reader = null;
        String currentLine;
        try {
            reader = new BufferedReader(new StringReader(context.getFlowStep().getComponent().get(SETTING_TEXT, "")));
            while ((currentLine = reader.readLine()) != null) {
                if (linesInMessage == textRowsPerMessage) {
                    callback.sendMessage(payload, false);
                    linesInMessage = 0;
                    payload = new ArrayList<String>();
                }
                getComponentStatistics().incrementNumberEntitiesProcessed();
                payload.add(currentLine);
                linesInMessage++;
            }
        } catch (IOException e) {
            throw new IoException("Error reading from file " + e.getMessage());
        } finally {
            IOUtils.closeQuietly(reader);
        }
        
        callback.sendMessage(payload, unitOfWorkBoundaryReached);
    }

}
