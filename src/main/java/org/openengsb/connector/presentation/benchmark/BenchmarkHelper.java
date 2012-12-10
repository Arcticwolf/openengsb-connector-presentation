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

package org.openengsb.connector.presentation.benchmark;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openengsb.core.ekb.api.TransformationEngine;
import org.openengsb.connector.presentation.benchmark.BenchmarkType;
import org.openengsb.domain.presentation.model.ExternalRequirement;
import org.openengsb.domain.presentation.model.SimpleModelA;
import org.openengsb.domain.presentation.model.Worker;

public class BenchmarkHelper {
    private TransformationEngine transformationEngine;

    public BenchmarkHelper(TransformationEngine transformationEngine) {
        this.transformationEngine = transformationEngine;
    }

    public String performBenchmarkSuite() {
        StringBuilder builder = new StringBuilder();
        performMeasurement(BenchmarkType.SIMPLE, 1, 5, builder);
        performMeasurement(BenchmarkType.SIMPLE, 10, 5, builder);
        performMeasurement(BenchmarkType.SIMPLE, 100, 5, builder);
        performMeasurement(BenchmarkType.SIMPLE, 1000, 5, builder);

        performMeasurement(BenchmarkType.COMPLEX, 1, 5, builder);
        performMeasurement(BenchmarkType.COMPLEX, 10, 5, builder);
        performMeasurement(BenchmarkType.COMPLEX, 100, 5, builder);
        performMeasurement(BenchmarkType.COMPLEX, 1000, 5, builder);

        performMeasurement(BenchmarkType.SIMPLE_PATH, 1, 5, builder);
        performMeasurement(BenchmarkType.SIMPLE_PATH, 10, 5, builder);
        performMeasurement(BenchmarkType.SIMPLE_PATH, 100, 5, builder);
        performMeasurement(BenchmarkType.SIMPLE_PATH, 1000, 5, builder);

        performMeasurement(BenchmarkType.COMPLEX_PATH, 1, 5, builder);
        performMeasurement(BenchmarkType.COMPLEX_PATH, 10, 5, builder);
        performMeasurement(BenchmarkType.COMPLEX_PATH, 100, 5, builder);
        performMeasurement(BenchmarkType.COMPLEX_PATH, 1000, 5, builder);
        return builder.toString();
    }

    public String performBenchmark(BenchmarkType type, int count, int iterations) {
        StringBuilder builder = new StringBuilder();
        performMeasurement(type, count, iterations, builder);
        return builder.toString();
    }

    private String performMeasurement(BenchmarkType type, int count, int iterations) {
        long duration = 0;
        switch (type) {
            case SIMPLE:
                duration = performTransformation(
                    new SimpleBenchmarkAction(transformationEngine, createTestSimpleModelA()), count, iterations);
                break;
            case COMPLEX:
                duration = performTransformation(
                    new ComplexBenchmarkAction(transformationEngine, createTestExtRequirement()), count, iterations);
                break;
            case SIMPLE_PATH:
                duration = performTransformation(
                    new SimplePathBenchmarkAction(transformationEngine, createTestSimpleModelA()), count, iterations);
                break;
            case COMPLEX_PATH:
                duration =
                    performTransformation(
                        new ComplexPathBenchmarkAction(transformationEngine, createTestExtRequirement()), count,
                        iterations);
                break;
        }
        return "The operation lasted " + duration + " ms";
    }

    private void performMeasurement(BenchmarkType type, int count, int iterations,
            StringBuilder builder) {
        builder.append("perform ").append(count);
        switch (type) {
            case SIMPLE:
                builder.append(" simple");
                break;
            case COMPLEX:
                builder.append(" complex");
                break;
            case SIMPLE_PATH:
                builder.append(" simple path");
                break;
            case COMPLEX_PATH:
                builder.append(" complex path");
                break;
        }
        builder.append(" transformation(s) ").append(iterations).append(" time(s). \n");
        builder.append(performMeasurement(type, count, iterations)).append("\n");
    }

    private long performTransformation(BenchmarkAction action, int count, int iterations) {
        // initialize for more realistic benchmark results
        action.perform();
        List<Long> durations = new ArrayList<Long>(iterations);
        for (int i = 0; i < iterations; i++) {
            long before = System.currentTimeMillis();
            for (int j = 0; j < count; j++) {
                action.perform();
            }
            long duration = System.currentTimeMillis() - before;
            durations.add(duration);
        }
        long duration = 0;
        for (Long value : durations) {
            duration += value;
        }
        return duration / durations.size();
    }

    private SimpleModelA createTestSimpleModelA() {
        SimpleModelA simple = new SimpleModelA();
        simple.setNameA("name");
        simple.setNumberA("number");
        simple.setDescriptionA("description");
        simple.setCommentA1("comment1");
        simple.setCommentA2("comment2");
        return simple;
    }

    private ExternalRequirement createTestExtRequirement() {
        ExternalRequirement requirement = new ExternalRequirement();
        Worker worker = new Worker();
        worker.setEmail("email");
        worker.setName("name");
        worker.setNickname("nickname");
        requirement.setAssigned(worker);
        requirement.setChangedBy(worker);
        requirement.setChanged(new Date());
        requirement.setComments("comment");
        requirement.setInformalDescription("description");
        requirement.setInternal_id("externalRequirement-1");
        requirement.setName("name");
        requirement.setPriority("LOW");
        requirement.setSummary("summary");
        requirement.setTechnicalDescription("technical description");
        requirement.setTimeEstimation("20 hours");
        requirement.setType("type");
        return requirement;
    }
}
