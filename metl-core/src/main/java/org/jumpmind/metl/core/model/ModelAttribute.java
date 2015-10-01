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
package org.jumpmind.metl.core.model;



public class ModelAttribute extends AbstractObject {

    private static final long serialVersionUID = 1L;
    
    String entityId;    

    String name;

    DataType type;

    String typeEntityId;

    public ModelAttribute() {
    	
    }
    
    public ModelAttribute(String id, String entityId, String name) {
        this.id = id;
        setEntityId(entityId);
        this.name = name;
    }
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setEntityId(String entityId) {
        this.entityId = entityId;
    }
	
	public String getEntityId() {
        return entityId;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DataType getDataType() {
		return type;
	}

	public String getType() {
		return type.toString();
	}

	public void setType(String type) {
		this.type = DataType.valueOf(type);
	}

	public void setDataType(DataType type) {
		this.type = type;
	}

	public String getTypeEntityId() {
		return typeEntityId;
	}

	public void setTypeEntityId(String typeEntityId) {
		this.typeEntityId = typeEntityId;
	}

}
