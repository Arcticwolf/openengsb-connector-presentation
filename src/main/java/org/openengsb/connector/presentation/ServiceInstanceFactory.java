/**
 * Licensed to the Austrian Association for Software Tool Integration (AASTI)
 * under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright
 * ownership. The AASTI licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openengsb.connector.presentation;

import java.util.Map;

import org.openengsb.connector.presentation.benchmark.BenchmarkHelper;
import org.openengsb.connector.presentation.internal.PresentationService;
import org.openengsb.connector.presentation.story.AHyUC;
import org.openengsb.connector.presentation.story.TransformationUC;
import org.openengsb.core.api.Connector;
import org.openengsb.core.common.AbstractConnectorInstanceFactory;

public class ServiceInstanceFactory extends AbstractConnectorInstanceFactory<PresentationService> {

    private final BenchmarkHelper benchmark;
    private final AHyUC ahyUseCase;
    private final TransformationUC transformUseCase;
    
    public ServiceInstanceFactory(BenchmarkHelper benchmark, AHyUC ahyUseCase, TransformationUC transformUseCase) {
        this.benchmark = benchmark;
        this.ahyUseCase = ahyUseCase;
        this.transformUseCase = transformUseCase;
    }

    @Override
    public Connector createNewInstance(String id) {
        return new PresentationService(id, benchmark, ahyUseCase, transformUseCase);
    }

    @Override
    public PresentationService doApplyAttributes(PresentationService instance, Map<String, String> attributes) {
        return instance;
    }
}
