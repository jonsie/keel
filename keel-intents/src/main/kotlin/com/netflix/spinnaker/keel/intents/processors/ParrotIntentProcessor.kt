/*
 * Copyright 2017 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.netflix.spinnaker.keel.intents.processors

import com.netflix.spinnaker.keel.Intent
import com.netflix.spinnaker.keel.IntentProcessor
import com.netflix.spinnaker.keel.IntentSpec
import com.netflix.spinnaker.keel.intents.ParrotIntent
import com.netflix.spinnaker.keel.model.Job
import com.netflix.spinnaker.keel.model.OrchestrationRequest
import com.netflix.spinnaker.keel.tracing.Trace
import com.netflix.spinnaker.keel.tracing.TraceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.Clock

@Component
class ParrotIntentProcessor
@Autowired constructor(
  private val traceRepository: TraceRepository,
  private val clock: Clock
) : IntentProcessor<ParrotIntent> {

  override fun supports(intent: Intent<IntentSpec>) = intent is ParrotIntent

  override fun converge(intent: ParrotIntent): List<OrchestrationRequest> {
    traceRepository.record(Trace(mapOf(), intent, clock.millis()))
    return listOf(
      OrchestrationRequest(
        name = "Squawk!",
        application = intent.spec.application,
        description = intent.spec.description,
        job = listOf(
          Job("wait", mutableMapOf("waitTime" to 1))
        )
      )
    )
  }
}
