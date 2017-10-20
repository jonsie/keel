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
import org.springframework.stereotype.Component

@Component
class ParrotIntentProcessor : IntentProcessor<ParrotIntent> {

  override fun supports(intent: Intent<IntentSpec>) = intent is ParrotIntent

  override fun converge(intent: ParrotIntent) = listOf(
    OrchestrationRequest(
      application = intent.spec.application,
      description = intent.spec.description,
      job = listOf(
        Job("wait", mutableMapOf("waitTime" to 1))
      )
    )
  )
}
