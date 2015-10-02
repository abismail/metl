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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;

import java.util.ArrayList;

import org.jumpmind.metl.core.runtime.EntityData;
import org.jumpmind.metl.core.runtime.Message;
import org.jumpmind.metl.core.runtime.StartupMessage;
import org.jumpmind.metl.core.runtime.component.helpers.PayloadTestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import javafx.beans.binding.When;

@RunWith(PowerMockRunner.class)
public class LookupTest extends AbstractComponentRuntimeTest<ArrayList<EntityData>> {

	@Test
	@Override
	public void testHandleStartupMessage() {
		setInputMessage(new StartupMessage());
		runHandle();
		assertHandle(0, 1, 0, 0);
	}

	@Test
	@Override
	public void testHandleEmptyPayload() {
		setupHandle();
		runHandle();
		assertHandle(0, 1, 0, 0);
	}

	@Test
	@Override
	public void testHandleUnitOfWorkInputMessage() {
		setupHandle();
		
		getInputMessage().setPayload(new ArrayList<EntityData>());
		//((Lookup) spy).unitOfWork = AbstractComponentRuntime.UNIT_OF_WORK_INPUT_MESSAGE;
		assertEquals("Unit of work not implemented for lookup", 1,2);
		
		runHandle();
		assertHandle(1, 1, 1, 0, true);
	}

	@Test
	@Override
	public void testHandleUnitOfWorkFlow() {
		setupHandle();
		
		getInputMessage().setPayload(new ArrayList<EntityData>());
		//((Lookup) spy).unitOfWork = AbstractComponentRuntime.UNIT_OF_WORK_FLOW;
		setUnitOfWorkLastMessage(true);
		assertEquals("Unit of work not implemented for lookup", 1,2);
		
		runHandle();
		assertHandle(1, 1, 1, 0, true);
	}

	@Test
	@Override
	public void testHandleNormal() {
		setupHandle();
		((Lookup) spy).sourceStepId = "step1";
		
		Message message1 = getInputMessage();
		message1.setPayload(PayloadTestHelper.createPayloadWithMultipleEntityData());
		message1.getHeader().setOriginatingStepId("step1");
		
		Message message2 = new Message("message2");
		message2.setPayload(PayloadTestHelper.createPayloadWithMultipleEntityData());
		message2.getHeader().setOriginatingStepId("step2");
		
		Message message3 = new Message("message3");
		message3.setPayload(PayloadTestHelper.createPayloadWithMultipleEntityData());
		message3.getHeader().setUnitOfWorkLastMessage(true);
		message3.getHeader().setOriginatingStepId("step1");
		
		
		messages.add(new HandleParams(message2));
		messages.add(new HandleParams(message3, true));
		
		//((Lookup) spy).unitOfWork = AbstractComponentRuntime.UNIT_OF_WORK_FLOW;
		
		
		((Lookup) spy).keyAttributeId = MODEL_ATTR_ID_1;
		((Lookup) spy).valueAttributeId = MODEL_ATTR_ID_2;
		((Lookup) spy).replacementKeyAttributeId = MODEL_ATTR_ID_1;
		((Lookup) spy).replacementValueAttributeId = MODEL_ATTR_ID_3;
		
		
		assertHandle(1, 1, 1, 1, false);
		assertFalse(((Lookup) spy).lookupInitialized);
		assertEquals(1, ((Lookup) spy).lookup.size());
		assertTrue(((Lookup) spy).lookup.containsKey(MODEL_ATTR_ID_1));
	}

	@Override
	public IComponentRuntime getComponentSpy() {
		return spy(new Lookup());
	}

	
}